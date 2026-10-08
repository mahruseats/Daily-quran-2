package com.example.ads

import android.app.Activity
import android.content.Context
import android.util.Log
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.RequestConfiguration
import com.google.android.gms.ads.appopen.AppOpenAd
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback

object AdMobManager {
    private const val TAG = "AdMobManager"

    // Exact IDs requested by the user
    const val INTERSTITIAL_AD_ID = "ca-app-pub-3940256099942544/1033173712"
    const val BANNER_AD_ID = "ca-app-pub-3940256099942544/9214589741"
    const val APP_OPEN_AD_ID = "ca-app-pub-3940256099942544/9257395921"

    private var interstitialAd: InterstitialAd? = null
    private var isInterstitialLoading = false
    private var appOpenAd: AppOpenAd? = null
    private var isAppOpenLoading = false
    private var isInitialized = false

    fun initialize(context: Context) {
        if (isInitialized) return
        try {
            val testConfig = RequestConfiguration.Builder()
                .setTestDeviceIds(listOf(AdRequest.DEVICE_ID_EMULATOR))
                .build()
            MobileAds.setRequestConfiguration(testConfig)

            MobileAds.initialize(context) { initializationStatus ->
                Log.d(TAG, "AdMob initialized: $initializationStatus")
                isInitialized = true
                loadInterstitial(context)
                loadAppOpenAd(context)
            }
        } catch (e: Exception) {
            Log.e(TAG, "AdMob init error", e)
        }
    }

    fun loadInterstitial(context: Context) {
        if (interstitialAd != null || isInterstitialLoading) return
        isInterstitialLoading = true

        val adRequest = AdRequest.Builder().build()
        InterstitialAd.load(
            context,
            INTERSTITIAL_AD_ID,
            adRequest,
            object : InterstitialAdLoadCallback() {
                override fun onAdLoaded(ad: InterstitialAd) {
                    interstitialAd = ad
                    isInterstitialLoading = false
                    Log.d(TAG, "Interstitial loaded successfully")
                }

                override fun onAdFailedToLoad(error: LoadAdError) {
                    interstitialAd = null
                    isInterstitialLoading = false
                    Log.w(TAG, "Interstitial failed to load: ${error.message}")
                }
            }
        )
    }

    private fun isEmulator(): Boolean {
        val finger = android.os.Build.FINGERPRINT ?: ""
        val model = android.os.Build.MODEL ?: ""
        val manufacturer = android.os.Build.MANUFACTURER ?: ""
        val brand = android.os.Build.BRAND ?: ""
        val device = android.os.Build.DEVICE ?: ""
        val product = android.os.Build.PRODUCT ?: ""
        val hardware = android.os.Build.HARDWARE ?: ""

        return finger.startsWith("generic")
                || finger.startsWith("unknown")
                || model.contains("google_sdk", ignoreCase = true)
                || model.contains("Emulator", ignoreCase = true)
                || model.contains("Android SDK built for x86", ignoreCase = true)
                || manufacturer.contains("Genymotion", ignoreCase = true)
                || (brand.startsWith("generic") && device.startsWith("generic"))
                || product.contains("sdk", ignoreCase = true)
                || hardware.contains("goldfish", ignoreCase = true)
                || hardware.contains("ranchu", ignoreCase = true)
    }

    /**
     * Pops interstitial ad when user exits a Surah or Rabbana Duas.
     * Always ensures the onDismiss callback runs so navigation is never blocked.
     */
    fun showInterstitial(activity: Activity, onDismiss: () -> Unit) {
        if (isEmulator()) {
            // Bypass full-screen AdActivity on virtual emulator to prevent SurfaceSyncGroup timeout errors
            onDismiss()
            return
        }
        val ad = interstitialAd
        if (ad != null) {
            ad.fullScreenContentCallback = object : FullScreenContentCallback() {
                override fun onAdDismissedFullScreenContent() {
                    interstitialAd = null
                    loadInterstitial(activity)
                    onDismiss()
                }

                override fun onAdFailedToShowFullScreenContent(error: AdError) {
                    Log.w(TAG, "Interstitial show failed: ${error.message}")
                    interstitialAd = null
                    loadInterstitial(activity)
                    onDismiss()
                }
            }
            ad.show(activity)
        } else {
            // Not ready or failed, trigger next navigation and reload in background
            loadInterstitial(activity)
            onDismiss()
        }
    }

    fun loadAppOpenAd(context: Context) {
        if (appOpenAd != null || isAppOpenLoading) return
        isAppOpenLoading = true

        val request = AdRequest.Builder().build()
        AppOpenAd.load(
            context,
            APP_OPEN_AD_ID,
            request,
            object : AppOpenAd.AppOpenAdLoadCallback() {
                override fun onAdLoaded(ad: AppOpenAd) {
                    appOpenAd = ad
                    isAppOpenLoading = false
                    Log.d(TAG, "App Open Ad loaded")
                }

                override fun onAdFailedToLoad(error: LoadAdError) {
                    appOpenAd = null
                    isAppOpenLoading = false
                    Log.w(TAG, "App Open Ad load failed: ${error.message}")
                }
            }
        )
    }

    fun showAppOpenAdIfAvailable(activity: Activity, onDismiss: (() -> Unit)? = null) {
        if (isEmulator()) {
            onDismiss?.invoke()
            return
        }
        val ad = appOpenAd
        if (ad != null) {
            ad.fullScreenContentCallback = object : FullScreenContentCallback() {
                override fun onAdDismissedFullScreenContent() {
                    appOpenAd = null
                    loadAppOpenAd(activity)
                    onDismiss?.invoke()
                }

                override fun onAdFailedToShowFullScreenContent(error: AdError) {
                    appOpenAd = null
                    loadAppOpenAd(activity)
                    onDismiss?.invoke()
                }
            }
            ad.show(activity)
        } else {
            onDismiss?.invoke()
        }
    }
}
