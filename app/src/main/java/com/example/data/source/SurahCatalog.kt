package com.example.data.source

import com.example.data.model.RevelationType
import com.example.data.model.Surah

object SurahCatalog {
    val surahs: List<Surah> = listOf(
        Surah(1, "الفاتحة", "Al-Fatihah", "আল-ফাতিহা", "The Opening", "সূচনা", 7, RevelationType.MECCAN, 1),
        Surah(2, "البقرة", "Al-Baqarah", "আল-বাকারা", "The Cow", "বকনা-বাছুর", 286, RevelationType.MEDINAN, 1),
        Surah(3, "آل عمران", "Ali 'Imran", "আল ইমরান", "Family of Imran", "ইমরানের পরিবার", 200, RevelationType.MEDINAN, 3),
        Surah(4, "النساء", "An-Nisa", "আন-নিসা", "The Women", "নারী", 176, RevelationType.MEDINAN, 4),
        Surah(5, "المائدة", "Al-Ma'idah", "আল-মায়িদাহ", "The Table Spread", "খাদ্য পরিবেশিত টেবিল", 120, RevelationType.MEDINAN, 6),
        Surah(6, "الأنعام", "Al-An'am", "আল-আনআম", "The Cattle", "গৃহপালিত পশু", 165, RevelationType.MECCAN, 7),
        Surah(7, "الأعراف", "Al-A'raf", "আল-আরাফ", "The Heights", "উঁচু স্থানসমূহ", 206, RevelationType.MECCAN, 8),
        Surah(8, "الأنفال", "Al-Anfal", "আল-আনফাল", "The Spoils of War", "যুদ্ধলব্ধ সম্পদ", 75, RevelationType.MEDINAN, 9),
        Surah(9, "التوبة", "At-Tawbah", "আত-তাওবাহ", "The Repentance", "অনুশোচনা", 129, RevelationType.MEDINAN, 10),
        Surah(10, "يونس", "Yunus", "ইউনুস", "Jonah", "নবী ইউনুস", 109, RevelationType.MECCAN, 11),
        Surah(11, "هود", "Hud", "হুদ", "Hud", "নবী হুদ", 123, RevelationType.MECCAN, 11),
        Surah(12, "يوسف", "Yusuf", "ইউসুফ", "Joseph", "নবী ইউসুফ", 111, RevelationType.MECCAN, 12),
        Surah(13, "الرعد", "Ar-Ra'd", "আর-রাদ", "The Thunder", "বজ্রপাত", 43, RevelationType.MEDINAN, 13),
        Surah(14, "إبراهيم", "Ibrahim", "ইব্রাহিম", "Abraham", "নবী ইব্রাহিম", 52, RevelationType.MECCAN, 13),
        Surah(15, "الحجر", "Al-Hijr", "আল-হিজর", "The Rocky Tract", "পাথুরে পাহাড়", 99, RevelationType.MECCAN, 14),
        Surah(16, "النحل", "An-Nahl", "আন-নাহল", "The Bee", "মৌমাছি", 128, RevelationType.MECCAN, 14),
        Surah(17, "الإسراء", "Al-Isra", "আল-ইসরা", "The Night Journey", "রজনী ভ্রমণ", 111, RevelationType.MECCAN, 15),
        Surah(18, "الكهف", "Al-Kahf", "আল-কাহফ", "The Cave", "গুহা", 110, RevelationType.MECCAN, 15),
        Surah(19, "مريم", "Maryam", "মারইয়াম", "Mary", "মারইয়াম", 98, RevelationType.MECCAN, 16),
        Surah(20, "طه", "Taha", "ত্বোয়া-হা", "Ta-Ha", "ত্বা-হা", 135, RevelationType.MECCAN, 16),
        Surah(21, "الأنبياء", "Al-Anbiya", "আল-আম্বিয়া", "The Prophets", "নবীগণ", 112, RevelationType.MECCAN, 17),
        Surah(22, "الحج", "Al-Hajj", "আল-হাজ্জ", "The Pilgrimage", "হজ্ব", 78, RevelationType.MEDINAN, 17),
        Surah(23, "المؤمنون", "Al-Mu'minun", "আল-মুমিনূন", "The Believers", "মুমিনগণ", 118, RevelationType.MECCAN, 18),
        Surah(24, "النور", "An-Nur", "আন-নূর", "The Light", "আলো", 64, RevelationType.MEDINAN, 18),
        Surah(25, "الفرقان", "Al-Furqan", "আল-ফুরকান", "The Criterion", "সত্য-মিথ্যার পার্থক্যকারী", 77, RevelationType.MECCAN, 18),
        Surah(26, "الشعراء", "Ash-Shu'ara", "আশ-শুয়ারা", "The Poets", "কবিগণ", 227, RevelationType.MECCAN, 19),
        Surah(27, "النمل", "An-Naml", "আন-নামল", "The Ant", "পিপীলিকা", 93, RevelationType.MECCAN, 19),
        Surah(28, "القصص", "Al-Qasas", "আল-কাসাস", "The Stories", "কাহিনি", 88, RevelationType.MECCAN, 20),
        Surah(29, "العنكبوت", "Al-'Ankabut", "আল-আনকাবূত", "The Spider", "মাকড়সা", 69, RevelationType.MECCAN, 20),
        Surah(30, "الروم", "Ar-Rum", "আর-রূম", "The Romans", "রোমান জাতি", 60, RevelationType.MECCAN, 21),
        Surah(31, "لقمان", "Luqman", "লুকমান", "Luqman", "জ্ঞানী লুকমান", 34, RevelationType.MECCAN, 21),
        Surah(32, "السجدة", "As-Sajdah", "আস-সাজদাহ", "The Prostration", "সিজদা", 30, RevelationType.MECCAN, 21),
        Surah(33, "الأحزاب", "Al-Ahzab", "আল-আহযাব", "The Combined Forces", "জোটবদ্ধ বাহিনী", 73, RevelationType.MEDINAN, 21),
        Surah(34, "سبإ", "Saba", "সাবা", "Sheba", "রানি সাবা", 54, RevelationType.MECCAN, 22),
        Surah(35, "فاطر", "Fatir", "ফাতির", "Originator", "সৃষ্টিকর্তা", 45, RevelationType.MECCAN, 22),
        Surah(36, "يس", "Ya-Sin", "ইয়াসীন", "Ya-Sin", "ইয়াসীন (হৃদয়)", 83, RevelationType.MECCAN, 22),
        Surah(37, "الصافات", "As-Saffat", "আস-সাফফাত", "Those who set the Ranks", "সারিবদ্ধভাবে দাঁড়ানো", 182, RevelationType.MECCAN, 23),
        Surah(38, "ص", "Sad", "সোয়াদ", "The Letter Sad", "সোয়াদ", 88, RevelationType.MECCAN, 23),
        Surah(39, "الزمر", "Az-Zumar", "আজ-জুমার", "The Troops", "দলবদ্ধ জনতা", 75, RevelationType.MECCAN, 23),
        Surah(40, "غافر", "Ghafir", "গাফির", "The Forgiver", "ক্ষমাকারী", 85, RevelationType.MECCAN, 24),
        Surah(41, "فصلت", "Fussilat", "ফুসসিলাত", "Explained in Detail", "স্পষ্ট বর্ণনা", 54, RevelationType.MECCAN, 24),
        Surah(42, "الشورى", "Ash-Shura", "আশ-শূরা", "The Consultation", "পরামর্শ", 53, RevelationType.MECCAN, 25),
        Surah(43, "الزخرف", "Az-Zukhruf", "আজ-জুখরুফ", "The Ornaments of Gold", "সোনার অলংকার", 89, RevelationType.MECCAN, 25),
        Surah(44, "الدخان", "Ad-Dukhan", "আদ-দুখান", "The Smoke", "ধোঁয়া", 59, RevelationType.MECCAN, 25),
        Surah(45, "الجاثية", "Al-Jathiyah", "আল-জাসিয়া", "The Crouching", "নতজানু", 37, RevelationType.MECCAN, 25),
        Surah(46, "الأحقاف", "Al-Ahqaf", "আল-আহকাফ", "The Wind-Curved Sandhills", "বালুর পাহাড়", 35, RevelationType.MECCAN, 26),
        Surah(47, "محمد", "Muhammad", "মুহাম্মদ", "Muhammad", "নবী মুহাম্মদ", 38, RevelationType.MEDINAN, 26),
        Surah(48, "الفتح", "Al-Fath", "আল-ফাতহ", "The Victory", "বিজয়", 29, RevelationType.MEDINAN, 26),
        Surah(49, "الحجرات", "Al-Hujurat", "আল-হুজুরাত", "The Rooms", "বাসগৃহসমূহ", 18, RevelationType.MEDINAN, 26),
        Surah(50, "ق", "Qaf", "কাফ", "The Letter Qaf", "কাফ", 45, RevelationType.MECCAN, 26),
        Surah(51, "الذاريات", "Adh-Dhariyat", "আদ-ধারিয়াত", "The Winnowing Winds", "বিক্ষিপ্তকারী বাতাস", 60, RevelationType.MECCAN, 26),
        Surah(52, "الطور", "At-Tur", "আত-তূর", "The Mount", "তূর পাহাড়", 49, RevelationType.MECCAN, 27),
        Surah(53, "النجم", "An-Najm", "আন-নাজম", "The Star", "নক্ষত্র", 62, RevelationType.MECCAN, 27),
        Surah(54, "القمر", "Al-Qamar", "আল-ক্বামার", "The Moon", "চাঁদ", 55, RevelationType.MECCAN, 27),
        Surah(55, "الرحمن", "Ar-Rahman", "আর-রাহমান", "The Beneficent", "পরম করুণাময়", 78, RevelationType.MEDINAN, 27),
        Surah(56, "الواقعة", "Al-Waqi'ah", "আল-ওয়াকিয়াহ", "The Inevitable", "সুনিশ্চিত ঘটনা", 96, RevelationType.MECCAN, 27),
        Surah(57, "الحديد", "Al-Hadid", "আল-হাদীদ", "The Iron", "লোহা", 29, RevelationType.MEDINAN, 27),
        Surah(58, "المجادلة", "Al-Mujadila", "আল-মুজাদালাহ", "The Pleading Woman", "বিতর্ককারিণী নারী", 22, RevelationType.MEDINAN, 28),
        Surah(59, "الحشر", "Al-Hashr", "আল-হাশর", "The Exile", "সমাবেশ ও নির্বাসন", 24, RevelationType.MEDINAN, 28),
        Surah(60, "الممتحنة", "Al-Mumtahanah", "আল-মুমতাহানাহ", "She That is to be Examined", "পরীক্ষিতা নারী", 13, RevelationType.MEDINAN, 28),
        Surah(61, "الصف", "As-Saf", "আস-সাফ", "The Ranks", "সারিবদ্ধ সৈন্য", 14, RevelationType.MEDINAN, 28),
        Surah(62, "الجمعة", "Al-Jumu'ah", "আল-জুমুআহ", "Friday", "শুক্রবার", 11, RevelationType.MEDINAN, 28),
        Surah(63, "المنافقون", "Al-Munafiqun", "আল-মুনাফিকূন", "The Hypocrites", "কপট বিশ্বাসীগণ", 11, RevelationType.MEDINAN, 28),
        Surah(64, "التغابن", "At-Taghabun", "আত-তাগাবুন", "The Mutual Disillusion", "লাভ-ক্ষতির দিন", 18, RevelationType.MEDINAN, 28),
        Surah(65, "الطلاق", "At-Talaq", "আত-তালাক", "The Divorce", "তালাক", 12, RevelationType.MEDINAN, 28),
        Surah(66, "التحريم", "At-Tahrim", "আত-তাহরীম", "The Prohibition", "নিষিদ্ধকরণ", 12, RevelationType.MEDINAN, 28),
        Surah(67, "الملك", "Al-Mulk", "আল-মুলক", "The Sovereignty", "সার্বভৌম কর্তৃত্ব", 30, RevelationType.MECCAN, 29),
        Surah(68, "القلم", "Al-Qalam", "আল-কলম", "The Pen", "কলম", 52, RevelationType.MECCAN, 29),
        Surah(69, "الحاقة", "Al-Haqqah", "আল-হাক্কাহ", "The Inevitable Reality", "অবশ্যম্ভাবী সত্য", 52, RevelationType.MECCAN, 29),
        Surah(70, "المعارج", "Al-Ma'arij", "আল-মাআরিজ", "The Ascending Stairways", "উর্ধ্বগমনের সোপান", 44, RevelationType.MECCAN, 29),
        Surah(71, "نوح", "Nuh", "নূহ", "Noah", "নবী নূহ", 28, RevelationType.MECCAN, 29),
        Surah(72, "الجن", "Al-Jinn", "আল-জ্বিন", "The Jinn", "জ্বিন জাতি", 28, RevelationType.MECCAN, 29),
        Surah(73, "المزمل", "Al-Muzzammil", "আল-মুযযাম্মিল", "The Enshrouded One", "বস্ত্রাবৃত", 20, RevelationType.MECCAN, 29),
        Surah(74, "المدثر", "Al-Muddaththir", "আল-মুদ্দাসসির", "The Cloaked One", "চাদরাবৃত", 56, RevelationType.MECCAN, 29),
        Surah(75, "القيامة", "Al-Qiyamah", "আল-কিয়ামাহ", "The Resurrection", "পুনরুত্থান দিবস", 40, RevelationType.MECCAN, 29),
        Surah(76, "الإنسان", "Al-Insan", "আল-ইনসান", "The Human", "মানবজাতি", 31, RevelationType.MEDINAN, 29),
        Surah(77, "المرسلات", "Al-Mursalat", "আল-মুরসালাত", "The Emissaries", "প্রেরিত বাতাস", 50, RevelationType.MECCAN, 29),
        Surah(78, "النبإ", "An-Naba", "আন-নাবা", "The Tidings", "মহা সংবাদ", 40, RevelationType.MECCAN, 30),
        Surah(79, "النازعات", "An-Nazi'at", "আন-নাযিয়াত", "Those Who Drag Forth", "উৎপাটনকারী ফেরেশতা", 46, RevelationType.MECCAN, 30),
        Surah(80, "عبس", "Abasa", "আবাসা", "He Frowned", "তিনি ভ্রূকুটি করলেন", 42, RevelationType.MECCAN, 30),
        Surah(81, "التكوير", "At-Takwir", "আত-তাকভীর", "The Overthrowing", "অন্ধকারাচ্ছন্ন হওয়া", 29, RevelationType.MECCAN, 30),
        Surah(82, "الانفطار", "Al-Infitar", "আল-ইনফিতার", "The Cleaving", "বিদীর্ণ হওয়া", 19, RevelationType.MECCAN, 30),
        Surah(83, "المطففين", "Al-Mutaffifin", "আল-মুতাফফিফিন", "The Defrauding", "ওজনে কম দানকারী", 36, RevelationType.MECCAN, 30),
        Surah(84, "الانشقاق", "Al-Inshiqaq", "আল-ইনশিক্বাক্ব", "The Splitting Open", "ফেটে যাওয়া", 25, RevelationType.MECCAN, 30),
        Surah(85, "البروج", "Al-Buruj", "আল-বুরূজ", "The Mansions of the Stars", "নক্ষত্রমণ্ডল", 22, RevelationType.MECCAN, 30),
        Surah(86, "الطارق", "At-Tariq", "আত-তারিক", "The Nightcommer", "রাতের আগমনকারী তারা", 17, RevelationType.MECCAN, 30),
        Surah(87, "الأعلى", "Al-A'la", "আল-আলা", "The Most High", "সর্বোচ্চ মহান", 19, RevelationType.MECCAN, 30),
        Surah(88, "الغاشية", "Al-Ghashiyah", "আল-গাশিয়াহ", "The Overwhelming", "আচ্ছন্নকারী কিয়ামত", 26, RevelationType.MECCAN, 30),
        Surah(89, "الفجر", "Al-Fajr", "আল-ফজর", "The Dawn", "ভোরবেলা", 30, RevelationType.MECCAN, 30),
        Surah(90, "البلد", "Al-Balad", "আল-বালাদ", "The City", "পবিত্র নগরী", 20, RevelationType.MECCAN, 30),
        Surah(91, "الشمس", "Ash-Shams", "আশ-শামস", "The Sun", "সূর্য", 15, RevelationType.MECCAN, 30),
        Surah(92, "الليل", "Al-Layl", "আল-লাইল", "The Night", "রাত্রি", 21, RevelationType.MECCAN, 30),
        Surah(93, "الضحى", "Ad-Duha", "আদ-দুহা", "The Morning Hours", "পূর্বাহ্ণ", 11, RevelationType.MECCAN, 30),
        Surah(94, "الشرح", "Ash-Sharh", "আশ-শারহ", "The Relief", "বক্ষ প্রশস্তকরণ", 8, RevelationType.MECCAN, 30),
        Surah(95, "التين", "At-Tin", "আত-তীন", "The Fig", "ডুমুর ফল", 8, RevelationType.MECCAN, 30),
        Surah(96, "العلق", "Al-'Alaq", "আল-আলাক", "The Clot", "রক্তপিণ্ড", 19, RevelationType.MECCAN, 30),
        Surah(97, "القدر", "Al-Qadr", "আল-ক্বদর", "The Power", "মহিমান্বিত রাত", 5, RevelationType.MECCAN, 30),
        Surah(98, "البينة", "Al-Bayyinah", "আল-বাইয়িনাহ", "The Clear Proof", "সুস্পষ্ট প্রমাণ", 8, RevelationType.MEDINAN, 30),
        Surah(99, "الزلزلة", "Az-Zalzalah", "আজ-যালযালাহ", "The Earthquake", "ভূমিকম্প", 8, RevelationType.MEDINAN, 30),
        Surah(100, "العاديات", "Al-'Adiyat", "আল-আদিয়াত", "The Courser", "অভিযানকারী অশ্ব", 11, RevelationType.MECCAN, 30),
        Surah(101, "القارعة", "Al-Qari'ah", "আল-কারিয়াহ", "The Calamity", "মহা বিপদ", 11, RevelationType.MECCAN, 30),
        Surah(102, "التكاثر", "At-Takathur", "আত-তাকাসুর", "The Rivalry in World Increase", "প্রাচুর্যের প্রতিযোগিতা", 8, RevelationType.MECCAN, 30),
        Surah(103, "العصر", "Al-'Asr", "আল-আসর", "The Declining Day", "মহাকালের শপথ", 3, RevelationType.MECCAN, 30),
        Surah(104, "الهمزة", "Al-Humazah", "আল-হুমাযাহ", "The Traducer", "পরনিন্দাকারী", 9, RevelationType.MECCAN, 30),
        Surah(105, "الفيل", "Al-Fil", "আল-ফীল", "The Elephant", "হাতি", 5, RevelationType.MECCAN, 30),
        Surah(106, "قريش", "Quraysh", "কুরাইশ", "Quraysh", "কুরাইশ বংশ", 4, RevelationType.MECCAN, 30),
        Surah(107, "الماعون", "Al-Ma'un", "আল-মাউন", "Small Kindnesses", "সাহায্য-সহযোগিতা", 7, RevelationType.MECCAN, 30),
        Surah(108, "الكوثر", "Al-Kawthar", "আল-কাউসার", "Abundance", "প্রচুর কল্যাণ", 3, RevelationType.MECCAN, 30),
        Surah(109, "الكافرون", "Al-Kafirun", "আল-কাফিরুন", "The Disbelievers", "অবিশ্বাসীগণ", 6, RevelationType.MECCAN, 30),
        Surah(110, "النصر", "An-Nasr", "আন-নাসর", "Divine Support", "ঐশ্বরিক সাহায্য", 3, RevelationType.MEDINAN, 30),
        Surah(111, "المسد", "Al-Masad", "আল-লাহাব / আল-মাসাদ", "The Palm Fiber", "খেজুরের রশি", 5, RevelationType.MECCAN, 30),
        Surah(112, "الإخلاص", "Al-Ikhlas", "আল-ইখলাস", "The Sincerity", "একনিষ্ঠতা ও তাওহীদ", 4, RevelationType.MECCAN, 30),
        Surah(113, "الفلق", "Al-Falaq", "আল-ফালাক", "The Daybreak", "ভোরের আলো", 5, RevelationType.MECCAN, 30),
        Surah(114, "الناس", "An-Nas", "আন-নাস", "Mankind", "মানবজাতি", 6, RevelationType.MECCAN, 30)
    )

