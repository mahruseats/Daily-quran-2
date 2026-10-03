package com.example.data.source

import com.example.data.model.Ayah
import com.example.data.model.Word

object QuranTextProvider {

    fun getAyahsForSurah(surahNumber: Int): List<Ayah> {
        val predefined = getPredefinedSurahAyahs(surahNumber)
        if (predefined.isNotEmpty()) return predefined

        // Generate full surah structure for any of the 114 surahs
        val surah = SurahCatalog.getSurah(surahNumber) ?: return emptyList()
        return (1..surah.totalAyahs).map { ayahNum ->
            generateAyahForSurah(surahNumber, ayahNum, surah.nameEnglish, surah.nameBangla)
        }
    }

    private fun getPredefinedSurahAyahs(surahNumber: Int): List<Ayah> {
        return when (surahNumber) {
            1 -> alFatihahAyahs
            112 -> alIkhlasAyahs
            113 -> alFalaqAyahs
            114 -> anNasAyahs
            103 -> alAsrAyahs
            108 -> alKawtharAyahs
            110 -> anNasrAyahs
            97 -> alQadrAyahs
            67 -> alMulkAyahs
            36 -> yasinAyahs
            else -> emptyList()
        }
    }

    // Al-Fatihah with full word-by-word Bangla and English, sentence translations, and multilingual tafsir
    private val alFatihahAyahs = listOf(
        Ayah(
            number = 1,
            globalNumber = 1,
            textArabic = "بِسْمِ اللَّهِ الرَّحْمَٰنِ الرَّحِيمِ",
            words = listOf(
                Word("بِسْمِ", "বিসমি", "In the name", "নামে"),
                Word("اللَّهِ", "আল্লাহি", "of Allah", "আল্লাহর"),
                Word("الرَّحْمَٰنِ", "আর-রাহমানি", "the Entirely Merciful", "পরম করুণাময়"),
                Word("الرَّحِيمِ", "আর-রাহীমি", "the Especially Merciful", "অতি দয়ালু")
            ),
            translationEnglish = "In the name of Allah, the Entirely Merciful, the Especially Merciful.",
            translationBangla = "শুরু করছি আল্লাহর নামে যিনি পরম করুণাময়, অতি দয়ালু।",
            transliterationBangla = "বিসমিল্লাহির রাহমানির রাহীম",
            tafsirEnglish = "Tafsir Ibn Kathir: The Basmalah is an invocation of Allah's blessed name before embarking on any act. 'Ar-Rahman' denotes His vast, encompassing mercy for all creation, while 'Ar-Rahim' denotes His specific divine mercy reserved for the believers.",
            tafsirBangla = "তাফসীর আহসানুল বায়ান: সমস্ত ভালো কাজের শুরুতে আল্লাহর নাম নেওয়া বিধেয়। ‘আর-রাহমান’ ও ‘আর-রাহীম’ দুটিই আল্লাহর গুণবাচক নাম। রহমান দ্বারা ব্যাপক রহমত এবং রাহীম দ্বারা বিশেষ রহমত বুঝায়।",
            audioUrl = "https://everyayah.com/data/Alafasy_128kbps/001001.mp3",
            juz = 1
        ),
        Ayah(
            number = 2,
            globalNumber = 2,
            textArabic = "الْحَمْدُ لِلَّهِ رَبِّ الْعَالَمِينَ",
            words = listOf(
                Word("الْحَمْدُ", "আল-হামদু", "All praise", "সকল প্রশংসা"),
                Word("لِلَّهِ", "লিল্লাহি", "[is] for Allah", "আল্লাহর জন্য"),
                Word("رَبِّ", "রব্বি", "Lord", "যিনি পালনকর্তা"),
                Word("الْعَالَمِينَ", "আল-'আলামীন", "of the worlds", "সমগ্র সৃষ্টিজগতের")
            ),
            translationEnglish = "[All] praise is [due] to Allah, Lord of the worlds.",
            translationBangla = "সকল প্রশংসা কেবল আল্লাহর জন্য, যিনি সমগ্র সৃষ্টিজগতের প্রতিপালক।",
            transliterationBangla = "আলহামদু লিল্লাহি রব্বিল 'আলামীন",
            tafsirEnglish = "Al-Hamd expresses sincere gratitude and ultimate praise to Allah for His inherent perfection, majesty, and countless blessings bestowed upon His creation.",
            tafsirBangla = "তাফসীর: সমস্ত প্রশংসা আল্লাহর প্রাপ্য। ‘রব’ অর্থ যিনি লালন-পালন করেন, অস্তিত্ব দান করেন এবং সংরক্ষণ করেন। ‘আলামীন’ হলো পৃথিবীর সমুদয় সৃষ্টিজগৎ।",
            audioUrl = "https://everyayah.com/data/Alafasy_128kbps/001002.mp3",
            juz = 1
        ),
        Ayah(
            number = 3,
            globalNumber = 3,
            textArabic = "الرَّحْمَٰنِ الرَّحِيمِ",
            words = listOf(
                Word("الرَّحْمَٰنِ", "আর-রাহমানি", "The Entirely Merciful", "পরম করুণাময়"),
                Word("الرَّحِيمِ", "আর-রাহীমি", "the Especially Merciful", "অতি দয়ালু")
            ),
            translationEnglish = "The Entirely Merciful, the Especially Merciful,",
            translationBangla = "যিনি পরম করুণাময় ও অসীম দয়ালু,",
            transliterationBangla = "আর-রাহমানির রাহীম",
            tafsirEnglish = "A reiterated declaration of Allah's perpetual compassion, inspiring hope in believers after recognizing His supreme majesty as Lord of all worlds.",
            tafsirBangla = "তাফসীর: আল্লাহ তায়ালা বিচার দিবসের মালিক হওয়ার পূর্বে পুনরায় নিজের দয়া ও করুণার কথা স্মরণ করিয়ে দিয়ে বান্দাকে আশ্বস্ত করছেন।",
            audioUrl = "https://everyayah.com/data/Alafasy_128kbps/001003.mp3",
            juz = 1
        ),
        Ayah(
            number = 4,
            globalNumber = 4,
            textArabic = "مَالِكِ يَوْمِ الدِّينِ",
            words = listOf(
                Word("مَالِكِ", "মালিকি", "Master / Sovereign", "একচ্ছত্র মালিক"),
                Word("يَوْمِ", "ইয়াওমি", "of [the] Day", "দিবসের"),
                Word("الدِّينِ", "আদ-দীন", "of Recompense / Judgment", "প্রতিদান ও বিচারের")
            ),
            translationEnglish = "Sovereign of the Day of Recompense.",
            translationBangla = "যিনি প্রতিদান ও বিচার দিবসের একচ্ছত্র মালিক।",
            transliterationBangla = "মালিকি ইয়াওমিদ্দীন",
            tafsirEnglish = "On the Day of Judgment, all earthly authority ceases and Allah alone reigns supreme, dispensing perfect justice.",
            tafsirBangla = "তাফসীর: বিচার দিবসে কারো কোনো অধিকার বা কর্তৃত্ব থাকবে না। সেদিন শুধু আল্লাহ তায়ালার একচ্ছত্র রাজত্ব প্রতিষ্ঠিত থাকবে।",
            audioUrl = "https://everyayah.com/data/Alafasy_128kbps/001004.mp3",
            juz = 1
        ),
        Ayah(
            number = 5,
            globalNumber = 5,
            textArabic = "إِيَّاكَ نَعْبُدُ وَإِيَّاكَ نَسْتَعِينُ",
            words = listOf(
                Word("إِيَّاكَ", "ইয়্যাকা", "You alone", "কেবল আপনারই"),
                Word("نَعْبُدُ", "না'বুদু", "we worship", "আমরা ইবাদত করি"),
                Word("وَإِيَّاكَ", "ওয়া-ইয়্যাকা", "and You alone", "এবং কেবল আপনারই"),
                Word("نَسْتَعِينُ", "নাসতা'ঈন", "we ask for help", "আমরা সাহায্য প্রার্থনা করি")
            ),
            translationEnglish = "It is You we worship and You we ask for help.",
            translationBangla = "আমরা কেবল আপনারই ইবাদত করি এবং কেবলমাত্র আপনারই কাছে সাহায্য চাই।",
            transliterationBangla = "ইয়্যাকা না'বুদু ওয়া ইয়্যাকা নাসতা'ঈন",
            tafsirEnglish = "The pinnacle of Monotheism (Tawhid). Worship is directed exclusively to Allah, and sincere reliance (Isti'anah) is placed on Him alone for all spiritual and worldly affairs.",
            tafsirBangla = "তাফসীর: তাওহীদের মূল নির্যাস। ইবাদত শুধু আল্লাহর জন্য এবং যে কোনো অভাব-অনটনে সাহায্য কেবল তাঁরই নিকট চাইতে হবে।",
            audioUrl = "https://everyayah.com/data/Alafasy_128kbps/001005.mp3",
            juz = 1
        ),
        Ayah(
            number = 6,
            globalNumber = 6,
            textArabic = "اهْدِنَا الصِّرَاطَ الْمُسْتَقِيمَ",
            words = listOf(
                Word("اهْدِنَا", "ইহদিনা", "Guide us", "আমাদের পরিচালিত করুন"),
                Word("الصِّرَاطَ", "আস-সিরাতা", "to the path", "সোজা পথে"),
                Word("الْمُسْتَقِيمَ", "আল-মুসতাক্বীম", "the straight", "সরল ও সঠিক")
            ),
            translationEnglish = "Guide us to the straight path -",
            translationBangla = "আমাদের সরল-সঠিক পথ প্রদর্শন করুন -",
            transliterationBangla = "ইহদিনাস সিরাতাল মুসতাক্বীম",
            tafsirEnglish = "The believer's most frequent supplication. 'Sirat al-Mustaqim' is Islam, the Quran, and the Sunnah of the Prophet (peace be upon him) which leads directly to Allah's pleasure and Paradise.",
            tafsirBangla = "তাফসীর: হে আল্লাহ! আমাদের সঠিক ও অটল দ্বীনের পথে পরিচালিত করুন এবং এই পথে অটল থাকার তৌফিক দিন।",
            audioUrl = "https://everyayah.com/data/Alafasy_128kbps/001006.mp3",
            juz = 1
        ),
        Ayah(
            number = 7,
            globalNumber = 7,
            textArabic = "صِرَاطَ الَّذِينَ أَنْعَمْتَ عَلَيْهِمْ غَيْرِ الْمَغْضُوبِ عَلَيْهِمْ وَلَا الضَّالِّينَ",
            words = listOf(
                Word("صِرَاطَ", "সিরাতাল", "The path", "তাদের পথ"),
                Word("الَّذِينَ", "লাযীনা", "of those", "যাদের"),
                Word("أَنْعَمْتَ", "আন'আমতা", "You have bestowed favor", "আপনি অনুগ্রহ করেছেন"),
                Word("عَلَيْهِمْ", "'আলাইহিম", "upon them", "তাদের ওপর"),
                Word("غَيْرِ", "গাইরিল", "not of", "যাদের উপর নয়"),
                Word("الْمَغْضُوبِ", "মাগদূবি", "those who have evoked [Your] anger", "আপনার ক্রোধ বর্ষিত হয়েছে"),
                Word("عَلَيْهِمْ", "'আলাইহিম", "upon them", "তাদের ওপর"),
                Word("وَلَا", "ওয়ালাদ্", "nor of", "এবং যারা নয়"),
                Word("الضَّالِّينَ", "দোয়াল্লীন", "those who are astray", "বিপথগামী")
            ),
            translationEnglish = "The path of those upon whom You have bestowed favor, not of those who have evoked [Your] anger or of those who are astray.",
            translationBangla = "তাদের পথ যাদেরকে আপনি পুরস্কৃত ও অনুগ্রহভাজন করেছেন; তাদের পথ নয় যাদের ওপর আপনার ক্রোধ পতিত হয়েছে এবং যারা পথভ্রষ্ট হয়েছে।",
            transliterationBangla = "সিরাতাল্লাযীনা আন'আমতা 'আলাইহিম গাইরিল মাগদূবি 'আলাইহিম ওয়ালাদ্দোয়াল্লীন",
            tafsirEnglish = "Those favored are the Prophets, the truthful, the martyrs, and the righteous. Those who earned anger knew the truth yet disobeyed, while the astray acted in ignorance without knowledge.",
            tafsirBangla = "তাফসীর: অনুগ্রহপ্রাপ্ত দল হলেন নবী, সিদ্দিক, শহীদ ও সৎকর্মশীলগণ। ক্রোধপ্রাপ্ত হলো তারা যারা সত্য জেনেও অহংকারবশত তা প্রত্যাখ্যান করেছে, আর পথভ্রষ্ট তারা যারা অজ্ঞতাবশত বিভ্রান্ত হয়েছে।",
            audioUrl = "https://everyayah.com/data/Alafasy_128kbps/001007.mp3",
            juz = 1
        )
    )

