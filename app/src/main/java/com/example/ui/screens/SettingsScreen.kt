package com.example.ui.screens

import androidx.activity.compose.BackHandler
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
import com.example.ui.theme.ArabicAccentGold
import com.example.ui.viewmodel.AvailableReciters
import com.example.ui.viewmodel.QuranBgColor
import com.example.ui.viewmodel.QuranTextColor
import com.example.ui.viewmodel.QuranViewModel
import com.example.ui.viewmodel.ReaderSettings
import com.example.ui.viewmodel.Reciter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    viewModel: QuranViewModel,
    onBack: () -> Unit
) {
    BackHandler { onBack() }

    val settings by viewModel.readerSettings.collectAsState()
    val isNightMode by viewModel.isNightMode.collectAsState()
    var selectedTab by remember { mutableIntStateOf(0) }

    val tabs = listOf(
        Pair("🎙️ তিলাওয়াতকারী", Icons.Default.Mic),
        Pair("📖 অনুবাদ", Icons.Default.MenuBook),
        Pair("🔤 হরফের আকার", Icons.Default.FormatSize),
        Pair("🎨 পটভূমি রঙ", Icons.Default.Palette),
        Pair("✍️ টেক্সট রঙ", Icons.Default.FormatColorText)
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
                            text = "কুরআন সেটিংস (Settings)",
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Text(
                            text = "পাঠ ও তিলাওয়াত কাস্টমাইজেশন",
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
                    0 -> FullReciterSection(
                        selectedReciter = settings.selectedReciter,
                        onSelectReciter = { reciter ->
                            viewModel.selectReciter(reciter)
                        }
                    )
                    1 -> FullTranslationsSection(
                        settings = settings,
                        onUpdate = { viewModel.updateReaderSettings(it) }
                    )
                    2 -> FullFontSizeSection(
                        settings = settings,
                        onUpdate = { viewModel.updateReaderSettings(it) }
                    )
                    3 -> FullBackgroundColorsSection(
                        selectedBg = settings.selectedBgColor,
                        onSelectBg = { viewModel.selectBgColor(it) }
                    )
                    4 -> FullTextColorsSection(
                        selectedTextColor = settings.selectedTextColor,
                        onSelectTextColor = { viewModel.selectTextColor(it) }
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))

                // Bottom Live Preview Card
                FullLivePreviewCard(settings = settings)

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
    onSelectReciter: (Reciter) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "বিশ্বখ্যাত ক্বারী নির্বাচন করুন (Reciters)",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
            Text(
                text = "নির্বাচিত ক্বারীর কণ্ঠেই পূর্ণ কুরআন তিলাওয়াত বাজানো হবে।",
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
                                    text = reciter.nameBangla,
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                                Text(
                                    text = reciter.nameEnglish,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Medium,
                                    color = MaterialTheme.colorScheme.primary
                                )
                                Text(
                                    text = reciter.subtext,
                                    fontSize = 11.sp,
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
                                    text = "সক্রিয়",
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
    onUpdate: ((ReaderSettings) -> ReaderSettings) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "অনুবাদ ও শব্দার্থ সেটিংস (Translations)",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
            Text(
                text = "আপনার সুবিধামতো প্রতিটি আয়াতের সাথে প্রদর্শিত তথ্য কাস্টমাইজ করুন।",
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(14.dp))

            FullScreenSwitchRow(
                title = "শব্দার্থে অর্থ (Word by Word Meaning)",
                subtitle = "প্রতিটি আরবি শব্দের সরাসরি নিচে তার বাংলা বা ইংরেজি অর্থ প্রদর্শন",
                checked = settings.showWordByWord,
                onCheckedChange = { onUpdate { s -> s.copy(showWordByWord = it) } }
            )

            HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp), color = MaterialTheme.colorScheme.surfaceVariant)

            FullScreenSwitchRow(
                title = "বাংলা অনুবাদ (Bangla Sentence Translation)",
                subtitle = "সহজ ও প্রাঞ্জল ভাষায় পূর্ণ আয়াতের বাংলা অর্থ",
                checked = settings.showBanglaTranslation,
                onCheckedChange = { onUpdate { s -> s.copy(showBanglaTranslation = it) } }
            )

            HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp), color = MaterialTheme.colorScheme.surfaceVariant)

            FullScreenSwitchRow(
                title = "English Translation",
                subtitle = "Sahih International authentic English meaning",
                checked = settings.showEnglishTranslation,
                onCheckedChange = { onUpdate { s -> s.copy(showEnglishTranslation = it) } }
            )

            HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp), color = MaterialTheme.colorScheme.surfaceVariant)

            FullScreenSwitchRow(
                title = "উচ্চারণ (Bangla Transliteration)",
                subtitle = "বাংলা বর্ণমালায় সহীহ কুরআন পাঠের উচ্চারণ সহায়িকা",
                checked = settings.showBanglaTransliteration,
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
    onUpdate: ((ReaderSettings) -> ReaderSettings) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "হরফের আকার নিয়ন্ত্রণ (Font Size)",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
            Text(
                text = "পড়ার সুবিধার জন্য আরবি আয়াত ও অনুবাদের হরফের আকার সামঞ্জস্য করুন।",
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
                    text = "আরবি হরফের আকার:",
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 14.sp
                )
                Surface(
                    shape = RoundedCornerShape(6.dp),
                    color = MaterialTheme.colorScheme.primaryContainer
                ) {
                    Text(
                        text = "${settings.arabicFontSizeSp.toInt()} sp",
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
                    text = "অনুবাদ ও উচ্চারণের আকার:",
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 14.sp
                )
                Surface(
                    shape = RoundedCornerShape(6.dp),
                    color = MaterialTheme.colorScheme.secondaryContainer
                ) {
                    Text(
                        text = "${settings.translationFontSizeSp.toInt()} sp",
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
    onSelectBg: (QuranBgColor) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "পৃষ্ঠার পটভূমি রঙ (Background Colours)",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
            Text(
                text = "দীর্ঘক্ষণ পাঠে চোখের সুরক্ষার জন্য আরামদায়ক পৃষ্ঠার পটভূমি নির্বাচন করুন।",
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
                                    text = bgColor.labelBangla,
                                    fontSize = 14.sp,
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                                Text(
                                    text = bgColor.labelEnglish,
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
                                    text = "নির্বাচিত",
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
    onSelectTextColor: (QuranTextColor) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "অক্ষরের টেক্সট রঙ (Text Colours)",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
            Text(
                text = "আরবি হরফের জন্য আপনার পছন্দের ক্যালিগ্রাফিক কালার থিম বেছে নিন।",
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
                                    text = textColor.labelBangla,
                                    fontSize = 14.sp,
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                                Text(
                                    text = textColor.labelEnglish,
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
                                    text = "নির্বাচিত",
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
private fun FullLivePreviewCard(settings: ReaderSettings) {
    val bg = if (settings.selectedBgColor != QuranBgColor.DEFAULT) settings.selectedBgColor.color else MaterialTheme.colorScheme.surface
    val textCol = if (settings.selectedTextColor != QuranTextColor.DEFAULT) settings.selectedTextColor.color else MaterialTheme.colorScheme.onSurface

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = bg),
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
                    text = "লাইভ প্রিভিউ (Live Preview)",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )

                Text(
                    text = "ক্বারী: ${settings.selectedReciter.nameBangla}",
                    fontSize = 11.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "بِسْمِ اللَّهِ الرَّحْمَٰنِ الرَّحِيمِ",
                fontSize = settings.arabicFontSizeSp.sp,
                lineHeight = (settings.arabicFontSizeSp * 1.5f).sp,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Right,
                color = textCol,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            if (settings.showBanglaTranslation) {
                Text(
                    text = "পরম করুণাময়, অসীম দয়ালু আল্লাহর নামে শুরু করছি।",
                    fontSize = settings.translationFontSizeSp.sp,
                    fontWeight = FontWeight.Medium,
                    color = textCol.copy(alpha = 0.9f)
                )
            }

            if (settings.showEnglishTranslation) {
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "In the name of Allah, the Entirely Merciful, the Especially Merciful.",
                    fontSize = (settings.translationFontSizeSp - 1f).sp,
                    color = textCol.copy(alpha = 0.75f)
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
    onCheckedChange: (Boolean) -> Unit
) {
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
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = subtitle,
                fontSize = 11.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        Spacer(modifier = Modifier.width(12.dp))
        Switch(checked = checked, onCheckedChange = onCheckedChange)
    }
}
