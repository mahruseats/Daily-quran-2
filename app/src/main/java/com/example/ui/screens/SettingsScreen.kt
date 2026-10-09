package com.example.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.FormatColorText
import androidx.compose.material.icons.filled.FormatSize
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Slider
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.components.SurahReadingThemeCardsRow
import com.example.ui.theme.ArabicAccentGold
import com.example.ui.theme.TimesRomanFontFamily
import com.example.ui.util.AppStrings
import com.example.ui.viewmodel.AvailableReciters
import com.example.ui.viewmodel.QuranBgColor
import com.example.ui.viewmodel.QuranTextColor
import com.example.ui.viewmodel.QuranViewModel
import com.example.ui.viewmodel.ReaderSettings
import com.example.ui.viewmodel.ReadingTheme
import com.example.ui.viewmodel.Reciter
import com.example.ui.viewmodel.TranslationDisplayMode

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    viewModel: QuranViewModel,
    onBack: () -> Unit
) {
    BackHandler { onBack() }

    val settings by viewModel.readerSettings.collectAsState()
    val isNightMode by viewModel.isNightMode.collectAsState()
    val translationLanguage by viewModel.translationLanguage.collectAsState()
    val isEng = AppStrings.isEnglish(translationLanguage)
    val font = if (isEng) com.example.ui.theme.TimesRomanFontFamily else androidx.compose.ui.text.font.FontFamily.Default
    var selectedTab by remember { mutableIntStateOf(0) }

    val tabs = listOf(
        Pair(AppStrings.settingsTabTheme(translationLanguage), Icons.Default.Palette),
        Pair(AppStrings.settingsTabReciter(translationLanguage), Icons.Default.Mic),
        Pair(AppStrings.settingsTabTranslation(translationLanguage), Icons.Default.MenuBook),
        Pair(AppStrings.settingsTabFontSize(translationLanguage), Icons.Default.FormatSize),
        Pair(AppStrings.settingsTabBg(translationLanguage), Icons.Default.Palette),
        Pair(AppStrings.settingsTabTextColor(translationLanguage), Icons.Default.FormatColorText)
    )

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                },
                title = {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = AppStrings.settingsTitle(translationLanguage),
                            fontFamily = font,
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Text(
                            text = AppStrings.settingsSubtitle(translationLanguage),
                            fontFamily = font,
                            fontSize = 11.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                },
                actions = {
                    // Day / Night Switch Toggle
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(end = 12.dp)
                    ) {
                        Icon(
                            imageVector = if (isNightMode) Icons.Default.DarkMode else Icons.Default.LightMode,
                            contentDescription = "Day / Night mode",
                            tint = if (isNightMode) ArabicAccentGold else MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(18.dp)
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
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(MaterialTheme.colorScheme.background)
        ) {
            // Category Tab Row
            ScrollableTabRow(
                selectedTabIndex = selectedTab,
                edgePadding = 12.dp,
                containerColor = MaterialTheme.colorScheme.surface,
                modifier = Modifier.fillMaxWidth()
            ) {
                tabs.forEachIndexed { index, pair ->
                    Tab(
                        selected = selectedTab == index,
                        onClick = { selectedTab = index },
                        text = {
                            Text(
                                text = pair.first,
                                fontSize = 13.sp,
                                fontWeight = if (selectedTab == index) FontWeight.Bold else FontWeight.Medium
                            )
                        }
                    )
                }
            }

            // Tab Content
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp)
            ) {
                when (selectedTab) {
                    0 -> Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                text = if (isEng) "Surah Reading Themes" else "সূরা পঠন থিম",
                                fontFamily = font,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary
                            )
                            Text(
                                text = if (isEng) "3 comfortable background modes while reading (Paper, Dark, and White/Modern):" else "পঠনকালীন ৩টি বিশেষ ব্যাকগ্রাউন্ড মোড (কাগজ/Paper, কালো/Dark এবং সাদা/Modern):",
                                fontFamily = font,
                                fontSize = 12.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                modifier = Modifier.padding(top = 2.dp, bottom = 12.dp)
                            )
                            SurahReadingThemeCardsRow(
                                selectedTheme = settings.selectedReadingTheme,
                                onSelectTheme = { viewModel.selectReadingTheme(it) }
                            )
                        }
                    }
                    1 -> FullReciterSection(
                        selectedReciter = settings.selectedReciter,
                        translationLanguage = translationLanguage,
                        onSelectReciter = { reciter ->
                            viewModel.selectReciter(reciter)
                        }
                    )
                    2 -> FullTranslationsSection(
                        settings = settings,
                        translationLanguage = translationLanguage,
                        onUpdate = { viewModel.updateReaderSettings(it) }
                    )
                    3 -> FullFontSizeSection(
                        settings = settings,
                        translationLanguage = translationLanguage,
                        onUpdate = { viewModel.updateReaderSettings(it) }
                    )
                    4 -> FullBackgroundColorsSection(
                        selectedBg = settings.selectedBgColor,
                        translationLanguage = translationLanguage,
                        onSelectBg = { viewModel.selectBgColor(it) }
                    )
                    5 -> FullTextColorsSection(
                        selectedTextColor = settings.selectedTextColor,
                        translationLanguage = translationLanguage,
                        onSelectTextColor = { viewModel.selectTextColor(it) }
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))

                // Bottom Live Preview Card
                FullLivePreviewCard(settings = settings, translationLanguage = translationLanguage)

                Spacer(modifier = Modifier.height(30.dp))
            }
        }
    }
}

