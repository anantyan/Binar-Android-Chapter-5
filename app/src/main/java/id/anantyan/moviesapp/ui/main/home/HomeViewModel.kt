package id.anantyan.moviesapp.ui.main.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import id.anantyan.moviesapp.model.ResultsItem
import id.anantyan.moviesapp.repository.MoviesRepository
import id.anantyan.utils.Resource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel(private val repository: MoviesRepository) : ViewModel() {
    val trendingResponse: LiveData<Resource<List<ResultsItem>>> = repository._trendingResponse
    val popularResponse: LiveData<Resource<List<ResultsItem>>> = repository._popularResponse
    val topRatedResponse: LiveData<Resource<List<ResultsItem>>> = repository._topRatedResponse
    val nowPlayingResponse: LiveData<Resource<List<ResultsItem>>> = repository._nowPlayingResponse
    val upcomingResponse: LiveData<Resource<List<ResultsItem>>> = repository._upcomingResponse

    fun getMoviesApi() = CoroutineScope(Dispatchers.IO).launch {
        repository.trendingWeek()
        repository.popular()
        repository.topRated()
        repository.nowPlaying()
        repository.upcoming()
    }
}