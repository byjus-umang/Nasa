package com.example.nasa

import androidx.lifecycle.viewmodel.compose.viewModel
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET


val baseUrl="https://api.nasa.gov/"
//val viewModel: NasaViewModel()
const val date:String= "2023-01-27"
val retrofitBuilder=Retrofit.Builder().addConverterFactory(GsonConverterFactory.create()).baseUrl(
    baseUrl).build()
interface dataservice{
    @GET(
        "planetary/apod?api_key=OHF8WZZT5AIs0UklqfKW1mHQvs6zLputIzkinGbb&date=${date}"
    )
    suspend fun getdata(): Response<UserResponse>

}
object dataApi{
    val retrofitService:dataservice by lazy {
        retrofitBuilder.create(dataservice::class.java)
    }
}