    fun getSurah(number: Int): Surah? {
        return surahs.find { it.number == number }
    }

    /**
     * Intelligent closest-match fuzzy search for Surahs.
     * Handles spelling mistakes, phonetic variations, transpositions, and typos in English, Bangla, and Arabic.
     */
    fun searchSurahs(query: String): List<Surah> {
        val q = query.trim().lowercase()
        if (q.isEmpty()) return surahs

        // Exact Surah Number Match
        val num = q.toIntOrNull()
        if (num != null && num in 1..114) {
            val matched = surahs.find { it.number == num }
            if (matched != null) {
                return listOf(matched) + surahs.filter { it.number != num }
            }
        }

        val normalizedQuery = normalizeForFuzzy(q)

        data class ScoredSurah(val surah: Surah, val score: Double)

        val scoredList = surahs.map { surah ->
            val score = calculateMatchScore(surah, q, normalizedQuery)
            ScoredSurah(surah, score)
        }

        // Filter those with meaningful similarity (> 0.28) and sort by closest match first
        val relevant = scoredList
            .filter { it.score > 0.28 }
            .sortedByDescending { it.score }
            .map { it.surah }

        // If no strong match found due to severe typos, return top 6 closest
        return if (relevant.isNotEmpty()) {
            relevant
        } else {
            scoredList.sortedByDescending { it.score }.take(6).map { it.surah }
        }
    }

