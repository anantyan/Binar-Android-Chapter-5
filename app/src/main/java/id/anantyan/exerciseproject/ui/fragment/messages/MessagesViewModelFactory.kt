package id.anantyan.exerciseproject.ui.fragment.messages

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import id.anantyan.exerciseproject.repository.MessagesRepository

class MessagesViewModelFactory(private val repository: MessagesRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(repository::class.java).newInstance(repository)
    }
}