package id.anantyan.utils.sharedPreferences

interface PreferenceHelper {
    fun setLogIn(value: Boolean)
    fun setUserToken(value: String)

    fun getLogIn(): Boolean?
    fun getUserToken(): String?
}