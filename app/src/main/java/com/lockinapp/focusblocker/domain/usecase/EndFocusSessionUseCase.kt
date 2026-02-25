package com.lockinapp.focusblocker.domain.usecase

import android.content.Context
import android.content.Intent
import com.lockinapp.focusblocker.data.repository.SessionRepository
import com.lockinapp.focusblocker.service.FocusMonitoringService

class EndFocusSessionUseCase(
    private val sessionRepository: SessionRepository,
    private val context: Context
) {
    suspend operator fun invoke(successful: Boolean) {
        sessionRepository.endSession(successful)
        val intent = Intent(context, FocusMonitoringService::class.java)
        context.stopService(intent)
    }
}

