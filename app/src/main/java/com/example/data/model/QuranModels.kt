package com.example.data.model

data class Surah(
    val number: Int,
    val nameArabic: String,
    val nameEnglish: String,
    val nameBangla: String,
    val englishMeaning: String,
    val banglaMeaning: String,
    val totalAyahs: Int,
    val revelationType: RevelationType,
    val startJuz: Int
)

enum class RevelationType {
    MECCAN, MEDINAN
}

data class Word(
    val arabic: String,
    val transliteration: String = "",
    val english: String,
    val bangla: String
)

data class Ayah(
    val number: Int, // Ayah number within Surah
    val globalNumber: Int = 0,
    val textArabic: String,
    val words: List<Word> = emptyList(),
    val translationEnglish: String,
    val translationBangla: String,
    val transliterationBangla: String = "",
    val tafsirEnglish: String = "",
    val tafsirBangla: String = "",
    val audioUrl: String = "",
    val juz: Int = 1,
    val sajdah: Boolean = false
)

data class JuzInfo(
    val number: Int,
    val nameArabic: String,
    val nameBangla: String,
    val nameEnglish: String,
    val startSurahNumber: Int,
    val startSurahName: String,
    val startAyah: Int,
    val endSurahNumber: Int,
    val endSurahName: String,
    val endAyah: Int
)

data class RabbanaDua(
    val id: Int,
    val surahNameEnglish: String,
    val surahNameBangla: String,
    val ayahReference: String,
    val arabicText: String,
    val banglaPronunciation: String,
    val englishTranslation: String,
    val banglaTranslation: String,
    val contextAndBenefits: String,
    val audioUrl: String
)

data class PrayerTiming(
    val nameEnglish: String,
    val nameBangla: String,
    val timeFormatted: String,
    val hour: Int,
    val minute: Int,
    val isCurrent: Boolean = false,
    val isNext: Boolean = false
)

data class PrayerTimesDay(
    val fajr: PrayerTiming,
    val sunrise: PrayerTiming,
    val dhuhr: PrayerTiming,
    val asr: PrayerTiming,
    val maghrib: PrayerTiming,
    val isha: PrayerTiming,
    val tahajjud: PrayerTiming,
    val nextPrayerName: String,
    val nextPrayerTimeLeft: String,
    val hijriDate: String,
    val gregorianDate: String,
    val cityName: String
)
