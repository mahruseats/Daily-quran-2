package com.example.data.source

import com.example.ui.viewmodel.TranslationDisplayMode

object QuranWordDictionary {

    private data class WordMeaning(val bangla: String, val english: String)

    // Strips Arabic diacritics / tashkeel for fuzzy dictionary matching
    fun normalizeArabic(text: String): String {
        return text
            .replace(Regex("[\u064B-\u065F\u0670\u06D6-\u06ED]"), "")
            .replace("ٱ", "ا")
            .replace("إ", "ا")
            .replace("أ", "ا")
            .replace("آ", "ا")
            .replace("ة", "ه")
            .replace("ى", "ي")
            .trim()
    }

    fun containsArabicLetters(text: String): Boolean {
        return text.any { it in '\u0600'..'\u06FF' || it in '\u0750'..'\u077F' || it in '\u08A0'..'\u08FF' }
    }

    private val dictionary: Map<String, WordMeaning> = mapOf(
        // Common pronouns, prepositions, particles
        "في" to WordMeaning("মধ্যে", "In"),
        "على" to WordMeaning("ওপর", "Upon"),
        "من" to WordMeaning("হতে / থেকে", "From"),
        "الى" to WordMeaning("দিকে", "To"),
        "عن" to WordMeaning("সম্পর্কে", "About"),
        "مع" to WordMeaning("সাথে", "With"),
        "ب" to WordMeaning("দ্বারা / সাথে", "By / With"),
        "ل" to WordMeaning("জন্য", "For"),
        "ان" to WordMeaning("নিশ্চয়ই", "Indeed"),
        "انما" to WordMeaning("কেবল / শুধুমাত্র", "Only"),
        "ما" to WordMeaning("যা / যা কিছু", "What / Not"),
        "لا" to WordMeaning("না / নেই", "No / Not"),
        "لم" to WordMeaning("না", "Not"),
        "لن" to WordMeaning("কখনও না", "Never"),
        "هو" to WordMeaning("তিনি", "He"),
        "هي" to WordMeaning("তিনি (স্ত্রী)", "She"),
        "هم" to WordMeaning("তারা", "They"),
        "نحن" to WordMeaning("আমরা", "We"),
        "انت" to WordMeaning("আপনি / তুমি", "You"),
        "انا" to WordMeaning("আমি", "I"),
        "هذا" to WordMeaning("এটি", "This"),
        "هذه" to WordMeaning("এটি (স্ত্রী)", "This"),
        "ذلك" to WordMeaning("ঐটি / এ সেই", "That"),
        "تلك" to WordMeaning("ঐটি", "That"),
        "اولئك" to WordMeaning("তারাই", "Those"),
        "الذي" to WordMeaning("যিনি / যা", "Who / Which"),
        "التي" to WordMeaning("যিনি (স্ত্রী)", "Who"),
        "الذين" to WordMeaning("যারা", "Those who"),
        "اذا" to WordMeaning("যখন", "When"),
        "اذ" to WordMeaning("যখন", "When"),
        "لو" to WordMeaning("যদি", "If"),
        "بل" to WordMeaning("বরং", "Rather"),
        "ثم" to WordMeaning("অতঃপর", "Then"),
        "او" to WordMeaning("অথবা", "Or"),
        "ام" to WordMeaning("নাকি", "Or"),
        "كل" to WordMeaning("প্রত্যেক", "Every / All"),
        "يا" to WordMeaning("হে", "O"),
        "ايها" to WordMeaning("হে", "O you"),
        "قد" to WordMeaning("নিশ্চয়ই / ইতিপূর্বে", "Already / Indeed"),
        "لقد" to WordMeaning("নিশ্চয়ই", "Indeed"),
        "حتى" to WordMeaning("পর্যন্ত", "Until"),
        "غير" to WordMeaning("ছাড়া / ব্যতীত", "Other than / Not"),

        // Divine names & attributes
        "الله" to WordMeaning("আল্লাহ", "Allah"),
        "الرحمن" to WordMeaning("পরম করুণাময়", "The Entirely Merciful"),
        "الرحيم" to WordMeaning("অতি দয়ালু", "The Especially Merciful"),
        "رب" to WordMeaning("প্রতিপালক", "Lord"),
        "ربكم" to WordMeaning("তোমাদের পালনকর্তা", "Your Lord"),
        "ربهم" to WordMeaning("তাদের পালনকর্তা", "Their Lord"),
        "ربنا" to WordMeaning("আমাদের পালনকর্তা", "Our Lord"),
        "اله" to WordMeaning("উপাস্য / ইলাহ", "God / Deity"),
        "العزيز" to WordMeaning("পরাক্রমশালী", "The Almighty"),
        "الحكيم" to WordMeaning("মহাপ্রজ্ঞাময়", "The All-Wise"),
        "العليم" to WordMeaning("সর্বজ্ঞ", "The All-Knowing"),
        "السميع" to WordMeaning("সর্বশ্রোতা", "The All-Hearing"),
        "البصير" to WordMeaning("সর্বদ্রষ্টা", "The All-Seeing"),
        "الغفور" to WordMeaning("পরম ক্ষমাশীল", "The Most Forgiving"),
        "القدير" to WordMeaning("সর্বশক্তিমান", "The All-Powerful"),
        "الملك" to WordMeaning("অধিপতি / বাদশাহ", "The King / Sovereign"),
        "القدوس" to WordMeaning("মহা পবিত্র", "The Most Holy"),
        "السلام" to WordMeaning("শান্তিদাতা", "The Source of Peace"),
        "المؤمن" to WordMeaning("নিরাপত্তাদাতা", "The Giver of Peace"),
        "الصمد" to WordMeaning("অমুখাপেক্ষী", "The Eternal Refuge"),
        "الحي" to WordMeaning("চিরঞ্জীব", "The Ever-Living"),
        "القيوم" to WordMeaning("চিরস্থায়ী পরিচালক", "The Sustainer"),
        "العلي" to WordMeaning("সর্বোচ্চ", "The Most High"),
        "العظيم" to WordMeaning("মহান", "The Magnificent"),

        // Key Quranic concepts
        "كتاب" to WordMeaning("কিতাব / গ্রন্থ", "Book"),
        "الكتاب" to WordMeaning("কিতাব / কুরআন", "The Book"),
        "قران" to WordMeaning("কুরআন", "Quran"),
        "القران" to WordMeaning("কুরআন মাজীদ", "The Quran"),
        "ايات" to WordMeaning("নিদর্শনসমূহ / আয়াত", "Signs / Verses"),
        "اية" to WordMeaning("আয়াত / নিদর্শন", "Sign / Verse"),
        "هدى" to WordMeaning("সঠিক পথপ্রদর্শন", "Guidance"),
        "نور" to WordMeaning("আলো / জ্যোতি", "Light"),
        "حق" to WordMeaning("সত্য", "Truth"),
        "الحق" to WordMeaning("পরম সত্য", "The Truth"),
        "حقا" to WordMeaning("সত্যিই", "Truly"),
        "باطل" to WordMeaning("মিথ্যা / অসত্য", "Falsehood"),
        "صراط" to WordMeaning("সরল পথ", "Path"),
        "الصراط" to WordMeaning("সঠিক পথ", "The Path"),
        "مستقيم" to WordMeaning("সুদৃঢ় সোজা", "Straight"),
        "المستقيم" to WordMeaning("সরল-সঠিক", "The Straight"),
        "سبيل" to WordMeaning("পথ", "Way / Path"),
        "دين" to WordMeaning("দ্বীন / ধর্ম / বিচার", "Religion / Judgment"),
        "الدين" to WordMeaning("প্রতিদান ও বিচার", "Judgment / Religion"),
        "يوم" to WordMeaning("দিন / দিবস", "Day"),
        "اليوم" to WordMeaning("আজ / সেই দিবস", "Today / The Day"),
        "العالمين" to WordMeaning("সমগ্র সৃষ্টিজগৎ", "The Worlds"),
        "السماوات" to WordMeaning("আকাশমন্ডলী", "The Heavens"),
        "السماء" to WordMeaning("আকাশ", "The Sky"),
        "الارض" to WordMeaning("পৃথিবী", "The Earth"),
        "شمس" to WordMeaning("সূর্য", "Sun"),
        "قمر" to WordMeaning("চাঁদ", "Moon"),
        "ليل" to WordMeaning("রাত", "Night"),
        "نهار" to WordMeaning("দিন", "Day"),
        "جنه" to WordMeaning("জান্নাত / বাগান", "Paradise / Garden"),
        "الجنه" to WordMeaning("জান্নাত", "The Paradise"),
        "نار" to WordMeaning("আগুন / জাহান্নাম", "Fire / Hell"),
        "النار" to WordMeaning("জাহান্নামের আগুন", "The Fire"),
        "جهنم" to WordMeaning("জাহান্নাম", "Hell"),
        "عذاب" to WordMeaning("শাস্তি", "Punishment"),
        "العذاب" to WordMeaning("কঠোর শাস্তি", "The Punishment"),
        "اجر" to WordMeaning("পুরস্কার / প্রতিদান", "Reward"),
        "رحمه" to WordMeaning("রহমত / দয়া", "Mercy"),
        "فضل" to WordMeaning("অনুগ্রহ", "Bounty / Favor"),
        "نعمه" to WordMeaning("নেয়ামত / দান", "Blessing"),
        "صلاه" to WordMeaning("সালাত / নামাজ", "Prayer"),
        "الصلاه" to WordMeaning("সালাত", "The Prayer"),
        "زكاه" to WordMeaning("যাকাত", "Zakah / Charity"),
        "صوم" to WordMeaning("রোজা / সিয়াম", "Fasting"),
        "حج" to WordMeaning("হজ্ব", "Pilgrimage"),
        "تقوى" to WordMeaning("তাকওয়া / খোদাভীতি", "Righteousness / Taqwa"),
        "ايمان" to WordMeaning("ঈমান / বিশ্বাস", "Faith / Belief"),
        "كفر" to WordMeaning("কুফরি / অবিশ্বাস", "Disbelief"),
        "شرك" to WordMeaning("শিরক", "Polytheism"),
        "نفاق" to WordMeaning("মুনাফেকি", "Hypocrisy"),
        "ذنب" to WordMeaning("পাপ", "Sin"),
        "سيئات" to WordMeaning("মন্দ কাজ", "Evil deeds"),
        "حسنات" to WordMeaning("নেক কাজ", "Good deeds"),
        "خير" to WordMeaning("উত্তম / কল্যাণ", "Good / Better"),
        "شر" to WordMeaning("অনিষ্ট / মন্দ", "Evil"),
        "فتنه" to WordMeaning("পরীক্ষা / ফিতনা", "Trial / Affliction"),
        "صبر" to WordMeaning("ধৈর্য", "Patience"),
        "شكر" to WordMeaning("কৃতজ্ঞতা", "Gratitude"),
        "توبه" to WordMeaning("তওবা / ক্ষমা প্রার্থনা", "Repentance"),
        "استغفار" to WordMeaning("ক্ষমা প্রার্থনা", "Seeking forgiveness"),

        // People & Creation
        "انسان" to WordMeaning("মানুষ", "Human / Man"),
        "الانسان" to WordMeaning("মানবজাতি", "Mankind"),
        "ناس" to WordMeaning("মানুষ", "People"),
        "الناس" to WordMeaning("মানুষসকল", "Mankind / People"),
        "مؤمن" to WordMeaning("মুমিন / বিশ্বাসী", "Believer"),
        "مؤمنون" to WordMeaning("মুমিনগণ", "Believers"),
        "المؤمنين" to WordMeaning("মুমিনদের", "The Believers"),
        "كافر" to WordMeaning("কাফির / অবিশ্বাসী", "Disbeliever"),
        "كافرون" to WordMeaning("কাফিরগণ", "Disbelievers"),
        "الكافرين" to WordMeaning("কাফিরদের", "The Disbelievers"),
        "منافق" to WordMeaning("মুনাফিক", "Hypocrite"),
        "منافقون" to WordMeaning("মুনাফিকগণ", "Hypocrites"),
        "متقين" to WordMeaning("মুত্তাকীগণ", "The Righteous"),
        "المتقين" to WordMeaning("মুত্তাকীদের", "The God-conscious"),
        "صالحين" to WordMeaning("সৎকর্মশীলগণ", "The Righteous"),
        "ظالمين" to WordMeaning("জালিমগণ", "Wrongdoers"),
        "الظالمين" to WordMeaning("জালিমদের", "The Wrongdoers"),
        "فاسقين" to WordMeaning("পাপাচারীগণ", "The Defiantly Disobedient"),
        "نبي" to WordMeaning("নবী", "Prophet"),
        "الانبياء" to WordMeaning("নবীগণ", "The Prophets"),
        "رسول" to WordMeaning("রাসূল", "Messenger"),
        "الرسول" to WordMeaning("রাসূল", "The Messenger"),
        "رسل" to WordMeaning("রাসূলগণ", "Messengers"),
        "ملائكه" to WordMeaning("ফেরেশতাগণ", "Angels"),
        "ملك" to WordMeaning("ফেরেশতা", "Angel"),
        "جن" to WordMeaning("জিন", "Jinn"),
        "شيطان" to WordMeaning("শয়তান", "Satan / Devil"),
        "الشيطان" to WordMeaning("শয়তান", "The Satan"),
        "ابليس" to WordMeaning("ইবলিস", "Iblees"),

        // Common Verbs
        "قال" to WordMeaning("বললেন", "Said"),
        "قالوا" to WordMeaning("তারা বলল", "They said"),
        "قل" to WordMeaning("বলুন", "Say"),
        "يقول" to WordMeaning("বলে", "Says"),
        "يقولون" to WordMeaning("তারা বলে", "They say"),
        "كان" to WordMeaning("ছিল / হয়", "Was / Is"),
        "كانوا" to WordMeaning("তারা ছিল", "They were"),
        "يكون" to WordMeaning("হবে / হয়", "Will be / Is"),
        "كن" to WordMeaning("হও", "Be"),
        "امن" to WordMeaning("ঈমান এনেছে", "Believed"),
        "امنوا" to WordMeaning("তারা ঈমান এনেছে", "They believed"),
        "يؤمن" to WordMeaning("ঈমান আনে", "Believes"),
        "يؤمنون" to WordMeaning("তারা ঈমান আনে", "They believe"),
        "كفر" to WordMeaning("কুফরি করেছে", "Disbelieved"),
        "كفروا" to WordMeaning("তারা কুফরি করেছে", "They disbelieved"),
        "عمل" to WordMeaning("কাজ করেছে", "Did / Worked"),
        "عملوا" to WordMeaning("তারা কাজ করেছে", "They did"),
        "يعملون" to WordMeaning("তারা করে", "They do"),
        "خلق" to WordMeaning("সৃষ্টি করেছেন", "Created"),
        "يخلق" to WordMeaning("সৃষ্টি করেন", "Creates"),
        "جعل" to WordMeaning("বানিয়েছেন / করেছেন", "Made / Appointed"),
        "انزل" to WordMeaning("নাজিল করেছেন", "Revealed"),
        "ارسل" to WordMeaning("প্রেরণ করেছেন", "Sent"),
        "اتقوا" to WordMeaning("তাকওয়া অবলম্বন কর", "Fear Allah / Be mindful"),
        "اعبدوا" to WordMeaning("তোমরা ইবাদত কর", "Worship"),
        "نعبد" to WordMeaning("আমরা ইবাদত করি", "We worship"),
        "نستعين" to WordMeaning("সাহায্য চাই", "We seek help"),
        "اهدنا" to WordMeaning("আমাদের পথ দেখান", "Guide us"),
        "انعمت" to WordMeaning("অনুগ্রহ করেছেন", "You bestowed favor"),
        "علم" to WordMeaning("জেনেছেন / শিখিয়েছেন", "Knew / Taught"),
        "يعلم" to WordMeaning("জানেন", "Knows"),
        "يعلمون" to WordMeaning("তারা জানে", "They know"),
        "لا يعلمون" to WordMeaning("তারা জানে না", "They know not"),
        "سمع" to WordMeaning("শুনেছেন", "Heard"),
        "يرى" to WordMeaning("দেখেন", "Sees"),
        "رزق" to WordMeaning("রিযিক দিয়েছেন", "Provided"),
        "رزقناهم" to WordMeaning("আমরা তাদের রিযিক দিয়েছি", "We provided them"),
        "ينفقون" to WordMeaning("তারা ব্যয় করে", "They spend"),
        "يقيمون" to WordMeaning("তারা কায়েম করে", "They establish"),
        "يوقنون" to WordMeaning("তারা বিশ্বাস রাখে", "They are certain"),
        "ظلموا" to WordMeaning("তারা জুলুম করেছে", "They wronged"),
        "يهدي" to WordMeaning("পথ দেখান", "Guides"),
        "يضل" to WordMeaning("পথভ্রষ্ট করেন", "Misguides"),
        "غفر" to WordMeaning("ক্ষমা করেছেন", "Forgave"),
        "يغفر" to WordMeaning("ক্ষমা করেন", "Forgives"),
        "رحم" to WordMeaning("দয়া করেছেন", "Had mercy"),
        "كتب" to WordMeaning("লিখেছেন / অবধারিত করেছেন", "Decreed / Wrote"),
        "جاء" to WordMeaning("এসেছে", "Came"),
        "ذهب" to WordMeaning("চলে গেছে", "Went away"),
        "راى" to WordMeaning("দেখেছেন", "Saw"),
        "اتى" to WordMeaning("এসেছেন / দিয়েছেন", "Came / Brought"),
        "حسب" to WordMeaning("মনে করেছে", "Thought"),
        "وعد" to WordMeaning("ওয়াদা করেছেন", "Promised"),
        "خاف" to WordMeaning("ভয় পেয়েছে", "Feared"),
        "تاب" to WordMeaning("তওবা কবুল করেছেন", "Turned in forgiveness"),
        "دخل" to WordMeaning("প্রবেশ করেছে", "Entered"),
        "خرج" to WordMeaning("বের হয়েছে", "Came out"),
        "نصر" to WordMeaning("সাহায্য করেছেন", "Helped"),
        "شهد" to WordMeaning("সাক্ষ্য দিয়েছেন", "Witnessed"),
        "حكم" to WordMeaning("ফয়সালা করেছেন", "Judged"),
        "حسبنا" to WordMeaning("আমাদের জন্য যথেষ্ট", "Sufficient for us"),
        "قلوب" to WordMeaning("অন্তরসমূহ", "Hearts"),
        "قلب" to WordMeaning("অন্তর", "Heart"),
        "صدور" to WordMeaning("বক্ষসমূহ / অন্তরসমূহ", "Breasts"),
        "اعين" to WordMeaning("চোখসমূহ", "Eyes"),
        "اذان" to WordMeaning("কানসমূহ", "Ears"),
        "انفس" to WordMeaning("প্রাণ / নিজেদের", "Souls / Themselves"),
        "نفس" to WordMeaning("আত্মা / ব্যক্তি", "Soul / Person"),
        "اموال" to WordMeaning("সম্পদসমূহ", "Wealth"),
        "اولاد" to WordMeaning("সন্তান-সন্ততি", "Children"),
        "قوم" to WordMeaning("জাতি / সম্প্রদায়", "People / Nation"),
        "بيت" to WordMeaning("ঘর / গৃহ", "House"),
        "مسجد" to WordMeaning("মসজিদ", "Mosque"),
        "ماء" to WordMeaning("পানি", "Water"),
        "شجر" to WordMeaning("গাছপালা", "Trees"),
        "جبل" to WordMeaning("পাহাড়", "Mountain"),
        "جبال" to WordMeaning("পাহাড়সমূহ", "Mountains"),
        "بحر" to WordMeaning("সাগর", "Sea"),
        "موت" to WordMeaning("মৃত্যু", "Death"),
        "حياه" to WordMeaning("জীবন", "Life"),
        "دنيا" to WordMeaning("দুনিয়া / পার্থিব জীবন", "World"),
        "اخره" to WordMeaning("আখিরাত / পরকাল", "Hereafter")
    )