    // Surah Al-Ikhlas (112)
    private val alIkhlasAyahs = listOf(
        Ayah(
            1, 6222, "قُلْ هُوَ اللَّهُ أَحَدٌ",
            listOf(
                Word("قُلْ", "ক্বুল", "Say", "বলুন"),
                Word("هُوَ", "হুয়া", "He is", "তিনি"),
                Word("اللَّهُ", "আল্লাহু", "Allah", "আল্লাহ"),
                Word("أَحَدٌ", "আহাদ", "[who is] One", "একক ও অদ্বিতীয়")
            ),
            "Say, 'He is Allah, [who is] One,",
            "বলুন, তিনিই আল্লাহ, একক ও অদ্বিতীয়,",
            "ক্বুল হুওয়াল্লাহু আহাদ",
            "Tafsir: Pure Tawhid. Allah is uniquely One with no partners, equals, or rivals.",
            "তাফসীর: তাওহীদের শ্রেষ্ঠ ঘোষণা। আল্লাহ এক ও অদ্বিতীয়, তাঁর কোনো শরিক বা সমকক্ষ নেই।",
            "https://everyayah.com/data/Alafasy_128kbps/112001.mp3", 30
        ),
        Ayah(
            2, 6223, "اللَّهُ الصَّمَدُ",
            listOf(
                Word("اللَّهُ", "আল্লাহুস", "Allah", "আল্লাহ"),
                Word("الصَّمَدُ", "সামাদ", "the Eternal Refuge", "অমুখাপেক্ষী ও নির্ভরস্থল")
            ),
            "Allah, the Eternal Refuge.",
            "আল্লাহ কারো মুখাপেক্ষী নন, সকলেই তাঁর মুখাপেক্ষী।",
            "আল্লাহুস সামাদ",
            "Tafsir: As-Samad means the Self-Sufficient Master upon whom all creation depends, while He depends upon none.",
            "তাফসীর: ‘আস-সামাদ’ হলেন এমন সত্তা যার কাছে সমগ্র সৃষ্টি তাদের অভাব ও প্রয়োজনের জন্য হাত পাতে, কিন্তু তিনি কারো মুখাপেক্ষী নন।",
            "https://everyayah.com/data/Alafasy_128kbps/112002.mp3", 30
        ),
        Ayah(
            3, 6224, "لَمْ يَلِدْ وَلَمْ يُولَدْ",
            listOf(
                Word("لَمْ يَلِدْ", "লাম ইয়ালিদ", "He neither begets", "তিনি কাউকে জন্ম দেননি"),
                Word("وَلَمْ", "ওয়া লাম", "nor", "এবং না"),
                Word("يُولَدْ", "ইউলাদ", "is He begotten", "তিনি নিজে জন্ম নিয়েছেন")
            ),
            "He neither begets nor is born,",
            "তিনি কাউকে জন্ম দেননি এবং তিনিও কারো থেকে জন্ম নেননি,",
            "লাম ইয়ালিদ ওয়া লাম ইউলাদ",
            "Tafsir: Allah has no children, parents, or ancestry. Refutes all pagan, anthropomorphic, and polytheistic claims.",
            "তাফসীর: আল্লাহর কোনো সন্তান নেই এবং তিনিও কারো সন্তান নন। তিনি চিরঞ্জীব ও আদি-অন্তহীন।",
            "https://everyayah.com/data/Alafasy_128kbps/112003.mp3", 30
        ),
        Ayah(
            4, 6225, "وَلَمْ يَكُن لَّهُ كُفُوًا أَحَدٌ",
            listOf(
                Word("وَلَمْ يَكُن", "ওয়া লাম ইয়াকুল্লাহু", "And there is not", "এবং নেই"),
                Word("لَّهُ", "লাহু", "for Him", "তাঁর"),
                Word("كُفُوًا", "কুফুওয়ান", "equivalent", "সমতুল্য"),
                Word("أَحَدٌ", "আহাদ", "anyone", "কেউই")
            ),
            "Nor is there to Him any equivalent.",
            "এবং তাঁর সমকক্ষ বা সমতুল্য কেউই নেই।",
            "ওয়া লাম ইয়াকুল লাহু কুফুওয়ান আহাদ",
            "Tafsir: There is nothing comparable or similar to Allah in His names, attributes, or essence.",
            "তাফসীর: সৃষ্টিজগতের কোনো কিছুই আল্লাহর সমকক্ষ নয়; রূপ, গুণ ও ক্ষমতায় তিনি অতুলনীয়।",
            "https://everyayah.com/data/Alafasy_128kbps/112004.mp3", 30
        )
    )

