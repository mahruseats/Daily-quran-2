package com.example

import com.example.data.repository.PrayerCalculator
import com.example.data.source.JuzCatalog
import com.example.data.source.QuranTextProvider
import com.example.data.source.RabbanaDuasData
import com.example.data.source.SurahCatalog
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test

class ExampleUnitTest {

    @Test
    fun testSurahCatalogCount() {
        assertEquals("Quran must contain 114 Surahs", 114, SurahCatalog.surahs.size)
    }

    @Test
    fun testSurahSearch() {
        val searchFatihah = SurahCatalog.searchSurahs("Fatihah")
        assertTrue(searchFatihah.any { it.number == 1 })

        val searchBangla = SurahCatalog.searchSurahs("ফাতিহা")
        assertTrue(searchBangla.any { it.number == 1 })

        val searchByNumber = SurahCatalog.searchSurahs("36")
        assertTrue(searchByNumber.any { it.nameEnglish == "Ya-Sin" })

        // Spelling mistakes / typos should return closest matching Surahs
        val typoFatihah = SurahCatalog.searchSurahs("fatiah")
        assertEquals("Al-Fatihah should be the top match for 'fatiah'", 1, typoFatihah.first().number)

        val typoBaqarah = SurahCatalog.searchSurahs("bakara")
        assertEquals("Al-Baqarah should be the top match for 'bakara'", 2, typoBaqarah.first().number)

        val typoYasin = SurahCatalog.searchSurahs("yaseen")
        assertEquals("Ya-Sin should be the top match for 'yaseen'", 36, typoYasin.first().number)

        val typoMulk = SurahCatalog.searchSurahs("mullk")
        assertEquals("Al-Mulk should be the top match for 'mullk'", 67, typoMulk.first().number)
    }

    @Test
    fun testJuzCatalogCount() {
        assertEquals("Quran must contain 30 Juz", 30, JuzCatalog.juzList.size)
        assertEquals("Juz 1 starts with Al-Fatihah", 1, JuzCatalog.juzList[0].startSurahNumber)
        assertEquals("Juz 30 ends with An-Nas", 114, JuzCatalog.juzList[29].endSurahNumber)
    }

    @Test
    fun testRabbanaDuasCountAndCompleteness() {
        assertEquals("Must contain 40 Rabbana Duas", 40, RabbanaDuasData.duas.size)
        RabbanaDuasData.duas.forEach { dua ->
            assertTrue("Dua ${dua.id} must have Arabic text", dua.arabicText.isNotEmpty())
            assertTrue("Dua ${dua.id} must have Bangla translation", dua.banglaTranslation.isNotEmpty())
            assertTrue("Dua ${dua.id} must have English translation", dua.englishTranslation.isNotEmpty())
            assertTrue("Dua ${dua.id} must have audio URL", dua.audioUrl.isNotEmpty())
        }
    }

    @Test
    fun testWordByWordAyahs() {
        val fatihahAyahs = QuranTextProvider.getAyahsForSurah(1)
        assertEquals("Al-Fatihah has 7 ayahs", 7, fatihahAyahs.size)
        assertTrue("Ayah 1 has words breakdown", fatihahAyahs[0].words.isNotEmpty())
        assertEquals("First word is Bismillah", "بِسْمِ", fatihahAyahs[0].words[0].arabic)
        assertNotNull("Ayah has Bangla translation", fatihahAyahs[0].translationBangla)
        assertNotNull("Ayah has English translation", fatihahAyahs[0].translationEnglish)
        assertNotNull("Ayah has Tafsir", fatihahAyahs[0].tafsirBangla)
    }

    @Test
    fun testPrayerCalculation() {
        val dhaka = PrayerCalculator.supportedCities[0]
        val times = PrayerCalculator.calculatePrayerTimes(dhaka)
        assertNotNull(times.fajr)
        assertNotNull(times.dhuhr)
        assertNotNull(times.asr)
        assertNotNull(times.maghrib)
        assertNotNull(times.isha)
        assertTrue(times.nextPrayerName.isNotEmpty())
        assertTrue(times.nextPrayerTimeLeft.isNotEmpty())
    }
}
