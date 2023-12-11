package com.example.gogolookhomework.model

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gogolookhomework.connect.PixabayResponse
import com.example.gogolookhomework.connect.PixabayService
import com.example.gogolookhomework.model.room.SearchHistory
import com.example.gogolookhomework.model.room.SearchHistoryRepository
import com.example.gogolookhomework.view.ProcessDialog
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


private const val TAG = "SearchViewModel"

class SearchViewModel : ViewModel() {
    private val mResponseLiveData: MutableLiveData<Response<PixabayResponse>> = MutableLiveData()

    private var mHasInitHomeFragment = false
    private var mIsGridLayout = true

    private val mSearchHistoryRepository = SearchHistoryRepository.get()
    private var mProcessDialog: ProcessDialog? = null

    fun callSearchApi(pContext: Context?, pString: String) {
        val apiKey = "41142826-c0ca063b999757c5a8f6f5c3a"
        val iPixabayService = PixabayService()
        val call = iPixabayService.searchImages(apiKey, pString, "photo")
        showProgressDialog(pContext)
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
                cancelProgressDialog()
            }

            override fun onFailure(call: Call<PixabayResponse>, t: Throwable) {
                Log.d(TAG, "onFailure: $t")
                cancelProgressDialog()
            }
        })

    }

    fun getSearchRes(): LiveData<Response<PixabayResponse>> {
        return mResponseLiveData
    }

    fun setHadInitHomeFragment(pIsInit: Boolean) {
        mHasInitHomeFragment = pIsInit
    }

    fun getHadInitHomeFragment() = mHasInitHomeFragment

    fun setIsGridLayout() {
        mIsGridLayout = !mIsGridLayout
    }

    fun getIsGridLayout() = mIsGridLayout


    fun insertSearchHistory(query: String) {
        viewModelScope.launch(Dispatchers.IO) {
            mSearchHistoryRepository.insertSearchHistory(query)
        }
    }

    fun deleteSearchHistory(searchHistoryId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            mSearchHistoryRepository.deleteSearchHistory(searchHistoryId)
        }
    }

    fun getAllHistoryData(): LiveData<List<SearchHistory>> {
        return mSearchHistoryRepository.getAllSearchHistory()
    }

    fun deleteAllSearchHistory() {
        viewModelScope.launch(Dispatchers.IO) {
            mSearchHistoryRepository.deleteAllSearchHistory()
        }
    }

    fun showProgressDialog(pContext: Context?) {
        val iProcessDialog = mProcessDialog
        if (iProcessDialog == null && pContext != null) {
            val iiProcessDialog = ProcessDialog(pContext)
            iiProcessDialog.show()
            mProcessDialog = iiProcessDialog
        }
    }

    fun cancelProgressDialog() {
        mProcessDialog?.cancel()
        mProcessDialog = null
    }


}