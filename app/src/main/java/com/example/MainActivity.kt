package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
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
import com.example.ui.screens.SplashScreen
import com.example.ui.screens.SurahReaderScreen
import com.example.ui.theme.DailyQuranTheme
import com.example.ui.viewmodel.HomeTab
import com.example.ui.viewmodel.QuranViewModel

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Safely initialize AdMob in background without blocking main UI
        try {
            AdMobManager.initialize(applicationContext)
        } catch (_: Exception) {}

        setContent {
            val quranViewModel: QuranViewModel = viewModel()
            val isNightMode by quranViewModel.isNightMode.collectAsState()
            var isSplashVisible by remember { mutableStateOf(true) }

            DailyQuranTheme(darkTheme = if (isSplashVisible) true else isNightMode) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Crossfade(
                        targetState = isSplashVisible,
                        animationSpec = tween(500),
                        label = "splash_crossfade"
                    ) { showSplash ->
                        if (showSplash) {
                            SplashScreen(
                                onSplashFinished = {
                                    isSplashVisible = false
                                }
                            )
                        } else {
                            QuranAppMain(
                                activity = this@MainActivity,
                                viewModel = quranViewModel
                            )
                        }
                    }
                }
            }
        }
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
