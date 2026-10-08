package com.example.ui.components

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.PauseCircle
import androidx.compose.material.icons.filled.PlayCircle
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.Ayah
import com.example.data.model.Surah
import com.example.data.model.Word
import com.example.ui.theme.ArabicAccentGold
import com.example.ui.theme.SajdahBadge
import com.example.ui.viewmodel.QuranBgColor
import com.example.ui.viewmodel.QuranTextColor
import com.example.ui.viewmodel.ReaderSettings
import com.example.ui.viewmodel.ReadingTheme
import com.example.ui.viewmodel.TranslationDisplayMode

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun WordByWordCard(
    ayah: Ayah,
    surah: Surah,
    settings: ReaderSettings,
    translationLanguage: TranslationDisplayMode,
    isCurrentPlaying: Boolean,
    isBookmarked: Boolean,
    onPlayAyah: () -> Unit,
    onToggleBookmark: () -> Unit,
    onOpenTafsir: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    var isWordsExpanded by remember { mutableStateOf(settings.showWordByWord) }

    val theme = settings.selectedReadingTheme

    val cardBg = if (settings.selectedBgColor != QuranBgColor.DEFAULT) {
        if (isCurrentPlaying) settings.selectedBgColor.color.copy(alpha = 0.85f) else settings.selectedBgColor.color
    } else if (isCurrentPlaying) {
        if (theme == ReadingTheme.DARK) Color(0xFF2E2E2E) else Color(0xFFE8DFC8)
    } else {
        theme.cardBackgroundColor
    }

    val customTextColor = if (settings.selectedTextColor != QuranTextColor.DEFAULT) {
        settings.selectedTextColor.color
    } else {
        theme.primaryTextColor
    }

    val secondaryTextColor = theme.secondaryTextColor

    val cardBorder = when (theme) {
        ReadingTheme.PAPER -> androidx.compose.foundation.BorderStroke(0.8.dp, Color(0xFFE8DFC8))
        ReadingTheme.DARK -> androidx.compose.foundation.BorderStroke(0.8.dp, Color(0xFF2C2C2C))
        ReadingTheme.WHITE -> androidx.compose.foundation.BorderStroke(0.8.dp, Color(0xFFE5E7EB))
    }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp, vertical = 3.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = cardBg),
        border = cardBorder,
        elevation = CardDefaults.cardElevation(defaultElevation = if (isCurrentPlaying) 3.dp else 1.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 9.dp)
        ) {
            // Compact Header: Ayah Number Badge, Word-by-word Toggle Chip, and Action Buttons
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    // Complete Ayah reference in a box on the upper left
                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = when (theme) {
                            ReadingTheme.DARK -> Color(0xFF222222)
                            ReadingTheme.PAPER -> Color(0xFFEADBBE)
                            ReadingTheme.WHITE -> Color(0xFFF1F5F9)
                        },
                        border = androidx.compose.foundation.BorderStroke(
                            1.2.dp,
                            theme.primaryTextColor.copy(alpha = 0.8f)
                        ),
                        modifier = Modifier.padding(end = 4.dp)
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "${surah.number}:${ayah.number}",
                                fontFamily = if (translationLanguage == TranslationDisplayMode.BANGLA_ONLY) androidx.compose.ui.text.font.FontFamily.Default else com.example.ui.theme.TimesRomanFontFamily,
                                fontWeight = FontWeight.Bold,
                                fontSize = 12.sp,
                                color = theme.primaryTextColor
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            val ayahSurahName = when (translationLanguage) {
                                TranslationDisplayMode.BANGLA_ONLY -> "সূরা ${surah.nameBangla}"
                                TranslationDisplayMode.ENGLISH_ONLY -> surah.nameEnglish
                                TranslationDisplayMode.BOTH -> surah.nameEnglish
                            }
                            Text(
                                text = "• $ayahSurahName",
                                fontFamily = if (translationLanguage == TranslationDisplayMode.BANGLA_ONLY) androidx.compose.ui.text.font.FontFamily.Default else com.example.ui.theme.TimesRomanFontFamily,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = theme.primaryTextColor
                            )
                        }
                    }

                    if (ayah.sajdah) {
                        Spacer(modifier = Modifier.width(6.dp))
                        Surface(
                            shape = RoundedCornerShape(4.dp),
                            color = theme.cardBackgroundColor,
                            border = androidx.compose.foundation.BorderStroke(1.dp, theme.primaryTextColor)
                        ) {
                            Text(
                                text = if (translationLanguage == TranslationDisplayMode.ENGLISH_ONLY) "Sajdah" else "সাজদাহ",
                                fontFamily = if (translationLanguage == TranslationDisplayMode.ENGLISH_ONLY) com.example.ui.theme.TimesRomanFontFamily else androidx.compose.ui.text.font.FontFamily.Default,
                                color = theme.primaryTextColor,
                                fontSize = 9.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(horizontal = 4.dp, vertical = 1.dp)
                            )
                        }
                    }

                    // Compact Word-by-Word Toggle button
                    if (ayah.words.isNotEmpty()) {
                        Spacer(modifier = Modifier.width(8.dp))
                        Surface(
                            shape = RoundedCornerShape(12.dp),
                            color = theme.cardBackgroundColor,
                            border = androidx.compose.foundation.BorderStroke(1.dp, theme.primaryTextColor),
                            modifier = Modifier.clickable { isWordsExpanded = !isWordsExpanded }
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = if (translationLanguage == TranslationDisplayMode.ENGLISH_ONLY) "Words" else "শব্দার্থ",
                                    fontFamily = if (translationLanguage == TranslationDisplayMode.ENGLISH_ONLY) com.example.ui.theme.TimesRomanFontFamily else androidx.compose.ui.text.font.FontFamily.Default,
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = theme.primaryTextColor
                                )
                                Icon(
                                    imageVector = if (isWordsExpanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                                    contentDescription = null,
                                    tint = theme.iconColor,
                                    modifier = Modifier.size(14.dp)
                                )
                            }
                        }
                    }
                }

                // Compact Action Buttons
                Row(verticalAlignment = Alignment.CenterVertically) {
                    IconButton(
                        onClick = onPlayAyah,
                        modifier = Modifier.size(28.dp)
                    ) {
                        Icon(
                            imageVector = if (isCurrentPlaying) Icons.Filled.PauseCircle else Icons.Filled.PlayCircle,
                            contentDescription = if (translationLanguage == TranslationDisplayMode.ENGLISH_ONLY) "Play Ayah" else "আয়াত শুনুন",
                            tint = theme.iconColor,
                            modifier = Modifier.size(18.dp)
                        )
                    }

                    IconButton(
                        onClick = onToggleBookmark,
                        modifier = Modifier.size(28.dp)
                    ) {
                        Icon(
                            imageVector = if (isBookmarked) Icons.Filled.Bookmark else Icons.Outlined.BookmarkBorder,
                            contentDescription = if (translationLanguage == TranslationDisplayMode.ENGLISH_ONLY) "Bookmark" else "বুকমার্ক",
                            tint = theme.iconColor,
                            modifier = Modifier.size(18.dp)
                        )
                    }

                    IconButton(
                        onClick = onOpenTafsir,
                        modifier = Modifier.size(28.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Filled.MenuBook,
                            contentDescription = if (translationLanguage == TranslationDisplayMode.ENGLISH_ONLY) "Tafsir" else "তাফসীর",
                            tint = theme.iconColor,
                            modifier = Modifier.size(18.dp)
                        )
                    }

                    IconButton(
                        onClick = {
                            val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                            val clipText = when (translationLanguage) {
                                TranslationDisplayMode.BANGLA_ONLY -> "${ayah.textArabic}\n${ayah.translationBangla}"
                                TranslationDisplayMode.ENGLISH_ONLY -> "${ayah.textArabic}\n${ayah.translationEnglish}"
                                TranslationDisplayMode.BOTH -> "${ayah.textArabic}\n${ayah.translationBangla}\n${ayah.translationEnglish}"
                            }
                            val clip = ClipData.newPlainText("Ayah", clipText)
                            clipboard.setPrimaryClip(clip)
                            val toastMsg = if (translationLanguage == TranslationDisplayMode.ENGLISH_ONLY) "Copied" else "কপি করা হয়েছে"
                            Toast.makeText(context, toastMsg, Toast.LENGTH_SHORT).show()
                        },
                        modifier = Modifier.size(28.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Filled.ContentCopy,
                            contentDescription = if (translationLanguage == TranslationDisplayMode.ENGLISH_ONLY) "Copy" else "কপি",
                            tint = theme.iconColor,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(7.dp))

            // Complete Arabic Ayah enclosed in a dedicated elegant Box with bold script
            val currentScriptText = ayah.getTextForScript(settings.selectedScriptType)
            val scriptFontFamily = com.example.ui.theme.getFontFamilyForScript(settings.selectedScriptType)
            Surface(
                shape = RoundedCornerShape(10.dp),
                color = when (theme) {
                    ReadingTheme.DARK -> Color(0xFF1E1E1E)
                    ReadingTheme.PAPER -> Color(0xFFF3DEC2).copy(alpha = 0.55f)
                    ReadingTheme.WHITE -> Color(0xFFF8FAFC)
                },
                border = androidx.compose.foundation.BorderStroke(
                    width = 1.dp,
                    color = when (theme) {
                        ReadingTheme.DARK -> Color(0xFF383838)
                        ReadingTheme.PAPER -> Color(0xFFD3B080)
                        ReadingTheme.WHITE -> Color(0xFFE2E8F0)
                    }
                ),
                modifier = Modifier.fillMaxWidth()
            ) {
                CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
                    Text(
                        text = currentScriptText,
                        fontFamily = scriptFontFamily,
                        fontSize = (settings.arabicFontSizeSp * 1.05f).sp,
                        lineHeight = (settings.arabicFontSizeSp * 1.85f).sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Start,
                        color = customTextColor,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 12.dp, vertical = 9.dp)
                    )
                }
            }

            // Short & Compact Word-by-Word Flow (Starts from top line and wraps downwards)
            AnimatedVisibility(visible = isWordsExpanded && ayah.words.isNotEmpty()) {
                Column(modifier = Modifier.padding(top = 6.dp)) {
                    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
                        FlowRow(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.Start),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            ayah.words.forEach { word ->
                                CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
                                    CompactWordChip(word = word, mode = translationLanguage, theme = theme)
                                }
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(5.dp))
            HorizontalDivider(
                color = when (theme) {
                    ReadingTheme.DARK -> Color(0xFF222222)
                    ReadingTheme.PAPER -> Color(0xFFD6B587)
                    ReadingTheme.WHITE -> Color(0xFFE5E7EB)
                },
                thickness = 0.5.dp
            )
            Spacer(modifier = Modifier.height(4.dp))

            // Concise Sentence Translations based on selected language (pure white in dark, pure black in paper/white)
            when (translationLanguage) {
                TranslationDisplayMode.BANGLA_ONLY -> {
                    Text(
                        text = ayah.translationBangla,
                        fontSize = settings.translationFontSizeSp.sp,
                        lineHeight = (settings.translationFontSizeSp * 1.35f).sp,
                        color = theme.primaryTextColor
                    )
                }
                TranslationDisplayMode.ENGLISH_ONLY -> {
                    Text(
                        text = ayah.translationEnglish,
                        fontFamily = com.example.ui.theme.TimesRomanFontFamily,
                        fontSize = settings.translationFontSizeSp.sp,
                        lineHeight = (settings.translationFontSizeSp * 1.4f).sp,
                        color = theme.primaryTextColor
                    )
                }
                TranslationDisplayMode.BOTH -> {
                    Text(
                        text = "বাং: ${ayah.translationBangla}",
                        fontSize = settings.translationFontSizeSp.sp,
                        lineHeight = (settings.translationFontSizeSp * 1.35f).sp,
                        color = theme.primaryTextColor
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = "EN: ${ayah.translationEnglish}",
                        fontFamily = com.example.ui.theme.TimesRomanFontFamily,
                        fontSize = settings.translationFontSizeSp.sp,
                        lineHeight = (settings.translationFontSizeSp * 1.35f).sp,
                        color = theme.primaryTextColor
                    )
                }
            }
        }
    }
}

