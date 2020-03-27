package org.d3if0060.mydiary.database

import androidx.lifecycle.LiveData
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Dao
import androidx.room.Update

@Dao
interface DiaryDatabaseDao {

    @Insert
    fun insert(diary: DataDiary)

    @Query("DELETE FROM daily_diary_table")
    fun clear()

    @Query("SELECT * FROM daily_diary_table")
    fun getAllDiaries(): LiveData<List<DataDiary>>

    @Update
    fun update(diary: DataDiary)

    @Query("DELETE FROM daily_diary_table WHERE diaryId = :id")
    fun del(id: Long)
}