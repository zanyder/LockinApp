package com.lockinapp.focusblocker.service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.app.usage.UsageEvents
import android.app.usage.UsageStatsManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.lockinapp.focusblocker.R
import com.lockinapp.focusblocker.data.Graph
import com.lockinapp.focusblocker.domain.model.FocusSession
import com.lockinapp.focusblocker.domain.usecase.HandleBlockedAppOpenUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

class FocusMonitoringService : Service() {

    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.Default)

    private var activeSession: FocusSession? = null
    private var blockedPackages: Set<String> = emptySet()
    private lateinit var handleBlockedAppOpenUseCase: HandleBlockedAppOpenUseCase

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()

        handleBlockedAppOpenUseCase = HandleBlockedAppOpenUseCase(this)

        scope.launch {
            Graph.sessionRepository.observeActiveSession().collectLatest { session ->
                activeSession = session
            }
        }

        scope.launch {
            Graph.blockedAppsRepository.observeBlockedApps().collectLatest { apps ->
                blockedPackages = apps.map { it.packageName }.toSet()
            }
        }

        scope.launch {
            monitoringLoop()
        }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val notification = buildNotification()
        startForeground(NOTIFICATION_ID, notification)
        return START_STICKY
    }

    override fun onDestroy() {
        scope.cancel()
        super.onDestroy()
    }

    override fun onBind(intent: Intent?): IBinder? = null

    private suspend fun monitoringLoop() {
        while (scope.isActive) {
            delay(1000L)

            val session = activeSession ?: continue
            val currentPackage = getForegroundAppPackageName(this) ?: continue

            if (currentPackage in blockedPackages && currentPackage != packageName) {
                handleBlockedAppOpenUseCase(currentPackage)
            }

            val now = System.currentTimeMillis()
            if (session.endTimeMillis == null &&
                now - session.startTimeMillis >= session.targetDurationMillis
            ) {
                Graph.sessionRepository.endSession(successful = true)
                stopSelf()
            }

            // Session end timing will be handled by a separate use-case.
        }
    }

    private fun getForegroundAppPackageName(context: Context): String? {
        val usageStatsManager =
            context.getSystemService(Context.USAGE_STATS_SERVICE) as UsageStatsManager
        val endTime = System.currentTimeMillis()
        val beginTime = endTime - 1000L * 10

        val events: UsageEvents = usageStatsManager.queryEvents(beginTime, endTime)
        val event = UsageEvents.Event()
        var lastPackage: String? = null

        while (events.hasNextEvent()) {
            events.getNextEvent(event)
            if (event.eventType == UsageEvents.Event.MOVE_TO_FOREGROUND) {
                lastPackage = event.packageName
            }
        }
        return lastPackage
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "Focus session monitoring",
                NotificationManager.IMPORTANCE_LOW
            )
            val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(channel)
        }
    }

    private fun buildNotification(): Notification {
        return NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("Focus session active")
            .setContentText("Blocking selected apps to help you stay focused.")
            .setSmallIcon(android.R.drawable.ic_lock_idle_alarm)
            .setOngoing(true)
            .build()
    }

    companion object {
        private const val CHANNEL_ID = "focus_monitoring_channel"
        private const val NOTIFICATION_ID = 1001
    }
}

