package com.lockinapp.focusblocker.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "focus_sessions")
data class FocusSessionEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0L,
    val startTimeMillis: Long,
    val endTimeMillis: Long? = null,
    val targetDurationMillis: Long,
    val isStrict: Boolean,
    val completedSuccessfully: Boolean? = null
)