/**
 * Word Item with Arabic on top and Bangla/English meaning underneath, with NO enclosing boxes or borders.
 */
@Composable
fun CompactWordChip(word: Word, mode: TranslationDisplayMode, theme: ReadingTheme) {
    val meaning = com.example.data.source.QuranWordDictionary.resolveMeaning(
        arabicWord = word.arabic,
        providedBangla = word.bangla,
        providedEnglish = word.english,
        mode = mode
    )

    Column(
        modifier = Modifier.padding(horizontal = 6.dp, vertical = 3.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Arabic Word
        Text(
            text = word.arabic,
            fontFamily = com.example.ui.theme.QuranArabicFontFamily,
            fontSize = 20.sp,
            lineHeight = 28.sp,
            fontWeight = FontWeight.Normal,
            color = theme.primaryTextColor,
            textAlign = TextAlign.Center
        )

        // Meaning in selected language (Bangla / English, NEVER Arabic)
        if (meaning.isNotBlank()) {
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = meaning,
                fontFamily = if (mode == TranslationDisplayMode.ENGLISH_ONLY || mode == TranslationDisplayMode.BOTH) com.example.ui.theme.TimesRomanFontFamily else androidx.compose.ui.text.font.FontFamily.Default,
                fontSize = 11.5.sp,
                lineHeight = 15.sp,
                fontWeight = FontWeight.Medium,
                color = theme.secondaryTextColor,
                textAlign = TextAlign.Center,
                maxLines = 2
            )
        }
    }
}