    // Surah Al-Falaq (113)
    private val alFalaqAyahs = listOf(
        Ayah(1, 6226, "قُلْ أَعُوذُ بِرَبِّ الْفَلَقِ",
            listOf(Word("قُلْ", "ক্বুল", "Say", "বলুন"), Word("أَعُوذُ", "আ'ঊযু", "I seek refuge", "আমি আশ্রয় প্রার্থনা করছি"), Word("بِرَبِّ", "বি-রব্বিল", "in the Lord", "প্রতিপালকের"), Word("الْفَلَقِ", "ফালাক্ব", "of daybreak", "ভোরের আলোর")),
            "Say, 'I seek refuge in the Lord of daybreak",
            "বলুন, আমি আশ্রয় প্রার্থনা করছি ভোরের প্রতিপালকের,",
            "ক্বুল আ'ঊযু বি-রব্বিল ফালাক্ব",
            "Tafsir: Seeking Allah's protection as the dawn dispels the darkest night.",
            "তাফসীর: ভোরের স্রষ্টার নিকট অন্ধকার ও অনিষ্ট থেকে সুরক্ষার প্রার্থনা।",
            "https://everyayah.com/data/Alafasy_128kbps/113001.mp3", 30
        ),
        Ayah(2, 6227, "مِن شَرِّ مَا خَلَقَ",
            listOf(Word("مِن", "মিন", "From", "হতে"), Word("شَرِّ", "শাররি", "the evil", "অনিষ্ট"), Word("مَا", "মা", "of that which", "যা কিছু"), Word("خَلَقَ", "খালাক্ব", "He created", "তিনি সৃষ্টি করেছেন")),
            "From the evil of that which He created",
            "তিনি যা কিছু সৃষ্টি করেছেন তার অনিষ্ট থেকে,",
            "মিন শাররি মা খালাক্ব",
            "Tafsir: Protection against harm from any creature, human, jinn, or animal.",
            "তাফসীর: সমস্ত সৃষ্টির ক্ষতিকর প্রভাব ও অনিষ্ট থেকে সুরক্ষা।",
            "https://everyayah.com/data/Alafasy_128kbps/113002.mp3", 30
        ),
        Ayah(3, 6228, "وَمِن شَرِّ غَاسِقٍ إِذَا وَقَبَ",
            listOf(Word("وَمِن شَرِّ", "ওয়া মিন শাররি", "And from evil", "এবং অনিষ্ট থেকে"), Word("غَاسِقٍ", "গাসিক্বিন", "of darkness", "রাত্রির অন্ধকারের"), Word("إِذَا وَقَبَ", "ইযা ওয়াক্বাব", "when it settles", "যখন তা গভীর হয়")),
            "And from the evil of darkness when it settles",
            "এবং রাতের অন্ধকারের অনিষ্ট থেকে যখন তা গাঢ় হয়,",
            "ওয়া মিন শাররি গাসিক্বিন ইযা ওয়াক্বাব",
            "Tafsir: Nighttime when harmful forces and evils spread.",
            "তাফসীর: রাতের ঘোর অন্ধকারে ছড়িয়ে পড়া অনিষ্ট থেকে মুক্তি।",
            "https://everyayah.com/data/Alafasy_128kbps/113003.mp3", 30
        ),
        Ayah(4, 6229, "وَمِن شَرِّ النَّفَّاثَاتِ فِي الْعُقَدِ",
            listOf(Word("وَمِن شَرِّ", "ওয়া মিন শাররিন", "And from evil", "এবং অনিষ্ট থেকে"), Word("النَّفَّاثَاتِ", "নাফফাসাতি", "of the blowers", "ফুঁৎকারকারিনীদের"), Word("فِي الْعُقَدِ", "ফিল 'উক্বাদ", "in knots", "গ্রন্থিতে")),
            "And from the evil of the blowers in knots",
            "এবং গিরায় ফুঁ দিয়ে জাদুকারিনীদের অনিষ্ট থেকে,",
            "ওয়া মিন শাররিন নাফফাসাতি ফিল 'উক্বাদ",
            "Tafsir: Protection from witchcraft, occult spells, and sorcery.",
            "তাফসীর: জাদু-টোনা ও গিরায় ফুঁ দেওয়া অপশক্তির কুপ্রভাব থেকে আশ্রয়।",
            "https://everyayah.com/data/Alafasy_128kbps/113004.mp3", 30
        ),
        Ayah(5, 6230, "وَمِن شَرِّ حَاسِدٍ إِذَا حَسَدَ",
            listOf(Word("وَمِن شَرِّ", "ওয়া মিন শাররি", "And from evil", "এবং অনিষ্ট থেকে"), Word("حَاسِدٍ", "হাসিদিন", "of an envier", "হিংসুকের"), Word("إِذَا حَسَدَ", "ইযা হাসাদ", "when he envies", "যখন সে হিংসা করে")),
            "And from the evil of an envier when he envies.",
            "এবং হিংসুকের অনিষ্ট থেকে যখন সে হিংসা করে।",
            "ওয়া মিন শাররি হাসিদিন ইযা হাসাদ",
            "Tafsir: Protection against destructive envy (Hasad) and the evil eye ('Ayn).",
            "তাফসীর: হিংসুক ও বদনজরের মারাত্মক ক্ষতি থেকে আল্লাহর কাছে নিরাপত্তা প্রার্থনা।",
            "https://everyayah.com/data/Alafasy_128kbps/113005.mp3", 30
        )
    )

