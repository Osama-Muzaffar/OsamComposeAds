package com.osama.compose.ads.Utils

import androidx.annotation.Keep

@Keep
data class AdsConfig(
    val test_banner: Boolean = true,
    val test_native: Boolean = true,
    val test_interstitial: Boolean = true
)