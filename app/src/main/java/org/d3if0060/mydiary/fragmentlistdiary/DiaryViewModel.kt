package org.d3if0060.mydiary.fragmentlistdiary

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import kotlinx.coroutines.*
import org.d3if0060.mydiary.database.DataDiary
import org.d3if0060.mydiary.database.DiaryDatabaseDao
import org.d3if0060.mydiary.formatDiary

class DiaryViewModel(val database :DiaryDatabaseDao, application: Application): AndroidViewModel
    (application){
    private var viewModelJob = Job()

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private var diary = MutableLiveData<DataDiary?>()

    val allDiary = database.getAllDiaries()

    val diaryString = Transformations.map(allDiary){ allDiary ->
        formatDiary(allDiary, application.resources)
    }

    fun onSaveDiary(newDiary: String){
        uiScope.launch {
            val diary_ = DataDiary(0, newDiary)
            insert(diary_)
        }
    }

    private suspend fun insert(diary: DataDiary){
        withContext(Dispatchers.IO){
            database.insert(diary)
        }
    }


    private suspend fun clear(){
        withContext(Dispatchers.IO){
            database.clear()
        }
    }

    fun onClear(){
        uiScope.launch {
            clear()
        }
    }

    fun onUpdate(diary: DataDiary) {
        uiScope.launch {
            update(diary)
        }
    }

    private suspend fun update(diary: DataDiary) {
        withContext(Dispatchers.IO) {
            database.update(diary)
        }
    }

    fun onDelete(diaryId: Long) {
        uiScope.launch {
            delete(diaryId)
        }
    }

    private suspend fun delete(diaryId: Long) {
        withContext(Dispatchers.IO) {
            database.del(diaryId)
        }
    }
}