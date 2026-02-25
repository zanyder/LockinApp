package com.lockinapp.focusblocker.domain.model

data class BlockedApp(
    val packageName: String,
    val label: String,
    val isBlocked: Boolean
)

