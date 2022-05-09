package id.anantyan.utils.sharedPreferences

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.preference.PreferenceManager

fun Context.preference(): PreferenceHelper {
    val pref: PreferenceHelper by lazy {
        id.anantyan.utils.sharedPreferences.PreferenceManager(this)
    }
    return pref
}

class PreferenceManager(context: Context) : PreferenceHelper {

    private var prefShared: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)

    companion object {
        private const val LOG_IN = "LOG_IN"
        private const val USR_TOKEN = "USR_TOKEN"
    }

    override fun setLogIn(value: Boolean) {
        prefShared.set(LOG_IN, value)
    }

    override fun setUserToken(value: String) {
        prefShared.set(USR_TOKEN, value)
    }

    override fun getLogIn(): Boolean? {
        return prefShared.getBoolean(LOG_IN, false)
    }

    override fun getUserToken(): String? {
        return prefShared.getString(USR_TOKEN, "")
    }
}

/**
 * setting listener editor & apply sharedPreferences
 * with extenstion function
 * */
private fun SharedPreferences.settings(operation: (SharedPreferences.Editor) -> Unit) {
    this.edit(true) {
        operation(this)
        this.apply()
    }
}

private fun SharedPreferences.set(key: String, value: Any?) {
     when(value) {
         is String -> this.settings { it.putString(key, value) }
         is Boolean -> this.settings { it.putBoolean(key, value) }
         is Int -> this.settings { it.putInt(key, value) }
         is Long -> this.settings { it.putLong(key, value) }
         is Float -> this.settings { it.putFloat(key, value) }
         else -> throw UnsupportedOperationException("Not yet implemented")
     }
}