package com.example.ui.screens

import android.app.Activity
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.PlayCircle
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ads.AdMobManager
import com.example.data.model.RabbanaDua
import com.example.data.source.RabbanaDuasData
import com.example.ui.theme.TimesRomanFontFamily
import com.example.ui.util.AppStrings
import com.example.ui.viewmodel.QuranViewModel
import com.example.ui.viewmodel.TranslationDisplayMode

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RabbanaDuasScreen(
    viewModel: QuranViewModel,
    translationLanguage: TranslationDisplayMode = TranslationDisplayMode.BOTH,
    onBack: () -> Unit
) {
    val context = LocalContext.current
    val activity = context as? Activity
    val duas = RabbanaDuasData.duas
    val isEng = AppStrings.isEnglish(translationLanguage)
    val font = if (isEng) TimesRomanFontFamily else androidx.compose.ui.text.font.FontFamily.Default

    // Exit requirement with Interstitial ad
    fun handleExit() {
        if (activity != null) {
            AdMobManager.showInterstitial(activity) {
                onBack()
            }
        } else {
            onBack()
        }
    }

    BackHandler {
        handleExit()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            text = AppStrings.duasTitle(translationLanguage),
                            fontFamily = font,
                            fontWeight = FontWeight.Bold,
                            fontSize = 17.sp
                        )
                        Text(
                            text = AppStrings.duasSubtitle(translationLanguage),
                            fontFamily = font,
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = { handleExit() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 14.dp, vertical = 6.dp)
        ) {
            items(duas, key = { it.id }) { dua ->
                RabbanaDuaCard(
                    dua = dua,
                    translationLanguage = translationLanguage,
                    onPlayAudio = { viewModel.playRabbanaDuaAudio(dua) }
                )
            }

            item {
                Spacer(modifier = Modifier.height(40.dp))
            }
        }
    }
}

@Composable
fun RabbanaDuaCard(
    dua: RabbanaDua,
    translationLanguage: TranslationDisplayMode = TranslationDisplayMode.BOTH,
    onPlayAudio: () -> Unit
) {
    val context = LocalContext.current
    val isEng = AppStrings.isEnglish(translationLanguage)
    val font = if (isEng) TimesRomanFontFamily else androidx.compose.ui.text.font.FontFamily.Default

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // Header Row: Dua Number Badge & Surah Reference & Play Button
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(34.dp)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.secondary.copy(alpha = 0.2f))
                            .border(1.dp, MaterialTheme.colorScheme.secondary, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "${dua.id}",
                            fontWeight = FontWeight.Bold,
                            fontSize = 13.sp,
                            fontFamily = font,
                            color = MaterialTheme.colorScheme.secondary
                        )
                    }

                    Spacer(modifier = Modifier.width(10.dp))

                    Text(
                        text = AppStrings.duaSurahRef(
                            mode = translationLanguage,
                            surahEng = dua.surahNameEnglish,
                            surahBan = dua.surahNameBangla,
                            ref = dua.ayahReference
                        ),
                        fontWeight = FontWeight.SemiBold,
                        fontFamily = font,
                        fontSize = 13.sp,
                        color = MaterialTheme.colorScheme.primary
                    )
                }

                Row {
                    IconButton(
                        onClick = onPlayAudio,
                        modifier = Modifier.size(36.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.PlayCircle,
                            contentDescription = "Play Dua Audio",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }

                    IconButton(
                        onClick = {
                            val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                            val clipText = if (isEng) {
                                "${dua.arabicText}\n\nEnglish: ${dua.englishTranslation}\n\nReference: Surah ${dua.surahNameEnglish} (${dua.ayahReference})"
                            } else {
                                "${dua.arabicText}\n\nউচ্চারণ: ${dua.banglaPronunciation}\n\nঅর্থ: ${dua.banglaTranslation}\n\nEnglish: ${dua.englishTranslation}\n\nসূত্র: সূরা ${dua.surahNameBangla} (${dua.ayahReference})"
                            }
                            val clip = ClipData.newPlainText("Rabbana Dua #${dua.id}", clipText)
                            clipboard.setPrimaryClip(clip)
                            Toast.makeText(context, AppStrings.duaCopiedToast(translationLanguage), Toast.LENGTH_SHORT).show()
                        },
                        modifier = Modifier.size(36.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.ContentCopy,
                            contentDescription = "Copy Dua",
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Arabic Text
            Text(
                text = dua.arabicText,
                fontFamily = com.example.ui.theme.QuranArabicFontFamily,
                fontSize = 24.sp,
                lineHeight = 40.sp,
                fontWeight = FontWeight.Normal,
                textAlign = TextAlign.Right,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(12.dp))
            HorizontalDivider(color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f))
            Spacer(modifier = Modifier.height(8.dp))

            // Pronunciation & Meaning according to translationLanguage
            if (translationLanguage == TranslationDisplayMode.BANGLA_ONLY) {
                Text(
                    text = "${AppStrings.pronunciationPrefix(translationLanguage)}${dua.banglaPronunciation}",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = "${AppStrings.translationPrefix(translationLanguage)}${dua.banglaTranslation}",
                    fontSize = 14.sp,
                    lineHeight = 20.sp,
                    color = MaterialTheme.colorScheme.onSurface
                )
            } else if (translationLanguage == TranslationDisplayMode.ENGLISH_ONLY) {
                Text(
                    text = "${AppStrings.translationPrefix(translationLanguage)}${dua.englishTranslation}",
                    fontFamily = TimesRomanFontFamily,
                    fontSize = 14.sp,
                    lineHeight = 20.sp,
                    color = MaterialTheme.colorScheme.onSurface
                )
            } else {
                Text(
                    text = "${AppStrings.pronunciationPrefix(translationLanguage)}${dua.banglaPronunciation}",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = "অর্থ: ${dua.banglaTranslation}",
                    fontSize = 14.sp,
                    lineHeight = 20.sp,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = "English: ${dua.englishTranslation}",
                    fontFamily = TimesRomanFontFamily,
                    fontSize = 13.sp,
                    lineHeight = 18.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            // Context and Virtues
            if (dua.contextAndBenefits.isNotEmpty()) {
                Spacer(modifier = Modifier.height(8.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.3f),
                            RoundedCornerShape(8.dp)
                        )
                        .padding(8.dp)
                ) {
                    Text(
                        text = "${AppStrings.virtuesPrefix(translationLanguage)}${dua.contextAndBenefits}",
                        fontFamily = font,
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}
