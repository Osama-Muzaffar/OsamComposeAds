package com.osama.compose.ads

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.osama.compose.ads.ui.theme.ComposeAdsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ComposeAdsTheme {

                NavRoutes()

//                val context= LocalContext.current
//                OsamAdsHelper.loadIntersialad(context,"ca-app-pub-3940256099942544/1033173712")
//                Scaffold(modifier = Modifier.fillMaxSize(),
//                    bottomBar = {
//                        val nativeAd= OsamNativeAdState(context = context,
//                            adUnitId = "ca-app-pub-3940256099942544/2247696110")
//
////                        OsamAdmobBanner(bannerId = "ca-app-pub-3940256099942544/6300978111")
//                        MyNativeAdAdmobSmall(loadedAd = nativeAd)
//
//                    }) { innerPadding ->
//
//                    val nativeAd= OsamNativeAdState(context = context,
//                        adUnitId = "ca-app-pub-3940256099942544/2247696110")
//
//                    var shouldShowAd by remember { mutableStateOf(false) }
//
//                    if (shouldShowAd) {
//                        ShowInterstitialAd(
//                            context = context,
//                            onDismissed = {
//                                shouldShowAd = false
//                                startActivity(Intent(context,SecondActivity::class.java))
//                            }
//                        )
//                    }
//
//
//                    Column(modifier = Modifier
//                        .fillMaxSize()
//                        .padding(innerPadding)) {
//                        Box(modifier = Modifier.weight(1f)){
//                            Column (Modifier.fillMaxSize(),
//                                verticalArrangement = Arrangement.Center,
//                                horizontalAlignment = Alignment.CenterHorizontally){
//
//                                Button(onClick = {
//                                    shouldShowAd=true
//                                }) {
//                                    Text(text = "Show Intersital")
//                                }
//
//                            }
//                        }
//
////                        MyNativeAdAdmobSmall(loadedAd = nativeAd)
//
//                    }
//                }
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