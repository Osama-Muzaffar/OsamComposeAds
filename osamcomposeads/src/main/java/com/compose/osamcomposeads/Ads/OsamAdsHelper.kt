package com.compose.osamcomposeads.Ads

import android.app.Activity
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.zIndex
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdLoader
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.google.android.gms.ads.nativead.NativeAd
import com.google.android.gms.ads.nativead.NativeAdView
import com.compose.osamcomposeads.Ads.OsamAdsHelper.Companion.showIntersialad
import com.compose.osamcomposeads.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

@Composable
fun MyNativeAdAdmobMedium(modifier: Modifier = Modifier, loadedAd: NativeAd?) {
    AndroidView(
        modifier = modifier.padding(start = 4.dp, top = 8.dp, end = 4.dp, bottom = 4.dp),
        factory = { context ->
            // Inflate the layout
            val inflater = LayoutInflater.from(context)
            inflater.inflate(R.layout.custom_native_ad_small, null, false)
        },
        update = { rootView ->
            Log.d("NativeSmall", "Native ad = "+loadedAd)
            loadedAd?.let { nativeAd ->
                val nativeAdView: NativeAdView = rootView.findViewById(R.id.nativeadview)
                // Set icon
                nativeAd.icon?.let { icon ->
                    val adIcon: ImageView = rootView.findViewById(R.id.ad_icon)
                    adIcon.setImageDrawable(icon.drawable)
                    nativeAdView.iconView = adIcon
                }

                // Set headline
                nativeAd.headline?.let { headline ->
                    val adHeadline: TextView = rootView.findViewById(R.id.ad_headline)
                    adHeadline.text = headline
                    nativeAdView.headlineView = adHeadline
                }

                // Set advertiser
                nativeAd.advertiser?.let { advertiser ->
                    val adAdvertiser: TextView = rootView.findViewById(R.id.ad_advertiser)
                    adAdvertiser.text = advertiser
                    nativeAdView.advertiserView = adAdvertiser
                }

                // Set body
                nativeAd.body?.let { body ->
                    val adBody: TextView = rootView.findViewById(R.id.ad_body)
                    adBody.text = body
                    nativeAdView.bodyView = adBody
                }

                // Set call to action
                nativeAd.callToAction?.let { actionButton ->
                    val adActionButton: Button = rootView.findViewById(R.id.ad_actionbutton)
                    adActionButton.text = actionButton
                    nativeAdView.callToActionView = adActionButton
                }

                // Hide shimmer frame
                val shimmerFrame: FrameLayout = rootView.findViewById(R.id.shimmerframe)
                shimmerFrame.visibility = View.GONE
                nativeAdView.visibility = View.VISIBLE

                // Set the native ad to the NativeAdView
                nativeAdView.setNativeAd(nativeAd)
            }
        }
    )
}


@Composable
fun OsamNativeAdState(
    context: Context,
    adUnitId: String,
    refreshInterval:Long = 6000L,
    onAdLoaded: () -> Unit = {},
    onAdFailed: (LoadAdError)-> Unit={""}
): NativeAd? {
    var state by remember {
        mutableStateOf<NativeAd?>(null)
    }
    LaunchedEffect(Unit) {
        // Load the ad only if it's not already loaded
        if (state == null) {
            Log.d("NativeSmall", "state is null")
            val adLoader = AdLoader.Builder(context, adUnitId)
                .forNativeAd { ad ->
                    state = ad
                    Log.d("NativeSmall", "Native ad state saved ")
                }
                .withAdListener(object: AdListener(){
                    override fun onAdLoaded() {
                        super.onAdLoaded()
                        onAdLoaded()
                        Log.d("NativeSmall", "Native ad load successfully ")
                    }

                    override fun onAdFailedToLoad(error: LoadAdError) {
                        super.onAdFailedToLoad(error)
                        onAdFailed(error)
                        Log.d("NativeSmall", "Native ad load failed = "+error.message)
                    }
                })
                .build()
            adLoader.loadAd(AdRequest.Builder().build())
        }
    }
    if(refreshInterval > 0){
        LaunchedEffect(Unit){
            while(true){
                delay(refreshInterval)
                state = null

                withContext(Dispatchers.IO){
                    val adLoader = AdLoader.Builder(context,adUnitId)
                        .forNativeAd { ad ->
                            state = ad
                        }
                        .withAdListener(object: AdListener(){
                            override fun onAdLoaded() {
                                super.onAdLoaded()
                                onAdLoaded()
                                Log.d("NativeSmall", "Native ad load successfully ")
                            }

                            override fun onAdFailedToLoad(error: LoadAdError) {
                                super.onAdFailedToLoad(error)
                                onAdFailed(error)
                                Log.d("NativeSmall", "Native ad load failed = "+error.message)

                            }
                        })
                        .build()
                    adLoader.loadAd(AdRequest.Builder().build())
                }
            }
        }
    }
    return state
}

