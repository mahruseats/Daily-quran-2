package com.example.data.repository

import com.example.data.model.PrayerTimesDay
import com.example.data.model.PrayerTiming
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.TimeZone
import kotlin.math.*

object PrayerCalculator {

    data class City(
        val name: String,
        val banglaName: String,
        val latitude: Double,
        val longitude: Double,
        val timezone: Double
    )

    val supportedCities = listOf(
        City("Dhaka, Bangladesh", "ঢাকা, বাংলাদেশ", 23.8103, 90.4125, 6.0),
        City("Chittagong, Bangladesh", "চট্টগ্রাম, বাংলাদেশ", 22.3569, 91.7832, 6.0),
        City("Sylhet, Bangladesh", "সিলেট, বাংলাদেশ", 24.8949, 91.8687, 6.0),
        City("Makkah, Saudi Arabia", "মক্কা মুকাররমা, সৌদি আরব", 21.4225, 39.8262, 3.0),
        City("Madinah, Saudi Arabia", "মদিনা মুনাওয়ারা, সৌদি আরব", 24.5247, 39.5692, 3.0),
        City("London, UK", "লন্ডন, যুক্তরাজ্য", 51.5074, -0.1278, 0.0),
        City("New York, USA", "নিউইয়র্ক, যুক্তরাষ্ট্র", 40.7128, -74.0060, -5.0),
        City("Dubai, UAE", "দুবাই, সংযুক্ত আরব আমিরাত", 25.2048, 55.2708, 4.0),
        City("Cairo, Egypt", "কায়রো, মিশর", 30.0444, 31.2357, 2.0),
        City("Kuala Lumpur, Malaysia", "কুয়ালালামপুর, মালয়েশিয়া", 3.1390, 101.6869, 8.0)
    )

    fun getCityTimeZone(city: City): TimeZone {
        val tzId = when {
            city.name.contains("Dhaka", ignoreCase = true) ||
            city.name.contains("Chittagong", ignoreCase = true) ||
            city.name.contains("Sylhet", ignoreCase = true) ||
            city.name.contains("Bangladesh", ignoreCase = true) -> "Asia/Dhaka"
            city.name.contains("Makkah", ignoreCase = true) ||
            city.name.contains("Madinah", ignoreCase = true) -> "Asia/Riyadh"
            city.name.contains("Dubai", ignoreCase = true) -> "Asia/Dubai"
            city.name.contains("Cairo", ignoreCase = true) -> "Africa/Cairo"
            city.name.contains("Kuala Lumpur", ignoreCase = true) -> "Asia/Kuala_Lumpur"
            city.name.contains("London", ignoreCase = true) -> "Europe/London"
            city.name.contains("New York", ignoreCase = true) -> "America/New_York"
            else -> {
                val offsetHours = city.timezone.toInt()
                val sign = if (offsetHours >= 0) "+" else "-"
                String.format(Locale.US, "GMT%s%02d:00", sign, abs(offsetHours))
            }
        }
        return TimeZone.getTimeZone(tzId)
    }

