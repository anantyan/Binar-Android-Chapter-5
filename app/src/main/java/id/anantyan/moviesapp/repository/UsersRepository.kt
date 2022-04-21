package id.anantyan.moviesapp.repository

import androidx.lifecycle.MutableLiveData
import id.anantyan.moviesapp.R
import id.anantyan.moviesapp.data.local.UsersDao
import id.anantyan.moviesapp.model.Profile
import id.anantyan.moviesapp.model.Users
import id.anantyan.utils.LiveEvent
import id.anantyan.utils.Resource
import java.lang.Exception

class UsersRepository(
    private val usersDao: UsersDao
) {
    val _showAccount: MutableLiveData<Resource<List<Profile>>> = MutableLiveData()
    val _login: LiveEvent<Resource<Users>> = LiveEvent()
    val _register: LiveEvent<Resource<Users>> = LiveEvent()
    val _setProfile: LiveEvent<Resource<List<Profile>>> = LiveEvent()
    val _setPassword: LiveEvent<Resource<String>> = LiveEvent()
    val _getAccount: LiveEvent<Resource<Users>> = LiveEvent()

    suspend fun getAccount(userId: Int?) {
        try {
            val response = usersDao.showAccount(userId)
            response?.let {
                _getAccount.postValue(Resource.Success(it))
            }
        } catch (ex: Exception) {
            _getAccount.postValue(
                ex.message?.let { Resource.Error(it) }
            )
        }
    }

    suspend fun setPassword(item: Users) {
        try {
            usersDao.setPassword(item.userId, item.password)
            _setPassword.postValue(Resource.Success("Berhasil mengubah password!"))
        } catch (ex: Exception) {
            _setPassword.postValue(
                ex.message?.let { Resource.Error(it) }
            )
        }
    }

    suspend fun setProfile(item: Users) {
        try {
            usersDao.setProfile(item.userId, item.fullname, item.username, item.email)
            val list = listOf(
                Profile(R.drawable.ic_baseline_person_outline_24, "Nama lengkap", item.fullname),
                Profile(R.drawable.ic_outline_person_pin_24, "Username", item.username),
                Profile(R.drawable.ic_outline_alternate_email_24, "Email", item.email)
            )
            _setProfile.postValue(Resource.Success(list))
        } catch (ex: Exception) {
            _setProfile.postValue(
                ex.message?.let { Resource.Error(it) }
            )
        }
    }

    suspend fun showAccount(userId: Int?) {
        try {
            val response = usersDao.showAccount(userId)
            response?.let {
                val list = listOf(
                    Profile(R.drawable.ic_baseline_person_outline_24, "Nama lengkap", it.fullname),
                    Profile(R.drawable.ic_outline_person_pin_24, "Username", it.username),
                    Profile(R.drawable.ic_outline_alternate_email_24, "Email", it.email)
                )
                _showAccount.postValue(Resource.Success(list))
            }
        } catch (ex: Exception) {
            _showAccount.postValue(
                ex.message?.let { Resource.Error(it) }
            )
        }
    }

    suspend fun login(item: Users) {
        try {
            val response = usersDao.login(item.email, item.password)
            if (response != null) {
                _login.postValue(Resource.Success(response))
            } else {
                throw Exception("Email atau Password tidak sah!")
            }
        } catch (ex: Exception) {
            _login.postValue(
                ex.message?.let { Resource.Error(it) }
            )
        }
    }

    suspend fun register(item: Users) {
        try {
            val check = usersDao.checkAccount(null, item.email)
            if (check == null) {
                val response = usersDao.register(item)
                if (response != 0L) {
                    _register.postValue(Resource.Success(item, "Akun berhasil dibuat!"))
                } else {
                    throw Exception("Gagal membuat akun!")
                }
            } else {
                throw Exception("Akun sudah dibuat!")
            }
        } catch (ex: Exception) {
            _register.postValue(
                ex.message?.let { Resource.Error(it) }
            )
        }
    }
}