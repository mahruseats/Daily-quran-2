package com.example.ui.util

import androidx.compose.ui.text.font.FontFamily
import com.example.ui.theme.TimesRomanFontFamily
import com.example.ui.viewmodel.TranslationDisplayMode

object AppStrings {

    fun isEnglish(mode: TranslationDisplayMode): Boolean =
        mode == TranslationDisplayMode.ENGLISH_ONLY

    fun isBangla(mode: TranslationDisplayMode): Boolean =
        mode == TranslationDisplayMode.BANGLA_ONLY

    fun font(mode: TranslationDisplayMode): FontFamily =
        if (isEnglish(mode)) TimesRomanFontFamily else FontFamily.Default

    // Top App Bar
    fun appTitle(mode: TranslationDisplayMode): String =
        if (isEnglish(mode)) "Daily Quran" else "দৈনিক কুরআন"

    fun upcomingSalah(mode: TranslationDisplayMode): String =
        if (isEnglish(mode)) "Next Salah" else "পরবর্তী নামাজ"

    // Bottom Navigation
    fun navSurahs(mode: TranslationDisplayMode): String =
        if (isEnglish(mode)) "Surahs" else "সূরাসমূহ"

    fun navJuz(mode: TranslationDisplayMode): String =
        if (isEnglish(mode)) "Juz / Para" else "পারা / জুয"

    fun navPrayerTimes(mode: TranslationDisplayMode): String =
        if (isEnglish(mode)) "Prayer Times" else "নামাজ"

    fun navDuas(mode: TranslationDisplayMode): String =
        if (isEnglish(mode)) "Rabbana Duas" else "দোয়া"

    fun navBookmarks(mode: TranslationDisplayMode): String =
        if (isEnglish(mode)) "Bookmarks" else "বুকমার্ক"

    // Search Bar
    fun searchSurahPlaceholder(mode: TranslationDisplayMode): String =
        if (isEnglish(mode)) "Search Surah (e.g. Fatihah, 36, Yaseen, Baqarah...)"
        else "সূরা খুঁজুন (যেমন: ফাতিহা, ৩৬, ইয়াসিন, বাকারা...)"

    fun searchDuasPlaceholder(mode: TranslationDisplayMode): String =
        if (isEnglish(mode)) "Search Duas (keyword, topic, surah, reference...)"
        else "দোয়া খুঁজুন (বিষয়, সূরা, উচ্চারণ...)"

    // Last Read & Section Headers
    fun lastReadHeader(mode: TranslationDisplayMode): String =
        if (isEnglish(mode)) "Resume Reading" else "সর্বশেষ পড়া"

    fun readButton(mode: TranslationDisplayMode): String =
        if (isEnglish(mode)) "Read" else "পড়ুন"

    fun verseNumber(mode: TranslationDisplayMode, num: Int): String =
        if (isEnglish(mode)) "Verse $num" else "আয়াত নং $num"

    fun allSurahsHeader(mode: TranslationDisplayMode): String =
        if (isEnglish(mode)) "All 114 Surahs" else "সকল ১১৪টি সূরা"

    fun surahsCount(mode: TranslationDisplayMode, count: Int): String =
        if (isEnglish(mode)) "$count Surahs" else "$count টি সূরা"

    fun versesCount(mode: TranslationDisplayMode, count: Int, juz: Int): String =
        if (isEnglish(mode)) "$count Verses • Juz $juz" else "$count আয়াত • পারা $juz"

    fun revelationMeccan(mode: TranslationDisplayMode): String =
        if (isEnglish(mode)) "Meccan" else "মক্কী"

    fun revelationMedinan(mode: TranslationDisplayMode): String =
        if (isEnglish(mode)) "Medinan" else "মাদানী"

    fun meaningPrefix(mode: TranslationDisplayMode): String =
        if (isEnglish(mode)) "Meaning: " else "অর্থ: "

    // Juz / Para List
    fun juzHeader(mode: TranslationDisplayMode): String =
        if (isEnglish(mode)) "Select Juz (1 to 30):" else "পারা নির্বাচন করুন (১ হতে ৩০):"

