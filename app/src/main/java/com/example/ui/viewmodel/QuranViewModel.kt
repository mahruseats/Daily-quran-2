package com.example.ui.viewmodel

import android.app.Application
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.audio.QuranAudioPlayer
import com.example.data.local.AppDatabase
import com.example.data.local.BookmarkEntity
import com.example.data.local.LastReadEntity
import com.example.data.model.Ayah
import com.example.data.model.JuzInfo
import com.example.data.model.PrayerTimesDay
import com.example.data.model.RabbanaDua
import com.example.data.model.Surah
import com.example.data.repository.PrayerCalculator
import com.example.data.source.JuzCatalog
import com.example.data.source.QuranTextProvider
import com.example.data.source.RabbanaDuasData
import com.example.data.source.SurahCatalog
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

enum class HomeTab {
    SURAHS, JUZ, PRAYER_TIMES, RABBANA_DUAS, BOOKMARKS
}

enum class TranslationDisplayMode {
    BOTH, BANGLA_ONLY, ENGLISH_ONLY
}

data class Reciter(
    val id: String,
    val nameEnglish: String,
    val nameBangla: String,
    val subtext: String,
    val baseUrl: String
)

val AvailableReciters = listOf(
    Reciter("alafasy", "Mishary Rashid Alafasy", "মিশারী রাশিদ আল-আফাসী", "কুয়েত • বিশ্বখ্যাত সুমধুর কণ্ঠ", "https://everyayah.com/data/Alafasy_128kbps/"),
    Reciter("abdulbasit", "Abdul Basit (Murattal)", "আব্দুল বাসেত আব্দুস সামাদ", "মিশর • সোনালী যুগের বিখ্যাত ক্বারী", "https://everyayah.com/data/Abdul_Basit_Murattal_192kbps/"),
    Reciter("maher", "Maher Al-Muaiqly", "মাহের আল-মুয়াইক্বলী", "মক্কা মুকাররমা • মসজিদুল হারামের ইমাম", "https://everyayah.com/data/Maher_AlMuaiqly_64kbps/"),
    Reciter("ghamdi", "Sa'ad Al-Ghamdi", "সা'দ আল-গামদী", "সৌদি আরব • হৃদয়গ্রাহী তিলাওয়াত", "https://everyayah.com/data/Ghamadi_40kbps/"),
    Reciter("husary", "Mahmoud Khalil Al-Husary", "মাহমুদ খলিল আল-হুসারী", "মিশর • নিখুঁত তাজবীদ শিক্ষক", "https://everyayah.com/data/Husary_128kbps/")
)

enum class QuranBgColor(
    val id: String,
    val labelBangla: String,
    val labelEnglish: String,
    val color: Color
) {
    DEFAULT("default", "স্বাভাবিক (Default)", "System Default", Color.Transparent),
    IVORY("ivory", "মুসহাফ আইভরি (Ivory)", "Ivory Parchment", Color(0xFFFAF7EE)),
    PURE_WHITE("white", "স্বচ্ছ সাদা (White)", "Crisp White", Color(0xFFFFFFFF)),
    MINT_GREEN("mint", "পুদিনা সবুজ (Mint)", "Soft Mint Green", Color(0xFFEBF6F0)),
    WARM_SEPIA("sepia", "উষ্ণ সেপিয়া (Sepia)", "Warm Sepia", Color(0xFFFBF1DE)),
    NIGHT_EMERALD("night_emerald", "গাঢ় পান্না (Emerald)", "Deep Emerald Dark", Color(0xFF0D1E17))
}

enum class QuranTextColor(
    val id: String,
    val labelBangla: String,
    val labelEnglish: String,
    val color: Color
) {
    DEFAULT("default", "স্বাভাবিক (Default)", "System Default", Color.Unspecified),
    GOLDEN("golden", "উজ্জ্বল সোনালী (Golden)", "Lustrous Golden", Color(0xFFD4AF37)),
    CHARCOAL("charcoal", "কালো (Charcoal)", "Classic Charcoal", Color(0xFF1B241F)),
    ROYAL_EMERALD("emerald", "পান্না সবুজ (Emerald)", "Royal Emerald", Color(0xFF09502D)),
    QURANIC_GOLD_BROWN("gold_brown", "সোনালী বাদামী (Bronze)", "Quranic Bronze", Color(0xFF6B4513)),
    NAVY_BLUE("navy", "গাঢ় নীল (Navy)", "Deep Indigo", Color(0xFF152A3E))
}

data class ReaderSettings(
    val showWordByWord: Boolean = true,
    val showEnglishTranslation: Boolean = true,
    val showBanglaTranslation: Boolean = true,
    val showBanglaTransliteration: Boolean = true,
    val arabicFontSizeSp: Float = 22f,
    val translationFontSizeSp: Float = 14f,
    val selectedReciter: Reciter = AvailableReciters[0],
    val selectedBgColor: QuranBgColor = QuranBgColor.DEFAULT,
    val selectedTextColor: QuranTextColor = QuranTextColor.DEFAULT
)

