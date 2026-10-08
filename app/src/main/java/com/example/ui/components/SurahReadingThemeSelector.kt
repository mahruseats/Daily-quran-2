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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.viewmodel.ReadingTheme

/**
 * Compact horizontal theme switcher designed to sit directly beside the Surah name in the TopAppBar.
 */
@Composable
fun SurahReadingThemeMiniBar(
    selectedTheme: ReadingTheme,
    onSelectTheme: (ReadingTheme) -> Unit,
    isBangla: Boolean = false,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.testTag("surah_reading_theme_minibar"),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        ThemeMiniChip(
            theme = ReadingTheme.PAPER,
            isSelected = selectedTheme == ReadingTheme.PAPER,
            isBangla = isBangla,
            onClick = { onSelectTheme(ReadingTheme.PAPER) }
        )
        ThemeMiniChip(
            theme = ReadingTheme.DARK,
            isSelected = selectedTheme == ReadingTheme.DARK,
            isBangla = isBangla,
            onClick = { onSelectTheme(ReadingTheme.DARK) }
        )
        ThemeMiniChip(
            theme = ReadingTheme.WHITE,
            isSelected = selectedTheme == ReadingTheme.WHITE,
            isBangla = isBangla,
            onClick = { onSelectTheme(ReadingTheme.WHITE) }
        )
    }
}

@Composable
private fun ThemeMiniChip(
    theme: ReadingTheme,
    isSelected: Boolean,
    isBangla: Boolean = false,
    onClick: () -> Unit
) {
    val selectedBorder = if (isSelected) {
        BorderStroke(1.8.dp, Color(0xFF2E7D32)) // Crisp green border from user's image
    } else {
        BorderStroke(1.dp, if (theme == ReadingTheme.DARK) Color(0xFF333333) else if (theme == ReadingTheme.PAPER) Color(0xFFD6B587) else Color(0xFFE5E7EB))
    }

    Surface(
        onClick = onClick,
        shape = RoundedCornerShape(8.dp),
        color = theme.backgroundColor,
        border = selectedBorder,
        modifier = Modifier
            .height(28.dp)
            .testTag("theme_chip_${theme.id}")
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(horizontal = 7.dp, vertical = 2.dp)
        ) {
            // Little dot preview
            Box(
                modifier = Modifier
                    .size(8.dp)
                    .clip(RoundedCornerShape(4.dp))
                    .background(theme.primaryTextColor)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = if (isBangla) theme.titleBangla else theme.title,
                fontSize = 11.sp,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                color = theme.primaryTextColor
            )
        }
    }
}

/**
 * Exact replica of the reference screenshot showing 3 theme cards:
 * 1. Paper: Authentic paper background with black text
 * 2. Dark: Black background with white text
 * 3. Modern: White background with black text
 * Highlighted with green rounded border when selected.
 */
@Composable
fun SurahReadingThemeCardsRow(
    selectedTheme: ReadingTheme,
    onSelectTheme: (ReadingTheme) -> Unit,
    isBangla: Boolean = false,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .testTag("surah_reading_theme_cards_row"),
        horizontalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterHorizontally),
        verticalAlignment = Alignment.CenterVertically
    ) {
        ThemeCardItem(
            theme = ReadingTheme.PAPER,
            isSelected = selectedTheme == ReadingTheme.PAPER,
            isBangla = isBangla,
            onClick = { onSelectTheme(ReadingTheme.PAPER) },
            modifier = Modifier.weight(1f)
        )
        ThemeCardItem(
            theme = ReadingTheme.DARK,
            isSelected = selectedTheme == ReadingTheme.DARK,
            isBangla = isBangla,
            onClick = { onSelectTheme(ReadingTheme.DARK) },
            modifier = Modifier.weight(1f)
        )
        ThemeCardItem(
            theme = ReadingTheme.WHITE,
            isSelected = selectedTheme == ReadingTheme.WHITE,
            isBangla = isBangla,
            onClick = { onSelectTheme(ReadingTheme.WHITE) },
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
private fun ThemeCardItem(
    theme: ReadingTheme,
    isSelected: Boolean,
    isBangla: Boolean = false,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val selectedBorder = if (isSelected) {
        BorderStroke(2.dp, Color(0xFF2E7D32)) // Crisp green border from user's image
    } else {
        BorderStroke(1.dp, if (theme == ReadingTheme.DARK) Color(0xFF333333) else if (theme == ReadingTheme.PAPER) Color(0xFFD6B587) else Color(0xFFE5E7EB))
    }

    Card(
        onClick = onClick,
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = theme.backgroundColor),
        border = selectedBorder,
        elevation = CardDefaults.cardElevation(defaultElevation = if (isSelected) 3.dp else 1.dp),
        modifier = modifier
            .height(72.dp)
            .testTag("theme_card_${theme.id}")
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 6.dp, horizontal = 4.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Arabic sample "بِسْمِ اللَّهِ"
            Text(
                text = "بِسْمِ اللَّهِ",
                fontFamily = com.example.ui.theme.QuranArabicFontFamily,
                fontSize = 17.sp,
                fontWeight = FontWeight.Normal,
                color = theme.primaryTextColor,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(4.dp))
            // Theme Label (pure white in dark, pure black in paper/white)
            Text(
                text = if (isBangla) theme.titleBangla else theme.title,
                fontSize = 12.sp,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                color = theme.primaryTextColor,
                textAlign = TextAlign.Center
            )
        }
    }
}