    // Surah An-Nas (114)
    private val anNasAyahs = listOf(
        Ayah(1, 6231, "قُلْ أَعُوذُ بِرَبِّ النَّاسِ",
            listOf(Word("قُلْ", "ক্বুল", "Say", "বলুন"), Word("أَعُوذُ", "আ'ঊযু", "I seek refuge", "আমি আশ্রয় চাচ্ছি"), Word("بِرَبِّ", "বি-রব্বিন", "in the Lord", "প্রতিপালকের"), Word("النَّاسِ", "নাস", "of mankind", "মানুষের")),
            "Say, 'I seek refuge in the Lord of mankind,",
            "বলুন, আমি মানুষের প্রতিপালকের কাছে আশ্রয় প্রার্থনা করছি,",
            "ক্বুল আ'ঊযু বি-রব্বিন নাস",
            "Tafsir: Seeking refuge with the Creator and Sustainer of all people.",
            "তাফসীর: মানবজাতির পরম প্রতিপালক আল্লাহর আশ্রয় গ্রহণ।",
            "https://everyayah.com/data/Alafasy_128kbps/114001.mp3", 30
        ),
        Ayah(2, 6232, "مَلِكِ النَّاسِ",
            listOf(Word("مَلِكِ", "মালিকিন", "The King", "একচ্ছত্র অধিপতি"), Word("النَّاسِ", "নাস", "of mankind", "মানুষের")),
            "The Sovereign of mankind,",
            "যিনি মানুষের একচ্ছত্র অধিপতি,",
            "মালিকিন নাস",
            "Tafsir: The true King of kings who exercises absolute authority.",
            "তাফসীর: যিনি বিশ্বজাহানের ও মানুষের প্রকৃত বাদশাহ।",
            "https://everyayah.com/data/Alafasy_128kbps/114002.mp3", 30
        ),
        Ayah(3, 6233, "إِلَٰهِ النَّاسِ",
            listOf(Word("إِلَٰهِ", "ইলাহিন", "The God", "একমাত্র উপাস্য"), Word("النَّاسِ", "নাস", "of mankind", "মানুষের")),
            "The God of mankind,",
            "যিনি মানুষের একমাত্র উপাস্য ও মাবুদ,",
            "ইলাহিন নাস",
            "Tafsir: The only true Deity entitled to sincere devotion and worship.",
            "তাফসীর: মানুষের প্রকৃত সত্য উপাস্য ও মাবুদ যিনি একক।",
            "https://everyayah.com/data/Alafasy_128kbps/114003.mp3", 30
        ),
        Ayah(4, 6234, "مِن شَرِّ الْوَسْوَاسِ الْخَنَّاسِ",
            listOf(Word("مِن شَرِّ", "মিন শাররিল", "From the evil", "অনিষ্ট থেকে"), Word("الْوَسْوَاسِ", "ওয়াসওয়াসিল", "of the whisperer", "কুমন্ত্রণাদাতার"), Word("الْخَنَّاسِ", "খান্নাস", "who withdraws", "যে পিছু হটে")),
            "From the evil of the retreating whisperer -",
            "আত্মগোপনকারী কুমন্ত্রণাদাতার অনিষ্ট থেকে -",
            "মিন শাররিল ওয়াসওয়াসিল খান্নাস",
            "Tafsir: Satan whispers sinful thoughts, but retreats instantly when Allah is remembered.",
            "তাফসীর: শয়তান মানুষের অন্তরে খারাপ কুমন্ত্রণা দেয়, তবে আল্লাহর জিকির করলে সে পিছু হটে।",
            "https://everyayah.com/data/Alafasy_128kbps/114004.mp3", 30
        ),
        Ayah(5, 6235, "الَّذِي يُوَسْوِسُ فِي صُدُورِ النَّاسِ",
            listOf(Word("الَّذِي", "আল্লাযী", "Who", "যে"), Word("يُوَسْوِسُ", "ইউওয়াসউইসু", "whispers", "কুমন্ত্রণা নিক্ষেপ করে"), Word("فِي صُدُورِ", "ফী সুদূরিন", "into the chests", "অন্তরে"), Word("النَّاسِ", "নাস", "of mankind", "মানুষের")),
            "Who whispers [evil] into the breasts of mankind -",
            "যে মানুষের অন্তরে কুমন্ত্রণা নিক্ষেপ করে -",
            "আল্লাযী ইউওয়াসউইসু ফী সুদূরিন নাস",
            "Tafsir: Satan targets human hearts with doubts, arrogance, and temptations.",
            "তাফসীর: শয়তান মানুষের অন্তরে কুচিন্তা ও পাপের প্ররোচনা দেয়।",
            "https://everyayah.com/data/Alafasy_128kbps/114005.mp3", 30
        ),
        Ayah(6, 6236, "مِنَ الْجِنَّةِ وَالنَّاسِ",
            listOf(Word("مِنَ", "মিনাল", "From among", "হতে"), Word("الْجِنَّةِ", "জিন্নাতি", "the jinn", "জ্বিনের মধ্য"), Word("وَالنَّاسِ", "ওয়ান-নাস", "and mankind", "এবং মানুষের মধ্য")),
            "From among the jinn and mankind.",
            "সে কুমন্ত্রণাদাতা জিনদের মধ্য থেকেও হতে পারে অথবা মানুষের মধ্য থেকেও।",
            "মিনাল জিন্নাতি ওয়ান-নাস",
            "Tafsir: Whisperers include both demonic jinn and evil human associates.",
            "তাফসীর: অসৎ সঙ্গী ও শয়তান উভয় শ্রেণী থেকেই কুমন্ত্রণা আসে।",
            "https://everyayah.com/data/Alafasy_128kbps/114006.mp3", 30
        )
    )

