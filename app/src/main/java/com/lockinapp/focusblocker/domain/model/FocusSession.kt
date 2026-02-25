package com.lockinapp.focusblocker.domain.model

data class FocusSession(
    val id: Long,
    val startTimeMillis: Long,
    val endTimeMillis: Long?,
    val targetDurationMillis: Long,
    val isStrict: Boolean,
    val completedSuccessfully: Boolean?
)