class QuranViewModel(application: Application) : AndroidViewModel(application) {

    private val db = AppDatabase.getDatabase(application)
    private val bookmarkDao = db.bookmarkDao()
    val audioPlayer = QuranAudioPlayer(application)

    // Day / Night Mode (false = Day mode, true = Night mode)
    private val _isNightMode = MutableStateFlow(false)
    val isNightMode: StateFlow<Boolean> = _isNightMode.asStateFlow()

    // Upper-side Bangla / English Language Toggle
    private val _translationLanguage = MutableStateFlow(TranslationDisplayMode.BOTH)
    val translationLanguage: StateFlow<TranslationDisplayMode> = _translationLanguage.asStateFlow()

    // Navigation & Tabs
    private val _currentTab = MutableStateFlow(HomeTab.SURAHS)
    val currentTab: StateFlow<HomeTab> = _currentTab.asStateFlow()

    // Search query & Surahs
    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _surahsList = MutableStateFlow(SurahCatalog.surahs)
    val surahsList: StateFlow<List<Surah>> = _surahsList.asStateFlow()

    // Currently reading Surah
    private val _currentReadingSurah = MutableStateFlow<Surah?>(null)
    val currentReadingSurah: StateFlow<Surah?> = _currentReadingSurah.asStateFlow()

    private val _currentAyahs = MutableStateFlow<List<Ayah>>(emptyList())
    val currentAyahs: StateFlow<List<Ayah>> = _currentAyahs.asStateFlow()

    // Reader Settings
    private val _readerSettings = MutableStateFlow(ReaderSettings())
    val readerSettings: StateFlow<ReaderSettings> = _readerSettings.asStateFlow()

    // Full Screen Settings Screen
    private val _isShowingSettings = MutableStateFlow(false)
    val isShowingSettings: StateFlow<Boolean> = _isShowingSettings.asStateFlow()

    fun openSettings() {
        _isShowingSettings.value = true
    }

    fun closeSettings() {
        _isShowingSettings.value = false
    }

    // Tafsir modal
    private val _selectedAyahForTafsir = MutableStateFlow<Ayah?>(null)
    val selectedAyahForTafsir: StateFlow<Ayah?> = _selectedAyahForTafsir.asStateFlow()

    // Prayer Times
    private val _selectedCity = MutableStateFlow(PrayerCalculator.supportedCities[0])
    val selectedCity: StateFlow<PrayerCalculator.City> = _selectedCity.asStateFlow()

    private val _prayerTimes = MutableStateFlow(PrayerCalculator.calculatePrayerTimes(PrayerCalculator.supportedCities[0]))
    val prayerTimes: StateFlow<PrayerTimesDay> = _prayerTimes.asStateFlow()

    init {
        viewModelScope.launch {
            while (isActive) {
                _prayerTimes.value = PrayerCalculator.calculatePrayerTimes(_selectedCity.value)
                delay(15_000) // Keep prayer countdown and next prayer updated
            }
        }
    }

    fun refreshPrayerTimes() {
        _prayerTimes.value = PrayerCalculator.calculatePrayerTimes(_selectedCity.value)
    }

    // Bookmarks and Last Read from Room Database
    val bookmarks: StateFlow<List<BookmarkEntity>> = bookmarkDao.getAllBookmarks()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val lastRead: StateFlow<LastReadEntity?> = bookmarkDao.getLastRead()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    // Download state for current surah
    private val _downloadProgress = MutableStateFlow<Int?>(null)
    val downloadProgress: StateFlow<Int?> = _downloadProgress.asStateFlow()

    fun toggleDayNight() {
        _isNightMode.value = !_isNightMode.value
    }

    fun setNightMode(night: Boolean) {
        _isNightMode.value = night
    }

    fun setTranslationLanguage(mode: TranslationDisplayMode) {
        _translationLanguage.value = mode
        _readerSettings.value = _readerSettings.value.copy(
            showBanglaTranslation = (mode == TranslationDisplayMode.BOTH || mode == TranslationDisplayMode.BANGLA_ONLY),
            showEnglishTranslation = (mode == TranslationDisplayMode.BOTH || mode == TranslationDisplayMode.ENGLISH_ONLY)
        )
    }

    fun setTab(tab: HomeTab) {
        _currentTab.value = tab
    }

    fun setSearchQuery(query: String) {
        _searchQuery.value = query
        _surahsList.value = SurahCatalog.searchSurahs(query)
    }

    fun openSurah(surah: Surah, targetAyahNumber: Int = 1) {
        _currentReadingSurah.value = surah
        _currentAyahs.value = QuranTextProvider.getAyahsForSurah(surah.number)
        saveLastRead(surah.number, surah.nameEnglish, surah.nameBangla, targetAyahNumber)
    }

    fun openJuz(juz: JuzInfo) {
        val surah = SurahCatalog.getSurah(juz.startSurahNumber) ?: SurahCatalog.surahs[0]
        openSurah(surah, juz.startAyah)
    }

    fun closeSurahReader() {
        _currentReadingSurah.value = null
        _currentAyahs.value = emptyList()
        _downloadProgress.value = null
    }