/**
 * 1. Full Reciter Selection Section
 */
@Composable
private fun FullReciterSection(
    selectedReciter: Reciter,
    translationLanguage: TranslationDisplayMode = TranslationDisplayMode.BOTH,
    onSelectReciter: (Reciter) -> Unit
) {
    val isEng = AppStrings.isEnglish(translationLanguage)
    val font = if (isEng) TimesRomanFontFamily else androidx.compose.ui.text.font.FontFamily.Default

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = if (isEng) "Select World-Renowned Reciter" else "বিশ্বখ্যাত ক্বারী নির্বাচন করুন (Reciters)",
                fontFamily = font,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
            Text(
                text = if (isEng) "Full Quran recitation will be played in the selected reciter's voice." else "নির্বাচিত ক্বারীর কণ্ঠেই পূর্ণ কুরআন তিলাওয়াত বাজানো হবে।",
                fontFamily = font,
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(12.dp))

            AvailableReciters.forEach { reciter ->
                val isSelected = reciter.id == selectedReciter.id
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 5.dp)
                        .clickable { onSelectReciter(reciter) },
                    shape = RoundedCornerShape(12.dp),
                    color = if (isSelected) MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.55f) else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.35f),
                    border = androidx.compose.foundation.BorderStroke(
                        width = if (isSelected) 1.5.dp else 0.5.dp,
                        color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline.copy(alpha = 0.2f)
                    )
                ) {
                    Row(
                        modifier = Modifier.padding(14.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            RadioButton(
                                selected = isSelected,
                                onClick = { onSelectReciter(reciter) },
                                modifier = Modifier.size(28.dp)
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                            Column {
                                Text(
                                    text = if (isEng) reciter.nameEnglish else reciter.nameBangla,
                                    fontFamily = font,
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                                Text(
                                    text = if (isEng) reciter.subtext else reciter.nameEnglish,
                                    fontFamily = font,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Medium,
                                    color = MaterialTheme.colorScheme.primary
                                )
                                if (!isEng) {
                                    Text(
                                        text = reciter.subtext,
                                        fontSize = 11.sp,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }
                            }
                        }

                        if (isSelected) {
                            Surface(
                                shape = RoundedCornerShape(6.dp),
                                color = MaterialTheme.colorScheme.primary
                            ) {
                                Text(
                                    text = if (isEng) "Active" else "সক্রিয়",
                                    fontFamily = font,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onPrimary,
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

/**
 * 2. Full Translations & Word-by-Word Section
 */
@Composable
private fun FullTranslationsSection(
    settings: ReaderSettings,
    translationLanguage: TranslationDisplayMode = TranslationDisplayMode.BOTH,
    onUpdate: ((ReaderSettings) -> ReaderSettings) -> Unit
) {
    val isEng = AppStrings.isEnglish(translationLanguage)
    val font = if (isEng) TimesRomanFontFamily else androidx.compose.ui.text.font.FontFamily.Default

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = if (isEng) "Translations & Word-by-Word" else "অনুবাদ ও শব্দার্থ সেটিংস (Translations)",
                fontFamily = font,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
            Text(
                text = if (isEng) "Customize the information and meanings displayed with each verse." else "আপনার সুবিধামতো প্রতিটি আয়াতের সাথে প্রদর্শিত তথ্য কাস্টমাইজ করুন।",
                fontFamily = font,
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(14.dp))

            FullScreenSwitchRow(
                title = if (isEng) "Word by Word Meaning" else "শব্দার্থে অর্থ (Word by Word Meaning)",
                subtitle = if (isEng) "Shows Bangla/English meaning directly underneath each Arabic word without boxes" else "প্রতিটি আরবি শব্দের সরাসরি নিচে তার বাংলা বা ইংরেজি অর্থ প্রদর্শন",
                checked = settings.showWordByWord,
                translationLanguage = translationLanguage,
                onCheckedChange = { onUpdate { s -> s.copy(showWordByWord = it) } }
            )

            HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp), color = MaterialTheme.colorScheme.surfaceVariant)

            FullScreenSwitchRow(
                title = if (isEng) "Bangla Sentence Translation" else "বাংলা অনুবাদ (Bangla Sentence Translation)",
                subtitle = if (isEng) "Clear and easy Bangla meaning for the full verse" else "সহজ ও প্রাঞ্জল ভাষায় পূর্ণ আয়াতের বাংলা অর্থ",
                checked = settings.showBanglaTranslation,
                translationLanguage = translationLanguage,
                onCheckedChange = { onUpdate { s -> s.copy(showBanglaTranslation = it) } }
            )

            HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp), color = MaterialTheme.colorScheme.surfaceVariant)

            FullScreenSwitchRow(
                title = if (isEng) "English Translation" else "English Translation (ইংরেজি অনুবাদ)",
                subtitle = if (isEng) "Sahih International authentic English translation" else "Sahih International authentic English meaning",
                checked = settings.showEnglishTranslation,
                translationLanguage = translationLanguage,
                onCheckedChange = { onUpdate { s -> s.copy(showEnglishTranslation = it) } }
            )

            HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp), color = MaterialTheme.colorScheme.surfaceVariant)

            FullScreenSwitchRow(
                title = if (isEng) "Transliteration" else "উচ্চারণ (Bangla Transliteration)",
                subtitle = if (isEng) "Pronunciation guide in Bangla alphabet" else "বাংলা বর্ণমালায় সহীহ কুরআন পাঠের উচ্চারণ সহায়িকা",
                checked = settings.showBanglaTransliteration,
                translationLanguage = translationLanguage,
                onCheckedChange = { onUpdate { s -> s.copy(showBanglaTransliteration = it) } }
            )
        }
    }
}

