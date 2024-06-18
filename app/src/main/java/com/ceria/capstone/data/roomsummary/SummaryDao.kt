package com.ceria.capstone.data.roomsummary

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface SummaryDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(song: SummaryEntity)

    @Query("SELECT * FROM song WHERE sessionId = :sessionId")
    fun getSummaryBySessionId(sessionId: String): LiveData<List<SummaryEntity>>
    @Query("DELETE FROM song")
    suspend fun deleteAll()


}

