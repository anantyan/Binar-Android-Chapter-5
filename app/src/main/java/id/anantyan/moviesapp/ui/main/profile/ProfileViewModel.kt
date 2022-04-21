package id.anantyan.moviesapp.ui.main.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import id.anantyan.moviesapp.model.Profile
import id.anantyan.moviesapp.model.Users
import id.anantyan.moviesapp.repository.UsersRepository
import id.anantyan.utils.Resource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProfileViewModel(private val repository: UsersRepository) : ViewModel() {

    val showAccount: LiveData<Resource<List<Profile>>> = repository._showAccount
    val setProfile: LiveData<Resource<List<Profile>>> = repository._setProfile
    val setPassword: LiveData<Resource<String>> = repository._setPassword
    val getAccount: LiveData<Resource<Users>> = repository._getAccount

    fun showAccount(userId: Int?) = CoroutineScope(Dispatchers.IO).launch {
        repository.showAccount(userId)
    }

    fun setProfile(item: Users) = CoroutineScope(Dispatchers.IO).launch {
        repository.setProfile(item)
    }

    fun setPassword(item: Users) = CoroutineScope(Dispatchers.IO).launch {
        repository.setPassword(item)
    }

    fun getAccount(userId: Int?) = CoroutineScope(Dispatchers.IO).launch {
        repository.getAccount(userId)
    }
}