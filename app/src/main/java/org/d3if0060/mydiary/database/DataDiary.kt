package org.d3if0060.mydiary.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "daily_diary_table")
data class DataDiary(

        @PrimaryKey(autoGenerate = true)
        var diaryId : Long = 0L,

        @ColumnInfo(name = "isi_diary")
        var isi_diary: String,

        @ColumnInfo(name = "date_time")
        val datetime: Long = System.currentTimeMillis()

)