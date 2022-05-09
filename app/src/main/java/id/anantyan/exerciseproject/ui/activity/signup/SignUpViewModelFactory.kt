package id.anantyan.exerciseproject.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import id.anantyan.exerciseproject.repository.UsersRepository

class UsersViewModelFactory(private val repository: UsersRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(repository::class.java).newInstance(repository)
    }
}