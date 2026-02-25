package com.lockinapp.focusblocker.viewmodel

import android.app.Application
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.lockinapp.focusblocker.data.Graph
import com.lockinapp.focusblocker.domain.model.BlockedApp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

data class BlockedAppsUiState(
    val apps: List<BlockedApp> = emptyList()
)

class BlockedAppsViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = Graph.blockedAppsRepository
    private val _uiState = MutableStateFlow(BlockedAppsUiState())
    val uiState: StateFlow<BlockedAppsUiState> = _uiState

    init {
        viewModelScope.launch(Dispatchers.IO) {
            preloadInstalledApps()
            repository.observeAllApps().collectLatest { apps ->
                _uiState.value = BlockedAppsUiState(apps = apps)
            }
        }
    }

    private suspend fun preloadInstalledApps() {
        val context = getApplication<Application>()
        val pm: PackageManager = context.packageManager
        val apps = pm.getInstalledApplications(PackageManager.GET_META_DATA)
            .filter { (it.flags and ApplicationInfo.FLAG_SYSTEM) == 0 }

        apps.forEach { appInfo ->
            val label = pm.getApplicationLabel(appInfo).toString()
            repository.setAppBlocked(
                packageName = appInfo.packageName,
                label = label,
                isBlocked = false
            )
        }
    }

    fun onToggleApp(app: BlockedApp, isBlocked: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.setAppBlocked(
                packageName = app.packageName,
                label = app.label,
                isBlocked = isBlocked
            )
        }
    }
}