public class OsamAdsHelper{

    companion object{
        var intersialAd: InterstitialAd? = null

        private var isloading = false
        public fun loadIntersialad(context: Context, intersialId: String,
                                   onAdLoaded: () -> Unit={},
                                   onAdFailed: (LoadAdError) -> Unit={}) {
            if(!isloading) {
                if (intersialAd == null) {
                    isloading = true
                    InterstitialAd.load(
                        context,
                        intersialId,
                        AdRequest.Builder().build(),
                        object : InterstitialAdLoadCallback() {
                            override fun onAdFailedToLoad(adError: LoadAdError) {
                                Log.d("showIntersialad", "Failed to load ad: $adError")
                                isloading =false
                                onAdFailed(adError)
                            }

                            override fun onAdLoaded(interstitialAd: InterstitialAd) {
                                Log.d("showIntersialad", "ad loaded successfully")
                                intersialAd = interstitialAd
                                isloading =false
                                onAdLoaded()
                            }


                        })
                }
                else{
                    Log.d("showIntersialad", "ad already loaded")
                }
            }
            else{
                Log.d("showIntersialad", "ad already in loadding")
            }
        }

        fun showIntersialad(context: Context,
                            onAdFailed: (com.google.android.gms.ads.AdError) -> Unit={},
                            onDismissed: () -> Unit={}) {
            Log.d("showIntersialad", "Trying to show Intersitial")
            if (intersialAd != null && !isloading) {
                Log.d("showIntersialad", "Intersitial is not null")
                //   Set the FullScreenContentCallback
                intersialAd!!.fullScreenContentCallback = object : FullScreenContentCallback() {
                    override fun onAdDismissedFullScreenContent() {
                        // This is called when the ad is dismissed
                        intersialAd = null
                        onDismissed()
                    }

                    override fun onAdFailedToShowFullScreenContent(adError: com.google.android.gms.ads.AdError) {
                        // Handle the failure by calling onAdDismissed to proceed with normal operation
                        intersialAd = null
                        onDismissed()
                        onAdFailed(adError)
                    }

                    override fun onAdShowedFullScreenContent() {
                        // You might want to handle this event as well
                        intersialAd = null
                    }
                }
                intersialAd!!.show(context as Activity)
            }
            else{
                Log.d("showIntersialad", "Intersitial is null")
                onDismissed()
            }
        }

    }

}
@Composable
fun ShowInterstitialAd(
    modifier: Modifier = Modifier,
    context: Context,
    onDismissed: () -> Unit = {}
) {
    var showLoading by remember { mutableStateOf(true) }

    // Show loading screen when ad is being loaded
    if (showLoading) {
        showAdloadingScreen(modifier.zIndex(1f))
    }

    LaunchedEffect(Unit) {
        delay(3000)
        showIntersialad(context) {
            onDismissed()
            showLoading = false
        }
    }
}

@Composable
fun showAdloadingScreen(modifier: Modifier = Modifier) {
    Box(modifier = modifier.fillMaxSize()
        .background(MaterialTheme.colorScheme.background)
        .shadow(elevation = 8.dp)
        .zIndex(1f))
    {
        Column(modifier = Modifier.align(Alignment.Center)
            .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center) {
            val preloaderLottieComposition by rememberLottieComposition(
                LottieCompositionSpec.RawRes(
                    R.raw.loading_animation
                )
            )

            val preloaderProgress by animateLottieCompositionAsState(
                preloaderLottieComposition,
                iterations = LottieConstants.IterateForever,
                isPlaying = true
            )


            LottieAnimation(
                composition = preloaderLottieComposition,
                progress = preloaderProgress,
                modifier = modifier.size(100.dp)
            )



            Text(text = "Loading  Ad",
                modifier = Modifier.align(Alignment.CenterHorizontally),
                color = MaterialTheme.colorScheme.onPrimaryContainer,
                fontSize = 10.sp)

        }
    }
}

