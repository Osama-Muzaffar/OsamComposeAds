package com.compose.osamcomposeads.Ads

import android.app.Activity
import android.content.res.Resources
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.LoadAdError
private fun getAdSize(activity: Activity, resources: Resources): AdSize {
    val outMetrics = resources.displayMetrics
    val widthPixels = outMetrics.widthPixels.toFloat()
    val density = outMetrics.density
    val adWidth = (widthPixels / density).toInt()
    return AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(activity, adWidth)
}

/*
@Composable
fun AdmobBanner(modifier: Modifier = Modifier,
                bannerId: String) {
    var isBannerLoaded by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val density = LocalDensity.current
    val screenWidthDp = context.resources.displayMetrics.widthPixels / density.density.toInt()
    val adaptiveAdSize = AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(context, screenWidthDp)

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(adaptiveAdSize.height.dp)
    ) {
        if (!isBannerLoaded) {
            ShimmerBox(
                modifier = Modifier.fillMaxSize()
            )
        }

        AndroidView(
            modifier = Modifier.fillMaxSize(),
            factory = { context ->
                AdView(context).apply {
                    setAdSize(getAdSize(context as Activity, context.resources))
                    adUnitId = bannerId
                    adListener = object : AdListener() {
                        override fun onAdLoaded() {
                            Log.d("tagBannerAd", "onAdLoaded: ")
                            Handler(Looper.getMainLooper()).post {
                                isBannerLoaded = true
                            }
                        }

                        override fun onAdFailedToLoad(error: LoadAdError) {
                            Log.d("tagBannerAd", "onAdFailedToLoad: $error")
                        }
                    }
                    loadAd(AdRequest.Builder().build())
                }
            },
            update = { adView ->
                if (isBannerLoaded) {
                    adView.visibility = android.view.View.VISIBLE
                } else {
                    adView.visibility = android.view.View.GONE
                }
            }
        )
    }
}
*/

@Composable
fun OsamAdmobBanner(
    modifier: Modifier = Modifier,
    bannerId: String,
    onAdLoaded: ()-> Unit = { "" },
    onAdFailedToLoad: (LoadAdError)-> Unit = { "" }
) {
    var isBannerLoaded by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val density = LocalDensity.current

    // Ensure density.density is non-zero to prevent ArithmeticException
    val screenWidthDp = remember(density) {
        context.resources.displayMetrics.widthPixels / density.density.coerceAtLeast(1f).toInt()
    }

    val adaptiveAdSize = AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(context, screenWidthDp)

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(adaptiveAdSize.height.dp)
    ) {
        if (!isBannerLoaded) {
            ShimmerBox(
                modifier = Modifier.fillMaxSize()
            )
        }

        AndroidView(
            modifier = Modifier.fillMaxSize(),
            factory = { context ->
                AdView(context).apply {
                    setAdSize(adaptiveAdSize)
                    adUnitId = bannerId
                    adListener = object : AdListener() {
                        override fun onAdLoaded() {
                            onAdLoaded()
                            Log.d("tagBannerAd", "onAdLoaded: ")
                            Handler(Looper.getMainLooper()).post {
                                isBannerLoaded = true
                            }
                        }

                        override fun onAdFailedToLoad(error: LoadAdError) {
                            Log.d("tagBannerAd", "onAdFailedToLoad: $error")
                            onAdFailedToLoad(error)
                        }
                    }
                    loadAd(AdRequest.Builder().build())
                }
            },
            update = { adView ->
                adView.visibility = if (isBannerLoaded) android.view.View.VISIBLE else android.view.View.GONE
            }
        )
    }
}

@Composable
fun ShimmerBox(
    modifier: Modifier = Modifier, shimmerColor: Color = Color.LightGray, gradientWidth: Float = 200f
) {
    val infiniteTransition = rememberInfiniteTransition(label = "")
    val shimmerTranslate by infiniteTransition.animateFloat(
        initialValue = 0f, targetValue = 1000f, animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 1000, easing = LinearEasing
            )
        ), label = ""
    )

    val shimmerBrush = Brush.linearGradient(
        colors = listOf(
            shimmerColor.copy(alpha = 0.6f), shimmerColor.copy(alpha = 0.2f), shimmerColor.copy(alpha = 0.6f)
        ), start = Offset.Zero, end = Offset(x = shimmerTranslate + gradientWidth, y = shimmerTranslate + gradientWidth)
    )

    Canvas(modifier = modifier) {
        drawRect(
            brush = shimmerBrush, size = size
        )
    }
}
