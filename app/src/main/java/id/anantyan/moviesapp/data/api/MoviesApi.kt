package id.anantyan.moviesapp.data.api

import id.anantyan.moviesapp.model.Credits
import id.anantyan.moviesapp.model.Movies
import id.anantyan.moviesapp.model.MoviesDetail
import id.anantyan.utils.Constant.API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MoviesApi {
    @GET("trending/movie/week")
    suspend fun byTrendingWeek(
        @Query("api_key") apiKey: String? = API_KEY
    ): Response<Movies>

    @GET("movie/{path}") // popular, top_rated, upcoming, now_playing
    suspend fun byCategory(
        @Path("path") path: String,
        @Query("api_key") apiKey: String? = API_KEY
    ): Response<Movies>

    @GET("search/movie")
    suspend fun bySearch(
        @Path("path") path: String,
        @Query("api_key") apiKey: String? = API_KEY,
        @Query("query") query: String
    ): Response<Movies>

    @GET("movie/{id}")
    suspend fun getIdMovie(
        @Path("id") id: String,
        @Query("api_key") apiKey: String? = API_KEY
    ): Response<MoviesDetail>

    @GET("movie/{id}/credits")
    suspend fun getCredits(
        @Path("id") id: String,
        @Query("api_key") apiKey: String? = API_KEY
    ): Response<Credits>
}