    fun openTafsir(ayah: Ayah) {
        _selectedAyahForTafsir.value = ayah
    }

    fun closeTafsir() {
        _selectedAyahForTafsir.value = null
    }

    fun toggleBookmark(ayah: Ayah, surah: Surah) {
        viewModelScope.launch {
            val isAlready = bookmarks.value.any { it.surahNumber == surah.number && it.ayahNumber == ayah.number }
            if (isAlready) {
                bookmarkDao.deleteBookmark(surah.number, ayah.number)
            } else {
                bookmarkDao.insertBookmark(
                    BookmarkEntity(
                        surahNumber = surah.number,
                        surahNameEnglish = surah.nameEnglish,
                        surahNameBangla = surah.nameBangla,
                        ayahNumber = ayah.number,
                        textArabic = ayah.textArabic,
                        translationEnglish = ayah.translationEnglish,
                        translationBangla = ayah.translationBangla
                    )
                )
            }
        }
    }

    fun isAyahBookmarked(surahNumber: Int, ayahNumber: Int): Boolean {
        return bookmarks.value.any { it.surahNumber == surahNumber && it.ayahNumber == ayahNumber }
    }

    fun removeBookmark(surahNumber: Int, ayahNumber: Int) {
        viewModelScope.launch {
            bookmarkDao.deleteBookmark(surahNumber, ayahNumber)
        }
    }

    fun saveLastRead(surahNumber: Int, surahNameEn: String, surahNameBn: String, ayahNumber: Int) {
        viewModelScope.launch {
            bookmarkDao.saveLastRead(
                LastReadEntity(
                    id = 1,
                    surahNumber = surahNumber,
                    surahNameEnglish = surahNameEn,
                    surahNameBangla = surahNameBn,
                    ayahNumber = ayahNumber
                )
            )
        }
    }

    fun resumeLastRead() {
        val last = lastRead.value ?: return
        val surah = SurahCatalog.getSurah(last.surahNumber) ?: return
        openSurah(surah, last.ayahNumber)
    }

    fun playAyahAudio(ayah: Ayah, surah: Surah) {
        val surahsAyahs = _currentAyahs.value
        val currentIndex = surahsAyahs.indexOfFirst { it.number == ayah.number }

        val reciter = _readerSettings.value.selectedReciter
        val formattedSurah = String.format("%03d", surah.number)
        val formattedAyah = String.format("%03d", ayah.number)
        val dynamicAudioUrl = "${reciter.baseUrl}$formattedSurah$formattedAyah.mp3"

        audioPlayer.playAyah(
            surahNumber = surah.number,
            surahName = "${surah.nameEnglish} • ${reciter.nameBangla}",
            ayahNumber = ayah.number,
            audioUrl = dynamicAudioUrl,
            onAyahCompleted = {
                if (currentIndex >= 0 && currentIndex < surahsAyahs.size - 1) {
                    val nextAyah = surahsAyahs[currentIndex + 1]
                    playAyahAudio(nextAyah, surah)
                }
            }
        )
    }

    fun playRabbanaDuaAudio(dua: RabbanaDua) {
        audioPlayer.playAyah(
            surahNumber = 0,
            surahName = "Rabbana Dua #${dua.id} (${dua.surahNameBangla})",
            ayahNumber = dua.id,
            audioUrl = dua.audioUrl
        )
    }

    fun downloadCurrentSurahOffline() {
        val surah = _currentReadingSurah.value ?: return
        val ayahs = _currentAyahs.value
        if (ayahs.isEmpty()) return

        val reciter = _readerSettings.value.selectedReciter
        val formattedSurah = String.format("%03d", surah.number)
        val formattedAyah = String.format("%03d", ayahs[0].number)
        val sampleUrl = "${reciter.baseUrl}$formattedSurah$formattedAyah.mp3"

        _downloadProgress.value = 0

        audioPlayer.downloadSurahForOffline(
            surahNumber = surah.number,
            sampleAudioUrl = sampleUrl,
            onProgress = { progress ->
                _downloadProgress.value = progress
            },
            onComplete = { success ->
                _downloadProgress.value = null
            }
        )
    }

    fun updateReaderSettings(update: (ReaderSettings) -> ReaderSettings) {
        _readerSettings.value = update(_readerSettings.value)
    }

    fun selectReciter(reciter: Reciter) {
        _readerSettings.value = _readerSettings.value.copy(selectedReciter = reciter)
    }

    fun selectBgColor(bgColor: QuranBgColor) {
        _readerSettings.value = _readerSettings.value.copy(selectedBgColor = bgColor)
    }

    fun selectTextColor(textColor: QuranTextColor) {
        _readerSettings.value = _readerSettings.value.copy(selectedTextColor = textColor)
    }

    fun selectCity(city: PrayerCalculator.City) {
        _selectedCity.value = city
        _prayerTimes.value = PrayerCalculator.calculatePrayerTimes(city)
    }

    override fun onCleared() {
        super.onCleared()
        audioPlayer.release()
    }
}
