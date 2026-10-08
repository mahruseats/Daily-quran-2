package com.example.ads

import android.view.View
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.LoadAdError

@Composable
fun AdBannerView(
    modifier: Modifier = Modifier,
    adUnitId: String = AdMobManager.BANNER_AD_ID
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f))
            .padding(vertical = 4.dp),
        contentAlignment = Alignment.Center
    ) {
        AndroidView(
            modifier = Modifier.fillMaxWidth(),
            factory = { context ->
                try {
                    AdView(context).apply {
                        setAdSize(AdSize.BANNER)
                        this.adUnitId = adUnitId
                        // Set LAYER_TYPE_SOFTWARE to avoid OpenGL/MESA rendernode failure logs on virtualized emulators
                        setLayerType(View.LAYER_TYPE_SOFTWARE, null)
                        adListener = object : AdListener() {
                            override fun onAdFailedToLoad(error: LoadAdError) {
                                // Silently handle failed ad load
                            }
                        }
                        loadAd(AdRequest.Builder().build())
                    }
                } catch (_: Exception) {
                    View(context)
                }
            }
        )
    }
}