/**
 * 3. Full Font Size Section
 */
@Composable
private fun FullFontSizeSection(
    settings: ReaderSettings,
    translationLanguage: TranslationDisplayMode = TranslationDisplayMode.BOTH,
    onUpdate: ((ReaderSettings) -> ReaderSettings) -> Unit
) {
    val isEng = AppStrings.isEnglish(translationLanguage)
    val font = if (isEng) TimesRomanFontFamily else androidx.compose.ui.text.font.FontFamily.Default

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = if (isEng) "Font Size Control" else "হরফের আকার নিয়ন্ত্রণ (Font Size)",
                fontFamily = font,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
            Text(
                text = if (isEng) "Adjust the size of Arabic verses and translations for comfortable reading." else "পড়ার সুবিধার জন্য আরবি আয়াত ও অনুবাদের হরফের আকার সামঞ্জস্য করুন।",
                fontFamily = font,
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(18.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = if (isEng) "Arabic Font Size:" else "আরবি হরফের আকার:",
                    fontFamily = font,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 14.sp
                )
                Surface(
                    shape = RoundedCornerShape(6.dp),
                    color = MaterialTheme.colorScheme.primaryContainer
                ) {
                    Text(
                        text = "${settings.arabicFontSizeSp.toInt()} sp",
                        fontFamily = font,
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                    )
                }
            }

            Slider(
                value = settings.arabicFontSizeSp,
                onValueChange = { onUpdate { s -> s.copy(arabicFontSizeSp = it) } },
                valueRange = 18f..38f
            )

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = if (isEng) "Translation & Pronunciation Size:" else "অনুবাদ ও উচ্চারণের আকার:",
                    fontFamily = font,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 14.sp
                )
                Surface(
                    shape = RoundedCornerShape(6.dp),
                    color = MaterialTheme.colorScheme.secondaryContainer
                ) {
                    Text(
                        text = "${settings.translationFontSizeSp.toInt()} sp",
                        fontFamily = font,
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.secondary,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                    )
                }
            }

            Slider(
                value = settings.translationFontSizeSp,
                onValueChange = { onUpdate { s -> s.copy(translationFontSizeSp = it) } },
                valueRange = 11f..22f
            )
        }
    }
}

/**
 * 4. Full Background Colors Section
 */