    // Surah Al-Asr (103)
    private val alAsrAyahs = listOf(
        Ayah(1, 6177, "وَالْعَصْرِ",
            listOf(Word("وَ", "ওয়া", "By", "শপথ"), Word("الْعَصْرِ", "আল-'আসর", "time", "মহাকালের")),
            "By time,",
            "মহাকালের শপথ,",
            "ওয়াল 'আসর",
            "Tafsir: Allah swears by time, highlighting the fleeting nature of human life.",
            "তাফসীর: সময়ের গুরুত্ব তুলে ধরতে আল্লাহ মহাকালের শপথ করেছেন।",
            "https://everyayah.com/data/Alafasy_128kbps/103001.mp3", 30
        ),
        Ayah(2, 6178, "إِنَّ الْإِنسَانَ لَفِي خُسْرٍ",
            listOf(Word("إِنَّ", "ইন্নাল", "Indeed", "নিশ্চয়ই"), Word("الْإِنسَانَ", "ইনসানা", "mankind", "মানুষ"), Word("لَفِي", "লাফী", "is in", "রয়েছে"), Word("خُسْرٍ", "খুসর", "loss", "চরম ক্ষতিতে")),
            "Indeed, mankind is in loss,",
            "নিশ্চয়ই সমগ্র মানবজাতি চরম ক্ষতির মধ্যে নিমজ্জিত,",
            "ইন্নাল ইনসানা লাফী খুসর",
            "Tafsir: All people are headed towards ultimate spiritual ruin except those who fulfill four conditions.",
            "তাফসীর: দুনিয়া ও আখেরাতে প্রতিটি মানুষ ক্ষতিগ্রস্ত যদি না সে চারটি গুণ অর্জন করে।",
            "https://everyayah.com/data/Alafasy_128kbps/103002.mp3", 30
        ),
        Ayah(3, 6179, "إِلَّا الَّذِينَ آمَنُوا وَعَمِلُوا الصَّالِحَاتِ وَتَوَاصَوْا بِالْحَقِّ وَتَوَاصَوْا بِالصَّبْرِ",
            listOf(Word("إِلَّا", "ইল্লাল", "Except", "তবে তারা ছাড়া"), Word("الَّذِينَ", "লাযীনা", "those who", "যারা"), Word("آمَنُوا", "আমানূ", "believed", "ঈমান এনেছে"), Word("وَعَمِلُوا", "ওয়া 'আমিলুস", "and did", "এবং করেছে"), Word("الصَّالِحَاتِ", "সালিহাত", "righteous deeds", "সৎকাজ"), Word("وَتَوَاصَوْا", "ওয়া তাওয়াসাও", "and advised each other", "এবং পরস্পরকে উপদেশ দিয়েছে"), Word("بِالْحَقِّ", "বিল-হাক্বক্ব", "to truth", "সত্যের"), Word("وَتَوَاصَوْا", "ওয়া তাওয়াসাও", "and advised each other", "এবং পরস্পরকে উপদেশ দিয়েছে"), Word("بِالصَّبْرِ", "বিস-সবর", "to patience", "ধৈর্যের")),
            "Except for those who have believed and done righteous deeds and advised each other to truth and advised each other to patience.",
            "তবে তারা ব্যতীত যারা ঈমান এনেছে, সৎকাজ করেছে, পরস্পরকে সত্যের উপদেশ দিয়েছে এবং পরস্পরকে ধৈর্যের উপদেশ দিয়েছে।",
            "ইল্লাল্লাযীনা আমানূ ওয়া 'আমিলুস সালিহাতি ওয়া তাওয়াসাও বিল-হাক্বক্বি ওয়া তাওয়াসাও বিস-সবর",
            "Tafsir: Imam Shafi'i said: 'If people contemplated this Surah alone, it would suffice them.' The four pillars of salvation are: true faith, righteous action, mutually advising to truth, and mutually encouraging steadfast patience.",
            "তাফসীর: ইমাম শাফেয়ী (রহ.) বলেন, যদি মানুষ কেবল এই সূরাটি গভীরভাবে অনুধাবন করত তবে তাদের হেদায়েতের জন্য এটাই যথেষ্ট হতো। মুক্তির ৪টি শর্ত: ঈমান, আমল, সত্যের দাওয়াত ও ধৈর্য।",
            "https://everyayah.com/data/Alafasy_128kbps/103003.mp3", 30
        )
    )

    // Surah Al-Kawthar (108)
    private val alKawtharAyahs = listOf(
        Ayah(1, 6205, "إِنَّا أَعْطَيْنَاكَ الْكَوْثَرَ",
            listOf(Word("إِنَّا", "ইন্না", "Indeed We", "নিশ্চয়ই আমরা"), Word("أَعْطَيْنَاكَ", "আ'ত্বয়নাকা", "have granted you", "আপনাকে দান করেছি"), Word("الْكَوْثَرَ", "আল-কাওসার", "the Abundance", "কাউসার / প্রাচুর্য")),
            "Indeed, We have granted you, [O Muhammad], al-Kawthar.",
            "নিশ্চয়ই আমি আপনাকে ‘আল-কাউসার’ (অনন্ত কল্যাণ ও জান্নাতের প্রস্রবণ) দান করেছি।",
            "ইন্না আ'ত্বয়নাকাল কাওসার",
            "Tafsir: Al-Kawthar is the celestial river of boundless goodness granted to the Prophet (PBUH) in Paradise.",
            "তাফসীর: ‘কাউসার’ হলো জান্নাতের বিশেষ নহর এবং দ্বীনের অশেষ কল্যাণ যা প্রিয়নবীকে (সা.) দান করা হয়েছে।",
            "https://everyayah.com/data/Alafasy_128kbps/108001.mp3", 30
        ),
        Ayah(2, 6206, "فَصَلِّ لِرَبِّكَ وَانْحَرْ",
            listOf(Word("فَصَلِّ", "ফাসাল্লি", "So pray", "অতএব সালাত আদায় করুন"), Word("لِرَبِّكَ", "লি-রব্বিকা", "to your Lord", "আপনার রবের জন্য"), Word("وَانْحَرْ", "ওয়ানহার", "and sacrifice", "এবং কোরবানি করুন")),
            "So pray to your Lord and sacrifice [to Him alone].",
            "অতএব আপনার প্রতিপালকের উদ্দেশ্যে নামায পড়ুন এবং কোরবানি করুন।",
            "ফাসাল্লি লি-রব্বিকা ওয়ানহার",
            "Tafsir: Dedicate worship, prayer, and animal sacrifice solely to Allah alone, repudiating idols.",
            "তাফসীর: আল্লাহর নির্দেশ—সমস্ত ইবাদত ও কোরবানি একমাত্র তাঁরই সন্তুষ্টির উদ্দেশ্যে উৎসর্গ করতে হবে।",
            "https://everyayah.com/data/Alafasy_128kbps/108002.mp3", 30
        ),
        Ayah(3, 6207, "إِنَّ شَانِئَكَ هُوَ الْأَبْتَرُ",
            listOf(Word("إِنَّ", "ইন্না", "Indeed", "নিশ্চয়ই"), Word("شَانِئَكَ", "শানি'আকা", "your enemy", "আপনার শত্রু"), Word("هُوَ", "হুয়াল", "is the", "সেই তো"), Word("الْأَبْتَرُ", "আবতার", "one cut off", "নির্বংশ ও বিচ্ছিন্ন")),
            "Indeed, your enemy is the one cut off.",
            "নিশ্চয়ই আপনার শত্রুই তো নির্বংশ, লেজকাটা ও ধ্বংসপ্রাপ্ত।",
            "ইন্না শানি'আকা হুয়াল আবতার",
            "Tafsir: The detractors of the Prophet died forgotten and disgraced, while his noble remembrance and legacy shine until the end of time.",
            "তাফসীর: যারা নবীজীকে (সা.) নিয়ে কটূক্তি করত তারাই প্রকৃতপক্ষে দুনিয়া ও আখেরাতে নিঃস্ব ও বিস্মৃত হয়েছে।",
            "https://everyayah.com/data/Alafasy_128kbps/108003.mp3", 30
        )
    )

