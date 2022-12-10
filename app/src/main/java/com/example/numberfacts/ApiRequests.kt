package com.example.numberfacts

import android.content.Context
import android.content.Intent
import android.util.Log
import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory


class ApiRequests {
    companion object {
        private val interceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        private val retrofit = Retrofit.Builder()
            .baseUrl(ServerApi.BASE_URL)
            .client(
                OkHttpClient.Builder()
                    .addInterceptor(interceptor)
                    .build()
            )
            .addConverterFactory(ScalarsConverterFactory.create())
            .build()


        private val api: ServerApi = retrofit.create(ServerApi::class.java)

        fun getNumberFact(int: Int, context: Context): Fact? {
            val gson = Gson()
            api.getNumberFact(int).execute().body()?.let {
                val fact = Fact(int, it)
                val intent = Intent(context, FactActivity::class.java)
                intent.putExtra("fact", gson.toJson(fact))
                context.startActivity(intent)
                return fact
            }
            return null
        }

        fun getRandomMathFact(context: Context): Fact? {
            val gson = Gson()
            api.getRandomMathFact().execute().body()?.let {
                val number = it.substringBefore(" ")
                val fact = Fact(number.toInt(), it)
                val intent = Intent(context, FactActivity::class.java)
                intent.putExtra("fact", gson.toJson(fact))
                context.startActivity(intent)
                return fact
            }
            return null
        }
    }
}