package com.lockinapp.focusblocker.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [
        BlockedAppEntity::class,
        FocusSessionEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun blockedAppDao(): BlockedAppDao
    abstract fun focusSessionDao(): FocusSessionDao
}

