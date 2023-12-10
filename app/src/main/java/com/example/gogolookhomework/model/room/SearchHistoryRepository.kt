package com.example.gogolookhomework.model.room

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.Room

class SearchHistoryRepository(context: Context) {

    private val mDatabase: AppDatabase = Room.databaseBuilder(
        context.applicationContext,
        AppDatabase::class.java,
        DATABASE_NAME
    ).build()

    private val searchHistoryDao = mDatabase.searchHistoryDao()

    companion object {
        private const val DATABASE_NAME = "crime-database"
        private var INSTANCE: SearchHistoryRepository? = null

        fun initialize(context: Context) {
            if (INSTANCE == null) {
                INSTANCE = SearchHistoryRepository(context)
            }
        }

        fun get(): SearchHistoryRepository {

            return INSTANCE
                ?: throw IllegalStateException("SearchHistoryRepository must be initialize")
        }
    }

    fun insertSearchHistory(query: String) {
        searchHistoryDao.insertSearchHistory(SearchHistory(query = query))
    }

    fun deleteSearchHistory(searchHistoryId: Int) {
        searchHistoryDao.deleteSearchHistory(searchHistoryId)
    }


    fun getAllSearchHistory(): LiveData<List<SearchHistory>> {
        return searchHistoryDao.getAllSearchHistory()
    }

    fun deleteAllSearchHistory() {
        searchHistoryDao.deleteAllSearchHistory()
    }
}
