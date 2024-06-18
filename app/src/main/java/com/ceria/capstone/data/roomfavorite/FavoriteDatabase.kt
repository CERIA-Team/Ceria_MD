package com.ceria.capstone.data.roomfavorite

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [FavoriteEntity::class],
    version = 1, exportSchema = false
)
abstract class FavoriteDatabase : RoomDatabase() {
    abstract fun favoriteuserDao(): FavoriteDao

    companion object {
        @Volatile
        var INSTANCE: FavoriteDatabase? = null

        @JvmStatic
        fun getDatabase(context: Context): FavoriteDatabase {
            if (INSTANCE == null) {
                synchronized(FavoriteDatabase::class.java) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        FavoriteDatabase::class.java, "favorite_database"
                    )
                        .build()
                }
            }
            return INSTANCE as FavoriteDatabase
        }


    }
}