    fun juzTitle(mode: TranslationDisplayMode, num: Int): String =
        if (isEnglish(mode)) "Juz $num" else "পারা $num"

    fun juzRange(mode: TranslationDisplayMode, startSurah: String, startAyah: Int, endSurah: String, endAyah: Int): String =
        if (isEnglish(mode)) "From $startSurah (Verse $startAyah) to $endSurah (Verse $endAyah)"
        else "শুরু: $startSurah (আয়াত $startAyah) হতে $endSurah (আয়াত $endAyah)"

    // Bookmarks
    fun noBookmarks(mode: TranslationDisplayMode): String =
        if (isEnglish(mode)) "No Bookmarks Saved" else "কোনো সংরক্ষিত বুকমার্ক নেই"

    fun noBookmarksHint(mode: TranslationDisplayMode): String =
        if (isEnglish(mode)) "Tap the bookmark icon on any Ayah while reading to save it here"
        else "সূরা পড়ার সময় বুকমার্ক আইকনে ট্যাপ করে আয়াত সংরক্ষণ করুন"

    fun bookmarkAyahHeader(mode: TranslationDisplayMode, surahEng: String, surahBan: String, ayahNum: Int): String =
        if (isEnglish(mode)) "$surahEng • Verse $ayahNum"
        else "সূরা $surahBan ($surahEng) • আয়াত $ayahNum"

    // Prayer Times
    fun prayerTimesTitle(mode: TranslationDisplayMode): String =
        if (isEnglish(mode)) "Prayer Times & Qibla Compass" else "নামাজের সময়সূচি ও কিবলা কম্পাস"

    fun todaySalahSchedule(mode: TranslationDisplayMode): String =
        if (isEnglish(mode)) "Today's Prayer Times (Salah Schedule)" else "আজকের নামাজের সময়সূচি"

    fun nextPrayerLabel(mode: TranslationDisplayMode): String =
        if (isEnglish(mode)) "Next Prayer: " else "পরবর্তী নামাজ: "

    fun nextPrayerBadge(mode: TranslationDisplayMode): String =
        if (isEnglish(mode)) "Next Prayer" else "পরবর্তী নামাজ"

    fun timeLeftLabel(mode: TranslationDisplayMode): String =
        if (isEnglish(mode)) "Time Left: " else "বাকি আছে: "

    fun qiblaDirection(mode: TranslationDisplayMode, degrees: Int): String =
        if (isEnglish(mode)) "Qibla: $degrees°" else "কিবলা: $degrees°"

    // Surah Reader
    fun playFullSurah(mode: TranslationDisplayMode): String =
        if (isEnglish(mode)) "Listen to Full Surah Recitation" else "সম্পূর্ণ সূরা তিলাওয়াত শুনুন"

    fun pauseSurah(mode: TranslationDisplayMode): String =
        if (isEnglish(mode)) "Pause Surah" else "বিরতি দিন"

    fun wordsToggle(mode: TranslationDisplayMode): String =
        if (isEnglish(mode)) "Words" else "শব্দার্থ"

    fun sajdahBadge(mode: TranslationDisplayMode): String =
        if (isEnglish(mode)) "Sajdah" else "সাজদাহ"

    fun copiedToast(mode: TranslationDisplayMode): String =
        if (isEnglish(mode)) "Copied to clipboard" else "ক্লিপবোর্ডে কপি করা হয়েছে"

    fun playAyahDesc(mode: TranslationDisplayMode): String =
        if (isEnglish(mode)) "Play Ayah" else "আয়াত শুনুন"

    fun bookmarkDesc(mode: TranslationDisplayMode): String =
        if (isEnglish(mode)) "Bookmark" else "বুকমার্ক"

    fun tafsirDesc(mode: TranslationDisplayMode): String =
        if (isEnglish(mode)) "Tafsir" else "তাফসীর"

    fun copyDesc(mode: TranslationDisplayMode): String =
        if (isEnglish(mode)) "Copy" else "কপি"

    fun readerSubTitle(mode: TranslationDisplayMode, surahNum: Int, totalAyahs: Int, juzNum: Int): String =
        if (isEnglish(mode)) "Surah $surahNum • $totalAyahs Verses • Juz $juzNum"
        else "সূরা নং $surahNum • $totalAyahs আয়াত • পারা $juzNum"

