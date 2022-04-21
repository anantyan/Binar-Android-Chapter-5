package id.anantyan.utils.sharedPreferences

import android.content.Context

interface PreferenceHelper {
    fun setLogIn(value: Boolean)
    fun setUserId(value: Int)

    fun getLogIn(): Boolean?
    fun getUserId(): Int?
}

fun Context.preference(): PreferenceHelper {
    val pref: PreferenceHelper by lazy {
        PreferenceManager(this)
    }
    return pref
}