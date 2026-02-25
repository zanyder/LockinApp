package com.lockinapp.focusblocker.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ListItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.lockinapp.focusblocker.viewmodel.BlockedAppsUiState
import com.lockinapp.focusblocker.viewmodel.BlockedAppsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BlockedAppsScreen(
    onBack: () -> Unit,
    viewModel: BlockedAppsViewModel = viewModel()
) {
    val state by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Blocked apps") }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            AppsList(
                state = state,
                onToggle = viewModel::onToggleApp
            )
        }
    }
}

@Composable
private fun AppsList(
    state: BlockedAppsUiState,
    onToggle: (app: com.lockinapp.focusblocker.domain.model.BlockedApp, Boolean) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 8.dp)
    ) {
        items(state.apps, key = { it.packageName }) { app ->
            ListItem(
                headlineContent = { Text(app.label) },
                supportingContent = { Text(app.packageName) },
                trailingContent = {
                    Checkbox(
                        checked = app.isBlocked,
                        onCheckedChange = { onToggle(app, it) }
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
            )
        }
    }
}

