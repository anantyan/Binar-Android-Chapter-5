package id.anantyan.exerciseproject.ui.activity.signin

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import id.anantyan.exerciseproject.database.RoomDB
import id.anantyan.exerciseproject.model.Users
import id.anantyan.exerciseproject.repository.UsersRepository
import id.anantyan.utils.LiveEvent
import id.anantyan.utils.Resource
import id.anantyan.utils.sharedPreferences.PreferenceHelper
import id.anantyan.utils.sharedPreferences.PreferenceManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SignInViewModel(
    private val repository: UsersRepository
): ViewModel() {

    val signInResponse: LiveData<Resource<Users>> = repository._signInResponse

    val selectByUsersLocal: LiveData<Users> = repository._selectByUsersLocal

    fun selectByUsersLocal(item: Users) = CoroutineScope(Dispatchers.IO).launch {
        repository.selectByUsersLocal(item)
    }

    fun signInApi(item: Users) = CoroutineScope(Dispatchers.IO).launch {
        repository.signInApi(item)
    }
}