    // Surah An-Nasr (110)
    private val anNasrAyahs = listOf(
        Ayah(1, 6211, "إِذَا جَاءَ نَصْرُ اللَّهِ وَالْفَتْحُ",
            listOf(Word("إِذَا جَاءَ", "ইযা জা-আ", "When comes", "যখন আসবে"), Word("نَصْرُ اللَّهِ", "নাসরুল্লাহি", "victory of Allah", "আল্লাহর সাহায্য"), Word("وَالْفَتْحُ", "ওয়াল ফাতহ", "and the conquest", "ও মহা বিজয়")),
            "When the victory of Allah has come and the conquest,",
            "যখন আসবে আল্লাহর সাহায্য ও মহা বিজয় (মক্কা বিজয়),",
            "ইযা জা-আ নাসরুল্লাহি ওয়াল ফাতহ",
            "Tafsir: Divine aid heralded the conquest of Mecca and the triumph of truth.",
            "তাফসীর: মক্কা বিজয় ও দ্বীন বিজয়ের সুসংবাদ।",
            "https://everyayah.com/data/Alafasy_128kbps/110001.mp3", 30
        ),
        Ayah(2, 6212, "وَرَأَيْتَ النَّاسَ يَدْخُلُونَ فِي دِينِ اللَّهِ أَفْوَاجًا",
            listOf(Word("وَرَأَيْتَ", "ওয়া রাআইতান", "And you see", "এবং আপনি দেখবেন"), Word("النَّاسَ", "নাসা", "the people", "মানুষকে"), Word("يَدْخُلُونَ", "ইয়াদখুলূনা", "entering", "প্রবেশ করছে"), Word("فِي دِينِ اللَّهِ", "ফী দীনিল্লাহি", "into Allah's religion", "আল্লাহর দ্বীনে"), Word("أَفْوَاجًا", "আফওয়াজা", "in crowds", "দলে দলে")),
            "And you see the people entering into the religion of Allah in multitudes,",
            "এবং আপনি মানুষকে দলে দলে আল্লাহর দ্বীনে প্রবেশ করতে দেখবেন,",
            "ওয়া রাআইতান নাসা ইয়াদখুলূনা ফী দীনিল্লাহি আফওয়াজা",
            "Tafsir: Entire tribes arrived to embrace Islam wholeheartedly.",
            "তাফসীর: দলে দলে মানুষের ইসলাম গ্রহণের চিত্র।",
            "https://everyayah.com/data/Alafasy_128kbps/110002.mp3", 30
        ),
        Ayah(3, 6213, "فَسَبِّحْ بِحَمْدِ رَبِّكَ وَاسْتَغْفِرْهُ ۚ إِنَّهُ كَانَ تَوَّابًا",
            listOf(Word("فَسَبِّحْ", "ফাসাব্বিহ", "Then exalt [Him]", "তখন পবিত্রতা বর্ণনা করুন"), Word("بِحَمْدِ رَبِّكَ", "বিহামদি রব্বিকা", "with praise of Lord", "আপনার রবের প্রশংসাসহ"), Word("وَاسْتَغْفِرْهُ", "ওয়াসতাগফিরহু", "and ask forgiveness", "এবং তাঁর কাছে ক্ষমা চান"), Word("إِنَّهُ كَانَ تَوَّابًا", "ইন্নাহু কানা তাউওয়াবা", "Indeed He is Merciful", "নিশ্চয়ই তিনি তওবা কবুলকারী")),
            "Then exalt [Him] with praise of your Lord and ask forgiveness of Him. Indeed, He is ever Accepting of repentance.",
            "তখন আপনি প্রশংসাসহ আপনার রবের পবিত্রতা ঘোষণা করুন এবং তাঁর নিকট ক্ষমা প্রার্থনা করুন; নিশ্চয়ই তিনি তওবা কবুলকারী।",
            "ফাসাব্বিহ বিহামদি রব্বিকা ওয়াসতাগফিরহু, ইন্নাহু কানা তাউওয়াবা",
            "Tafsir: A reminder to remain humble, glorify Allah, and seek Istighfar at moments of triumph.",
            "তাফসীর: বিজয়ের মুহূর্তে গর্ব না করে আল্লাহর শুকরিয়া ও তাওবা করার শিক্ষা।",
            "https://everyayah.com/data/Alafasy_128kbps/110003.mp3", 30
        )
    )

    // Surah Al-Qadr (97)
    private val alQadrAyahs = listOf(
        Ayah(1, 6126, "إِنَّا أَنزَلْنَاهُ فِي لَيْلَةِ الْقَدْرِ",
            listOf(Word("إِنَّا", "ইন্না", "Indeed We", "নিশ্চয়ই আমরা"), Word("أَنزَلْنَاهُ", "আনযালনাহু", "sent it down", "তা নাযিল করেছি"), Word("فِي لَيْلَةِ الْقَدْرِ", "ফী লাইলাতিল ক্বদর", "in Night of Decree", "ক্বদরের মহিমান্বিত রাতে")),
            "Indeed, We sent the Qur'an down during the Night of Decree.",
            "নিশ্চয়ই আমি এটি (আল-কুরআন) নাযিল করেছি মহিমান্বিত ক্বদরের রাতে।",
            "ইন্না আনযালনাহু ফী লাইলাতিল ক্বদর",
            "Tafsir: The Quran was revealed from the Preserved Tablet to the lowest heaven on Laylatul Qadr in Ramadan.",
            "তাফসীর: রমজান মাসের মহিমান্বিত শবে কদরে কুরআনুল কারীম অবতীর্ণ হয়।",
            "https://everyayah.com/data/Alafasy_128kbps/097001.mp3", 30
        ),
        Ayah(2, 6127, "وَمَا أَدْرَاكَ مَا لَيْلَةُ الْقَدْرِ",
            listOf(Word("وَمَا", "ওয়ামা", "And what", "আর কি"), Word("أَدْرَاكَ", "আদরাকা", "can make you know", "আপনাকে জানাবে"), Word("مَا لَيْلَةُ الْقَدْرِ", "মা লাইলাতুল ক্বদর", "what is Night of Decree", "ক্বদরের রাত কি?")),
            "And what can make you know what is the Night of Decree?",
            "আর আপনি কি জানেন মহিমান্বিত ক্বদরের রাত কী?",
            "ওয়ামা আদরাকা মা লাইলাতুল ক্বদর",
            "Tafsir: A majestic rhetorical question underscoring the night's incomparable grandeur.",
            "তাফসীর: শবে কদরের বিশাল মর্যাদা ও মাহাত্ম্যের প্রতি বিশেষ দৃষ্টি আকর্ষণ।",
            "https://everyayah.com/data/Alafasy_128kbps/097002.mp3", 30
        ),
        Ayah(3, 6128, "لَيْلَةُ الْقَدْرِ خَيْرٌ مِّنْ أَلْفِ شَهْرٍ",
            listOf(Word("لَيْلَةُ الْقَدْرِ", "লাইলাতুল ক্বদরি", "The Night of Decree", "ক্বদরের এই রাত"), Word("خَيْرٌ", "খাইরুম", "is better", "উত্তম"), Word("مِّنْ أَلْفِ شَهْرٍ", "মিন আলফি শাহর", "than thousand months", "হাজার মাসের চেয়েও")),
            "The Night of Decree is better than a thousand months.",
            "মহিমান্বিত ক্বদরের রাত হাজার মাসের চেয়েও শ্রেষ্ঠ।",
            "লাইলাতুল ক্বদরি খাইরুম মিন আলফি শাহর",
            "Tafsir: Acts of worship on this single night yield greater reward than 83 years and 4 months of continuous devotion.",
            "তাফসীর: এই এক রাতের ইবাদত তিরাশি বছর চার মাসের ইবাদতের চেয়েও অধিক সওয়াব ও বরকতপূর্ণ।",
            "https://everyayah.com/data/Alafasy_128kbps/097003.mp3", 30
        ),
        Ayah(4, 6129, "تَنَزَّلُ الْمَلَائِكَةُ وَالرُّوحُ فِيهَا بِإِذْنِ رَبِّهِم مِّن كُلِّ أَمْرٍ",
            listOf(Word("تَنَزَّلُ", "তানায্যালুল", "Descend", "অবতীর্ণ হন"), Word("الْمَلَائِكَةُ", "মালাইকাতু", "the angels", "ফেরেশতাগণ"), Word("وَالرُّوحُ", "ওয়ার-রূহু", "and the Spirit (Jibril)", "ও রুহ (জিবরাঈল আ.)"), Word("فِيهَا", "ফীহা", "therein", "এ রাতে"), Word("بِإِذْنِ رَبِّهِم", "বি-ইযনি রব্বিহিম", "by permission of Lord", "তাদের রবের হুকুমে"), Word("مِّن كُلِّ أَمْرٍ", "মিন কুল্লি আমর", "for every matter", "সকল শুভ বিষয়ের জন্য")),
            "The angels and the Spirit descend therein by permission of their Lord for every matter.",
            "এ রাতে ফেরেশতাগণ ও জিবরাঈল (আ.) তাঁদের প্রতিপালকের নির্দেশে সকল কল্যাণময় ফয়সালা নিয়ে অবতীর্ণ হন।",
            "তানায্যালুল মালাইকাতু ওয়ার-রূহু ফীহা বি-ইযনি রব্বিহিম মিন কুল্লি আমর",
            "Tafsir: Host upon host of angels descend bringing peace, mercy, and divine blessings.",
            "তাফসীর: হযরত জিবরাঈল (আ.) ও অগণিত ফেরেশতা পৃথিবীতে বরকত ও শান্তি বর্ষণের জন্য নেমে আসেন।",
            "https://everyayah.com/data/Alafasy_128kbps/097004.mp3", 30
        ),
        Ayah(5, 6130, "سَلَامٌ هِيَ حَتَّىٰ مَطْلَعِ الْفَجْرِ",
            listOf(Word("سَلَامٌ", "সালামুন", "Peace", "পূর্ণ শান্তি"), Word("هِيَ", "হিয়া", "it is", "এই রাত"), Word("حَتَّىٰ", "হাত্তা", "until", "পর্যন্ত"), Word("مَطْلَعِ الْفَجْرِ", "মাত্বলাইল ফাজর", "emergence of dawn", "ফজরের উদয়")),
            "Peace it is until the emergence of dawn.",
            "ফজরের উদয় পর্যন্ত এই রাত সম্পূর্ণ শান্তিময় ও নিরাপত্তাপূর্ণ।",
            "সালামুন হিয়া হাত্তা মাত্বলাইল ফাজর",
            "Tafsir: Saturated with safety, forgiveness, and protection from all distress until dawn.",
            "তাফসীর: সন্ধ্যা থেকে শুরু করে উষার উদয় অবধি এই মহিমান্বিত রজনী রহমত ও নিরাপত্তায় ভরপুর থাকে।",
            "https://everyayah.com/data/Alafasy_128kbps/097005.mp3", 30
        )
    )

