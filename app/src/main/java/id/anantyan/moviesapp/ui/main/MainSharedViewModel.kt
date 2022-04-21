package id.anantyan.moviesapp.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainSharedViewModel: ViewModel() {

    private val _movieId: MutableLiveData<Int> = MutableLiveData(-1)
    val movieId: LiveData<Int> = _movieId

    fun movieId(id: Int) {
        _movieId.postValue(id)
    }
}