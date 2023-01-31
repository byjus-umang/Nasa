package com.example.nasa

import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

val baseUrl="https://api.nasa.gov/"


val retrofitBuilder : Retrofit = Retrofit.Builder()
    .addConverterFactory(GsonConverterFactory.create())
    .baseUrl(baseUrl)
    .build()

interface DataService {

    @GET("planetary/apod")
    suspend fun getData(
        @Query("api_key") apiKey: String = "OHF8WZZT5AIs0UklqfKW1mHQvs6zLputIzkinGbb",
        @Query("date") date: String
    ): Response<ApodResponse>
}
    object DataApi {
        val retrofitService: DataService by lazy {
            retrofitBuilder.create(DataService::class.java)
        }
    }




