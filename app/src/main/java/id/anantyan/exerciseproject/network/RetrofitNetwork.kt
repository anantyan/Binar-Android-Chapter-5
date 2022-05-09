package id.anantyan.exerciseproject.network

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonDeserializer
import id.anantyan.exerciseproject.data.api.MessagesApi
import id.anantyan.exerciseproject.data.api.UsersApi
import okhttp3.JavaNetCookieJar
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import java.net.CookieManager
import java.util.*
import java.util.concurrent.TimeUnit

object RetrofitNetwork {
    private fun retrofit(): Retrofit {
        return Retrofit.Builder().apply {
            client(providerHttpClient())
            baseUrl("http://drivingrake.backendless.app/api/")
            addConverterFactory(GsonConverterFactory.create(gson()))
        }.build()
    }

    private fun gson(): Gson {
        return GsonBuilder().apply {
            setLenient()
            registerTypeAdapter(Date::class.java, JsonDeserializer { jsonElement, _, _ ->
                Date(jsonElement.asJsonPrimitive.asLong)
            })
        }.create()
    }

    private fun providerHttpClient(): OkHttpClient {
        return OkHttpClient.Builder().apply {
            retryOnConnectionFailure(true)
            addNetworkInterceptor(providerHttpLoggingInterceptor())
            cookieJar(JavaNetCookieJar(CookieManager()))
            connectTimeout(15, TimeUnit.MINUTES)
            writeTimeout(30, TimeUnit.SECONDS)
            readTimeout(30, TimeUnit.SECONDS)
        }.build()
    }

    private fun providerHttpLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            setLevel(HttpLoggingInterceptor.Level.BODY)
        }
    }

    val messagesApi: MessagesApi by lazy { retrofit().create(MessagesApi::class.java) }
    val usersApi: UsersApi by lazy { retrofit().create(UsersApi::class.java) }
}