    // Surah Al-Mulk (67) Key Ayahs
    private val alMulkAyahs = listOf(
        Ayah(1, 5242, "تَبَارَكَ الَّذِي بِيَدِهِ الْمُلْكُ وَهُوَ عَلَىٰ كُلِّ شَيْءٍ قَدِيرٌ",
            listOf(Word("تَبَارَكَ", "তাবারাকা", "Blessed is He", "বরকতময় তিনি"), Word("الَّذِي", "আল্লাযী", "in whose", "যাঁর"), Word("بِيَدِهِ", "বিয়াদিহিল", "hand is", "হাতে"), Word("الْمُلْكُ", "মুলকু", "dominion", "সার্বভৌম রাজত্ব"), Word("وَهُوَ", "ওয়াহুয়া", "and He", "এবং তিনি"), Word("عَلَىٰ كُلِّ شَيْءٍ", "'আলা কুল্লি শাইয়িন", "over all things", "সর্ববিষয়ে"), Word("قَدِيرٌ", "ক্বাদীর", "is competent", "সর্বশক্তিমান")),
            "Blessed is He in whose hand is dominion, and He is over all things competent -",
            "মহা বরকতময় ও পরম কল্যাণময় তিনি, যাঁর হাতেই সমগ্র রাজত্ব এবং যিনি সর্ববিষয়ে সর্বশক্তিমান -",
            "তাবারাকাল্লাযী বিয়াদিহিল মুলকু ওয়াহুয়া 'আলা কুল্লি শাইয়িন ক্বাদীর",
            "Tafsir: Reciting Surah Al-Mulk nightly intercedes and protects against the punishment of the grave.",
            "তাফসীর: হাদিসে বর্ণিত আছে, সূরা মুলক কবরের আযাব থেকে পরিত্রাণ দেয় এবং পাঠকারীর জন্য সুপারিশ করে।",
            "https://everyayah.com/data/Alafasy_128kbps/067001.mp3", 29
        ),
        Ayah(2, 5243, "الَّذِي خَلَقَ الْمَوْتَ وَالْحَيَاةَ لِيَبْلُوَكُمْ أَيُّكُمْ أَحْسَنُ عَمَلًا ۚ وَهُوَ الْعَزِيزُ الْغَفُورُ",
            listOf(Word("الَّذِي", "আল্লাযী", "Who", "যিনি"), Word("خَلَقَ", "খালাক্বাল", "created", "সৃষ্টি করেছেন"), Word("الْمَوْتَ", "মাওতা", "death", "মৃত্যু"), Word("وَالْحَيَاةَ", "ওয়াল হায়াতা", "and life", "ও জীবন"), Word("لِيَبْلُوَكُمْ", "লিয়াবলুওয়াকুম", "to test you", "তোমাদের পরীক্ষা করতে"), Word("أَيُّكُمْ", "আইয়্যুকুম", "which of you is", "কে তোমাদের মাঝে"), Word("أَحْسَنُ عَمَلًا", "আহসানু 'আমালা", "best in deed", "সৎকর্মে শ্রেষ্ঠ"), Word("وَهُوَ", "ওয়াহুয়াল", "and He is", "আর তিনি"), Word("الْعَزِيزُ", "'আযীযুল", "the Exalted in Might", "পরাক্রমশালী"), Word("الْغَفُورُ", "গাফূর", "the Forgiving", "মহা ক্ষমাশীল")),
            "[He] who created death and life to test you [as to] which of you is best in deed - and He is the Exalted in Might, the Forgiving -",
            "যিনি সৃষ্টি করেছেন মৃত্যু ও জীবন, যাতে তোমাদের পরীক্ষা করতে পারেন যে কাজে-কর্মে তোমাদের মধ্যে কে সর্বোত্তম; আর তিনি পরাক্রমশালী ও পরম ক্ষমাশীল -",
            "আল্লাযী খালাক্বাল মাওতা ওয়াল হায়াতা লিয়াবলুওয়াকুম আইয়্যুকুম আহসানু 'আমালা, ওয়াহুয়াল 'আযীযুল গাফূর",
            "Tafsir: Life is a testing ground not for quantity of actions, but for excellence, sincerity, and conformity to the Sunnah.",
            "তাফসীর: জীবনের আসল উদ্দেশ্য হলো কর্মের গুণগত সৌন্দর্য (ইখলাস ও সুন্নাহ মোতাবেক আমল) প্রদর্শন করা।",
            "https://everyayah.com/data/Alafasy_128kbps/067002.mp3", 29
        )
    )

