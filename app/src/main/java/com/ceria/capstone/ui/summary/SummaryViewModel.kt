package com.ceria.capstone.ui.summary

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.ceria.capstone.data.roomfavorite.FavoriteDao
import com.ceria.capstone.data.roomfavorite.FavoriteDatabase
import com.ceria.capstone.data.roomsummary.SummaryDao
import com.ceria.capstone.data.roomsummary.SummaryDatabase
import com.ceria.capstone.data.roomsummary.SummaryEntity
import kotlinx.coroutines.launch

class SummaryViewModel(application: Application) : AndroidViewModel(application) {

    // Define DAO and Database instances
    private var summaryDao: SummaryDao
    private var summaryDatabase: SummaryDatabase
    private var userDao: FavoriteDao
    private var userDb: FavoriteDatabase

    // LiveData to observe the summary entity
    private lateinit var summaryLiveData: LiveData<List<SummaryEntity>>

    init {
        summaryDatabase = SummaryDatabase.getDatabase(application)
        summaryDao = summaryDatabase.songDao()
        userDb = FavoriteDatabase.getDatabase(application)
        userDao = userDb.favoriteuserDao()
    }

    // Function to get summary by sessionId
    fun getSummaryBySessionId(sessionId: String): LiveData<List<SummaryEntity>> {
        summaryLiveData = summaryDao.getSummaryBySessionId(sessionId)
        return summaryLiveData
    }
    fun checkUser(username: String):Int = userDao.checkuserfavorite(username)

}
