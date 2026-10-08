package com.example.ui.screens

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.AutoStories
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.local.BookmarkEntity
import com.example.data.model.JuzInfo
import com.example.data.model.QuranScriptType
import com.example.data.model.RevelationType
import com.example.data.model.Surah
import com.example.data.source.JuzCatalog
import com.example.data.source.SurahCatalog
import com.example.ui.components.BottomAudioPlayerBar
import com.example.ui.theme.ArabicAccentGold
import com.example.ui.theme.TimesRomanFontFamily
import com.example.ui.util.AppStrings
import com.example.ui.viewmodel.HomeTab
import com.example.ui.viewmodel.QuranViewModel
import com.example.ui.viewmodel.TranslationDisplayMode

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuranHomeScreen(
    viewModel: QuranViewModel,
    onOpenSurah: (Surah) -> Unit,
    onOpenRabbanaDuas: () -> Unit,
    onOpenSettings: () -> Unit
) {
    val currentTab by viewModel.currentTab.collectAsState()
    val surahsList by viewModel.surahsList.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()
    val lastRead by viewModel.lastRead.collectAsState()
    val bookmarks by viewModel.bookmarks.collectAsState()
    val isNightMode by viewModel.isNightMode.collectAsState()
    val translationLanguage by viewModel.translationLanguage.collectAsState()
    val audioState by viewModel.audioPlayer.state.collectAsState()
    val settings by viewModel.readerSettings.collectAsState()
    val prayerTimes by viewModel.prayerTimes.collectAsState()

    // Determine upcoming Salah timing
    val nextTiming = when (prayerTimes.nextPrayerName.lowercase()) {
        "fajr" -> prayerTimes.fajr
        "sunrise" -> prayerTimes.sunrise
        "dhuhr" -> prayerTimes.dhuhr
        "asr" -> prayerTimes.asr
        "maghrib" -> prayerTimes.maghrib
        "isha" -> prayerTimes.isha
        else -> prayerTimes.fajr
    }

    Scaffold(
        topBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.surface)
            ) {
                CenterAlignedTopAppBar(
                    navigationIcon = {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(start = 4.dp)
                        ) {
                            IconButton(onClick = onOpenSettings) {
                                Icon(
                                    imageVector = Icons.Default.Settings,
                                    contentDescription = "Settings",
                                    tint = MaterialTheme.colorScheme.primary
                                )
                            }

                            // Next upcoming salah displayed beside settings
                            Surface(
                                onClick = { viewModel.setTab(HomeTab.PRAYER_TIMES) },
                                shape = RoundedCornerShape(12.dp),
                                color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.7f),
                                border = BorderStroke(1.dp, ArabicAccentGold.copy(alpha = 0.5f))
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.padding(horizontal = 7.dp, vertical = 4.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.AccessTime,
                                        contentDescription = "Upcoming Salah",
                                        tint = ArabicAccentGold,
                                        modifier = Modifier.size(13.dp)
                                    )
                                    Spacer(modifier = Modifier.width(3.dp))
                                    val isEng = AppStrings.isEnglish(translationLanguage)
                                    val salahName = if (isEng) nextTiming.nameEnglish else nextTiming.nameBangla
                                    Text(
                                        text = "$salahName ${nextTiming.timeFormatted}",
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Bold,
                                        fontFamily = if (isEng) TimesRomanFontFamily else androidx.compose.ui.text.font.FontFamily.Default,
                                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                                        maxLines = 1
                                    )
                                }
                            }
                        }
                    },
                    title = {
                        val isEng = AppStrings.isEnglish(translationLanguage)
                        Text(
                            text = AppStrings.appTitle(translationLanguage),
                            fontWeight = FontWeight.Bold,
                            fontFamily = if (isEng) TimesRomanFontFamily else androidx.compose.ui.text.font.FontFamily.Default,
                            fontSize = 19.sp,
                            color = MaterialTheme.colorScheme.primary,
                            maxLines = 1
                        )
                    },
                    actions = {
                        // Day / Night Mode Toggle
                        IconButton(
                            onClick = { viewModel.setNightMode(!isNightMode) },
                            modifier = Modifier.padding(end = 6.dp)
                        ) {
                            Icon(
                                imageVector = if (isNightMode) Icons.Default.LightMode else Icons.Default.DarkMode,
                                contentDescription = if (isNightMode) "Switch to Day Mode" else "Switch to Night Mode",
                                tint = if (isNightMode) ArabicAccentGold else MaterialTheme.colorScheme.primary
                            )
                        }
                    },
                    colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
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
                // Bottom Audio Player bar
                BottomAudioPlayerBar(
                    state = audioState,
                    translationLanguage = translationLanguage,
                    onTogglePlayPause = { viewModel.audioPlayer.togglePlayPause() },
                    onSeekTo = { viewModel.audioPlayer.seekTo(it) },
                    onChangeSpeed = { viewModel.audioPlayer.setPlaybackSpeed(it) },
                    onClose = { viewModel.audioPlayer.stopAndClose() }
                )

                val isEng = AppStrings.isEnglish(translationLanguage)
                val navFont = if (isEng) TimesRomanFontFamily else androidx.compose.ui.text.font.FontFamily.Default

                // Navigation Bar for Primary Tabs
                NavigationBar(
                    containerColor = MaterialTheme.colorScheme.surface,
                    tonalElevation = 8.dp
                ) {
                    NavigationBarItem(
                        selected = currentTab == HomeTab.SURAHS,
                        onClick = { viewModel.setTab(HomeTab.SURAHS) },
                        icon = { Icon(Icons.Default.MenuBook, contentDescription = AppStrings.navSurahs(translationLanguage)) },
                        label = { Text(AppStrings.navSurahs(translationLanguage), fontFamily = navFont) },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = MaterialTheme.colorScheme.primary,
                            indicatorColor = MaterialTheme.colorScheme.primaryContainer
                        )
                    )
                    NavigationBarItem(
                        selected = currentTab == HomeTab.JUZ,
                        onClick = { viewModel.setTab(HomeTab.JUZ) },
                        icon = { Icon(Icons.Default.AutoStories, contentDescription = AppStrings.navJuz(translationLanguage)) },
                        label = { Text(AppStrings.navJuz(translationLanguage), fontFamily = navFont) },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = MaterialTheme.colorScheme.primary,
                            indicatorColor = MaterialTheme.colorScheme.primaryContainer
                        )
                    )
                    NavigationBarItem(
                        selected = currentTab == HomeTab.PRAYER_TIMES,
                        onClick = { viewModel.setTab(HomeTab.PRAYER_TIMES) },
                        icon = { Icon(Icons.Default.AccessTime, contentDescription = AppStrings.navPrayerTimes(translationLanguage)) },
                        label = { Text(AppStrings.navPrayerTimes(translationLanguage), fontFamily = navFont) },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = MaterialTheme.colorScheme.primary,
                            indicatorColor = MaterialTheme.colorScheme.primaryContainer
                        )
                    )
                    NavigationBarItem(
                        selected = currentTab == HomeTab.RABBANA_DUAS,
                        onClick = {
                            viewModel.setTab(HomeTab.RABBANA_DUAS)
                            onOpenRabbanaDuas()
                        },
                        icon = { Icon(Icons.Default.Favorite, contentDescription = AppStrings.navDuas(translationLanguage)) },
                        label = { Text(AppStrings.navDuas(translationLanguage), fontFamily = navFont) },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = MaterialTheme.colorScheme.primary,
                            indicatorColor = MaterialTheme.colorScheme.primaryContainer
                        )
                    )
                    NavigationBarItem(
                        selected = currentTab == HomeTab.BOOKMARKS,
                        onClick = { viewModel.setTab(HomeTab.BOOKMARKS) },
                        icon = { Icon(Icons.Default.Bookmark, contentDescription = AppStrings.navBookmarks(translationLanguage)) },
                        label = { Text(AppStrings.navBookmarks(translationLanguage), fontFamily = navFont) },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = MaterialTheme.colorScheme.primary,
                            indicatorColor = MaterialTheme.colorScheme.primaryContainer
                        )
                    )
                }
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            when (currentTab) {
                HomeTab.SURAHS -> {
                    VerticalSurahsList(
                        surahs = surahsList,
                        searchQuery = searchQuery,
                        translationLanguage = translationLanguage,
                        lastRead = lastRead,
                        onResumeLastRead = { viewModel.resumeLastRead() },
                        onSearchChange = { viewModel.setSearchQuery(it) },
                        onSelectSurah = onOpenSurah
                    )
                }
                HomeTab.JUZ -> {
                    JuzTabContent(
                        translationLanguage = translationLanguage,
                        onSelectJuz = { juz ->
                            val surah = SurahCatalog.getSurah(juz.startSurahNumber) ?: SurahCatalog.surahs[0]
                            viewModel.openSurah(surah, juz.startAyah)
                            onOpenSurah(surah)
                        }
                    )
                }
                HomeTab.PRAYER_TIMES -> {
                    PrayerTimesScreen(
                        viewModel = viewModel,
                        translationLanguage = translationLanguage
                    )
                }
                HomeTab.RABBANA_DUAS -> {
                    RabbanaDuasScreen(
                        viewModel = viewModel,
                        translationLanguage = translationLanguage,
                        onBack = { viewModel.setTab(HomeTab.SURAHS) }
                    )
                }
                HomeTab.BOOKMARKS -> {
                    BookmarksTabContent(
                        bookmarks = bookmarks,
                        translationLanguage = translationLanguage,
                        onSelectBookmark = { b ->
                            val surah = SurahCatalog.getSurah(b.surahNumber) ?: SurahCatalog.surahs[0]
                            viewModel.openSurah(surah, b.ayahNumber)
                            onOpenSurah(surah)
                        },
                        onDeleteBookmark = { b ->
                            viewModel.removeBookmark(b.surahNumber, b.ayahNumber)
                        }
                    )
                }
            }
        }
    }
}

