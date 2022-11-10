package com.example.newsly

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

//https://newsapi.org/v2/everything?q=apple&from=2022-11-06&to=2022-11-06&sortBy=popularity&apiKey=API_KEY
//https://newsapi.org/v2/top-headlines?country=in&apiKey=API_KEY

const val BASE_URL = "https://newsapi.org/"
const val API_KEY = "5ca2994f06bf4c56a7f70959de868c66"

interface NewsServiceInterface {

    @GET("v2/top-headlines?apiKey=$API_KEY")
    fun getHeadlines(@Query("country") country : String, @Query("page") page : Int) : Call<News>

}

//https://newsapi.org/v2/top-headlines?apiKey=5ca2994f06bf4c56a7f70959de868c66&country=in&page=1"

object NewsService{
    val newsInstance : NewsServiceInterface
    init {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        newsInstance = retrofit.create(NewsServiceInterface::class.java)
    }
}