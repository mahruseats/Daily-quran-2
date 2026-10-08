package com.example.ui.viewmodel

import androidx.compose.ui.graphics.Color

/**
 * Three background colours when reading a surah:
 * 1. Paper: Authentic skin-toned paper background (similar to natural warm skin tone) with pure black text.
 *    Everything is paper colored except the text!
 * 2. Dark: Solid pure black background with pure white text. Everything is black except text, no gray!
 * 3. White: Pure white background with pure black text.
 */
enum class ReadingTheme(
    val id: String,
    val title: String,
    val titleBangla: String,
    val backgroundColor: Color,
    val cardBackgroundColor: Color,
    val primaryTextColor: Color,
    val secondaryTextColor: Color,
    val iconColor: Color,
    val surfaceColor: Color,
    val borderColor: Color = Color(0xFF2E7D32) // Selected green border from reference screenshot
) {
    PAPER(
        id = "paper",
        title = "Paper",
        titleBangla = "কাগজ",
        backgroundColor = Color(0xFFF0D0A4),      // Rich, warm bright skin-toned paper
        cardBackgroundColor = Color(0xFFF0D0A4),  // Everything is paper colored
        primaryTextColor = Color(0xFF000000),     // Solid black text
        secondaryTextColor = Color(0xFF000000),   // Solid black text
        iconColor = Color(0xFF000000),            // Solid black icons
        surfaceColor = Color(0xFFF0D0A4)          // Full paper surface
    ),
    DARK(
        id = "dark",
        title = "Dark",
        titleBangla = "কালো",
        backgroundColor = Color(0xFF000000),      // Solid pure black (everything black)
        cardBackgroundColor = Color(0xFF000000),  // Solid pure black card
        primaryTextColor = Color(0xFFFFFFFF),     // Solid pure white text (no gray)
        secondaryTextColor = Color(0xFFFFFFFF),   // Solid pure white text (no gray)
        iconColor = Color(0xFFFFFFFF),            // Solid pure white icons
        surfaceColor = Color(0xFF000000)          // Solid pure black surface
    ),
    WHITE(
        id = "white",
        title = "Modern",
        titleBangla = "সাদা",
        backgroundColor = Color(0xFFFFFFFF),      // Pure white
        cardBackgroundColor = Color(0xFFFFFFFF),  // Pure white card
        primaryTextColor = Color(0xFF000000),     // Solid black text
        secondaryTextColor = Color(0xFF000000),   // Solid black text
        iconColor = Color(0xFF000000),            // Solid black icons
        surfaceColor = Color(0xFFFFFFFF)          // Pure white surface
    )
}