    fun calculatePrayerTimes(city: City, date: Date = Date()): PrayerTimesDay {
        // Evaluate the date and current time using the city's exact timezone
        val cityTz = getCityTimeZone(city)
        val cal = Calendar.getInstance(cityTz).apply { time = date }
        val dayOfYear = cal.get(Calendar.DAY_OF_YEAR)

        // Solar calculations
        val b = 2 * Math.PI * (dayOfYear - 81) / 365.0
        val eot = 9.87 * sin(2 * b) - 7.53 * cos(b) - 1.5 * sin(b) // Equation of Time in minutes
        val declination = 23.45 * sin(b) // Solar declination in degrees

        // Solar noon in hours (local time)
        val timeCorrection = (city.longitude - (city.timezone * 15.0)) * 4.0 // minutes
        val solarNoonMinutes = 720 - timeCorrection - eot
        val solarNoonHours = solarNoonMinutes / 60.0

        val latRad = Math.toRadians(city.latitude)
        val decRad = Math.toRadians(declination)

        // Helper for hour angle
        fun hourAngle(altitudeDeg: Double): Double {
            val altRad = Math.toRadians(altitudeDeg)
            val cosHA = (sin(altRad) - sin(latRad) * sin(decRad)) / (cos(latRad) * cos(decRad))
            val clamped = cosHA.coerceIn(-1.0, 1.0)
            return Math.toDegrees(acos(clamped)) / 15.0 // in hours
        }

        val fajrHA = hourAngle(-18.0) // 18 degree twilight
        val sunriseHA = hourAngle(-0.833) // Sunrise
        val maghribHA = hourAngle(-0.833) // Sunset
        val ishaHA = hourAngle(-18.0) // 18 degree twilight

        // Asr calculation: In Bangladesh/South Asia, Hanafi (shadow factor = 2) is observed by default
        val isHanafi = city.name.contains("Bangladesh", ignoreCase = true) ||
                       city.name.contains("Dhaka", ignoreCase = true) ||
                       city.name.contains("Chittagong", ignoreCase = true) ||
                       city.name.contains("Sylhet", ignoreCase = true)
        val shadowFactor = if (isHanafi) 2.0 else 1.0
        val asrAltRad = atan(1.0 / (shadowFactor + tan(abs(latRad - decRad))))
        val asrAltDeg = Math.toDegrees(asrAltRad)
        val asrHA = hourAngle(asrAltDeg)

        val fajrTime = solarNoonHours - fajrHA
        val sunriseTime = solarNoonHours - sunriseHA
        val dhuhrTime = solarNoonHours + (2.0 / 60.0) // 2 minutes after zenith
        val asrTime = solarNoonHours + asrHA
        val maghribTime = solarNoonHours + maghribHA + (2.0 / 60.0)
        val ishaTime = solarNoonHours + ishaHA

        // Tahajjud is recommended in the last third of the night
        val nightDuration = (24.0 - maghribTime) + fajrTime
        val tahajjudTime = (maghribTime + (2.0 / 3.0) * nightDuration) % 24.0

        // Current time in city's local timezone
        val currentHour = cal.get(Calendar.HOUR_OF_DAY)
        val currentMin = cal.get(Calendar.MINUTE)
        val currentSec = cal.get(Calendar.SECOND)
        val nowDec = currentHour + (currentMin / 60.0) + (currentSec / 3600.0)

        fun toTiming(nameEn: String, nameBn: String, decHours: Double): PrayerTiming {
            val h = (decHours.toInt() % 24 + 24) % 24
            val m = ((decHours - floor(decHours)) * 60).toInt().coerceIn(0, 59)
            val amPm = if (h >= 12) "PM" else "AM"
            val displayH = if (h % 12 == 0) 12 else h % 12
            val formatted = String.format(Locale.getDefault(), "%02d:%02d %s", displayH, m, amPm)
            return PrayerTiming(nameEn, nameBn, formatted, h, m)
        }

        val fajr = toTiming("Fajr", "ফজর", fajrTime)
        val sunrise = toTiming("Sunrise", "সূর্যোদয়", sunriseTime)
        val dhuhr = toTiming("Dhuhr", "যোহর", dhuhrTime)
        val asr = toTiming("Asr", "আসর", asrTime)
        val maghrib = toTiming("Maghrib", "মাগরিব", maghribTime)
        val isha = toTiming("Isha", "ইশা", ishaTime)
        val tahajjud = toTiming("Tahajjud", "তাহাজ্জুদ", tahajjudTime)

        // Only the 5 obligatory Fard prayers determine the next upcoming Salah
        // (Sunrise is a prohibited prayer time, not a Salah)
        val fardPrayers = listOf(
            fajrTime to fajr,
            dhuhrTime to dhuhr,
            asrTime to asr,
            maghribTime to maghrib,
            ishaTime to isha
        )

        var nextTiming = fajr
        var minDiffHours = 24.0

        for ((pTime, timing) in fardPrayers) {
            val diff = if (pTime >= nowDec) {
                pTime - nowDec
            } else {
                (pTime + 24.0) - nowDec
            }
            if (diff < minDiffHours) {
                minDiffHours = diff
                nextTiming = timing
            }
        }

        val nextHours = minDiffHours.toInt()
        val nextMins = ((minDiffHours - nextHours) * 60).toInt()
        val timeLeftFormatted = "${nextHours}h ${nextMins}m"

        // Hijri date approximation
        val hijriDate = calculateHijriDate(cal)
        val gregorianFormat = SimpleDateFormat("EEEE, d MMMM yyyy", Locale.getDefault()).apply {
            timeZone = cityTz
        }
        val gregorianDate = gregorianFormat.format(date)

        return PrayerTimesDay(
            fajr = fajr,
            sunrise = sunrise,
            dhuhr = dhuhr,
            asr = asr,
            maghrib = maghrib,
            isha = isha,
            tahajjud = tahajjud,
            nextPrayerName = nextTiming.nameEnglish,
            nextPrayerTimeLeft = timeLeftFormatted,
            hijriDate = hijriDate,
            gregorianDate = gregorianDate,
            cityName = city.banglaName
        )
    }

    private fun calculateHijriDate(calendar: Calendar): String {
        // Approximate Hijri computation
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH) + 1
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        // Estimated Hijri Year based on current year
        val hijriYear = 1448
        val islamicMonths = listOf(
            "মুহাররম", "সফর", "রবিউল আউয়াল", "রবিউস সানি",
            "জুমাদাল উলা", "জুমাদাস সানি", "রজব", "শাবান",
            "রমাদান", "শাওয়াল", "জিলকদ", "জিলহজ"
        )
        val hijriMonth = islamicMonths[month % 12]
        return "$day $hijriMonth, $hijriYear হিজরি"
    }

    fun calculateQiblaBearing(lat: Double, lon: Double): Double {
        // Kaaba coordinates
        val kaabaLat = Math.toRadians(21.4225)
        val kaabaLon = Math.toRadians(39.8262)
        val userLat = Math.toRadians(lat)
        val userLon = Math.toRadians(lon)

        val dLon = kaabaLon - userLon
        val y = sin(dLon) * cos(kaabaLat)
        val x = cos(userLat) * sin(kaabaLat) - sin(userLat) * cos(kaabaLat) * cos(dLon)
        var qibla = Math.toDegrees(atan2(y, x))
        qibla = (qibla + 360) % 360
        return qibla
    }
}