    // Rabbana Duas
    fun duasTitle(mode: TranslationDisplayMode): String =
        if (isEnglish(mode)) "40 Rabbana Duas" else "৪০টি রাব্বানা দোয়া"

    fun duasSubtitle(mode: TranslationDisplayMode): String =
        if (isEnglish(mode)) "Supplications from the Holy Quran" else "কুরআনুল কারীম থেকে সংকলিত সকল দোয়া"

    fun duaBadge(mode: TranslationDisplayMode, id: Int): String =
        if (isEnglish(mode)) "Dua #$id" else "দোয়া নং $id"

    fun duaSurahRef(mode: TranslationDisplayMode, surahEng: String, surahBan: String, ref: String): String =
        if (isEnglish(mode)) "Surah $surahEng ($ref)" else "সূরা $surahBan ($ref)"

    fun pronunciationPrefix(mode: TranslationDisplayMode): String =
        if (isEnglish(mode)) "Pronunciation: " else "উচ্চারণ: "

    fun translationPrefix(mode: TranslationDisplayMode): String =
        if (isEnglish(mode)) "Meaning: " else "অর্থ: "

    fun virtuesPrefix(mode: TranslationDisplayMode): String =
        if (isEnglish(mode)) "Virtues & Context: " else "ফযিলত ও প্রেক্ষাপট: "

    fun duaCopiedToast(mode: TranslationDisplayMode): String =
        if (isEnglish(mode)) "Dua copied to clipboard" else "দোয়া ক্লিপবোর্ডে কপি করা হয়েছে"

    // Tafsir Bottom Sheet
    fun tafsirTitle(mode: TranslationDisplayMode): String =
        if (isEnglish(mode)) "Tafsir & Commentary" else "তাফসীর ও ব্যাখ্যা"

    fun tafsirEnglishTab(mode: TranslationDisplayMode): String =
        if (isEnglish(mode)) "English Tafsir" else "ইংরেজি তাফসীর"

    fun tafsirBanglaTab(mode: TranslationDisplayMode): String =
        if (isEnglish(mode)) "Bangla Tafsir" else "বাংলা তাফসীর"

    // Settings Screen
    fun settingsTitle(mode: TranslationDisplayMode): String =
        if (isEnglish(mode)) "Quran Settings" else "কুরআন সেটিংস"

    fun settingsSubtitle(mode: TranslationDisplayMode): String =
        if (isEnglish(mode)) "Reading & Recitation Customization" else "পাঠ ও তিলাওয়াত কাস্টমাইজেশন"

    fun settingsTabTheme(mode: TranslationDisplayMode): String =
        if (isEnglish(mode)) "🎨 Theme" else "🎨 পঠন থিম"

    fun settingsTabReciter(mode: TranslationDisplayMode): String =
        if (isEnglish(mode)) "🎙️ Reciter" else "🎙️ তিলাওয়াতকারী"

    fun settingsTabTranslation(mode: TranslationDisplayMode): String =
        if (isEnglish(mode)) "📖 Translation" else "📖 অনুবাদ"

    fun settingsTabFontSize(mode: TranslationDisplayMode): String =
        if (isEnglish(mode)) "🔤 Font Size" else "🔤 হরফের আকার"

    fun settingsTabBg(mode: TranslationDisplayMode): String =
        if (isEnglish(mode)) "🌈 Background" else "🌈 ব্যাকগ্রাউন্ড"

    fun settingsTabTextColor(mode: TranslationDisplayMode): String =
        if (isEnglish(mode)) "✍️ Text Color" else "✍️ টেক্সট রঙ"

    // Audio Player Bar
    fun audioAyahReciter(mode: TranslationDisplayMode, ayahNum: Int, reciterName: String): String =
        if (isEnglish(mode)) "Verse $ayahNum • $reciterName" else "আয়াত $ayahNum • $reciterName"

    fun audioRecitationDefault(mode: TranslationDisplayMode): String =
        if (isEnglish(mode)) "Audio Recitation" else "অডিও তিলাওয়াত"
}
