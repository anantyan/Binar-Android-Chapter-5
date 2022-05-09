package id.anantyan.exerciseproject.repository

import id.anantyan.exerciseproject.data.local.UsersDao
import id.anantyan.exerciseproject.model.Users
import id.anantyan.exerciseproject.network.RetrofitNetwork
import id.anantyan.utils.LiveEvent
import id.anantyan.utils.Resource
import id.anantyan.utils.sharedPreferences.PreferenceHelper

class UsersRepository(
    private val usersDao: UsersDao
) {
    val _signInResponse: LiveEvent<Resource<Users>> = LiveEvent()
    val _signUpResponse: LiveEvent<Resource<Users>> = LiveEvent()

    val _selectByUsersLocal: LiveEvent<Users> = LiveEvent()
    val _insertLocal: LiveEvent<Users> = LiveEvent()

    suspend fun selectByUsersLocal(item: Users) {
        _selectByUsersLocal.postValue(usersDao.selectByUsers(item.email, item.password))
    }

    suspend fun insertLocal(item: Users) {
        usersDao.insert(item)
        _insertLocal.postValue(item)
    }

    suspend fun signInApi(item: Users) {
        _signInResponse.postValue(Resource.Loading())
        try {
            val response = RetrofitNetwork.usersApi.signInUsers(item)
            when {
                response.isSuccessful -> {
                    response.body()?.let {
                        _signInResponse.postValue(Resource.Success(it))
                        usersDao.insert(it)
                    }
                }
                response.code() == 401 -> {
                    throw Exception("Invalid login or password!")
                }
                else -> {
                    throw Exception("Gagal memuat data API!")
                }
            }
        } catch (ex: Exception) {
            ex.message?.let {
                _signInResponse.postValue(Resource.Error(it))
            }
        }
    }

    suspend fun signUpApi(item: Users) {
        _signUpResponse.postValue(Resource.Loading())
        try {
            val response = RetrofitNetwork.usersApi.signUpUsers(item)
            when {
                response.isSuccessful -> {
                    response.body()?.let {
                        _signUpResponse.postValue(Resource.Success(it, "Data berhasil dibuat!"))
                    }
                }
                response.code() == 409 -> {
                    throw Exception("User already exists!")
                }
                else -> {
                    throw Exception("Gagal memuat data API!")
                }
            }
        } catch (ex: Exception) {
            ex.message?.let {
                _signUpResponse.postValue(Resource.Error(it))
            }
        }
    }
}