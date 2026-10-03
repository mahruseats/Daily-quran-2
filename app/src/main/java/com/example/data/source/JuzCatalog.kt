package com.example.data.source

import com.example.data.model.JuzInfo

object JuzCatalog {
    val juzList: List<JuzInfo> = listOf(
        JuzInfo(1, "الم", "আলিফ লাম মীম", "Alif Lam Meem", 1, "Al-Fatihah", 1, 2, "Al-Baqarah", 141),
        JuzInfo(2, "سيقول", "সায়াকূল", "Sayaqool", 2, "Al-Baqarah", 142, 2, "Al-Baqarah", 252),
        JuzInfo(3, "تلك الرسل", "তিলকার রুসুল", "Tilka'r-Rusul", 2, "Al-Baqarah", 253, 3, "Ali 'Imran", 92),
        JuzInfo(4, "لن تنالوا", "লান তানা-লূ", "Lan Tanaalu", 3, "Ali 'Imran", 93, 4, "An-Nisa", 23),
        JuzInfo(5, "والمحصنات", "ওয়াল মুহসানাত", "Wal Muhsanat", 4, "An-Nisa", 24, 4, "An-Nisa", 147),
        JuzInfo(6, "لا يحب الله", "লা ইউহিব্বুল্লাহ", "La Yuhibbullah", 4, "An-Nisa", 148, 5, "Al-Ma'idah", 81),
        JuzInfo(7, "وإذا سمعوا", "ওয়া ইযা সামিউ", "Wa Iza Sami'u", 5, "Al-Ma'idah", 82, 6, "Al-An'am", 110),
        JuzInfo(8, "ولو أننا", "ওয়ালাও আন্নানা", "Wa Lau Annana", 6, "Al-An'am", 111, 7, "Al-A'raf", 87),
        JuzInfo(9, "قال الملأ", "ক্বা-লাল মালাউ", "Qalal Mala'u", 7, "Al-A'raf", 88, 8, "Al-Anfal", 40),
        JuzInfo(10, "واعلموا", "ওয়া'লামূ", "Wa'lamu", 8, "Al-Anfal", 41, 9, "At-Tawbah", 92),
        JuzInfo(11, "يعتذرون", "ইয়া'তাযিরূন", "Ya'taziruna", 9, "At-Tawbah", 93, 11, "Hud", 5),
        JuzInfo(12, "وما من دابة", "ওয়ামা মিন দা-ব্বাহ", "Wa Ma Min Dabbah", 11, "Hud", 6, 12, "Yusuf", 52),
        JuzInfo(13, "وما أبرئ", "ওয়ামা উবাররিউ", "Wa Ma Ubarri'u", 12, "Yusuf", 53, 14, "Ibrahim", 52),
        JuzInfo(14, "ربما", "রুবামা", "Rubama", 15, "Al-Hijr", 1, 16, "An-Nahl", 128),
        JuzInfo(15, "سبحان الذي", "সুবহানাল্লাযী", "Subhana'llazi", 17, "Al-Isra", 1, 18, "Al-Kahf", 74),
        JuzInfo(16, "قال ألم", "ক্বা-লা আলাম", "Qala Alam", 18, "Al-Kahf", 75, 20, "Taha", 135),
        JuzInfo(17, "اقترب للناس", "ইক্বতারা বা লিন্নাস", "Iqtaraba Lin-Nas", 21, "Al-Anbiya", 1, 22, "Al-Hajj", 78),
        JuzInfo(18, "قد أفلح", "ক্বাদ আফলাহা", "Qad Aflaha", 23, "Al-Mu'minun", 1, 25, "Al-Furqan", 20),
        JuzInfo(19, "وقال الذين", "ওয়া ক্বালাল্লাযীনা", "Wa Qalal-Lazina", 25, "Al-Furqan", 21, 27, "An-Naml", 55),
        JuzInfo(20, "أمن خلق", "আম্মান খালাক্বা", "Amman Khalaqa", 27, "An-Naml", 56, 29, "Al-'Ankabut", 45),
        JuzInfo(21, "اتل ما أوحي", "উতলু মা ঊহিয়া", "Utlu Ma Uhiya", 29, "Al-'Ankabut", 46, 33, "Al-Ahzab", 30),
        JuzInfo(22, "ومن يقنت", "ওয়া মাইঁ ইয়াক্বনুত", "Wa Man Yaqnut", 33, "Al-Ahzab", 31, 36, "Ya-Sin", 27),
        JuzInfo(23, "وما أنزلنا", "ওয়ামা আনযালনা", "Wa Maliya", 36, "Ya-Sin", 28, 39, "Az-Zumar", 31),
        JuzInfo(24, "فمن أظلم", "ফামান আজলামু", "Fa-Man Azlamu", 39, "Az-Zumar", 32, 41, "Fussilat", 46),
        JuzInfo(25, "إليه يرد", "ইলাইহি ইউরাদ্দু", "Ilayhi Yuraddu", 41, "Fussilat", 47, 45, "Al-Jathiyah", 37),
        JuzInfo(26, "حم", "হা-মীম", "Ha'a-Meem", 46, "Al-Ahqaf", 1, 51, "Adh-Dhariyat", 30),
        JuzInfo(27, "قال فما خطبكم", "ক্বা-লা ফামা খাতবুকুম", "Qala Fama Khatbukum", 51, "Adh-Dhariyat", 31, 57, "Al-Hadid", 29),
        JuzInfo(28, "قد سمع الله", "ক্বাদ সামিআল্লাহু", "Qad Sami'Allahu", 58, "Al-Mujadila", 1, 66, "At-Tahrim", 12),
        JuzInfo(29, "تبارك الذي", "তাবারাকাল্লাযী", "Tabaraka'llazi", 67, "Al-Mulk", 1, 77, "Al-Mursalat", 50),
        JuzInfo(30, "عم يتساءلون", "আম্মা ইয়াতাসাআলূন", "'Amma Yatasa'alun", 78, "An-Naba", 1, 114, "An-Nas", 6)
    )

    fun getJuz(number: Int): JuzInfo? {
        return juzList.find { it.number == number }
    }
}
