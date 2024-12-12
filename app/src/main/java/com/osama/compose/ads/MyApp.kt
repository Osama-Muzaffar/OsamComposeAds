package com.osama.compose.ads

import android.app.Application
import com.compose.osamcomposeads.Ads.AppOpenManager
import com.google.firebase.BuildConfig
import com.osama.compose.ads.Utils.AdsConfigManager
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MyApp : Application() {
    private lateinit var appOpenAdManager: AppOpenManager

    override fun onCreate() {
        super.onCreate()
        context = this


        appOpenAdManager = AppOpenManager(this, "ca-app-pub-3940256099942544/9257395921")
//        appOpenAdManager.disableAppOpenWithActivity(MainActivity::class.java)

    }
    companion object {
        private lateinit var context: MyApp
    }
}
