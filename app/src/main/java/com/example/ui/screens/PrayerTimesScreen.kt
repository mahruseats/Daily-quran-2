package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CompassCalibration
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.NotificationsActive
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.PrayerTiming
import com.example.data.repository.PrayerCalculator
import com.example.ui.theme.ArabicAccentGold
import com.example.ui.theme.TimesRomanFontFamily
import com.example.ui.util.AppStrings
import com.example.ui.viewmodel.QuranViewModel
import com.example.ui.viewmodel.TranslationDisplayMode

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PrayerTimesScreen(
    viewModel: QuranViewModel,
    translationLanguage: TranslationDisplayMode = TranslationDisplayMode.BOTH
) {
    val prayerTimes by viewModel.prayerTimes.collectAsState()
    val selectedCity by viewModel.selectedCity.collectAsState()
    var showCityMenu by remember { mutableStateOf(false) }
    val isEng = AppStrings.isEnglish(translationLanguage)
    val font = if (isEng) TimesRomanFontFamily else androidx.compose.ui.text.font.FontFamily.Default

    val qiblaBearing = remember(selectedCity) {
        PrayerCalculator.calculateQiblaBearing(selectedCity.latitude, selectedCity.longitude)
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        // City Selector & Hijri Date Card
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f)
                )
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    // City Dropdown
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .clip(RoundedCornerShape(12.dp))
                                    .clickable { showCityMenu = true }
                                    .background(MaterialTheme.colorScheme.surface)
                                    .padding(horizontal = 12.dp, vertical = 6.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.LocationOn,
                                    contentDescription = "Location",
                                    tint = MaterialTheme.colorScheme.primary,
                                    modifier = Modifier.size(18.dp)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = if (isEng) selectedCity.name else selectedCity.banglaName,
                                    fontFamily = font,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 14.sp,
                                    color = MaterialTheme.colorScheme.primary
                                )
                            }

                            DropdownMenu(
                                expanded = showCityMenu,
                                onDismissRequest = { showCityMenu = false }
                            ) {
                                PrayerCalculator.supportedCities.forEach { city ->
                                    DropdownMenuItem(
                                        text = {
                                            Text(
                                                text = if (isEng) city.name else city.banglaName,
                                                fontFamily = font
                                            )
                                        },
                                        onClick = {
                                            viewModel.selectCity(city)
                                            showCityMenu = false
                                        }
                                    )
                                }
                            }
                        }

                        // Qibla Direction
                        Surface(
                            shape = RoundedCornerShape(12.dp),
                            color = MaterialTheme.colorScheme.surface
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Default.CompassCalibration,
                                    contentDescription = "Qibla",
                                    tint = ArabicAccentGold,
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = AppStrings.qiblaDirection(translationLanguage, qiblaBearing.toInt()),
                                    fontFamily = font,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    // Next Prayer Countdown
                    val nextName = if (isEng) {
                        when (prayerTimes.nextPrayerName) {
                            prayerTimes.fajr.nameBangla -> prayerTimes.fajr.nameEnglish
                            prayerTimes.sunrise.nameBangla -> prayerTimes.sunrise.nameEnglish
                            prayerTimes.dhuhr.nameBangla -> prayerTimes.dhuhr.nameEnglish
                            prayerTimes.asr.nameBangla -> prayerTimes.asr.nameEnglish
                            prayerTimes.maghrib.nameBangla -> prayerTimes.maghrib.nameEnglish
                            prayerTimes.isha.nameBangla -> prayerTimes.isha.nameEnglish
                            prayerTimes.tahajjud.nameBangla -> prayerTimes.tahajjud.nameEnglish
                            else -> prayerTimes.nextPrayerName
                        }
                    } else {
                        prayerTimes.nextPrayerName
                    }

                    Text(
                        text = "${AppStrings.nextPrayerLabel(translationLanguage)}$nextName",
                        fontFamily = font,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                    Text(
                        text = "${AppStrings.timeLeftLabel(translationLanguage)}${prayerTimes.nextPrayerTimeLeft}",
                        fontFamily = font,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )

                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "${prayerTimes.hijriDate} • ${prayerTimes.gregorianDate}",
                        fontFamily = font,
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }

        // Section title
        item {
            Text(
                text = AppStrings.todaySalahSchedule(translationLanguage),
                fontFamily = font,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(vertical = 8.dp)
            )
        }

        // Daily Salah Timings
        val timings = listOf(
            prayerTimes.fajr,
            prayerTimes.sunrise,
            prayerTimes.dhuhr,
            prayerTimes.asr,
            prayerTimes.maghrib,
            prayerTimes.isha,
            prayerTimes.tahajjud
        )

        items(timings.size) { index ->
            val timing = timings[index]
            val isNext = timing.nameEnglish.equals(prayerTimes.nextPrayerName, ignoreCase = true) ||
                    timing.nameBangla.equals(prayerTimes.nextPrayerName, ignoreCase = true)

            PrayerTimingCard(
                timing = timing,
                isNext = isNext,
                translationLanguage = translationLanguage
            )
        }

        item {
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
fun PrayerTimingCard(
    timing: PrayerTiming,
    isNext: Boolean,
    translationLanguage: TranslationDisplayMode = TranslationDisplayMode.BOTH
) {
    val isEng = AppStrings.isEnglish(translationLanguage)
    val font = if (isEng) TimesRomanFontFamily else androidx.compose.ui.text.font.FontFamily.Default

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isNext) {
                MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.6f)
            } else {
                MaterialTheme.colorScheme.surface
            }
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = if (isNext) 3.dp else 1.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 14.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(38.dp)
                        .clip(CircleShape)
                        .background(
                            if (isNext) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceVariant
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = if (timing.nameEnglish == "Sunrise") Icons.Default.WbSunny else Icons.Default.NotificationsActive,
                        contentDescription = timing.nameEnglish,
                        tint = if (isNext) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(20.dp)
                    )
                }

                Spacer(modifier = Modifier.width(12.dp))

                Column {
                    Text(
                        text = if (isEng) timing.nameEnglish else "${timing.nameBangla} (${timing.nameEnglish})",
                        fontFamily = font,
                        fontWeight = if (isNext) FontWeight.Bold else FontWeight.SemiBold,
                        fontSize = 15.sp,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    if (isNext) {
                        Text(
                            text = AppStrings.nextPrayerBadge(translationLanguage),
                            fontFamily = font,
                            fontSize = 11.sp,
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            Text(
                text = timing.timeFormatted,
                fontFamily = font,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = if (isNext) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
            )
        }
    }
}
