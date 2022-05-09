package id.anantyan.exerciseproject.ui.activity.signup

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import id.anantyan.exerciseproject.database.RoomDB
import id.anantyan.exerciseproject.model.Users
import id.anantyan.exerciseproject.repository.UsersRepository
import id.anantyan.utils.Resource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SignUpViewModel(private val repository: UsersRepository): ViewModel() {

    val signUpResponse: LiveData<Resource<Users>> = repository._signUpResponse

    val selectByUsersLocal: LiveData<Users> = repository._selectByUsersLocal
    val insertLocal: LiveData<Users> = repository._insertLocal

    fun selectByUsersLocal(item: Users) = CoroutineScope(Dispatchers.IO).launch {
        repository.selectByUsersLocal(item)
    }

    fun insertLocal(item: Users) = CoroutineScope(Dispatchers.IO).launch {
        repository.insertLocal(item)
    }

    fun signUpApi(item: Users) = CoroutineScope(Dispatchers.IO).launch {
        repository.signUpApi(item)
    }
}