package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.Ayah
import com.example.data.model.Surah
import com.example.ui.viewmodel.TranslationDisplayMode

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TafsirBottomSheet(
    ayah: Ayah,
    surah: Surah,
    translationMode: TranslationDisplayMode = TranslationDisplayMode.BOTH,
    onDismiss: () -> Unit
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val isEnglishMode = translationMode == TranslationDisplayMode.ENGLISH_ONLY
    var selectedTabIndex by remember { mutableIntStateOf(if (isEnglishMode) 1 else 0) }
    val tabs = listOf("বাংলা তাফসীর", "English Tafsir")

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
        containerColor = MaterialTheme.colorScheme.surface
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 8.dp)
                .verticalScroll(rememberScrollState())
        ) {
            // Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = if (isEnglishMode) "Tafsir & Commentary" else "তাফসীর ও ব্যাখ্যা",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        text = if (isEnglishMode) "${surah.nameEnglish} • Verse ${ayah.number}" else "${surah.nameBangla} • আয়াত ${ayah.number}",
                        fontSize = 13.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                IconButton(onClick = onDismiss) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Close Tafsir"
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Arabic Ayah Preview
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                        shape = RoundedCornerShape(12.dp)
                    )
                    .padding(14.dp)
            ) {
                Text(
                    text = ayah.textArabic,
                    fontFamily = com.example.ui.theme.QuranArabicFontFamily,
                    fontSize = 22.sp,
                    lineHeight = 38.sp,
                    textAlign = TextAlign.Right,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.fillMaxWidth()
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Tab selector for Bangla vs English Tafsir
            TabRow(
                selectedTabIndex = selectedTabIndex,
                containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f),
                contentColor = MaterialTheme.colorScheme.primary
            ) {
                tabs.forEachIndexed { index, title ->
                    Tab(
                        selected = selectedTabIndex == index,
                        onClick = { selectedTabIndex = index },
                        text = {
                            Text(
                                text = title,
                                fontWeight = if (selectedTabIndex == index) FontWeight.Bold else FontWeight.Normal,
                                fontSize = 14.sp
                            )
                        }
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Tafsir Content
            when (selectedTabIndex) {
                0 -> {
                    // Bangla Tafsir
                    Text(
                        text = "তাফসীর আহসানুল বায়ান / সংক্ষিপ্ত সারমর্ম",
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = ayah.tafsirBangla.ifEmpty {
                            "তাফসীর ও প্রেক্ষাপট: এই আয়াতে আল্লাহ তায়ালা বান্দাদের জন্য গভীর প্রজ্ঞা, ইবাদতের মূলনীতি এবং হেদায়েতের বার্তা অবতীর্ণ করেছেন। বিজ্ঞ মুফাসসিরদের মতে এটি সত্যের ওপর অবিচল থাকার জন্য এক অনন্য অনুপ্রেরণা।"
                        },
                        fontSize = 15.sp,
                        lineHeight = 24.sp,
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    Spacer(modifier = Modifier.height(16.dp))
                    HorizontalDivider()
                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = "আয়াতের অর্থ:",
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 13.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = ayah.translationBangla,
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
                1 -> {
                    // English Tafsir
                    Text(
                        text = "Tafsir Ibn Kathir / Quranic Exegesis",
                        fontFamily = com.example.ui.theme.TimesRomanFontFamily,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.secondary
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = ayah.tafsirEnglish.ifEmpty {
                            "Commentary & Context: This verse provides foundational lessons on Tawhid, moral conduct, and devotion to the Creator. Classical commentators emphasize reflection (Tadabbur) upon its timeless guidance."
                        },
                        fontFamily = com.example.ui.theme.TimesRomanFontFamily,
                        fontSize = 15.sp,
                        lineHeight = 24.sp,
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    Spacer(modifier = Modifier.height(16.dp))
                    HorizontalDivider()
                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = "English Translation:",
                        fontFamily = com.example.ui.theme.TimesRomanFontFamily,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 13.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = ayah.translationEnglish,
                        fontFamily = com.example.ui.theme.TimesRomanFontFamily,
                        fontSize = 14.sp,
                        lineHeight = 20.sp,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}
