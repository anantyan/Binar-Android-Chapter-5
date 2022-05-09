package id.anantyan.exerciseproject.ui.fragment.messages

import android.app.Application
import androidx.lifecycle.*
import id.anantyan.exerciseproject.database.RoomDB
import id.anantyan.exerciseproject.model.Messages
import id.anantyan.exerciseproject.model.MessagesResponse
import id.anantyan.exerciseproject.repository.MessagesRepository
import id.anantyan.utils.Resource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MessagesViewModel(private val repository: MessagesRepository): ViewModel() {

    val selectResponse: LiveData<Resource<List<Messages>>> = repository._selectResponse
    val insertResponse: LiveData<Resource<Messages>> = repository._insertResponse
    val updateResponse: LiveData<Resource<Messages>> = repository._updateResponse
    val deleteResponse: LiveData<Resource<Messages>> = repository._deleteResponse

    val insertLocal: LiveData<Messages> = repository._insertLocal
    val updateLocal: LiveData<Messages> = repository._updateLocal
    val deleteLocal: LiveData<Messages> = repository._deleteLocal

    fun selectLocal() = repository.selectLocal()
    fun selectApi() = CoroutineScope(Dispatchers.IO).launch {
        repository.selectApi()
    }

    fun insertLocal(item: Messages) = CoroutineScope(Dispatchers.IO).launch {
        repository.insertLocal(item)
    }
    fun insertApi(item: Messages) = CoroutineScope(Dispatchers.IO).launch {
        repository.insertApi(item)
    }

    fun updateLocal(item: Messages) = CoroutineScope(Dispatchers.IO).launch {
        repository.updateLocal(item)
    }
    fun updateApi(item: Messages) = CoroutineScope(Dispatchers.IO).launch {
        repository.updateApi(item)
    }

    fun deleteLocal(item: Messages) = CoroutineScope(Dispatchers.IO).launch {
        repository.deleteLocal(item)
    }
    fun deleteApi(item: Messages) = CoroutineScope(Dispatchers.IO).launch {
        repository.deleteApi(item)
    }
}