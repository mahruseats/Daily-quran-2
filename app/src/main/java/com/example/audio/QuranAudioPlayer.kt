package com.example.audio

import android.content.Context
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.media.PlaybackParams
import android.os.Build
import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.net.HttpURLConnection
import java.net.URL

data class AudioPlayerState(
    val isPlaying: Boolean = false,
    val isLoading: Boolean = false,
    val currentSurahNumber: Int = 0,
    val currentSurahName: String = "",
    val currentAyahNumber: Int = 0,
    val currentPositionMs: Int = 0,
    val totalDurationMs: Int = 0,
    val playbackSpeed: Float = 1.0f,
    val isOfflineMode: Boolean = false,
    val downloadedSurahs: Set<Int> = emptySet(),
    val errorMessage: String? = null
)

class QuranAudioPlayer(private val context: Context) {
    private var mediaPlayer: MediaPlayer? = null
    private val scope = CoroutineScope(Dispatchers.Main + Job())
    private var progressJob: Job? = null

    private val _state = MutableStateFlow(AudioPlayerState())
    val state: StateFlow<AudioPlayerState> = _state.asStateFlow()

    init {
        scanDownloadedSurahs()
    }

    private fun getAudioDir(): File {
        val dir = File(context.filesDir, "quran_audio")
        if (!dir.exists()) dir.mkdirs()
        return dir
    }

    fun scanDownloadedSurahs() {
        val audioDir = getAudioDir()
        val files = audioDir.listFiles() ?: emptyArray()
        val downloaded = files
            .filter { it.name.startsWith("surah_") && it.name.endsWith(".mp3") && it.length() > 1000 }
            .mapNotNull { it.name.substringAfter("surah_").substringBefore(".").toIntOrNull() }
            .toSet()

        _state.value = _state.value.copy(downloadedSurahs = downloaded)
    }

    fun isSurahDownloaded(surahNumber: Int): Boolean {
        return _state.value.downloadedSurahs.contains(surahNumber)
    }

    fun playAyah(
        surahNumber: Int,
        surahName: String,
        ayahNumber: Int,
        audioUrl: String,
        onAyahCompleted: (() -> Unit)? = null
    ) {
        scope.launch {
            try {
                _state.value = _state.value.copy(
                    isLoading = true,
                    currentSurahNumber = surahNumber,
                    currentSurahName = surahName,
                    currentAyahNumber = ayahNumber,
                    errorMessage = null
                )

                mediaPlayer?.stop()
                mediaPlayer?.release()
                mediaPlayer = null

                val localFile = File(getAudioDir(), "ayah_${surahNumber}_${ayahNumber}.mp3")
                val isLocal = localFile.exists() && localFile.length() > 0

                val player = MediaPlayer().apply {
                    setAudioAttributes(
                        AudioAttributes.Builder()
                            .setContentType(AudioAttributes.CONTENT_TYPE_SPEECH)
                            .setUsage(AudioAttributes.USAGE_MEDIA)
                            .build()
                    )

                    if (isLocal) {
                        setDataSource(localFile.absolutePath)
                    } else {
                        setDataSource(audioUrl)
                    }

                    setOnPreparedListener { mp ->
                        _state.value = _state.value.copy(
                            isLoading = false,
                            isPlaying = true,
                            totalDurationMs = mp.duration,
                            isOfflineMode = isLocal
                        )
                        applyPlaybackSpeed(_state.value.playbackSpeed)
                        mp.start()
                        startProgressTracking()
                    }

                    setOnCompletionListener {
                        _state.value = _state.value.copy(isPlaying = false, currentPositionMs = 0)
                        stopProgressTracking()
                        onAyahCompleted?.invoke()
                    }

                    setOnErrorListener { _, what, extra ->
                        _state.value = _state.value.copy(
                            isLoading = false,
                            isPlaying = false,
                            errorMessage = "Audio playback error ($what, $extra)"
                        )
                        true
                    }
                }

                mediaPlayer = player
                player.prepareAsync()
            } catch (e: Exception) {
                Log.e("QuranAudioPlayer", "Error playing audio", e)
                _state.value = _state.value.copy(
                    isLoading = false,
                    isPlaying = false,
                    errorMessage = e.localizedMessage
                )
            }
        }
    }

    fun togglePlayPause() {
        val player = mediaPlayer ?: return
        if (player.isPlaying) {
            player.pause()
            _state.value = _state.value.copy(isPlaying = false)
            stopProgressTracking()
        } else {
            player.start()
            _state.value = _state.value.copy(isPlaying = true)
            startProgressTracking()
        }
    }

    fun seekTo(positionMs: Int) {
        mediaPlayer?.seekTo(positionMs)
        _state.value = _state.value.copy(currentPositionMs = positionMs)
    }

    fun setPlaybackSpeed(speed: Float) {
        _state.value = _state.value.copy(playbackSpeed = speed)
        applyPlaybackSpeed(speed)
    }

    private fun applyPlaybackSpeed(speed: Float) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            mediaPlayer?.let { player ->
                try {
                    player.playbackParams = PlaybackParams().apply { this.speed = speed }
                } catch (e: Exception) {
                    Log.w("QuranAudioPlayer", "Could not set speed: ${e.message}")
                }
            }
        }
    }

    private fun startProgressTracking() {
        stopProgressTracking()
        progressJob = scope.launch {
            while (isActive) {
                mediaPlayer?.let { player ->
                    if (player.isPlaying) {
                        _state.value = _state.value.copy(
                            currentPositionMs = player.currentPosition,
                            totalDurationMs = player.duration
                        )
                    }
                }
                delay(200)
            }
        }
    }

    private fun stopProgressTracking() {
        progressJob?.cancel()
        progressJob = null
    }

    fun downloadSurahForOffline(
        surahNumber: Int,
        sampleAudioUrl: String,
        onProgress: (Int) -> Unit,
        onComplete: (Boolean) -> Unit
    ) {
        scope.launch(Dispatchers.IO) {
            try {
                val targetFile = File(getAudioDir(), "surah_${surahNumber}.mp3")
                val url = URL(sampleAudioUrl)
                val connection = (url.openConnection() as HttpURLConnection).apply {
                    connectTimeout = 8000
                    readTimeout = 15000
                    requestMethod = "GET"
                }

                connection.connect()
                val totalLength = connection.contentLength

                val inputStream = connection.inputStream
                val outputStream = FileOutputStream(targetFile)

                val buffer = ByteArray(4096)
                var bytesRead: Int
                var downloaded = 0

                while (inputStream.read(buffer).also { bytesRead = it } != -1) {
                    outputStream.write(buffer, 0, bytesRead)
                    downloaded += bytesRead
                    if (totalLength > 0) {
                        val progress = (downloaded * 100) / totalLength
                        withContext(Dispatchers.Main) { onProgress(progress) }
                    }
                }

                outputStream.flush()
                outputStream.close()
                inputStream.close()

                withContext(Dispatchers.Main) {
                    scanDownloadedSurahs()
                    onComplete(true)
                }
            } catch (e: Exception) {
                Log.e("QuranAudioPlayer", "Failed to download offline audio", e)
                withContext(Dispatchers.Main) {
                    onComplete(false)
                }
            }
        }
    }

    fun release() {
        stopProgressTracking()
        mediaPlayer?.release()
        mediaPlayer = null
    }
}
