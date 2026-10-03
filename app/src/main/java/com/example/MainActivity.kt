package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ads.AdMobManager
import com.example.ui.screens.QuranHomeScreen
import com.example.ui.screens.RabbanaDuasScreen
import com.example.ui.screens.SettingsScreen
import com.example.ui.screens.SurahReaderScreen
import com.example.ui.theme.DailyQuranTheme
import com.example.ui.viewmodel.HomeTab
import com.example.ui.viewmodel.QuranViewModel

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Initialize AdMob and load App Open, Interstitial, and Banner
        AdMobManager.initialize(this)

        setContent {
            val quranViewModel: QuranViewModel = viewModel()
            val isNightMode by quranViewModel.isNightMode.collectAsState()

            DailyQuranTheme(darkTheme = isNightMode) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    QuranAppMain(
                        activity = this,
                        viewModel = quranViewModel
                    )
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        // Attempt to show App Open Ad on launch if available
        AdMobManager.showAppOpenAdIfAvailable(this)
    }
}

@Composable
fun QuranAppMain(
    activity: ComponentActivity,
    viewModel: QuranViewModel
) {
    val currentReadingSurah by viewModel.currentReadingSurah.collectAsState()
    val currentTab by viewModel.currentTab.collectAsState()
    val isShowingSettings by viewModel.isShowingSettings.collectAsState()
    var isShowingRabbanaDuasSubscreen by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        AdMobManager.showAppOpenAdIfAvailable(activity)
    }

    when {
        isShowingSettings -> {
            SettingsScreen(
                viewModel = viewModel,
                onBack = {
                    viewModel.closeSettings()
                }
            )
        }
        currentReadingSurah != null -> {
            SurahReaderScreen(
                surah = currentReadingSurah!!,
                viewModel = viewModel,
                onBack = {
                    viewModel.closeSurahReader()
                }
            )
        }
        isShowingRabbanaDuasSubscreen || currentTab == HomeTab.RABBANA_DUAS -> {
            RabbanaDuasScreen(
                viewModel = viewModel,
                onBack = {
                    isShowingRabbanaDuasSubscreen = false
                    viewModel.setTab(HomeTab.SURAHS)
                }
            )
        }
        else -> {
            QuranHomeScreen(
                viewModel = viewModel,
                onOpenSurah = { surah ->
                    viewModel.openSurah(surah)
                },
                onOpenRabbanaDuas = {
                    isShowingRabbanaDuasSubscreen = true
                },
                onOpenSettings = {
                    viewModel.openSettings()
                }
            )
        }
    }
}
