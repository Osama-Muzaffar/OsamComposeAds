package com.osama.compose.ads

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.compose.osamcomposeads.Ads.OsamAdmobBanner
import com.compose.osamcomposeads.Ads.ShowInterstitialAd
import com.osama.compose.ads.ui.theme.ComposeAdsTheme

class SecondActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ComposeAdsTheme {
                val context= LocalContext.current
                Scaffold(modifier = Modifier.fillMaxSize(),
                    bottomBar = {
//                        val nativeAd= OsamNativeAdState(context = context,
//                            adUnitId = "ca-app-pub-3940256099942544/2247696110")

                        OsamAdmobBanner(bannerId = "ca-app-pub-3940256099942544/6300978111")
//                        MyNativeAdAdmobSmall(loadedAd = nativeAd)

                    }) { innerPadding ->


                    var shouldShowAd by remember { mutableStateOf(false) }

                    if (shouldShowAd) {
                        ShowInterstitialAd(
                            context = context,
                            onDismissed = {
                                shouldShowAd = false
                            }
                        )
                    }


                    Column(modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)) {
                        Box(modifier = Modifier.weight(1f)){
                            Column (
                                Modifier.fillMaxSize(),
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally){

                                Text(text = "I'm in Second Activity")

                            }
                        }


                    }
                }
            }
        }

    }
}