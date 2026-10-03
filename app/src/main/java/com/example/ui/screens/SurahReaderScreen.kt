package com.example.ui.screens

import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.DownloadDone
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ads.AdBannerView
import com.example.ads.AdMobManager
import com.example.data.model.Surah
import com.example.ui.components.BottomAudioPlayerBar
import com.example.ui.components.TafsirBottomSheet
import com.example.ui.components.WordByWordCard
import com.example.ui.theme.ArabicAccentGold
import com.example.ui.viewmodel.QuranViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SurahReaderScreen(
    surah: Surah,
    viewModel: QuranViewModel,
    onBack: () -> Unit
) {
    val context = LocalContext.current
    val activity = context as? Activity
    val ayahs by viewModel.currentAyahs.collectAsState()
    val settings by viewModel.readerSettings.collectAsState()
    val audioState by viewModel.audioPlayer.state.collectAsState()
    val selectedAyahForTafsir by viewModel.selectedAyahForTafsir.collectAsState()
    val isNightMode by viewModel.isNightMode.collectAsState()
    val translationLanguage by viewModel.translationLanguage.collectAsState()
    val downloadProgress by viewModel.downloadProgress.collectAsState()
    val isDownloaded = viewModel.audioPlayer.isSurahDownloaded(surah.number)

    val listState = rememberLazyListState()

    // Handle exit with Interstitial Ad requirement:
    // "admob ads(interstitial:ca-app-pub-3940256099942544/1033173712)will pop everytime the user exits a surah or rabbana duas"
    fun handleExit() {
        if (activity != null) {
            AdMobManager.showInterstitial(activity) {
                viewModel.closeSurahReader()
                onBack()
            }
        } else {
            viewModel.closeSurahReader()
            onBack()
        }
    }

    BackHandler {
        handleExit()
    }

    Scaffold(
        topBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.surface)
            ) {
                TopAppBar(
                    title = {
                        Column {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    text = surah.nameEnglish,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 16.sp
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = "(${surah.nameBangla})",
                                    fontSize = 13.sp,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                            Text(
                                text = "${surah.nameArabic} • ${surah.totalAyahs} আয়াত • পারা ${surah.startJuz}",
                                fontSize = 11.sp,
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                    },
                    navigationIcon = {
                        IconButton(onClick = { handleExit() }) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Back"
                            )
                        }
                    },
                    actions = {
                        // Day / Night Switch Toggle
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(end = 4.dp)
                        ) {
                            Icon(
                                imageVector = if (isNightMode) Icons.Default.DarkMode else Icons.Default.LightMode,
                                contentDescription = if (isNightMode) "Night Mode Active" else "Day Mode Active",
                                tint = if (isNightMode) ArabicAccentGold else MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(17.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Switch(
                                checked = isNightMode,
                                onCheckedChange = { viewModel.setNightMode(it) },
                                thumbContent = {
                                    Icon(
                                        imageVector = if (isNightMode) Icons.Default.DarkMode else Icons.Default.LightMode,
                                        contentDescription = null,
                                        modifier = Modifier.size(12.dp)
                                    )
                                },
                                colors = SwitchDefaults.colors(
                                    checkedThumbColor = ArabicAccentGold,
                                    checkedTrackColor = MaterialTheme.colorScheme.primaryContainer,
                                    uncheckedThumbColor = MaterialTheme.colorScheme.primary,
                                    uncheckedTrackColor = MaterialTheme.colorScheme.surfaceVariant
                                )
                            )
                        }

                        // Offline download button
                        if (downloadProgress != null) {
                            Box(contentAlignment = Alignment.Center, modifier = Modifier.padding(end = 4.dp)) {
                                CircularProgressIndicator(
                                    progress = { (downloadProgress ?: 0) / 100f },
                                    modifier = Modifier.size(22.dp),
                                    strokeWidth = 2.dp
                                )
                            }
                        } else if (isDownloaded) {
                            IconButton(onClick = {}) {
                                Icon(
                                    imageVector = Icons.Default.DownloadDone,
                                    contentDescription = "Surah audio downloaded",
                                    tint = MaterialTheme.colorScheme.primary
                                )
                            }
                        } else {
                            IconButton(onClick = { viewModel.downloadCurrentSurahOffline() }) {
                                Icon(
                                    imageVector = Icons.Default.Download,
                                    contentDescription = "Download surah audio offline",
                                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }

                        // Settings Screen button
                        IconButton(onClick = { viewModel.openSettings() }) {
                            Icon(
                                imageVector = Icons.Default.Settings,
                                contentDescription = "Reader Settings"
                            )
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.surface
                    )
                )

                // UPPER SIDE LANGUAGE TOGGLE (Bangla / English / Both)
                UpperSideLanguageToggle(
                    currentMode = translationLanguage,
                    onSelectMode = { viewModel.setTranslationLanguage(it) }
                )
            }
        },
        bottomBar = {
            Column {
                // Bottom Audio Player when playing
                BottomAudioPlayerBar(
                    state = audioState,
                    onTogglePlayPause = { viewModel.audioPlayer.togglePlayPause() },
                    onSeekTo = { viewModel.audioPlayer.seekTo(it) },
                    onChangeSpeed = { viewModel.audioPlayer.setPlaybackSpeed(it) },
                    onClose = { viewModel.audioPlayer.release() }
                )

                // AdMob Banner Ad at the bottom when reading surah
                AdBannerView()
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            LazyColumn(
                state = listState,
                modifier = Modifier.fillMaxSize()
            ) {
                // Surah Header card
                item {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 14.dp, vertical = 8.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.4f)
                        ),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = surah.nameArabic,
                                fontSize = 28.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "${surah.nameEnglish} • ${surah.englishMeaning}",
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 14.sp
                            )
                            Text(
                                text = "বাংলা অর্থ: ${surah.banglaMeaning}",
                                fontSize = 13.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Spacer(modifier = Modifier.height(10.dp))

                            // Play full surah audio chip
                            Surface(
                                shape = RoundedCornerShape(20.dp),
                                color = MaterialTheme.colorScheme.primary,
                                modifier = Modifier
                                    .clickable {
                                        if (ayahs.isNotEmpty()) {
                                            viewModel.playAyahAudio(ayahs[0], surah)
                                        }
                                    }
                                    .padding(top = 4.dp)
                            ) {
                                Row(
                                    modifier = Modifier
                                        .padding(horizontal = 14.dp, vertical = 6.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.PlayArrow,
                                        contentDescription = "Play Surah",
                                        tint = MaterialTheme.colorScheme.onPrimary,
                                        modifier = Modifier.size(18.dp)
                                    )
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text(
                                        text = "সম্পূর্ণ সূরা তিলাওয়াত শুনুন",
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.onPrimary
                                    )
                                }
                            }
                        }
                    }
                }

                // Bismillah banner (Surah 1-114 except Surah 9 At-Tawbah)
                if (surah.number != 1 && surah.number != 9) {
                    item {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "بِسْمِ اللَّهِ الرَّحْمَٰنِ الرَّحِيمِ",
                                fontSize = 22.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = MaterialTheme.colorScheme.primary,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }

                // Ayahs with Word-by-Word, full sentence translations and Tafsir button
                items(ayahs, key = { it.number }) { ayah ->
                    val isPlaying = audioState.isPlaying && audioState.currentSurahNumber == surah.number && audioState.currentAyahNumber == ayah.number
                    val isBookmarked = viewModel.isAyahBookmarked(surah.number, ayah.number)

                    WordByWordCard(
                        ayah = ayah,
                        surah = surah,
                        settings = settings,
                        translationLanguage = translationLanguage,
                        isCurrentPlaying = isPlaying,
                        isBookmarked = isBookmarked,
                        onPlayAyah = { viewModel.playAyahAudio(ayah, surah) },
                        onToggleBookmark = { viewModel.toggleBookmark(ayah, surah) },
                        onOpenTafsir = { viewModel.openTafsir(ayah) }
                    )
                }

                item {
                    Spacer(modifier = Modifier.height(60.dp))
                }
            }
        }
    }

    // Multilingual Tafsir Modal
    selectedAyahForTafsir?.let { ayah ->
        TafsirBottomSheet(
            ayah = ayah,
            surah = surah,
            onDismiss = { viewModel.closeTafsir() }
        )
    }
}