/**
 * Upper side Bangla / English language toggle
 */
@Composable
fun UpperSideLanguageToggle(
    currentMode: TranslationDisplayMode,
    onSelectMode: (TranslationDisplayMode) -> Unit,
    theme: com.example.ui.viewmodel.ReadingTheme? = null
) {
    val containerBg = when (theme) {
        com.example.ui.viewmodel.ReadingTheme.DARK -> Color(0xFF000000)
        com.example.ui.viewmodel.ReadingTheme.PAPER -> Color(0xFFE2BC8B)
        com.example.ui.viewmodel.ReadingTheme.WHITE -> Color(0xFFF3F4F6)
        null -> MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
    }

    val containerBorder = if (theme == com.example.ui.viewmodel.ReadingTheme.DARK) {
        BorderStroke(1.dp, Color(0xFF333333))
    } else if (theme == com.example.ui.viewmodel.ReadingTheme.PAPER) {
        BorderStroke(1.dp, Color(0xFFD4AA70))
    } else null

    val tab1Title = when (currentMode) {
        TranslationDisplayMode.ENGLISH_ONLY -> "Bangla"
        TranslationDisplayMode.BANGLA_ONLY -> "বাংলা"
        TranslationDisplayMode.BOTH -> "বাংলা (Bangla)"
    }
    val tab2Title = "English"
    val tab3Title = when (currentMode) {
        TranslationDisplayMode.ENGLISH_ONLY -> "Both"
        TranslationDisplayMode.BANGLA_ONLY -> "উভয়"
        TranslationDisplayMode.BOTH -> "উভয় (Both)"
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 14.dp, vertical = 6.dp)
            .background(color = containerBg, shape = RoundedCornerShape(12.dp))
            .then(if (containerBorder != null) Modifier.border(containerBorder, RoundedCornerShape(12.dp)) else Modifier)
            .padding(3.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        LanguageTabPill(
            title = tab1Title,
            isSelected = currentMode == TranslationDisplayMode.BANGLA_ONLY,
            onClick = { onSelectMode(TranslationDisplayMode.BANGLA_ONLY) },
            theme = theme,
            modifier = Modifier.weight(1f)
        )
        LanguageTabPill(
            title = tab2Title,
            isSelected = currentMode == TranslationDisplayMode.ENGLISH_ONLY,
            onClick = { onSelectMode(TranslationDisplayMode.ENGLISH_ONLY) },
            theme = theme,
            modifier = Modifier.weight(1f)
        )
        LanguageTabPill(
            title = tab3Title,
            isSelected = currentMode == TranslationDisplayMode.BOTH,
            onClick = { onSelectMode(TranslationDisplayMode.BOTH) },
            theme = theme,
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
fun LanguageTabPill(
    title: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    theme: com.example.ui.viewmodel.ReadingTheme? = null,
    modifier: Modifier = Modifier
) {
    val activeBg = when (theme) {
        com.example.ui.viewmodel.ReadingTheme.DARK -> Color(0xFFFFFFFF)
        com.example.ui.viewmodel.ReadingTheme.PAPER -> Color(0xFF000000)
        com.example.ui.viewmodel.ReadingTheme.WHITE -> Color(0xFF000000)
        null -> MaterialTheme.colorScheme.primary
    }
    val activeText = when (theme) {
        com.example.ui.viewmodel.ReadingTheme.DARK -> Color(0xFF000000)
        com.example.ui.viewmodel.ReadingTheme.PAPER -> Color(0xFFF0D0A4)
        com.example.ui.viewmodel.ReadingTheme.WHITE -> Color(0xFFFFFFFF)
        null -> MaterialTheme.colorScheme.onPrimary
    }
    val inactiveText = when (theme) {
        com.example.ui.viewmodel.ReadingTheme.DARK -> Color(0xFFFFFFFF)
        com.example.ui.viewmodel.ReadingTheme.PAPER -> Color(0xFF000000)
        com.example.ui.viewmodel.ReadingTheme.WHITE -> Color(0xFF000000)
        null -> MaterialTheme.colorScheme.onSurfaceVariant
    }

    val bgColor by animateColorAsState(
        targetValue = if (isSelected) activeBg else Color.Transparent,
        label = "pill_bg"
    )
    val textColor by animateColorAsState(
        targetValue = if (isSelected) activeText else inactiveText,
        label = "pill_text"
    )

    Box(
        modifier = modifier
            .clip(RoundedCornerShape(10.dp))
            .background(bgColor)
            .clickable { onClick() }
            .padding(vertical = 7.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = title,
            fontSize = 12.sp,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
            color = textColor,
            textAlign = TextAlign.Center
        )
    }
}

/**
 * One-by-one vertical Surahs list
 */
@Composable
fun VerticalSurahsList(
    surahs: List<Surah>,
    searchQuery: String,
    translationLanguage: TranslationDisplayMode,
    lastRead: com.example.data.local.LastReadEntity?,
    onResumeLastRead: () -> Unit,
    onSearchChange: (String) -> Unit,
    onSelectSurah: (Surah) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(horizontal = 14.dp, vertical = 6.dp)
    ) {
        // Search Bar
        item {
            val isEng = AppStrings.isEnglish(translationLanguage)
            val searchFont = if (isEng) TimesRomanFontFamily else androidx.compose.ui.text.font.FontFamily.Default
            OutlinedTextField(
                value = searchQuery,
                onValueChange = onSearchChange,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                placeholder = { Text(AppStrings.searchSurahPlaceholder(translationLanguage), fontFamily = searchFont) },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search",
                        tint = MaterialTheme.colorScheme.primary
                    )
                },
                trailingIcon = {
                    if (searchQuery.isNotEmpty()) {
                        IconButton(onClick = { onSearchChange("") }) {
                            Icon(imageVector = Icons.Default.Clear, contentDescription = "Clear")
                        }
                    }
                },
                shape = RoundedCornerShape(14.dp),
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = MaterialTheme.colorScheme.primary,
                    unfocusedBorderColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.3f),
                    focusedContainerColor = MaterialTheme.colorScheme.surface,
                    unfocusedContainerColor = MaterialTheme.colorScheme.surface
                )
            )
        }

        // Last Read Banner
        if (lastRead != null && searchQuery.isEmpty()) {
            item {
                val isEng = AppStrings.isEnglish(translationLanguage)
                val cardFont = if (isEng) TimesRomanFontFamily else androidx.compose.ui.text.font.FontFamily.Default
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 6.dp)
                        .clickable { onResumeLastRead() },
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.6f)
                    )
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(14.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(40.dp)
                                    .clip(CircleShape)
                                    .background(MaterialTheme.colorScheme.primary),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.AutoStories,
                                    contentDescription = "Last Read",
                                    tint = MaterialTheme.colorScheme.onPrimary,
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                            Spacer(modifier = Modifier.width(12.dp))
                            Column {
                                Text(
                                    text = AppStrings.lastReadHeader(translationLanguage),
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    fontFamily = cardFont,
                                    color = MaterialTheme.colorScheme.primary
                                )
                                Text(
                                    text = if (isEng) lastRead.surahNameEnglish else "${lastRead.surahNameBangla} (${lastRead.surahNameEnglish})",
                                    fontSize = 15.sp,
                                    fontWeight = FontWeight.Bold,
                                    fontFamily = cardFont,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                                Text(
                                    text = AppStrings.verseNumber(translationLanguage, lastRead.ayahNumber),
                                    fontSize = 12.sp,
                                    fontFamily = cardFont,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }

                        Surface(
                            shape = RoundedCornerShape(20.dp),
                            color = MaterialTheme.colorScheme.primary
                        ) {
                            Text(
                                text = AppStrings.readButton(translationLanguage),
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                fontFamily = cardFont,
                                color = MaterialTheme.colorScheme.onPrimary,
                                modifier = Modifier.padding(horizontal = 14.dp, vertical = 6.dp)
                            )
                        }
                    }
                }
            }
        }

        // Section header
        item {
            val isEng = AppStrings.isEnglish(translationLanguage)
            val headerFont = if (isEng) TimesRomanFontFamily else androidx.compose.ui.text.font.FontFamily.Default
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = AppStrings.allSurahsHeader(translationLanguage),
                    fontWeight = FontWeight.Bold,
                    fontFamily = headerFont,
                    fontSize = 15.sp,
                    color = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = if (isEng) "${surahs.size} Surahs" else "${surahs.size} টি সূরা",
                    fontSize = 12.sp,
                    fontFamily = headerFont,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        // ONE-BY-ONE VERTICAL SURAH CARDS
        items(surahs, key = { it.number }) { surah ->
            VerticalSurahCard(
                surah = surah,
                translationLanguage = translationLanguage,
                onClick = { onSelectSurah(surah) }
            )
        }

        item {
            Spacer(modifier = Modifier.height(30.dp))
        }
    }
}

