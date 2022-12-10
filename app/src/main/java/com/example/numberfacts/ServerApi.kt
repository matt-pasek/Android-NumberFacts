package com.example.numberfacts

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path


data class Fact(
    val number: Int,
    val text: String,
)

interface ServerApi {
    companion object {
        const val BASE_URL = "http://numbersapi.com/"
    }

    @GET("{number}")
    fun getNumberFact(@Path("number") number: Int): Call<String>

    @GET("random/math")
    fun getRandomMathFact(): Call<String>
}
