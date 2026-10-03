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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
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

    val cardBg = if (settings.selectedBgColor != QuranBgColor.DEFAULT) {
        if (isCurrentPlaying) settings.selectedBgColor.color.copy(alpha = 0.85f) else settings.selectedBgColor.color
    } else if (isCurrentPlaying) {
        MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.35f)
    } else {
        MaterialTheme.colorScheme.surface
    }

    val customTextColor = if (settings.selectedTextColor != QuranTextColor.DEFAULT) {
        settings.selectedTextColor.color
    } else {
        MaterialTheme.colorScheme.onSurface
    }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp, vertical = 3.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = cardBg),
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
                    // Small Ayah number badge
                    Box(
                        modifier = Modifier
                            .size(26.dp)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.12f))
                            .border(1.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.4f), CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "${ayah.number}",
                            fontWeight = FontWeight.Bold,
                            fontSize = 11.sp,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }

                    Spacer(modifier = Modifier.width(6.dp))

                    Text(
                        text = "${surah.nameEnglish}:${ayah.number}",
                        fontSize = 11.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    if (ayah.sajdah) {
                        Spacer(modifier = Modifier.width(6.dp))
                        Surface(
                            shape = RoundedCornerShape(4.dp),
                            color = SajdahBadge.copy(alpha = 0.15f)
                        ) {
                            Text(
                                text = "সাজদাহ",
                                color = SajdahBadge,
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
                            color = if (isWordsExpanded) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surfaceVariant,
                            modifier = Modifier.clickable { isWordsExpanded = !isWordsExpanded }
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "শব্দার্থ",
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = if (isWordsExpanded) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant
                                )
                                Icon(
                                    imageVector = if (isWordsExpanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                                    contentDescription = null,
                                    tint = if (isWordsExpanded) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant,
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
                            contentDescription = "Play",
                            tint = if (isCurrentPlaying) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.size(18.dp)
                        )
                    }

                    IconButton(
                        onClick = onToggleBookmark,
                        modifier = Modifier.size(28.dp)
                    ) {
                        Icon(
                            imageVector = if (isBookmarked) Icons.Filled.Bookmark else Icons.Outlined.BookmarkBorder,
                            contentDescription = "Bookmark",
                            tint = if (isBookmarked) ArabicAccentGold else MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.size(18.dp)
                        )
                    }

                    IconButton(
                        onClick = onOpenTafsir,
                        modifier = Modifier.size(28.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Filled.MenuBook,
                            contentDescription = "Tafsir",
                            tint = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.size(18.dp)
                        )
                    }

                    IconButton(
                        onClick = {
                            val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                            val clip = ClipData.newPlainText(
                                "Ayah",
                                "${ayah.textArabic}\n${ayah.translationBangla}\n${ayah.translationEnglish}"
                            )
                            clipboard.setPrimaryClip(clip)
                            Toast.makeText(context, "Copied", Toast.LENGTH_SHORT).show()
                        },
                        modifier = Modifier.size(28.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Filled.ContentCopy,
                            contentDescription = "Copy",
                            tint = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(6.dp))

            // Arabic Text (Crisp, clean, compact line height)
            Text(
                text = ayah.textArabic,
                fontSize = (settings.arabicFontSizeSp * 0.9f).sp,
                lineHeight = (settings.arabicFontSizeSp * 1.45f).sp,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Right,
                color = customTextColor,
                modifier = Modifier.fillMaxWidth()
            )

            // Short & Compact Word-by-Word Flow (Only 2 lines per chip instead of 4)
            AnimatedVisibility(visible = isWordsExpanded && ayah.words.isNotEmpty()) {
                Column(modifier = Modifier.padding(top = 6.dp)) {
                    FlowRow(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(5.dp, Alignment.End),
                        verticalArrangement = Arrangement.spacedBy(5.dp)
                    ) {
                        ayah.words.reversed().forEach { word ->
                            CompactWordChip(word = word, mode = translationLanguage)
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(5.dp))
            HorizontalDivider(
                color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f),
                thickness = 0.5.dp
            )
            Spacer(modifier = Modifier.height(4.dp))

            // Concise Sentence Translations based on selected language
            when (translationLanguage) {
                TranslationDisplayMode.BANGLA_ONLY -> {
                    Text(
                        text = ayah.translationBangla,
                        fontSize = settings.translationFontSizeSp.sp,
                        lineHeight = (settings.translationFontSizeSp * 1.35f).sp,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
                TranslationDisplayMode.ENGLISH_ONLY -> {
                    Text(
                        text = ayah.translationEnglish,
                        fontSize = settings.translationFontSizeSp.sp,
                        lineHeight = (settings.translationFontSizeSp * 1.35f).sp,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
                TranslationDisplayMode.BOTH -> {
                    Text(
                        text = "বাং: ${ayah.translationBangla}",
                        fontSize = settings.translationFontSizeSp.sp,
                        lineHeight = (settings.translationFontSizeSp * 1.35f).sp,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = "EN: ${ayah.translationEnglish}",
                        fontSize = (settings.translationFontSizeSp - 1f).sp,
                        lineHeight = (settings.translationFontSizeSp * 1.3f).sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}

/**
 * Compact Word Chip with only 2 lines: Arabic on top + Selected Language meaning underneath!
 */
@Composable
fun CompactWordChip(word: Word, mode: TranslationDisplayMode) {
    Surface(
        shape = RoundedCornerShape(6.dp),
        color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
        border = androidx.compose.foundation.BorderStroke(0.5.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.15f))
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 6.dp, vertical = 3.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Arabic Word
            Text(
                text = word.arabic,
                fontSize = 15.sp,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSurface,
                textAlign = TextAlign.Center
            )

            // Meaning in selected language (short & clean)
            val meaningText = when (mode) {
                TranslationDisplayMode.BANGLA_ONLY -> word.bangla
                TranslationDisplayMode.ENGLISH_ONLY -> word.english
                TranslationDisplayMode.BOTH -> "${word.bangla} (${word.english})"
            }

            Text(
                text = meaningText,
                fontSize = 10.sp,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.primary,
                textAlign = TextAlign.Center
            )
        }
    }
}
