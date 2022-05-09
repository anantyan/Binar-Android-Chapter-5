package id.anantyan.exerciseproject.data.api

import id.anantyan.exerciseproject.model.Users
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface UsersApi {

    @POST("users/login")
    suspend fun signInUsers(@Body request: Users): Response<Users>

    @POST("users/register")
    suspend fun signUpUsers(@Body request: Users): Response<Users>
}