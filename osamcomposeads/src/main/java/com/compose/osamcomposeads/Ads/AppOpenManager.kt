package com.compose.osamcomposeads.Ads

import android.app.Activity
import android.app.Application
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ProcessLifecycleOwner
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.appopen.AppOpenAd

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner

/**
 * Prefetches App Open Ads.
 */
class AppOpenManager(private val myApplication: Application, private var adUnitId: String) :
    Application.ActivityLifecycleCallbacks, DefaultLifecycleObserver {

    private var currentActivity: Activity? = null
    private var appOpenAd: AppOpenAd? = null

    private val excludedActivities: MutableSet<Class<*>> = HashSet()

    private var skipNextAd = false

    init {
        myApplication.registerActivityLifecycleCallbacks(this)
        ProcessLifecycleOwner.get().lifecycle.addObserver(this)
    }

    companion object {
        private const val LOG_TAG = "AppOpenManager"
        var isShowingAd = false
        var isShownAd = false
    }

    /**
     * Shows the ad if one isn't already showing.
     */
    fun showAdIfAvailable() {
        if (currentActivity != null && excludedActivities.contains(currentActivity!!::class.java)) {
            Log.d(LOG_TAG, "Ad display is skipped for this activity.")
            fetchAd()
            return
        }

        if (!isShowingAd && isAdAvailable() && !skipNextAd) {
            Log.e(LOG_TAG, "Will show ad.")

            val fullScreenContentCallback = object : FullScreenContentCallback() {
                override fun onAdDismissedFullScreenContent() {
                    appOpenAd = null
                    isShowingAd = false
                    fetchAd()
                }

                override fun onAdFailedToShowFullScreenContent(adError: AdError) {
                }

                override fun onAdShowedFullScreenContent() {
                    isShowingAd = true
                    isShownAd = true

                }
            }

            appOpenAd?.apply {

                setFullScreenContentCallback(fullScreenContentCallback)
                show(currentActivity!!)
            }
        } else {
            Log.d(LOG_TAG, "Cannot show ad.")
            fetchAd()
        }

        skipNextAd = false
    }

    fun skipNextAd() {
        skipNextAd = true
    }

    /**
     * Request an ad
     */
    public fun fetchAd() {
        if (isAdAvailable()) {
            return
        }
        val request = getAdRequest()
        AppOpenAd.load(myApplication,
            adUnitId,
            request,
            object : AppOpenAd.AppOpenAdLoadCallback() {
                override fun onAdLoaded(ad: AppOpenAd) {
                    appOpenAd = ad

                }

                override fun onAdFailedToLoad(loadAdError: LoadAdError) {
                    Log.e(LOG_TAG, "onAdFailedToLoad: failed to load")


                }
            })
    }


    /**
     * Creates and returns ad request.
     */
    private fun getAdRequest(): AdRequest {
        return AdRequest.Builder().build()
    }

    /**
     * Utility method that checks if ad exists and can be shown.
     */
    fun isAdAvailable(): Boolean {
        return appOpenAd != null
    }

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {}

    override fun onActivityStarted(activity: Activity) {
        currentActivity = activity
    }

    override fun onActivityResumed(activity: Activity) {
        currentActivity = activity
    }

    override fun onActivityStopped(activity: Activity) {}

    override fun onActivityPaused(activity: Activity) {}

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {}

    override fun onActivityDestroyed(activity: Activity) {
        currentActivity = null
    }

    /**
     * Adds an activity class to the set of excluded activities.
     */
    fun disableAppOpenWithActivity(activityClass: Class<*>) {
        excludedActivities.add(activityClass)
    }

    /**
     * Removes an activity class from the set of excluded activities.
     */
    fun includeAppOpenActivityForAds(activityClass: Class<*>) {
        excludedActivities.remove(activityClass)
    }

    override fun onStart(owner: LifecycleOwner) {

            showAdIfAvailable()
            Log.d(LOG_TAG, "onStart")
    }
}