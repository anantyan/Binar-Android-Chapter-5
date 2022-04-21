package id.anantyan.moviesapp.ui.main.home_detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import id.anantyan.moviesapp.model.CastItem
import id.anantyan.moviesapp.model.MoviesDetail
import id.anantyan.moviesapp.repository.MoviesRepository
import id.anantyan.utils.Resource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeDetailViewModel(private val repository: MoviesRepository) : ViewModel() {

    val getMovieByIdResponse: LiveData<Resource<MoviesDetail>> = repository._getMovieByIdResonse
    val getCreditsByIdResponse: LiveData<Resource<List<CastItem>>> = repository._getCreditsByIdResponse

    fun getMovieById(id: String) = CoroutineScope(Dispatchers.IO).launch {
        repository.getMovieById(id)
        repository.getCreditsById(id)
    }
}