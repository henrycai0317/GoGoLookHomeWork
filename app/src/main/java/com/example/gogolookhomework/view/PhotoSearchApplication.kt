package com.example.gogolookhomework.view

import android.app.Application
import com.example.gogolookhomework.model.room.SearchHistoryRepository

class PhotoSearchApplication:Application() {

    override fun onCreate() {
        super.onCreate()
        SearchHistoryRepository.initialize(this)
    }
}