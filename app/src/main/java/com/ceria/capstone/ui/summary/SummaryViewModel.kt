package com.ceria.capstone.ui.summary

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import com.ceria.capstone.domain.model.SongDTO
import com.ceria.capstone.domain.usecase.GetSessionDetailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
//import com.ceria.capstone.data.local.room.FavoriteDao
//import com.ceria.capstone.data.local.room.FavoriteDatabase
//import com.ceria.capstone.data.roomsummary.SummaryDao
//import com.ceria.capstone.data.roomsummary.SummaryDatabase
//import com.ceria.capstone.data.roomsummary.SummaryEntity
//>>>>>>> d496628957a59956f658224fba5756676fb00558
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.ceria.capstone.data.Result
import com.ceria.capstone.domain.usecase.FavoriteUseCase

@HiltViewModel
class SummaryViewModel @Inject constructor(
    private val getSessionDetailUseCase: GetSessionDetailUseCase,
    private val favoriteUseCase: FavoriteUseCase
) : ViewModel() {
    private val _songs = MutableLiveData<Result<List<SongDTO>>>()
    val songs: LiveData<Result<List<SongDTO>>> = _songs

    fun getSessionDetail(id: String) {
        viewModelScope.launch {
            getSessionDetailUseCase.getSessionDetail(id).asFlow().collect {
                _songs.value = it
            }
        }
    }

    fun addSongToFavorite(song: SongDTO) {
        viewModelScope.launch {
            favoriteUseCase.addSongToFavorite(song)
        }
    }

    fun removeSongFromFavorite(song: SongDTO) {
        viewModelScope.launch {
            favoriteUseCase.removeSongFromFavorite(song)
        }
    }

//=======
//    // Define DAO and Database instances
//    private var summaryDao: SummaryDao
//    private var summaryDatabase: SummaryDatabase
//    private var userDao: FavoriteDao
//    private var userDb: FavoriteDatabase
//
//    // LiveData to observe the summary entity
//    private lateinit var summaryLiveData: LiveData<List<SummaryEntity>>
//
//    init {
//        summaryDatabase = SummaryDatabase.getDatabase(application)
//        summaryDao = summaryDatabase.songDao()
//        userDb = FavoriteDatabase.getDatabase(application)
//        userDao = userDb.favoriteuserDao()
//    }
//
//    // Function to get summary by sessionId
//    fun getSummaryBySessionId(sessionId: String): LiveData<List<SummaryEntity>> {
//        summaryLiveData = summaryDao.getSummaryBySessionId(sessionId)
//        return summaryLiveData
//    }
//    fun checkUser(username: String):Int = userDao.checkuserfavorite(username)
//
//<<<<<<< Updated upstream
//=======
//>>>>>>> d496628957a59956f658224fba5756676fb00558
//>>>>>>> Stashed changes
}
