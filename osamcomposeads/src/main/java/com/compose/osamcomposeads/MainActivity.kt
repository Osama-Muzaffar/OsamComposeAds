package com.compose.osamcomposeads

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.compose.osamcomposeads.Ads.MyNativeAdAdmobMedium
import com.compose.osamcomposeads.Ads.OsamAdsHelper
import com.compose.osamcomposeads.Ads.OsamNativeAdState
import com.compose.osamcomposeads.Ads.ShowInterstitialAd
import com.compose.osamcomposeads.ui.theme.ComposeAdsTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            ComposeAdsTheme {
                Scaffold(modifier = Modifier.fillMaxSize(),
                    bottomBar = {


//                        OsamAdmobBanner(bannerId = "ca-app-pub-3940256099942544/6300978111",
//                            onAdLoaded = {
//                                Log.d("MainActivity", "Ad loaded Successfully!")
//                            },
//                            onAdFailedToLoad = {error->
//                                Log.d("MainActivity", "onAdFailedToLoad:  ${error.message}")
//                            })


                        
                        
                    }) { innerPadding ->
                    val context= LocalContext.current
                    val osamstate = OsamNativeAdState(context = context, adUnitId = "ca-app-pub-3940256099942544/2247696110")
                    val adstate=  osamstate

                    OsamAdsHelper.loadIntersialad(context,"ca-app-pub-3940256099942544/1033173712")
                    var shouldShowAd by remember { mutableStateOf(false) }

                    if (shouldShowAd) {
                        ShowInterstitialAd(
                            context = context,
                            onDismissed = {
                                shouldShowAd = false
                            }
                        )
                    }

                    Column (modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)){

                        Column(modifier = Modifier.weight(1f)){
                            Greeting(
                                name = "Show Intersitial",
                                modifier = Modifier.weight(1f)
                                    .clickable {
                                        shouldShowAd=true
                                    }
                            )

                        }


                        Box(modifier = Modifier.wrapContentSize()){
                            MyNativeAdAdmobMedium(loadedAd = adstate)
                        }

                    }

                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ComposeAdsTheme {
        Greeting("Android")
    }
}