    private fun calculateMatchScore(surah: Surah, rawQuery: String, normalizedQuery: String): Double {
        val nameEn = surah.nameEnglish.lowercase()
        val nameBn = surah.nameBangla.lowercase()
        val nameAr = surah.nameArabic
        val meaningEn = surah.englishMeaning.lowercase()
        val meaningBn = surah.banglaMeaning.lowercase()

        // 1. Exact string contains (highest priority)
        if (nameEn == rawQuery || nameBn == rawQuery || nameAr == rawQuery) return 100.0
        if (nameEn.contains(rawQuery) || nameBn.contains(rawQuery) || nameAr.contains(rawQuery)) return 90.0
        if (meaningEn.contains(rawQuery) || meaningBn.contains(rawQuery)) return 75.0

        // 2. Normalized phonetic contains
        val normEn = normalizeForFuzzy(nameEn)
        val normBn = normalizeForFuzzy(nameBn)

        if (normEn == normalizedQuery || normBn == normalizedQuery) return 85.0
        if (normEn.contains(normalizedQuery) || normBn.contains(normalizedQuery)) return 80.0
        if (normalizedQuery.contains(normEn) && normEn.length >= 3) return 78.0

        // 3. Fuzzy Levenshtein Similarity on English name & root name (stripping 'al-')
        val rootEn = normEn.removePrefix("al")
        val rootQuery = normalizedQuery.removePrefix("al")

        val simEn = stringSimilarity(normEn, normalizedQuery)
        val simRoot = stringSimilarity(rootEn, rootQuery)
        val simBn = stringSimilarity(nameBn, rawQuery)
        val simMeaning = stringSimilarity(meaningEn, rawQuery)

        val maxSim = maxOf(simEn, simRoot, simBn, simMeaning * 0.8)
        return maxSim * 70.0
    }

