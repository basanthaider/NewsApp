package com.example.newsapp

import retrofit2.Call
import retrofit2.http.GET

interface NewsCallable {
    @GET("/v2/everything?q=bitcoin&apiKey=b82ed426dd2e4bbca6aa270b447556b5&pageSize=20")
    fun getNews():Call<News>
}