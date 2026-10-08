package com.example.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.material.icons.filled.AutoStories
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.QuranScriptType
import com.example.ui.theme.ArabicAccentGold

/**
 * Three Kinds of Quran Types:
 * 1. Indo-Pak (ইন্দো-পাক)
 * 2. Indonesian Standard Quran (ইন্দোনেশিয়ান স্ট্যান্ডার্ড)
 * 3. Osmani (উসমানী / Uthmani)
 */
@Composable
fun QuranScriptTypeSelector(
    selectedScript: QuranScriptType,
    onSelectScript: (QuranScriptType) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 14.dp, vertical = 3.dp)
            .background(
                color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.45f),
                shape = RoundedCornerShape(12.dp)
            )
            .padding(3.dp)
            .testTag("quran_script_selector_row"),
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        QuranScriptTabPill(
            title = "Indo-Pak",
            subtitle = "ইন্দো-পাক",
            isSelected = selectedScript == QuranScriptType.INDO_PAK,
            onClick = { onSelectScript(QuranScriptType.INDO_PAK) },
            modifier = Modifier.weight(1f)
        )
        QuranScriptTabPill(
            title = "Indonesian",
            subtitle = "ইন্দোনেশিয়ান",
            isSelected = selectedScript == QuranScriptType.INDONESIAN_STANDARD,
            onClick = { onSelectScript(QuranScriptType.INDONESIAN_STANDARD) },
            modifier = Modifier.weight(1f)
        )
        QuranScriptTabPill(
            title = "Osmani",
            subtitle = "উসমানী",
            isSelected = selectedScript == QuranScriptType.UTHMANI,
            onClick = { onSelectScript(QuranScriptType.UTHMANI) },
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
fun QuranScriptTabPill(
    title: String,
    subtitle: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val bgColor by animateColorAsState(
        targetValue = if (isSelected) MaterialTheme.colorScheme.primary else Color.Transparent,
        label = "pill_bg"
    )
    val textColor by animateColorAsState(
        targetValue = if (isSelected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurface,
        label = "pill_text"
    )
    val subtextColor by animateColorAsState(
        targetValue = if (isSelected) MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.85f) else MaterialTheme.colorScheme.onSurfaceVariant,
        label = "pill_subtext"
    )

    Surface(
        onClick = onClick,
        shape = RoundedCornerShape(10.dp),
        color = bgColor,
        modifier = modifier
            .height(38.dp)
            .testTag("quran_script_pill_${title.lowercase()}")
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp)
        ) {
            Text(
                text = title,
                fontSize = 11.sp,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.SemiBold,
                color = textColor,
                maxLines = 1
            )
            Text(
                text = subtitle,
                fontSize = 9.sp,
                color = subtextColor,
                maxLines = 1
            )
        }
    }
}

/**
 * Compact badge to display right beside the Quran name.
 * Tapping it quickly switches to the next script type.
 */
@Composable
fun QuranScriptBadgeBesideTitle(
    selectedScript: QuranScriptType,
    onCycleScript: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        onClick = onCycleScript,
        shape = RoundedCornerShape(8.dp),
        color = ArabicAccentGold.copy(alpha = 0.18f),
        border = BorderStroke(1.dp, ArabicAccentGold.copy(alpha = 0.6f)),
        modifier = modifier.testTag("quran_script_badge_beside_title")
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
        ) {
            Icon(
                imageVector = Icons.Default.AutoStories,
                contentDescription = "Quran Script Type",
                tint = ArabicAccentGold,
                modifier = Modifier.size(11.dp)
            )
            Spacer(modifier = Modifier.width(3.dp))
            Text(
                text = selectedScript.shortName,
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold,
                color = ArabicAccentGold
            )
        }
    }
}

/**
 * Comprehensive settings card for Quran Script Types with live preview.
 */
@Composable
fun QuranScriptSettingsCard(
    selectedScript: QuranScriptType,
    onSelectScript: (QuranScriptType) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f))
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "📜 কুরআন স্ক্রিপ্ট টাইপ (Quran Script Type)",
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
            Text(
                text = "আপনার পছন্দসই কুরআন টাইপ নির্বাচন করুন। এটি সমস্ত সূরা ও আয়াতে প্রদর্শিত হবে।",
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(top = 2.dp, bottom = 12.dp)
            )

            // 1. Indo-Pak Script Card
            ScriptOptionRow(
                script = QuranScriptType.INDO_PAK,
                isSelected = selectedScript == QuranScriptType.INDO_PAK,
                sampleArabic = "بِسۡمِ اللّٰهِ الرَّحۡمٰنِ الرَّحِیۡمِ",
                badgeText = "জনপ্রিয় (Bangladesh / Pakistan)",
                onClick = { onSelectScript(QuranScriptType.INDO_PAK) }
            )

            Spacer(modifier = Modifier.height(10.dp))

            // 2. Indonesian Standard Quran Card
            ScriptOptionRow(
                script = QuranScriptType.INDONESIAN_STANDARD,
                isSelected = selectedScript == QuranScriptType.INDONESIAN_STANDARD,
                sampleArabic = "بِسْمِ اللّٰهِ الرَّحْمٰنِ الرَّحِيْمِ",
                badgeText = "মুসহাফ কেমেনাং (Kemenag RI)",
                onClick = { onSelectScript(QuranScriptType.INDONESIAN_STANDARD) }
            )

            Spacer(modifier = Modifier.height(10.dp))

            // 3. Osmani / Uthmani Script Card
            ScriptOptionRow(
                script = QuranScriptType.UTHMANI,
                isSelected = selectedScript == QuranScriptType.UTHMANI,
                sampleArabic = "بِسْمِ ٱللَّهِ ٱلرَّحْمَٰنِ ٱلرَّحِيمِ",
                badgeText = "মদিনা স্ট্যান্ডার্ড (Al-Madinah)",
                onClick = { onSelectScript(QuranScriptType.UTHMANI) }
            )
        }
    }
}

@Composable
private fun ScriptOptionRow(
    script: QuranScriptType,
    isSelected: Boolean,
    sampleArabic: String,
    badgeText: String,
    onClick: () -> Unit
) {
    Surface(
        onClick = onClick,
        shape = RoundedCornerShape(12.dp),
        color = if (isSelected) MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f) else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f),
        border = BorderStroke(
            width = if (isSelected) 1.5.dp else 0.8.dp,
            color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outlineVariant
        ),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(24.dp)
                    .clip(CircleShape)
                    .background(if (isSelected) MaterialTheme.colorScheme.primary else Color.Transparent)
                    .border(
                        1.5.dp,
                        if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline,
                        CircleShape
                    )
            ) {
                if (isSelected) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = "Selected",
                        tint = MaterialTheme.colorScheme.onPrimary,
                        modifier = Modifier.size(16.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "${script.titleEnglish} (${script.titleBangla})",
                        fontWeight = FontWeight.Bold,
                        fontSize = 13.sp,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }

                Text(
                    text = script.description,
                    fontSize = 11.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Spacer(modifier = Modifier.height(4.dp))

                // Arabic Sample Preview
                Text(
                    text = sampleArabic,
                    fontFamily = com.example.ui.theme.getFontFamilyForScript(script),
                    fontSize = 18.sp,
                    lineHeight = 28.sp,
                    fontWeight = FontWeight.Normal,
                    color = ArabicAccentGold,
                    textAlign = TextAlign.Right,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}
