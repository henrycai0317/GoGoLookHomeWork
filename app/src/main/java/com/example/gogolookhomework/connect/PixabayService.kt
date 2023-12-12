package com.example.gogolookhomework.connect

import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private const val TAG = "PixabayService"

class PixabayService {

    private val pixabayApi: PixabayApi

    init {
        val retrofit = RetrofitClient.getRetrofitInstance()
        pixabayApi = retrofit.create(PixabayApi::class.java)
    }

    fun searchImages(
        apiKey: String,
        query: String,
        perPage: Int,
        imageType: String
    ): Call<PixabayResponse> {
        return pixabayApi.searchPhoto(apiKey, query, perPage, imageType)
    }
}