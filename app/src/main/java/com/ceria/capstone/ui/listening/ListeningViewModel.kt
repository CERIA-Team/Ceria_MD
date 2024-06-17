package com.ceria.capstone.ui.listening

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.ceria.capstone.data.room.FavoriteDao
import com.ceria.capstone.data.room.FavoriteDatabase
import com.ceria.capstone.data.room.FavoriteEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber

class ListeningViewModel(application: Application) : AndroidViewModel(application) {
    private val userDao: FavoriteDao
    private val userDb: FavoriteDatabase

    init {
        userDb = FavoriteDatabase.getDatabase(application)
        userDao = userDb.favoriteuserDao()
    }

    fun insert(username: String,id:Int,album:String,avatarurl:String){
        CoroutineScope(Dispatchers.IO).launch {
            val user = FavoriteEntity(
                id,
                username,
                album,
                avatarurl
            )
            userDao?.insert(user)
        }

    }

    fun checkUser(id: Int) = userDao?.checkuser(id)

    fun remove(id: Int){
        CoroutineScope(Dispatchers.IO).launch {
            userDao?.remove(id)
        }
    }

}