@Composable
private fun FullBackgroundColorsSection(
    selectedBg: QuranBgColor,
    translationLanguage: TranslationDisplayMode = TranslationDisplayMode.BOTH,
    onSelectBg: (QuranBgColor) -> Unit
) {
    val isEng = AppStrings.isEnglish(translationLanguage)
    val font = if (isEng) TimesRomanFontFamily else androidx.compose.ui.text.font.FontFamily.Default

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = if (isEng) "Page Background Colors" else "পৃষ্ঠার পটভূমি রঙ (Background Colours)",
                fontFamily = font,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
            Text(
                text = if (isEng) "Select a comfortable background color to protect your eyes during extended reading." else "দীর্ঘক্ষণ পাঠে চোখের সুরক্ষার জন্য আরামদায়ক পৃষ্ঠার পটভূমি নির্বাচন করুন।",
                fontFamily = font,
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(14.dp))

            QuranBgColor.values().forEach { bgColor ->
                val isSelected = bgColor == selectedBg
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                        .clickable { onSelectBg(bgColor) },
                    shape = RoundedCornerShape(12.dp),
                    color = if (isSelected) MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f) else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f),
                    border = androidx.compose.foundation.BorderStroke(
                        width = if (isSelected) 1.5.dp else 0.5.dp,
                        color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline.copy(alpha = 0.2f)
                    )
                ) {
                    Row(
                        modifier = Modifier.padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(34.dp)
                                    .clip(CircleShape)
                                    .background(if (bgColor == QuranBgColor.DEFAULT) MaterialTheme.colorScheme.surface else bgColor.color)
                                    .border(1.5.dp, if (isSelected) MaterialTheme.colorScheme.primary else Color.Gray.copy(alpha = 0.4f), CircleShape),
                                contentAlignment = Alignment.Center
                            ) {
                                if (isSelected) {
                                    Icon(
                                        imageVector = Icons.Default.Check,
                                        contentDescription = null,
                                        tint = if (bgColor == QuranBgColor.NIGHT_EMERALD) Color.White else MaterialTheme.colorScheme.primary,
                                        modifier = Modifier.size(18.dp)
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.width(12.dp))

                            Column {
                                Text(
                                    text = if (isEng) bgColor.labelEnglish else bgColor.labelBangla,
                                    fontFamily = font,
                                    fontSize = 14.sp,
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                                Text(
                                    text = if (isEng) bgColor.name else bgColor.labelEnglish,
                                    fontFamily = font,
                                    fontSize = 12.sp,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }

                        if (isSelected) {
                            Surface(
                                shape = RoundedCornerShape(6.dp),
                                color = MaterialTheme.colorScheme.primary
                            ) {
                                Text(
                                    text = if (isEng) "Selected" else "নির্বাচিত",
                                    fontFamily = font,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onPrimary,
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

/**
 * 5. Full Text Colors Section
 */
@Composable
private fun FullTextColorsSection(
    selectedTextColor: QuranTextColor,
    translationLanguage: TranslationDisplayMode = TranslationDisplayMode.BOTH,
    onSelectTextColor: (QuranTextColor) -> Unit
) {
    val isEng = AppStrings.isEnglish(translationLanguage)
    val font = if (isEng) TimesRomanFontFamily else androidx.compose.ui.text.font.FontFamily.Default

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = if (isEng) "Arabic Calligraphy Text Colors" else "অক্ষরের টেক্সট রঙ (Text Colours)",
                fontFamily = font,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
            Text(
                text = if (isEng) "Select your favorite calligraphic accent color for the Arabic verses." else "আরবি হরফের জন্য আপনার পছন্দের ক্যালিগ্রাফিক কালার থিম বেছে নিন।",
                fontFamily = font,
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(14.dp))

            QuranTextColor.values().forEach { textColor ->
                val isSelected = textColor == selectedTextColor
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                        .clickable { onSelectTextColor(textColor) },
                    shape = RoundedCornerShape(12.dp),
                    color = if (isSelected) MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f) else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f),
                    border = androidx.compose.foundation.BorderStroke(
                        width = if (isSelected) 1.5.dp else 0.5.dp,
                        color = if (isSelected) ArabicAccentGold else MaterialTheme.colorScheme.outline.copy(alpha = 0.2f)
                    )
                ) {
                    Row(
                        modifier = Modifier.padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(34.dp)
                                    .clip(CircleShape)
                                    .background(if (textColor == QuranTextColor.DEFAULT) MaterialTheme.colorScheme.onSurface else textColor.color)
                                    .border(1.5.dp, if (isSelected) ArabicAccentGold else Color.Gray.copy(alpha = 0.4f), CircleShape),
                                contentAlignment = Alignment.Center
                            ) {
                                if (isSelected) {
                                    Icon(
                                        imageVector = Icons.Default.Check,
                                        contentDescription = null,
                                        tint = if (textColor == QuranTextColor.GOLDEN) Color.Black else Color.White,
                                        modifier = Modifier.size(18.dp)
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.width(12.dp))

                            Column {
                                Text(
                                    text = if (isEng) textColor.labelEnglish else textColor.labelBangla,
                                    fontFamily = font,
                                    fontSize = 14.sp,
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                                Text(
                                    text = if (isEng) textColor.name else textColor.labelEnglish,
                                    fontFamily = font,
                                    fontSize = 12.sp,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }

                        if (isSelected) {
                            Surface(
                                shape = RoundedCornerShape(6.dp),
                                color = ArabicAccentGold
                            ) {
                                Text(
                                    text = if (isEng) "Selected" else "নির্বাচিত",
                                    fontFamily = font,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.Black,
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

/**
 * Bottom Live Preview Card
 */
@Composable
private fun FullLivePreviewCard(
    settings: ReaderSettings,
    translationLanguage: TranslationDisplayMode = TranslationDisplayMode.BOTH
) {
    val theme = settings.selectedReadingTheme
    val isEng = AppStrings.isEnglish(translationLanguage)
    val font = if (isEng) TimesRomanFontFamily else androidx.compose.ui.text.font.FontFamily.Default
    val bg = if (settings.selectedBgColor != QuranBgColor.DEFAULT) settings.selectedBgColor.color else theme.backgroundColor
    val textCol = if (settings.selectedTextColor != QuranTextColor.DEFAULT) settings.selectedTextColor.color else theme.primaryTextColor
    val subtextCol = theme.secondaryTextColor

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = bg),
        border = BorderStroke(1.dp, if (theme == ReadingTheme.DARK) Color(0xFF2C2C2C) else Color(0xFFE5E7EB)),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = if (isEng) "Live Preview" else "লাইভ প্রিভিউ (Live Preview)",
                    fontFamily = font,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Surface(
                        shape = RoundedCornerShape(6.dp),
                        color = theme.borderColor.copy(alpha = 0.15f),
                        border = BorderStroke(1.dp, theme.borderColor),
                        modifier = Modifier.padding(end = 6.dp)
                    ) {
                        Text(
                            text = theme.title,
                            fontFamily = font,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            color = theme.borderColor,
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                        )
                    }

                    val reciterName = if (isEng) settings.selectedReciter.nameEnglish else settings.selectedReciter.nameBangla
                    Text(
                        text = "${if (isEng) "Reciter: " else "ক্বারী: "}$reciterName",
                        fontFamily = font,
                        fontSize = 11.sp,
                        color = subtextCol
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            val sampleArabicText = "بِسْمِ اللَّهِ الرَّحْمَٰنِ الرَّحِيمِ"

            Text(
                text = sampleArabicText,
                fontFamily = com.example.ui.theme.QuranArabicFontFamily,
                fontSize = settings.arabicFontSizeSp.sp,
                lineHeight = (settings.arabicFontSizeSp * 1.5f).sp,
                fontWeight = FontWeight.Normal,
                textAlign = TextAlign.Right,
                color = textCol,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            if (settings.showBanglaTranslation && !isEng) {
                Text(
                    text = "পরম করুণাময়, অসীম দয়ালু আল্লাহর নামে শুরু করছি।",
                    fontSize = settings.translationFontSizeSp.sp,
                    fontWeight = FontWeight.Medium,
                    color = textCol.copy(alpha = 0.9f)
                )
            }

            if (settings.showEnglishTranslation || isEng) {
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "In the name of Allah, the Entirely Merciful, the Especially Merciful.",
                    fontFamily = TimesRomanFontFamily,
                    fontSize = (settings.translationFontSizeSp - 1f).sp,
                    color = textCol.copy(alpha = 0.85f)
                )
            }
        }
    }
}

@Composable
private fun FullScreenSwitchRow(
    title: String,
    subtitle: String,
    checked: Boolean,
    translationLanguage: TranslationDisplayMode = TranslationDisplayMode.BOTH,
    onCheckedChange: (Boolean) -> Unit
) {
    val isEng = AppStrings.isEnglish(translationLanguage)
    val font = if (isEng) TimesRomanFontFamily else androidx.compose.ui.text.font.FontFamily.Default

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                fontFamily = font,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = subtitle,
                fontFamily = font,
                fontSize = 11.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        Spacer(modifier = Modifier.width(12.dp))
        Switch(checked = checked, onCheckedChange = onCheckedChange)
    }
}
