package com.example.gogolookhomework.model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.gogolookhomework.connect.PixabayResponse
import com.example.gogolookhomework.connect.PixabayService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


private const val TAG = "SearchViewModel"

class SearchViewModel : ViewModel() {
    private val mResponseLiveData: MutableLiveData<Response<PixabayResponse>> = MutableLiveData()

    private var mHasInitHomeFragment = false

    fun callSearchApi(pString: String) {
        val apiKey = "41142826-c0ca063b999757c5a8f6f5c3a"
        val iPixabayService = PixabayService()
        val call = iPixabayService.searchImages(apiKey, pString, "photo")

        call.enqueue(object : Callback<PixabayResponse> {
            override fun onResponse(
                call: Call<PixabayResponse>,
                response: Response<PixabayResponse>
            ) {
                if (response.isSuccessful) {
                    val apiResponse = response.body()
                    Log.d(TAG, "onResponse: $apiResponse")
                } else {
                    Log.d(TAG, "onResponse: not success ${response.code()}")
                }
                mResponseLiveData.value = response
            }

            override fun onFailure(call: Call<PixabayResponse>, t: Throwable) {
                Log.d(TAG, "onFailure: $t")
            }
        })

    }

    fun getSearchRes(): LiveData<Response<PixabayResponse>> {
        return mResponseLiveData
    }

    fun setHadInitHomeFragment(pIsInit:Boolean) {
        mHasInitHomeFragment = pIsInit
    }

    fun getHadInitHomeFragment() = mHasInitHomeFragment

}