package com.example.data.source

object QuranScriptConverter {

    /**
     * Converts standard Uthmani Arabic text to authentic Indo-Pak Quranic script.
     * Common in Bangladesh, Pakistan, and India.
     */
    fun convertToIndoPak(arabicText: String): String {
        if (arabicText.isEmpty()) return arabicText

        var result = arabicText

        // Hamzat al-Wasl -> Standard Alif
        result = result.replace("ٱ", "ا")
        result = result.replace("إ", "اِ")
        result = result.replace("أ", "اَ")

        // Allah ligature formatting in Indo-Pak (Khari Zabar on Lam)
        result = result.replace("اللَّهِ", "اللّٰهِ")
        result = result.replace("لِلَّهِ", "لِلّٰهِ")
        result = result.replace("اللَّهُ", "اللّٰهُ")
        result = result.replace("اللَّهَ", "اللّٰهَ")
        result = result.replace("بِاللَّهِ", "بِاللّٰهِ")
        result = result.replace("وَاللَّهِ", "وَاللّٰهِ")

        // Dagger Alif (Superscript Alef) normalization
        result = result.replace("الرَّحْمَٰنِ", "الرَّحۡمٰنِ")
        result = result.replace("الرَّحْمَٰنُ", "الرَّحۡمٰنُ")
        result = result.replace("الْعَالَمِينَ", "الۡعٰلَمِیۡنَ")
        result = result.replace("الْعَٰلَمِينَ", "الۡعٰلَمِیۡنَ")
        result = result.replace("مَالِكِ", "مٰلِکِ")
        result = result.replace("مَٰلِكِ", "مٰلِکِ")
        result = result.replace("هَٰذَا", "هٰذَا")
        result = result.replace("ذَٰلِكَ", "ذٰلِکَ")
        result = result.replace("إِلَٰهَ", "اِلٰهَ")

        // Sukun conversion: Standard Arabic Sukun (ْ) -> IndoPak Jazm (ۡ)
        result = result.replace("\u0652", "\u06E1")

        // Kaf replacement for Indo-Pak Persian/Urdu style Kaf (ک)
        result = result.replace("ك", "ک")

        // Ya with sukun -> Indo-Pak style Ya with Jazm (یۡ)
        result = result.replace("ينَ", "ینَ")
        result = result.replace("يمِ", "یمِ")
        result = result.replace("يرَ", "یرَ")
        result = result.replace("يعُ", "یعُ")
        result = result.replace("يۡ", "یۡ")
        result = result.replace("يْ", "یۡ")

        // Maddah normalization
        result = result.replace("آ", "آ")

        return result
    }

    /**
     * Converts standard Uthmani Arabic text to Indonesian Standard Quran script (Mushaf Standar Indonesia - Kemenag RI).
     */
    fun convertToIndonesian(arabicText: String): String {
        if (arabicText.isEmpty()) return arabicText

        var result = arabicText

        // Hamzat al-Wasl rendered with explicit vowel in Indonesian standard
        result = result.replace("ٱلْ", "اَلْ")
        result = result.replace("ٱل", "ال")
        result = result.replace("ٱ", "ا")

        // Word 'Allah' representation in Indonesian Kemenag standard
        result = result.replace("اللَّهِ", "اللّٰهِ")
        result = result.replace("لِلَّهِ", "لِلّٰهِ")
        result = result.replace("اللَّهُ", "اللّٰهُ")
        result = result.replace("اللَّهَ", "اللّٰهَ")
        result = result.replace("بِاللَّهِ", "بِاللّٰهِ")

        // Common Quranic words according to Indonesian Standard
        result = result.replace("الرَّحْمَٰنِ", "الرَّحْمٰنِ")
        result = result.replace("الرَّحْمَٰنُ", "الرَّحْمٰنُ")
        result = result.replace("الْعَالَمِينَ", "الْعٰلَمِيْنَ")
        result = result.replace("الْعَٰلَمِينَ", "الْعٰلَمِيْنَ")
        result = result.replace("مَالِكِ", "مٰلِكِ")
        result = result.replace("مَٰلِكِ", "مٰلِكِ")
        result = result.replace("الصِّرَاطَ", "الصِّرَاطَ")
        result = result.replace("الْمُسْتَقِيمَ", "الْمُسْتَقِيْمَ")
        result = result.replace("الَّذِينَ", "الَّذِيْنَ")
        result = result.replace("أَنْعَمْتَ", "اَنْعَمْتَ")
        result = result.replace("الْمَغْضُوبِ", "الْمَغْضُوْبِ")
        result = result.replace("الضَّالِّينَ", "الضَّاۤلِّيْنَ")

        // Indonesian Standard uses traditional Arabic Kaf and Yaa
        result = result.replace("ک", "ك")

        // Add Indonesian Madd Jaiz/Wajib mark where appropriate
        result = result.replace("الضَّآلِّینَ", "الضَّاۤلِّيْنَ")
        result = result.replace("الضَّالِّينَ", "الضَّاۤلِّيْنَ")

        return result
    }
}
