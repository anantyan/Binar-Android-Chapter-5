package id.anantyan.exerciseproject.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import id.anantyan.exerciseproject.model.Messages
import id.anantyan.exerciseproject.model.Users
import id.anantyan.utils.LiveEvent

class SharedViewModel : ViewModel() {
    private val _item: MutableLiveData<Users?> = MutableLiveData()
    private val _messages: LiveEvent<Messages?> = LiveEvent()

    val item: LiveData<Users?> = _item
    val messages: LiveData<Messages?> = _messages

    fun setItem(item: Users?) {
        _item.postValue(item)
    }

    fun setMessages(messages: Messages?) {
        _messages.postValue(messages)
    }
}