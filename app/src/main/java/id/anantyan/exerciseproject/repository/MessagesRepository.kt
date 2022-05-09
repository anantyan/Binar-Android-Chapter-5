package id.anantyan.exerciseproject.repository

import androidx.lifecycle.MutableLiveData
import id.anantyan.exerciseproject.data.local.MessagesDao
import id.anantyan.exerciseproject.model.Messages
import id.anantyan.exerciseproject.network.RetrofitNetwork
import id.anantyan.utils.LiveEvent
import id.anantyan.utils.Resource

class MessagesRepository(private val usersDao: MessagesDao) {

    val _selectResponse: MutableLiveData<Resource<List<Messages>>> = MutableLiveData()
    val _insertResponse: LiveEvent<Resource<Messages>> = LiveEvent()
    val _updateResponse: LiveEvent<Resource<Messages>> = LiveEvent()
    val _deleteResponse: LiveEvent<Resource<Messages>> = LiveEvent()

    val _seectById: LiveEvent<Messages> = LiveEvent()

    val _insertLocal: LiveEvent<Messages> = LiveEvent()
    val _updateLocal: LiveEvent<Messages> = LiveEvent()
    val _deleteLocal: LiveEvent<Messages> = LiveEvent()

    var _deleteAll: LiveEvent<Unit> = LiveEvent()

    fun selectLocal() = usersDao.select()
    suspend fun selectApi() {
        _selectResponse.postValue(Resource.Loading())
        try {
            val response = RetrofitNetwork.messagesApi.getMessage()
            if (response.isSuccessful) {
                response.body()?.let {
                    _selectResponse.postValue(Resource.Success(it))
                }
            } else {
                throw Exception("Gagal memuat data API!")
            }
        } catch (ex: Exception) {
            ex.message?.let {
                _selectResponse.postValue(Resource.Error(it))
            }
        }
    }

    suspend fun selectById(id: String) {
        _seectById.postValue(usersDao.selectById(id))
    }

    suspend fun insertLocal(item: Messages) {
        usersDao.insert(item)
        _insertLocal.postValue(item)
    }
    suspend fun insertApi(item: Messages) {
        _insertResponse.postValue(Resource.Loading())
        try {
            val response = RetrofitNetwork.messagesApi.postMessage(item)
            if (response.isSuccessful) {
                response.body()?.let {
                    _insertResponse.postValue(Resource.Success(item, "Data berhasil ditambahkan!"))
                }
            } else {
                throw Exception("Gagal memuat data API!")
            }
        } catch (ex: Exception) {
            ex.message?.let {
                _selectResponse.postValue(Resource.Error(it))
            }
        }
    }

    suspend fun updateLocal(item: Messages) {
        usersDao.update(item)
        _updateLocal.postValue(item)
    }
    suspend fun updateApi(item: Messages) {
        _updateResponse.postValue(Resource.Loading())
        try {
            val response = RetrofitNetwork.messagesApi.updateMessages(item.id, item)
            if (response.isSuccessful) {
                response.body()?.let {
                    _updateResponse.postValue(Resource.Success(item, "Data berhasil diubah!"))
                }
            } else {
                throw Exception("Gagal memuat data API!")
            }
        } catch (ex: Exception) {
            ex.message?.let {
                _updateResponse.postValue(Resource.Error(it))
            }
        }
    }

    suspend fun deleteLocal(item: Messages) {
        usersDao.delete(item)
        _deleteLocal.postValue(item)
    }
    suspend fun deleteApi(item: Messages) {
        _deleteResponse.postValue(Resource.Loading())
        try {
            val response = RetrofitNetwork.messagesApi.deleteMessages(item.id)
            if (response.isSuccessful) {
                response.body()?.let {
                    _deleteResponse.postValue(Resource.Success(item, "Data berhasil dihapus!"))
                }
            } else {
                throw Exception("Gagal memuat data API!")
            }
        } catch (ex: Exception) {
            ex.message?.let {
                _deleteResponse.postValue(Resource.Error(it))
            }
        }
    }

    suspend fun deleteAll() {
        _deleteAll.postValue(usersDao.deleteAll())
    }
}