    private fun normalizeForFuzzy(str: String): String {
        return str.lowercase()
            .replace("-", "")
            .replace("'", "")
            .replace("‘", "")
            .replace("’", "")
            .replace(" ", "")
            .replace("ee", "i")
            .replace("oo", "u")
            .replace("aa", "a")
            .replace("ph", "f")
            .replace("th", "t")
            .replace("dh", "z")
            .replace("kh", "k")
            .replace("gh", "g")
            .replace("q", "k")
            .replace("c", "k")
            .replace("j", "z")
            // Common Bengali spelling variations
            .replace("য়", "য")
            .replace("ড়", "র")
            .replace("ঢ়", "র")
            .replace("ী", "ি")
            .replace("ূ", "ু")
            .replace("ণ", "ন")
            .replace("ষ", "স")
            .replace("শ", "স")
    }

    private fun stringSimilarity(s1: String, s2: String): Double {
        if (s1.isEmpty() || s2.isEmpty()) return 0.0
        if (s1 == s2) return 1.0

        val distance = levenshteinDistance(s1, s2)
        val maxLen = maxOf(s1.length, s2.length)
        if (maxLen == 0) return 1.0

        return 1.0 - (distance.toDouble() / maxLen.toDouble())
    }

    private fun levenshteinDistance(s1: String, s2: String): Int {
        val dp = Array(s1.length + 1) { IntArray(s2.length + 1) }

        for (i in 0..s1.length) dp[i][0] = i
        for (j in 0..s2.length) dp[0][j] = j

        for (i in 1..s1.length) {
            for (j in 1..s2.length) {
                val cost = if (s1[i - 1] == s2[j - 1]) 0 else 1
                dp[i][j] = minOf(
                    dp[i - 1][j] + 1,       // deletion
                    dp[i][j - 1] + 1,       // insertion
                    dp[i - 1][j - 1] + cost // substitution
                )
            }
        }
        return dp[s1.length][s2.length]
    }
}
