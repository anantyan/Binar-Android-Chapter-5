package id.anantyan.moviesapp.ui.auth.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import id.anantyan.moviesapp.model.Users
import id.anantyan.moviesapp.repository.UsersRepository
import id.anantyan.utils.Resource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginViewModel(private val repository: UsersRepository) : ViewModel() {

    val login: LiveData<Resource<Users>> = repository._login

    fun login(item: Users) = CoroutineScope(Dispatchers.IO).launch {
        repository.login(item)
    }
}