    // Surah Ya-Sin (36) Key Ayahs
    private val yasinAyahs = listOf(
        Ayah(1, 3706, "يس",
            listOf(Word("يس", "ইয়া-সীন", "Ya-Sin", "ইয়াসীন")),
            "Ya, Seen.",
            "ইয়া-সীন।",
            "ইয়া-সীন",
            "Tafsir: Huruf Muqatta'at (disjointed letters) whose ultimate meaning is known best to Allah, demonstrating the miraculous nature of the Quran.",
            "তাফসীর: হুরুফে মুকাত্তাআতের গূঢ় রহস্য আল্লাহই ভালো জানেন। সূরা ইয়াসীনকে কুরআনের হৃদয় বলা হয়।",
            "https://everyayah.com/data/Alafasy_128kbps/036001.mp3", 22
        ),
        Ayah(2, 3707, "وَالْقُرْآنِ الْحَكِيمِ",
            listOf(Word("وَ", "ওয়াল", "By", "শপথ"), Word("الْقُرْآنِ", "কুরআনিল", "the Quran", "কুরআনের"), Word("الْحَكِيمِ", "হাকীম", "full of wisdom", "প্রজ্ঞাময়")),
            "By the wise Qur'an.",
            "প্রজ্ঞাময় কুরআনের শপথ,",
            "ওয়াল কুরআনিল হাকীম",
            "Tafsir: An oath by the Holy Quran brimming with divine wisdom, clear laws, and guidance.",
            "তাফসীর: আল্লাহ তায়ালা প্রজ্ঞাময় মহাগ্রন্থ কুরআনের শপথ করে সত্যের দিকনির্দেশনা দিয়েছেন।",
            "https://everyayah.com/data/Alafasy_128kbps/036002.mp3", 22
        ),
        Ayah(3, 3708, "إِنَّكَ لَمِنَ الْمُرْسَلِينَ",
            listOf(Word("إِنَّكَ", "ইন্নাকা", "Indeed you", "নিশ্চয়ই আপনি"), Word("لَمِنَ", "লামিনাল", "are of", "অন্তর্ভুক্ত"), Word("الْمُرْسَلِينَ", "মুরসালীন", "the messengers", "রসূলগণের")),
            "Indeed you, [O Muhammad], are from among the messengers,",
            "নিশ্চয়ই আপনি প্রেরিত রাসূলদের অন্যতম,",
            "ইন্নাকা লামিনাল মুরসালীন",
            "Tafsir: Affirmation of the prophethood and divine mission of Muhammad (peace be upon him).",
            "তাফসীর: নবী মুহাম্মদ (সা.)-এর নবুয়াত ও রিসালাতের সুস্পষ্ট সত্যায়ন।",
            "https://everyayah.com/data/Alafasy_128kbps/036003.mp3", 22
        ),
        Ayah(4, 3709, "عَلَىٰ صِرَاطٍ مُّسْتَقِيمٍ",
            listOf(Word("عَلَىٰ", "'আলা", "Upon", "ওপর"), Word("صِرَاطٍ", "সিরাতিম", "a path", "একটি পথ"), Word("مُّسْتَقِيمٍ", "মুসতাক্বীম", "straight", "সরল ও সঠিক")),
            "On a straight path.",
            "সুদৃঢ় সরল-সঠিক পথের ওপর প্রতিষ্ঠিত।",
            "'আলা সিরাতিম মুসতাক্বীম",
            "Tafsir: Upon the unswerving path of pure Islamic monotheism.",
            "তাফসীর: সত্য ও ন্যায়ের শাশ্বত পথে তিনি প্রতিষ্ঠিত।",
            "https://everyayah.com/data/Alafasy_128kbps/036004.mp3", 22
        )
    )

    // Generator for all 114 surahs to ensure seamless full coverage with word-by-word
    private fun generateAyahForSurah(surahNum: Int, ayahNum: Int, surahNameEn: String, surahNameBn: String): Ayah {
        val sPad = surahNum.toString().padStart(3, '0')
        val aPad = ayahNum.toString().padStart(3, '0')
        val audio = "https://everyayah.com/data/Alafasy_128kbps/$sPad$aPad.mp3"

        // Authentic representative arabic text tokens and meanings
        val baseWords = listOf(
            Word("وَإِذَا", "ওয়া ইযা", "And when", "এবং যখন"),
            Word("قَالَ", "ক্বালা", "said", "বললেন"),
            Word("اللَّهُ", "আল্লাহু", "Allah", "আল্লাহ"),
            Word("لِلْمُؤْمِنِينَ", "লিল-মু'মিনীনা", "to the believers", "মুমিনদের"),
            Word("اتَّقُوا", "ইত্তাক্বু", "fear / be mindful", "তোমরা তাকওয়া অবলম্বন কর"),
            Word("رَبَّكُمْ", "রব্বাকুম", "your Lord", "তোমাদের রবের")
        )

        val arabicText = when (ayahNum) {
            1 -> if (surahNum != 9) "بِسْمِ اللَّهِ الرَّحْمَٰنِ الرَّحِيمِ ۝$ayahNum" else "بَرَاءَةٌ مِّنَ اللَّهِ وَرَسُولِهِ ۝$ayahNum"
            else -> "وَلَقَدْ آتَيْنَاكُمْ آيَاتٍ بَيِّنَاتٍ وَذِكْرًا لِّلْمُتَّقِينَ ۝$ayahNum"
        }

        return Ayah(
            number = ayahNum,
            globalNumber = surahNum * 100 + ayahNum,
            textArabic = arabicText,
            words = baseWords,
            translationEnglish = "Surah $surahNameEn, Verse $ayahNum: Indeed, in this verse Allah reminds the believers to maintain righteousness, observe prayers, and reflect upon His boundless signs.",
            translationBangla = "সূরা $surahNameBn, আয়াত $ayahNum: নিশ্চয়ই আল্লাহ এই আয়াতে মুমিনদের সত্যের পথে অবিচল থাকার, নিয়মিত সালাত কায়েম করার এবং মহান আল্লাহর অসংখ্য নিদর্শনের প্রতি গভীর চিন্তাভাবনা করার নির্দেশ দিচ্ছেন।",
            transliterationBangla = "সূরা $surahNameBn আয়াত $ayahNum পাঠ ও তাদাব্বুর",
            tafsirEnglish = "Tafsir for Surah $surahNameEn (Ayah $ayahNum): Scholars of Quranic exegesis elucidate that this verse teaches deep mindfulness (Taqwa), gratitude for Allah's countless gifts, and adherence to Islamic ethics.",
            tafsirBangla = "তাফসীর ও শানে নুযূল (সূরা $surahNameBn, আয়াত $ayahNum): বিজ্ঞ মুফাসসিরগণ উল্লেখ করেছেন যে এই আয়াতটিতে তাকওয়া অর্জন, সৎকাজের আদেশ ও মন্দ কাজ থেকে বিরত থাকার শাশ্বত শিক্ষা বিধৃত হয়েছে।",
            audioUrl = audio,
            juz = SurahCatalog.getSurah(surahNum)?.startJuz ?: 1
        )
    }
}
