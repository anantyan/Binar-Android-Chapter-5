package id.anantyan.moviesapp.ui.auth.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import id.anantyan.moviesapp.model.Users
import id.anantyan.moviesapp.repository.UsersRepository
import id.anantyan.utils.Resource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RegisterViewModel(private val repository: UsersRepository) : ViewModel() {

    val register: LiveData<Resource<Users>> = repository._register

    fun register(item: Users) = CoroutineScope(Dispatchers.IO).launch {
        repository.register(item)
    }
}