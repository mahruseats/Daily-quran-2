package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    onSplashFinished: () -> Unit
) {
    var startAnimation by remember { mutableStateOf(false) }

    val scaleAnim by animateFloatAsState(
        targetValue = if (startAnimation) 1f else 0.82f,
        animationSpec = tween(
            durationMillis = 1000,
            easing = FastOutSlowInEasing
        ),
        label = "splash_scale"
    )

    val alphaAnim by animateFloatAsState(
        targetValue = if (startAnimation) 1f else 0f,
        animationSpec = tween(
            durationMillis = 900,
            easing = FastOutSlowInEasing
        ),
        label = "splash_alpha"
    )

    LaunchedEffect(Unit) {
        startAnimation = true
        // Keep splash icon visible while initializing
        delay(1200)
        onSplashFinished()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF000000))
            .clickable { onSplashFinished() }
            .testTag("splash_screen_container"),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .padding(32.dp)
                .scale(scaleAnim)
        ) {
            // Gold ring container around the emblem
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(190.dp)
                    .clip(CircleShape)
                    .background(Color(0xFF000000))
                    .border(
                        width = 2.dp,
                        brush = Brush.radialGradient(
                            colors = listOf(
                                Color(0xFFFFE082),
                                Color(0xFFD4AF37),
                                Color(0xFF8C6D1F)
                            )
                        ),
                        shape = CircleShape
                    )
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_quran_app_icon),
                    contentDescription = "Holy Quran App Icon",
                    modifier = Modifier
                        .size(175.dp)
                        .clip(CircleShape)
                )
            }

            Spacer(modifier = Modifier.height(28.dp))

            Text(
                text = "القرآن الكريم",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFFE5C158),
                fontFamily = FontFamily.Serif,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "আল-কুরআনুল কারীম",
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium,
                color = Color(0xFFCCCCCC),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "সহজ বাংলা অনুবাদ, তাফসীর ও তিলাওয়াত",
                fontSize = 13.sp,
                color = Color(0xFF888888),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(36.dp))

            CircularProgressIndicator(
                modifier = Modifier.size(28.dp),
                color = Color(0xFFD4AF37),
                strokeWidth = 2.5.dp
            )
        }
    }
}
