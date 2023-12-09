package com.example.gogolookhomework.connect

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface PixabayApi  {

    @GET("/api/")
    fun searchPhoto(
        @Query("key") key : String,
        @Query("q") query: String,
        @Query("image_type") imageType: String
    ): Call<PixabayResponse>
}