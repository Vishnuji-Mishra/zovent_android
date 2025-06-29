package com.app.zovent.data.api

//import com.app.zovent.BuildConfig
//import com.app.blink_agent.utils.Constants.TOKEN
//import com.orhanobut.hawk.Hawk
import com.app.zovent.utils.network.TokenInterceptor
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object  RetrofitBuilder {
    private const val BASE_URL = "https://api.zovent.in/api/accounts/"


    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(httpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build() //Doesn't require the adapter
    }

    private val httpClient: OkHttpClient =
        OkHttpClient.Builder().connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(20, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .addInterceptor(Interceptor { chain ->
                val ongoing: Request.Builder = chain.request().newBuilder()
                ongoing.addHeader("Accept", "application/json")
//                ongoing.addHeader("Content-Type", "application/json")
                //add header
                /*Hawk.get<String>(TOKEN, null)?.let {
                    ongoing.addHeader(
                        "Authorization",
                        it
                    )
                }*/
                chain.proceed(ongoing.build())
            }).addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .addInterceptor(TokenInterceptor())          // Attach token interceptor (new)
            .build()


    val apiService: ApiService = getRetrofit().create(ApiService::class.java)

}
