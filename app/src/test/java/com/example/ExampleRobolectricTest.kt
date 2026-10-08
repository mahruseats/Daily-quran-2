package com.example

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.data.source.QuranTextProvider
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class ExampleRobolectricTest {

  @Test
  fun `read string from context`() {
    val context = ApplicationProvider.getApplicationContext<Context>()
    val appName = context.getString(R.string.app_name)
    assertEquals("Daily Quran", appName)
  }

  @Test
  fun testSurahBaqarahAuthenticVerses() {
    val context = ApplicationProvider.getApplicationContext<Context>()
    val baqarahAyahs = QuranTextProvider.getAyahsForSurah(2, context)
    assertEquals("Al-Baqarah must have exactly 286 ayahs", 286, baqarahAyahs.size)
    assertTrue("Ayah 1 is Alif Lam Meem", baqarahAyahs[0].textArabic.contains("الٓمٓ") || baqarahAyahs[0].translationEnglish.contains("Alif"))
    assertTrue("Ayah 2 is Zalikal Kitabu", baqarahAyahs[1].textArabic.contains("ذَٰلِكَ"))
    // Verse 255 is Ayat al-Kursi
    val ayatAlKursi = baqarahAyahs[254]
    assertEquals("Ayat al-Kursi number is 255", 255, ayatAlKursi.number)
    assertTrue("Ayat al-Kursi has authentic Arabic text", ayatAlKursi.textArabic.contains("ٱللَّهُ") || ayatAlKursi.textArabic.contains("اللَّهُ"))
    assertTrue("Ayat al-Kursi has authentic Bangla translation", ayatAlKursi.translationBangla.contains("আল্লাহ ছাড়া অন্য কোন উপাস্য নেই"))
  }
}
