package com.example.gogolookhomework.model.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface SearchHistoryDao {

    @Query("SELECT * FROM search_history ORDER BY id DESC")
    fun getAllSearchHistory(): LiveData<List<SearchHistory>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSearchHistory(searchHistory: SearchHistory)

    @Query("DELETE FROM search_history WHERE id = :searchHistoryId")
    fun deleteSearchHistory(searchHistoryId: Int): Int // 返回類型應為 Int

    @Query("DELETE FROM search_history")
    fun deleteAllSearchHistory()

}