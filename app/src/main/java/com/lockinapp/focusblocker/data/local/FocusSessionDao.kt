package com.lockinapp.focusblocker.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface FocusSessionDao {

    @Query("SELECT * FROM focus_sessions WHERE endTimeMillis IS NULL LIMIT 1")
    fun observeActiveSession(): Flow<FocusSessionEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSession(entity: FocusSessionEntity): Long

    @Update
    suspend fun updateSession(entity: FocusSessionEntity)

    @Query("SELECT * FROM focus_sessions ORDER BY startTimeMillis DESC")
    fun observeAllSessions(): Flow<List<FocusSessionEntity>>
}

