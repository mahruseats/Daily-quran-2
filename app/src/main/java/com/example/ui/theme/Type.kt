package com.example.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.data.model.QuranScriptType

// Authentic Quranic Calligraphy Font Families installed from Google Fonts
val ScheherazadeQuranFontFamily = FontFamily(
    Font(R.font.scheherazade_new, FontWeight.Normal),
    Font(R.font.scheherazade_new, FontWeight.Medium),
    Font(R.font.scheherazade_new, FontWeight.Bold)
)

val AmiriQuranFontFamily = FontFamily(
    Font(R.font.amiri, FontWeight.Normal),
    Font(R.font.amiri, FontWeight.Bold)
)

val IndoPakLateefFontFamily = FontFamily(
    Font(R.font.lateef, FontWeight.Normal),
    Font(R.font.lateef, FontWeight.Bold)
)

// Times New Roman (Tinos) font family for English text
val TimesRomanFontFamily = FontFamily(
    Font(R.font.tinos, FontWeight.Normal),
    Font(R.font.tinos, FontWeight.Bold)
)

fun getAppFontFamily(isEnglish: Boolean): FontFamily {
    return if (isEnglish) TimesRomanFontFamily else FontFamily.Default
}

// Primary authentic Quran Arabic font matching user's reference calligraphy
val QuranArabicFontFamily = ScheherazadeQuranFontFamily

fun getFontFamilyForScript(scriptType: QuranScriptType): FontFamily {
    return when (scriptType) {
        QuranScriptType.UTHMANI -> ScheherazadeQuranFontFamily
        QuranScriptType.INDO_PAK -> IndoPakLateefFontFamily
        QuranScriptType.INDONESIAN_STANDARD -> AmiriQuranFontFamily
    }
}

// Set of Material typography styles with Times New Roman for text
val Typography =
  Typography(
    displayLarge = TextStyle(fontFamily = TimesRomanFontFamily),
    displayMedium = TextStyle(fontFamily = TimesRomanFontFamily),
    displaySmall = TextStyle(fontFamily = TimesRomanFontFamily),
    headlineLarge = TextStyle(fontFamily = TimesRomanFontFamily),
    headlineMedium = TextStyle(fontFamily = TimesRomanFontFamily),
    headlineSmall = TextStyle(fontFamily = TimesRomanFontFamily),
    titleLarge = TextStyle(fontFamily = TimesRomanFontFamily),
    titleMedium = TextStyle(fontFamily = TimesRomanFontFamily),
    titleSmall = TextStyle(fontFamily = TimesRomanFontFamily),
    bodyLarge =
      TextStyle(
        fontFamily = TimesRomanFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp,
      ),
    bodyMedium = TextStyle(fontFamily = TimesRomanFontFamily),
    bodySmall = TextStyle(fontFamily = TimesRomanFontFamily),
    labelLarge = TextStyle(fontFamily = TimesRomanFontFamily),
    labelMedium = TextStyle(fontFamily = TimesRomanFontFamily),
    labelSmall = TextStyle(fontFamily = TimesRomanFontFamily)
  )
