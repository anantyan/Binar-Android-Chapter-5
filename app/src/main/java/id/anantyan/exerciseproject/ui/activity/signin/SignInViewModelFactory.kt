package id.anantyan.exerciseproject.ui.activity.signin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import id.anantyan.exerciseproject.repository.UsersRepository
import id.anantyan.utils.sharedPreferences.PreferenceHelper

class SignInViewModelFactory(private val repository: UsersRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(repository::class.java).newInstance(repository)
    }
}