    /**
     * Looks up authentic Bangla or English meaning for an Arabic word.
     * Returns empty string if not found or if the meaning contains Arabic characters.
     */
    fun resolveMeaning(
        arabicWord: String,
        providedBangla: String,
        providedEnglish: String,
        mode: TranslationDisplayMode
    ): String {
        // First check if provided meanings are valid (non-blank and NOT Arabic)
        val hasValidBangla = providedBangla.isNotBlank() && !containsArabicLetters(providedBangla) && providedBangla != arabicWord
        val hasValidEnglish = providedEnglish.isNotBlank() && !containsArabicLetters(providedEnglish) && providedEnglish != arabicWord

        var bangla = if (hasValidBangla) providedBangla else ""
        var english = if (hasValidEnglish) providedEnglish else ""

        // If either is missing, look up in normalized dictionary
        if (bangla.isBlank() || english.isBlank()) {
            val normalized = normalizeArabic(arabicWord)
            val match = dictionary[normalized]
                ?: dictionary[normalized.removePrefix("و")]
                ?: dictionary[normalized.removePrefix("ف")]
                ?: dictionary[normalized.removePrefix("ب")]
                ?: dictionary[normalized.removePrefix("ل")]
                ?: dictionary[normalized.removePrefix("ال")]

            if (match != null) {
                if (bangla.isBlank()) bangla = match.bangla
                if (english.isBlank()) english = match.english
            }
        }

        // Format according to user's selected translation mode
        val result = when (mode) {
            TranslationDisplayMode.BANGLA_ONLY -> bangla
            TranslationDisplayMode.ENGLISH_ONLY -> english
            TranslationDisplayMode.BOTH -> {
                when {
                    bangla.isNotBlank() && english.isNotBlank() -> "$bangla ($english)"
                    bangla.isNotBlank() -> bangla
                    english.isNotBlank() -> english
                    else -> ""
                }
            }
        }

        // STRICT SAFETY: Ensure NO Arabic characters are ever returned as the meaning!
        return if (containsArabicLetters(result)) "" else result.trim()
    }
}
