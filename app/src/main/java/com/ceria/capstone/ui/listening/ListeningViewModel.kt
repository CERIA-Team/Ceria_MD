package com.ceria.capstone.ui.listening

import android.app.Application
import androidx.lifecycle.*
import com.ceria.capstone.data.Result
import com.ceria.capstone.domain.model.SongDTO
import com.ceria.capstone.domain.usecase.GetNextQueueUseCase
import com.ceria.capstone.domain.usecase.GetSongRecommendationsUseCase
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

//    private val favoriteDao: FavoriteDao
//
//
//    init {
//        val favoriteDb = FavoriteDatabase.getDatabase(application)
//        favoriteDao = favoriteDb.favoriteuserDao()
//
//
//    }

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


}
// import android.app.Application
// import androidx.lifecycle.AndroidViewModel
// import com.ceria.capstone.data.local.room.FavoriteDao
// import com.ceria.capstone.data.local.room.FavoriteDatabase
// import com.ceria.capstone.data.local.room.FavoriteEntity
// import com.ceria.capstone.data.roomsummary.SummaryDao
// import com.ceria.capstone.data.roomsummary.SummaryDatabase
// import com.ceria.capstone.data.roomsummary.SummaryEntity
// import com.spotify.protocol.types.Track
// import kotlinx.coroutines.CoroutineScope
// import kotlinx.coroutines.Dispatchers
// import kotlinx.coroutines.launch

// class ListeningViewModel(application: Application) : AndroidViewModel(application) {
//     private val userDao: FavoriteDao
//     private val userDb: FavoriteDatabase
//     private val userDao2: SummaryDao
//     private val userDb2: SummaryDatabase

//     init {
//         userDb = FavoriteDatabase.getDatabase(application)
//         userDao = userDb.favoriteuserDao()
//         userDb2 = SummaryDatabase.getDatabase(application)
//         userDao2 = userDb2.songDao()
//     }

//     fun insert(username: String, id: Int, album: String, avatarUrl: String) {
//         CoroutineScope(Dispatchers.IO).launch {
//             val user = FavoriteEntity(
//                 id,
//                 username,
//                 album,
//                 avatarUrl
//             )
//             userDao.insert(user)
//         }
//     }

//     fun checkUser(id: Int) = userDao.checkuser(id)



// }
