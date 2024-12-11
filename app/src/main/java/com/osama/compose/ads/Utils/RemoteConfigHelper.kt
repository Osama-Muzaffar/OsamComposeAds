package com.osama.compose.ads.Utils
import android.content.Context
import android.util.Log
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.ConfigUpdate
import com.google.firebase.remoteconfig.ConfigUpdateListener
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigException
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings
import com.google.gson.Gson


class AdsConfigManager {

    private val remoteConfig = Firebase.remoteConfig

    private  val TAG = "AdsConfigManager"
    fun initialize(defaultXmlRes: Int, isDebugMode: Boolean = false) {
        // Set default values from the XML resource
        remoteConfig.setDefaultsAsync(defaultXmlRes)

        val configSettings = remoteConfigSettings {
            minimumFetchIntervalInSeconds = if (isDebugMode) 0 else 12 * 3600L // 12 hours in production
        }
        remoteConfig.setConfigSettingsAsync(configSettings)

        // Add a listener for config updates
        addOnConfigUpdateListener()
    }
    fun fetchAndActivate() {
        remoteConfig.fetchAndActivate().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Log.d(TAG, "Fetch and Activate succeeded.")
                loadAdsConfig()

            } else {
                Log.e(TAG, "Fetch and Activate failed: ${task.exception?.message}")
            }
        }
    }

    private fun addOnConfigUpdateListener() {
        remoteConfig.addOnConfigUpdateListener(object : ConfigUpdateListener {
            override fun onUpdate(configUpdate: ConfigUpdate) {
                Log.d("AdsConfigManager", "Updated keys: ${configUpdate.updatedKeys}")

                // If the relevant JSON key is updated, re-activate and re-process it.
                if (configUpdate.updatedKeys.contains("ads_json_1")) {
                    remoteConfig.activate().addOnCompleteListener {
                        // Once activated, reload the ads config
                        loadAdsConfig()

                    }
                }
            }

            override fun onError(error: FirebaseRemoteConfigException) {
                Log.w("AdsConfigManager", "Config update error with code: ${error.code}", error)
            }
        })
    }

    fun loadAdsConfig() {
        val jsonConfig = remoteConfig.getString("ads_json_test")
        Log.d("AdsConfigManager", "Loaded JSON config: $jsonConfig")

        cachedConfig = try {
            Gson().fromJson(jsonConfig, AdsConfig::class.java)
        } catch (e: Exception) {
            Log.e("AdsConfigManager", "Error parsing ads JSON: ${e.message}")
            null
        }
    }


}
