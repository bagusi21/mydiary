package org.d3if0060.mydiary.fragmentlistdiary

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import org.d3if0060.mydiary.database.DiaryDatabaseDao
import javax.sql.DataSource

class DiaryViewModelFactory(
    private val dataSource: DiaryDatabaseDao,
    private val application: Application) :  ViewModelProvider.Factory{
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DiaryViewModel::class.java)) {
            return DiaryViewModel(dataSource, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}