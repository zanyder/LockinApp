package com.lockinapp.focusblocker.domain.usecase

import android.content.Context
import android.content.Intent
import com.lockinapp.focusblocker.ui.blocker.BlockerActivity

class HandleBlockedAppOpenUseCase(
    private val context: Context
) {
    operator fun invoke(blockedPackage: String) {
        val intent = Intent(context, BlockerActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP)
            putExtra("blocked_package", blockedPackage)
        }
        context.startActivity(intent)
    }
}

