package id.anantyan.exerciseproject.data.api

import id.anantyan.exerciseproject.model.Messages
import retrofit2.Response
import retrofit2.http.*

interface MessagesApi {
    @GET("data/message")
    suspend fun getMessage() : Response<List<Messages>>

    @POST("data/message")
    suspend fun postMessage(@Body request: Messages): Response<Messages>

    @PUT("data/message/{ID}")
    suspend fun updateMessages(
        @Path("ID") id: String,
        @Body request: Messages,
    ): Response<Messages>

    @DELETE("data/message/{ID}")
    suspend fun deleteMessages(@Path("ID") id: String): Response<Messages>
}