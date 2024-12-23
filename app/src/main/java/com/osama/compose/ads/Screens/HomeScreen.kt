package com.osama.compose.ads.Screens

import android.content.Intent
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.compose.osamcomposeads.Ads.MyNativeAdAdmobSmall
import com.compose.osamcomposeads.Ads.OsamAdsHelper
import com.compose.osamcomposeads.Ads.OsamNativeAdState
import com.compose.osamcomposeads.Ads.ShowInterstitialAd
import com.google.firebase.BuildConfig
import com.osama.compose.ads.R
import com.osama.compose.ads.SecondActivity
import com.osama.compose.ads.Utils.AdsConfigManager
import com.osama.compose.ads.Utils.cachedConfig
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    onSecond: () -> Unit = {}
) {
    val context = LocalContext.current
    OsamAdsHelper.loadIntersialad(context, "ca-app-pub-3940256099942544/1033173712")

    val remoteConfigHelper = AdsConfigManager()
    remoteConfigHelper.initialize(R.xml.ads_remote_config, BuildConfig.DEBUG)

    LaunchedEffect(Unit) {
        // Optionally, fetch and activate at startup using a coroutine:
        CoroutineScope(Dispatchers.IO).launch {
            remoteConfigHelper.fetchAndActivate()
        }
    }
    LaunchedEffect(Unit) {
        delay(2000)
        if (cachedConfig != null) {
            Log.d("HomeScreen", "remote banner value: " + cachedConfig?.test_banner)
            Log.d("HomeScreen", "remote native value: " + cachedConfig?.test_native)
            Log.d("HomeScreen", "remote Inter value: " + cachedConfig?.test_interstitial)
        } else {
            Log.d("HomeScreen", "cachedConfig is null")
        }

    }
    Scaffold(modifier = Modifier.fillMaxSize(),
        bottomBar = {
            val nativeConfig = if (cachedConfig != null) {
                cachedConfig!!.test_native
            } else {
                true
            }


            val nativeAd = OsamNativeAdState(
                context = context,
                adUnitId = "ca-app-pub-3940256099942544/2247696110"
            )


//                        OsamAdmobBanner(bannerId = "ca-app-pub-3940256099942544/6300978111")
            MyNativeAdAdmobSmall(loadedAd = nativeAd, nativeConfig = nativeConfig)

        }) { innerPadding ->

        val nativeAd = OsamNativeAdState(
            context = context,
            adUnitId = "ca-app-pub-3940256099942544/2247696110"
        )

        var shouldShowAd by remember { mutableStateOf(false) }

        if (shouldShowAd) {
            val interConfig = if (cachedConfig != null) {
                cachedConfig!!.test_interstitial
            } else {
                true
            }
            Log.d("showIntersialad", "call intersitial method")
                    ShowInterstitialAd(
                        context = context,
                        onDismissed = {
                            shouldShowAd = false
                            onSecond()
                        },
                        interConfig= interConfig
                    )
        }


        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Box(modifier = Modifier.weight(1f)) {
                Column(
                    Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Button(onClick = {
                        shouldShowAd = true
                    }) {
                        Text(text = "Show Intersital")
                    }

                }
            }

//                        MyNativeAdAdmobSmall(loadedAd = nativeAd)

        }
    }
}