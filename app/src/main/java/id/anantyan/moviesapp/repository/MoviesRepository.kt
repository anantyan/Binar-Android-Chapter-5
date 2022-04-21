package id.anantyan.moviesapp.repository

import androidx.lifecycle.MutableLiveData
import id.anantyan.moviesapp.model.CastItem
import id.anantyan.moviesapp.model.Credits
import id.anantyan.moviesapp.model.MoviesDetail
import id.anantyan.moviesapp.model.ResultsItem
import id.anantyan.moviesapp.network.RetrofitNetwork
import id.anantyan.utils.Constant.CAT_NOW_PLAYING
import id.anantyan.utils.Constant.CAT_POPULAR
import id.anantyan.utils.Constant.CAT_TOP_RATED
import id.anantyan.utils.Constant.CAT_UPCOMING
import id.anantyan.utils.Resource

class MoviesRepository {
    val _trendingResponse: MutableLiveData<Resource<List<ResultsItem>>> = MutableLiveData()
    val _popularResponse: MutableLiveData<Resource<List<ResultsItem>>> = MutableLiveData()
    val _topRatedResponse: MutableLiveData<Resource<List<ResultsItem>>> = MutableLiveData()
    val _nowPlayingResponse: MutableLiveData<Resource<List<ResultsItem>>> = MutableLiveData()
    val _upcomingResponse: MutableLiveData<Resource<List<ResultsItem>>> = MutableLiveData()
    val _getMovieByIdResonse: MutableLiveData<Resource<MoviesDetail>> = MutableLiveData()
    val _getCreditsByIdResponse: MutableLiveData<Resource<List<CastItem>>> = MutableLiveData()

    suspend fun trendingWeek() {
        _trendingResponse.postValue(Resource.Loading())
        try {
            val response = RetrofitNetwork.moviesApi.byTrendingWeek()
            if (response.isSuccessful) {
                response.body()?.let {
                    _trendingResponse.postValue(Resource.Success(it.results!!))
                }
            } else {
                response.body()?.let {
                    throw Exception(it.statusMessage)
                }
            }
        } catch(ex: Exception) {
            ex.message?.let {
                _trendingResponse.postValue(Resource.Error(it))
            }
        }
    }

    suspend fun popular() {
        _popularResponse.postValue(Resource.Loading())
        try {
            val response = RetrofitNetwork.moviesApi.byCategory(path = CAT_POPULAR)
            if (response.isSuccessful) {
                response.body()?.let {
                    _popularResponse.postValue(Resource.Success(it.results!!))
                }
            } else {
                response.body()?.let {
                    throw Exception(it.statusMessage)
                }
            }
        } catch (ex: Exception) {
            ex.message?.let {
                _popularResponse.postValue(Resource.Error(it))
            }
        }
    }

    suspend fun topRated() {
        _topRatedResponse.postValue(Resource.Loading())
        try {
            val response = RetrofitNetwork.moviesApi.byCategory(path = CAT_TOP_RATED)
            if (response.isSuccessful) {
                response.body()?.let {
                    _topRatedResponse.postValue(Resource.Success(it.results!!))
                }
            } else {
                response.body()?.let {
                    throw Exception(it.statusMessage)
                }
            }
        } catch (ex: Exception) {
            ex.message?.let {
                _topRatedResponse.postValue(Resource.Error(it))
            }
        }
    }

    suspend fun nowPlaying() {
        _nowPlayingResponse.postValue(Resource.Loading())
        try {
            val response = RetrofitNetwork.moviesApi.byCategory(path = CAT_NOW_PLAYING)
            if (response.isSuccessful) {
                response.body()?.let {
                    _nowPlayingResponse.postValue(Resource.Success(it.results!!))
                }
            } else {
                response.body()?.let {
                    throw Exception(it.statusMessage)
                }
            }
        } catch (ex: Exception) {
            ex.message?.let {
                _nowPlayingResponse.postValue(Resource.Error(it))
            }
        }
    }

    suspend fun upcoming() {
        _upcomingResponse.postValue(Resource.Loading())
        try {
            val response = RetrofitNetwork.moviesApi.byCategory(path = CAT_UPCOMING)
            if (response.isSuccessful) {
                response.body()?.let {
                    _upcomingResponse.postValue(Resource.Success(it.results!!))
                }
            } else {
                response.body()?.let {
                    throw Exception(it.statusMessage)
                }
            }
        } catch (ex: Exception) {
            ex.message?.let {
                _upcomingResponse.postValue(Resource.Error(it))
            }
        }
    }

    suspend fun getMovieById(id: String) {
        _getMovieByIdResonse.postValue(Resource.Loading())
        try {
            val response = RetrofitNetwork.moviesApi.getIdMovie(id = id)
            if (response.isSuccessful) {
                response.body()?.let {
                    _getMovieByIdResonse.postValue(Resource.Success(it))
                }
            } else {
                response.body()?.let {
                    throw Exception(it.statusMessage)
                }
            }
        } catch(ex: Exception) {
            ex.message?.let {
                _getMovieByIdResonse.postValue(Resource.Error(it))
            }
        }
    }

    suspend fun getCreditsById(id: String) {
        _getCreditsByIdResponse.postValue(Resource.Loading())
        try {
            val response = RetrofitNetwork.moviesApi.getCredits(id = id)
            if (response.isSuccessful) {
                response.body()?.let {
                    _getCreditsByIdResponse.postValue(Resource.Success(it.cast!!))
                }
            } else {
                response.body()?.let {
                    throw Exception(it.statusMessage)
                }
            }
        } catch(ex: Exception) {
            ex.message?.let {
                _getCreditsByIdResponse.postValue(Resource.Error(it))
            }
        }
    }
}