package com.lockinapp.focusblocker.domain.usecase

import android.content.Context
import android.content.Intent
import androidx.core.content.ContextCompat
import com.lockinapp.focusblocker.data.repository.SessionRepository
import com.lockinapp.focusblocker.service.FocusMonitoringService

class StartFocusSessionUseCase(
    private val sessionRepository: SessionRepository,
    private val context: Context
) {
    suspend operator fun invoke(durationMillis: Long, isStrict: Boolean) {
        sessionRepository.startSession(durationMillis, isStrict)
        val intent = Intent(context, FocusMonitoringService::class.java)
        ContextCompat.startForegroundService(context, intent)
    }
}

