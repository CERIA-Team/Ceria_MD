package com.ceria.capstone.ui.listening

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel

class ListeningViewModel : ViewModel() {
    private val _currentHeartRate = MutableLiveData<Int>()
    val currentHeartRate: LiveData<Int> = _currentHeartRate

    fun setCurrentHeartRate(value: Int) {
        _currentHeartRate.value = value
    }
    fun incrementHeartRate() {
        _currentHeartRate.value = _currentHeartRate.value?.plus(1)
    }

    fun decrementHeartRate() {
        _currentHeartRate.value = _currentHeartRate.value?.minus(1)
    }
}
// import android.app.Application
// import androidx.lifecycle.AndroidViewModel
// import com.ceria.capstone.data.roomfavorite.FavoriteDao
// import com.ceria.capstone.data.roomfavorite.FavoriteDatabase
// import com.ceria.capstone.data.roomfavorite.FavoriteEntity
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

//     fun remove(id: Int) {
//         CoroutineScope(Dispatchers.IO).launch {
//             userDao.remove(id)
//         }
//     }

//     fun insertSong(track: Track, sessionId: String) {
//         val artists = track.artists.map { it.name }.joinToString(", ")
//         val albumName = track.album.name
//         val imageUrl = track.imageUri.raw?.replace("spotify:image:", "https://i.scdn.co/image/")

//         CoroutineScope(Dispatchers.IO).launch {
//             val song = SummaryEntity(
//                 sessionId = sessionId,
//                 artists = artists,
//                 albumNames = albumName,
//                 imageUrls = imageUrl ?: ""
//             )
//             userDao2.insert(song)
//         }
//     }

// }
