package com.ceria.capstone.ui.liked

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.ceria.capstone.data.roomfavorite.FavoriteDao
import com.ceria.capstone.data.roomfavorite.FavoriteDatabase
import com.ceria.capstone.data.roomfavorite.FavoriteEntity
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
