package com.example.ui.screens

import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ads.AdBannerView
import com.example.ads.AdMobManager
import com.example.data.model.Surah
import com.example.ui.components.BottomAudioPlayerBar
import com.example.ui.components.SurahReadingThemeCardsRow
import com.example.ui.components.SurahReadingThemeMiniBar
import com.example.ui.components.TafsirBottomSheet
import com.example.ui.components.WordByWordCard
import com.example.ui.theme.ArabicAccentGold
import com.example.ui.viewmodel.QuranViewModel
import com.example.ui.viewmodel.ReadingTheme
import com.example.ui.viewmodel.TranslationDisplayMode

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
    val readingTheme by viewModel.readingTheme.collectAsState()
    val isDownloaded = viewModel.audioPlayer.isSurahDownloaded(surah.number)

    val listState = rememberLazyListState()

    // Auto-scroll to currently reciting Ayah
    val headerItemsCount = if (surah.number != 1 && surah.number != 9) 2 else 1
    LaunchedEffect(audioState.currentAyahNumber, audioState.currentSurahNumber) {
        if (audioState.currentSurahNumber == surah.number && audioState.currentAyahNumber > 0) {
            val ayahIndex = ayahs.indexOfFirst { it.number == audioState.currentAyahNumber }
            if (ayahIndex >= 0) {
                val targetIndex = ayahIndex + headerItemsCount
                listState.animateScrollToItem(targetIndex)
            }
        }
    }

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
        containerColor = readingTheme.backgroundColor,
        topBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(readingTheme.surfaceColor)
            ) {
                TopAppBar(
                    title = {
                        val isBanglaMode = translationLanguage == TranslationDisplayMode.BANGLA_ONLY
                        val isEnglishMode = translationLanguage == TranslationDisplayMode.ENGLISH_ONLY

                        val mainTitle = when {
                            isBanglaMode -> "সূরা ${surah.nameBangla}"
                            isEnglishMode -> surah.nameEnglish
                            else -> "${surah.nameEnglish} (${surah.nameBangla})"
                        }

                        val subTitle = when {
                            isBanglaMode -> "${surah.nameArabic} • ${surah.banglaMeaning} • ${surah.totalAyahs} আয়াত"
                            isEnglishMode -> "${surah.nameArabic} • ${surah.englishMeaning} • ${surah.totalAyahs} Verses"
                            else -> "${surah.nameArabic} • ${surah.totalAyahs} আয়াত • পারা ${surah.startJuz}"
                        }

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column(modifier = Modifier.weight(1f, fill = false)) {
                                Text(
                                    text = mainTitle,
                                    fontFamily = if (isEnglishMode) com.example.ui.theme.TimesRomanFontFamily else androidx.compose.ui.text.font.FontFamily.Default,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 15.sp,
                                    color = readingTheme.primaryTextColor,
                                    maxLines = 1
                                )
                                Text(
                                    text = subTitle,
                                    fontFamily = if (isEnglishMode) com.example.ui.theme.TimesRomanFontFamily else androidx.compose.ui.text.font.FontFamily.Default,
                                    fontSize = 11.sp,
                                    color = readingTheme.secondaryTextColor,
                                    maxLines = 1
                                )
                            }

                            Spacer(modifier = Modifier.width(6.dp))

                            // Three background colours beside the surah name horizontally:
                            // 1st: Paper (black text), 2nd: Dark (white text), 3rd: White/Modern (black text)
                            SurahReadingThemeMiniBar(
                                selectedTheme = readingTheme,
                                onSelectTheme = { viewModel.selectReadingTheme(it) },
                                isBangla = isBanglaMode
                            )
                        }
                    },
                    navigationIcon = {
                        IconButton(onClick = { handleExit() }) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Back",
                                tint = readingTheme.primaryTextColor
                            )
                        }
                    },
                    actions = {
                        // Offline download button
                        if (downloadProgress != null) {
                            Box(contentAlignment = Alignment.Center, modifier = Modifier.padding(end = 4.dp)) {
                                CircularProgressIndicator(
                                    progress = { (downloadProgress ?: 0) / 100f },
                                    modifier = Modifier.size(22.dp),
                                    strokeWidth = 2.dp,
                                    color = MaterialTheme.colorScheme.primary
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
                                    tint = readingTheme.secondaryTextColor
                                )
                            }
                        }

                        // Settings Screen button
                        IconButton(onClick = { viewModel.openSettings() }) {
                            Icon(
                                imageVector = Icons.Default.Settings,
                                contentDescription = "Reader Settings",
                                tint = readingTheme.primaryTextColor
                            )
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = readingTheme.surfaceColor,
                        titleContentColor = readingTheme.primaryTextColor,
                        navigationIconContentColor = readingTheme.primaryTextColor,
                        actionIconContentColor = readingTheme.primaryTextColor
                    )
                )

                // UPPER SIDE LANGUAGE TOGGLE (Bangla / English / Both)
                UpperSideLanguageToggle(
                    currentMode = translationLanguage,
                    onSelectMode = { viewModel.setTranslationLanguage(it) },
                    theme = readingTheme
                )
            }
        },
        bottomBar = {
            Column {
                // Bottom Audio Player when playing
                BottomAudioPlayerBar(
                    state = audioState,
                    translationLanguage = translationLanguage,
                    onTogglePlayPause = { viewModel.audioPlayer.togglePlayPause() },
                    onSeekTo = { viewModel.audioPlayer.seekTo(it) },
                    onChangeSpeed = { viewModel.audioPlayer.setPlaybackSpeed(it) },
                    onClose = { viewModel.audioPlayer.stopAndClose() }
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
                .background(readingTheme.backgroundColor)
        ) {
            LazyColumn(
                state = listState,
                modifier = Modifier
                    .fillMaxSize()
                    .background(readingTheme.backgroundColor)
            ) {
                // Surah Header card
                item {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 14.dp, vertical = 8.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = readingTheme.cardBackgroundColor
                        ),
                        shape = RoundedCornerShape(16.dp),
                        border = androidx.compose.foundation.BorderStroke(
                            0.8.dp,
                            when (readingTheme) {
                                ReadingTheme.PAPER -> Color(0xFFE8DFC8)
                                ReadingTheme.DARK -> Color(0xFF2C2C2C)
                                ReadingTheme.WHITE -> Color(0xFFE5E7EB)
                            }
                        )
                    ) {
                        val isBanglaMode = translationLanguage == TranslationDisplayMode.BANGLA_ONLY
                        val isEnglishMode = translationLanguage == TranslationDisplayMode.ENGLISH_ONLY

                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = surah.nameArabic,
                                fontFamily = com.example.ui.theme.QuranArabicFontFamily,
                                fontSize = 32.sp,
                                fontWeight = FontWeight.Normal,
                                color = readingTheme.primaryTextColor
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = when {
                                    isBanglaMode -> "সূরা ${surah.nameBangla}"
                                    isEnglishMode -> surah.nameEnglish
                                    else -> "${surah.nameEnglish} • ${surah.nameBangla}"
                                },
                                fontFamily = if (isBanglaMode) androidx.compose.ui.text.font.FontFamily.Default else com.example.ui.theme.TimesRomanFontFamily,
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 16.sp,
                                color = readingTheme.primaryTextColor
                            )
                            Text(
                                text = when {
                                    isBanglaMode -> "অর্থ: ${surah.banglaMeaning} • ${if (surah.revelationType.name == "MECCAN") "মাক্কী সূরা" else "মাদানী সূরা"} • ${surah.totalAyahs} আয়াত • পারা ${surah.startJuz}"
                                    isEnglishMode -> "Meaning: ${surah.englishMeaning} • ${if (surah.revelationType.name == "MECCAN") "Meccan Surah" else "Medinan Surah"} • ${surah.totalAyahs} Verses • Juz ${surah.startJuz}"
                                    else -> "অর্থ: ${surah.banglaMeaning} • Meaning: ${surah.englishMeaning} • ${surah.totalAyahs} আয়াত"
                                },
                                fontFamily = if (isBanglaMode) androidx.compose.ui.text.font.FontFamily.Default else com.example.ui.theme.TimesRomanFontFamily,
                                fontSize = 12.sp,
                                color = readingTheme.secondaryTextColor,
                                textAlign = TextAlign.Center
                            )
                            Spacer(modifier = Modifier.height(10.dp))

                            // Play full surah audio chip
                            Surface(
                                shape = RoundedCornerShape(20.dp),
                                color = if (readingTheme == ReadingTheme.DARK) Color(0xFF1E1E1E) else if (readingTheme == ReadingTheme.PAPER) Color(0xFF000000) else MaterialTheme.colorScheme.primary,
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
                                        tint = if (readingTheme == ReadingTheme.PAPER) Color(0xFFF0D0A4) else Color(0xFFFFFFFF),
                                        modifier = Modifier.size(18.dp)
                                    )
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text(
                                        text = when {
                                            isBanglaMode -> "সম্পূর্ণ সূরা তিলাওয়াত শুনুন"
                                            isEnglishMode -> "Listen to Full Surah Recitation"
                                            else -> "সূরা তিলাওয়াত শুনুন (Listen Full Surah)"
                                        },
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Medium,
                                        color = if (readingTheme == ReadingTheme.PAPER) Color(0xFFF0D0A4) else Color(0xFFFFFFFF)
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(14.dp))

                            // Themes selection row matching user's image: Paper, Dark, Modern
                            Text(
                                text = when {
                                    isBanglaMode -> "পঠন ব্যাকগ্রাউন্ড ও থিম"
                                    isEnglishMode -> "Reading Background & Themes"
                                    else -> "পঠন ব্যাকগ্রাউন্ড ও থিম (Themes)"
                                },
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = readingTheme.secondaryTextColor,
                                modifier = Modifier.align(Alignment.Start)
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            SurahReadingThemeCardsRow(
                                selectedTheme = readingTheme,
                                onSelectTheme = { viewModel.selectReadingTheme(it) },
                                isBangla = isBanglaMode
                            )
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
                                fontFamily = com.example.ui.theme.QuranArabicFontFamily,
                                fontSize = 26.sp,
                                fontWeight = FontWeight.Normal,
                                color = readingTheme.primaryTextColor,
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
            translationMode = translationLanguage,
            onDismiss = { viewModel.closeTafsir() }
        )
    }
}
