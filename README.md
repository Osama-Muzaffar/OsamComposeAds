# OsamComposeAds
[![](https://jitpack.io/v/Osama-Muzaffar/OsamComposeAds.svg)](https://jitpack.io/#Osama-Muzaffar/OsamComposeAds)
**OsamComposeAds** is a Jetpack Compose library for integrating AdMob ads easily into your Android applications. This library supports:

- AdMob Banner Ads
- Native Medium Ads
- Interstitial Ads

## Features

- **AdMob Banner Ads**
- **Native Medium Ads**
- **Interstitial Ads**

## Installation

To add this library to your project, include the following in your build.gradle file:

```groovy
dependencies {
    implementation 'com.github.Osama-Muzaffar:OsamComposeAds:latest_version'
}
```

```kotlin
dependencies {
   implementation ("com.github.Osama-Muzaffar:OsamComposeAds:latest_version")
}
```
## Usage

### 1. AdMob Banner

To display an AdMob banner ad, use the following code snippet:

```kotlin
OsamAdmobBanner(bannerId = "banner_ad_id")
```

## banner_ad_id -> should be replace with original admob banner id 

### 2. AdMob Native Medium

#### Load Native Ad:

```kotlin
val nativeAd = OsamNativeAdState(
    context = context,
    adUnitId = "native_ad_id"
)
```
## native_ad_id -> should be replace with original admob Native id

#### Show Native Ad:

```kotlin
MyNativeAdAdmobMedium(loadedAd = nativeAd)
```

### 3. Interstitial Ad

#### Save the state:

```kotlin
var shouldShowAd by remember { mutableStateOf(false) }
```

#### Load Interstitial Ad:

```kotlin
OsamAdsHelper.loadIntersialad(
    context = context,
    adUnitId = "intersitial_ad_Unit"
)
```
## intersitial_ad_Unit -> should be replace with original admob Intersitial id

#### Show Interstitial Ad:

```kotlin
if (shouldShowAd) {
    ShowInterstitialAd(
        context = context,
        onDismissed = {
            shouldShowAd = false
            onSecond()  // Callback after ad is dismissed
        }
    )
}
```

**Note:**  
`onDismissed` is a callback that is triggered when the ad is closed, fails to show, or is not loaded.

---

### License

This library is licensed under the MIT License. See the [LICENSE](./LICENSE) file for more information.

---

You can expand on the "Installation" section with specific repository details or version information if needed.
### Sample Project

A sample project is included in the `app` directory. It demonstrates how to use OsamComposeAds to
manage ads, purchases, and user consent. Follow these steps to run the sample project:

1. Clone the repository:

   ```bash
   git clone https://github.com/Osama-Muzaffar/OsamComposeAds.git
   ```

2. Open the sample project in Android Studio.

3. Replace placeholders with your own AdMob IDs and configure your app in the Google Play Console
   for in-app purchases.

4. Run the project on an Android device or emulator.

### Contributing

Contributions are welcome! If you'd like to contribute to this project, please follow these steps:

1. Fork the repository.
2. Create a new branch (`git checkout -b feature/YourFeature`).
3. Commit your changes (`git commit -m 'Add YourFeature'`).
4. Push to the branch (`git push origin feature/YourFeature`).
5. Open a Pull Request.




For any questions or issues, please open an issue in this repository or contact me
at [osamamuzaffar1122@gmail.com](mailto:osamamuzaffar1122@gmail.com).
