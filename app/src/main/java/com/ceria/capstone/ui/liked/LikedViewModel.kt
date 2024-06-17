package com.ceria.capstone.ui.liked

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.ceria.capstone.data.room.FavoriteDao
import com.ceria.capstone.data.room.FavoriteDatabase
import com.ceria.capstone.data.room.FavoriteEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LikedViewModel(application: Application) : AndroidViewModel(application) {
    private var userDao: FavoriteDao?
    private var userDb: FavoriteDatabase?

    init {
        userDb = FavoriteDatabase.getDatabase(application)
        userDao = userDb?.favoriteuserDao()
    }

    fun getfavoriteuser(): LiveData<List<FavoriteEntity>>? {
        return userDao?.getfavoriteuser()
    }
    fun removeFavorite(favoriteEntity: FavoriteEntity) {
        viewModelScope.launch {
            userDao?.remove(favoriteEntity.id)
        }
    }
}
