package pokemon.code.challenge.app.util

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun String.getPokemonId(): String {
    return this.replace(POKEMON_BASE_URL.plus(POKEMON_GET), "").replace("/", "").toIntOrNull()?.toString().orEmpty()
}

fun String.toCamelCase(): String {
    return this.replaceFirstChar(Char::titlecase)
}

fun Int.toPokemonImageUrl(): String {
    return POKEMON_IMAGE_URL_RAW.plus(this).plus(".png")
}

fun Context.hasLocationPermission(): Boolean {
    return ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
            && ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
}

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
fun Context.hasPostNotificationPermission(): Boolean {
    return ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED
}

fun Date.toDateFormat(): String {
    return try {
        val simpleDateFormat = SimpleDateFormat(DATE_FORMAT_PATTERN_YYYY_MM_DD_H_MM_SS, Locale.getDefault())
        simpleDateFormat.format(this)
    } catch (e: Exception) {
        Log.e(APP_TAG,"Date format error: ${e.message}")
        ""
    }
}

fun Context.checkUser() {
    val preference = this.getSharedPreferences(SHARED_PREFERENCES_FILE_NAME, Context.MODE_PRIVATE)
    val user = preference.getString(SHARED_PREFERENCES_USER, "").orEmpty()

    if (user.isEmpty()) {
        preference.edit().putString(SHARED_PREFERENCES_USER, SHARED_PREFERENCES_USER.plus("_").plus(System.currentTimeMillis())).apply()
    }
}

fun Context.getUser(): String {
    return this.getSharedPreferences(SHARED_PREFERENCES_FILE_NAME, Context.MODE_PRIVATE).getString(SHARED_PREFERENCES_USER, "").orEmpty()
}

fun Context.isOnline(): Boolean {
    val connectivityManager = this.getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager ?: return false
    val capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork) ?: return false

    return capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)
            || capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
            || capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)
}

fun Int?.orDefault() = this ?: 0

fun Double?.orDefault() = this ?: 0.0