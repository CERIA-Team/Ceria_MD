package com.ceria.capstone.ui.listening

import android.app.Application
import androidx.lifecycle.*
import com.ceria.capstone.data.Result
import com.ceria.capstone.data.roomfavorite.FavoriteDao
import com.ceria.capstone.data.roomfavorite.FavoriteDatabase
import com.ceria.capstone.data.roomfavorite.FavoriteEntity
import com.ceria.capstone.data.roomsummary.SummaryDao
import com.ceria.capstone.data.roomsummary.SummaryDatabase
import com.ceria.capstone.data.roomsummary.SummaryEntity
import com.ceria.capstone.domain.model.SongDTO
import com.ceria.capstone.domain.usecase.GetNextQueueUseCase
import com.ceria.capstone.domain.usecase.GetSongRecommendationsUseCase
import com.spotify.protocol.types.Track
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListeningViewModel @Inject constructor(
    private val getSongRecommendationsUseCase: GetSongRecommendationsUseCase,
    private val getNextQueueUseCase: GetNextQueueUseCase,
    application: Application
) : AndroidViewModel(application) {

    private val _currentHeartRate = MutableLiveData(70)
    val currentHeartRate: LiveData<Int> = _currentHeartRate

    private val _nextQueue = MutableLiveData<Result<SongDTO>>(Result.Empty)
    val nextQueue: LiveData<Result<SongDTO>> = _nextQueue

    val initFlag = MutableLiveData(true)

    private val _recommendations = MutableLiveData<Result<List<String>>>()
    val recommendations: LiveData<Result<List<String>>> = _recommendations

    private val favoriteDao: FavoriteDao
    private val summaryDao: SummaryDao

    init {
        val favoriteDb = FavoriteDatabase.getDatabase(application)
        favoriteDao = favoriteDb.favoriteuserDao()

        val summaryDb = SummaryDatabase.getDatabase(application)
        summaryDao = summaryDb.songDao()
    }

    fun setCurrentHeartRate(value: Int) {
        _currentHeartRate.value = value
    }

    fun incrementHeartRate() {
        _currentHeartRate.value = _currentHeartRate.value?.plus(1)
    }

    fun decrementHeartRate() {
        _currentHeartRate.value = _currentHeartRate.value?.minus(1)
    }

    fun getNextQueue() {
        viewModelScope.launch {
            getNextQueueUseCase.getNextQueue().asFlow().collect {
                _nextQueue.postValue(it)
            }
        }
    }

    fun getSongRecommendations(bpm: Int, listenId: String) {
        viewModelScope.launch {
            getSongRecommendationsUseCase.getRecommendations(bpm, listenId).asFlow().collect {
                _recommendations.postValue(it)
            }
        }
    }

    fun insertFavorite(username: String, id: Int, album: String, avatarUrl: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val user = FavoriteEntity(
                id = id,
                username = username,
                album = album,
                avatarurl = avatarUrl
            )
            favoriteDao.insert(user)
        }
    }

    fun checkFavorite(id: Int) = favoriteDao.checkuser(id)

    fun removeFavorite(id: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            favoriteDao.remove(id)
        }
    }


}