/**
 * Vertical single Surah card
 */
@Composable
fun VerticalSurahCard(
    surah: Surah,
    translationLanguage: TranslationDisplayMode,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.5.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 14.dp, vertical = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Left: Number Badge + Titles
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.weight(1f)
            ) {
                // Surah Number
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.12f))
                        .border(1.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.3f), CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "${surah.number}",
                        fontWeight = FontWeight.Bold,
                        fontSize = 13.sp,
                        color = MaterialTheme.colorScheme.primary
                    )
                }

                Spacer(modifier = Modifier.width(12.dp))

                Column {
                    // Titles according to translation toggle
                    when (translationLanguage) {
                        TranslationDisplayMode.BANGLA_ONLY -> {
                            Text(
                                text = surah.nameBangla,
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Text(
                                text = "অর্থ: ${surah.banglaMeaning}",
                                fontSize = 12.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                        TranslationDisplayMode.ENGLISH_ONLY -> {
                            Text(
                                text = surah.nameEnglish,
                                fontFamily = com.example.ui.theme.TimesRomanFontFamily,
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Text(
                                text = "Meaning: ${surah.englishMeaning}",
                                fontFamily = com.example.ui.theme.TimesRomanFontFamily,
                                fontSize = 12.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                        TranslationDisplayMode.BOTH -> {
                            Text(
                                text = "${surah.nameEnglish} • ${surah.nameBangla}",
                                fontFamily = com.example.ui.theme.TimesRomanFontFamily,
                                fontWeight = FontWeight.Bold,
                                fontSize = 15.sp,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Text(
                                text = "${surah.banglaMeaning} (${surah.englishMeaning})",
                                fontFamily = com.example.ui.theme.TimesRomanFontFamily,
                                fontSize = 12.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(2.dp))
                    val isEng = AppStrings.isEnglish(translationLanguage)
                    val cardSubFont = if (isEng) com.example.ui.theme.TimesRomanFontFamily else androidx.compose.ui.text.font.FontFamily.Default
                    Text(
                        text = if (isEng) "${surah.totalAyahs} Verses • Juz ${surah.startJuz}" else "${surah.totalAyahs} আয়াত • পারা ${surah.startJuz}",
                        fontFamily = cardSubFont,
                        fontSize = 11.sp,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }

            // Right: Arabic Calligraphy Name & Revelation badge
            Column(
                horizontalAlignment = Alignment.End,
                modifier = Modifier.padding(start = 8.dp)
            ) {
                Text(
                    text = surah.nameArabic,
                    fontFamily = com.example.ui.theme.QuranArabicFontFamily,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Normal,
                    color = MaterialTheme.colorScheme.primary
                )

                val isEng = AppStrings.isEnglish(translationLanguage)
                val badgeFont = if (isEng) com.example.ui.theme.TimesRomanFontFamily else androidx.compose.ui.text.font.FontFamily.Default
                Surface(
                    shape = RoundedCornerShape(4.dp),
                    color = if (surah.revelationType == RevelationType.MECCAN) ArabicAccentGold.copy(alpha = 0.15f) else MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f),
                    modifier = Modifier.padding(top = 2.dp)
                ) {
                    val revText = if (surah.revelationType == RevelationType.MECCAN) {
                        if (isEng) "Meccan" else "মক্কী"
                    } else {
                        if (isEng) "Medinan" else "মাদানী"
                    }
                    Text(
                        text = revText,
                        fontFamily = badgeFont,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (surah.revelationType == RevelationType.MECCAN) ArabicAccentGold else MaterialTheme.colorScheme.primary,
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun JuzTabContent(
    translationLanguage: TranslationDisplayMode = TranslationDisplayMode.BOTH,
    onSelectJuz: (JuzInfo) -> Unit
) {
    val juzList = JuzCatalog.juzList
    val isEng = AppStrings.isEnglish(translationLanguage)
    val font = if (isEng) com.example.ui.theme.TimesRomanFontFamily else androidx.compose.ui.text.font.FontFamily.Default

    Column(modifier = Modifier.fillMaxSize()) {
        Text(
            text = AppStrings.juzHeader(translationLanguage),
            fontFamily = font,
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(start = 14.dp, top = 8.dp, bottom = 4.dp)
        )

        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(juzList) { juz ->
                Surface(
                    shape = RoundedCornerShape(20.dp),
                    color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f),
                    modifier = Modifier.clickable { onSelectJuz(juz) }
                ) {
                    Text(
                        text = AppStrings.juzTitle(translationLanguage, juz.number),
                        fontFamily = font,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 13.sp,
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                        modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(6.dp))

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp)
        ) {
            items(juzList, key = { it.number }) { juz ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                        .clickable { onSelectJuz(juz) },
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    elevation = CardDefaults.cardElevation(defaultElevation = 1.5.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(14.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(38.dp)
                                    .clip(CircleShape)
                                    .background(MaterialTheme.colorScheme.secondary.copy(alpha = 0.2f)),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "${juz.number}",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 14.sp,
                                    fontFamily = font,
                                    color = MaterialTheme.colorScheme.secondary
                                )
                            }

                            Spacer(modifier = Modifier.width(12.dp))

                            Column {
                                val juzTitleText = if (isEng) "Juz ${juz.number} - ${juz.nameEnglish}" else "পারা ${juz.number} - ${juz.nameBangla}"
                                val juzRangeText = if (isEng) {
                                    "From ${juz.startSurahName} (Verse ${juz.startAyah}) to ${juz.endSurahName} (Verse ${juz.endAyah})"
                                } else {
                                    "শুরু: ${juz.startSurahName} (আয়াত ${juz.startAyah}) হতে ${juz.endSurahName} (আয়াত ${juz.endAyah})"
                                }
                                Text(
                                    text = juzTitleText,
                                    fontWeight = FontWeight.Bold,
                                    fontFamily = font,
                                    fontSize = 15.sp
                                )
                                Text(
                                    text = juzRangeText,
                                    fontFamily = font,
                                    fontSize = 12.sp,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }

                        Text(
                            text = juz.nameArabic,
                            fontFamily = com.example.ui.theme.QuranArabicFontFamily,
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Normal,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun BookmarksTabContent(
    bookmarks: List<BookmarkEntity>,
    translationLanguage: TranslationDisplayMode = TranslationDisplayMode.BOTH,
    onSelectBookmark: (BookmarkEntity) -> Unit,
    onDeleteBookmark: (BookmarkEntity) -> Unit
) {
    val isEng = AppStrings.isEnglish(translationLanguage)
    val font = if (isEng) com.example.ui.theme.TimesRomanFontFamily else androidx.compose.ui.text.font.FontFamily.Default

    if (bookmarks.isEmpty()) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(
                    imageVector = Icons.Default.Bookmark,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.4f),
                    modifier = Modifier.size(64.dp)
                )
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = AppStrings.noBookmarks(translationLanguage),
                    fontFamily = font,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = AppStrings.noBookmarksHint(translationLanguage),
                    fontFamily = font,
                    fontSize = 13.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(horizontal = 32.dp)
                )
            }
        }
    } else {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(horizontal = 12.dp, vertical = 8.dp)
        ) {
            items(bookmarks, key = { it.id }) { bookmark ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                        .clickable { onSelectBookmark(bookmark) },
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(14.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = AppStrings.bookmarkAyahHeader(
                                    mode = translationLanguage,
                                    surahEng = bookmark.surahNameEnglish,
                                    surahBan = bookmark.surahNameBangla,
                                    ayahNum = bookmark.ayahNumber
                                ),
                                fontFamily = font,
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp,
                                color = MaterialTheme.colorScheme.primary
                            )

                            IconButton(
                                onClick = { onDeleteBookmark(bookmark) },
                                modifier = Modifier.size(32.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Delete,
                                    contentDescription = "Delete Bookmark",
                                    tint = MaterialTheme.colorScheme.error.copy(alpha = 0.7f)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = bookmark.textArabic,
                            fontFamily = com.example.ui.theme.QuranArabicFontFamily,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Normal,
                            textAlign = TextAlign.Right,
                            color = MaterialTheme.colorScheme.onSurface,
                            modifier = Modifier.fillMaxWidth()
                        )

                        Spacer(modifier = Modifier.height(6.dp))
                        val transText = if (isEng) {
                            bookmark.translationEnglish.ifEmpty { bookmark.translationBangla }
                        } else {
                            bookmark.translationBangla
                        }
                        Text(
                            text = transText,
                            fontFamily = font,
                            fontSize = 13.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
            }
        }
    }
}
