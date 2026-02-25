package com.lockinapp.focusblocker.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.lockinapp.focusblocker.data.Graph
import com.lockinapp.focusblocker.domain.usecase.StartFocusSessionUseCase
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SessionSetupScreen(
    onBack: () -> Unit,
    onSessionStarted: () -> Unit
) {
    var selectedDurationMinutes by remember { mutableStateOf(25) }
    var isStrict by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Start focus session") }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(24.dp)
        ) {
            Text(text = "Duration")

            DurationOption(
                label = "25 minutes",
                selected = selectedDurationMinutes == 25,
                onClick = { selectedDurationMinutes = 25 }
            )
            DurationOption(
                label = "50 minutes",
                selected = selectedDurationMinutes == 50,
                onClick = { selectedDurationMinutes = 50 }
            )
            DurationOption(
                label = "90 minutes",
                selected = selectedDurationMinutes == 90,
                onClick = { selectedDurationMinutes = 90 }
            )

            Text(
                text = "Mode",
                modifier = Modifier.padding(top = 24.dp)
            )

            DurationOption(
                label = "Flexible (you can end early)",
                selected = !isStrict,
                onClick = { isStrict = false }
            )
            DurationOption(
                label = "Strict (no early exit)",
                selected = isStrict,
                onClick = { isStrict = true }
            )

            Button(
                onClick = {
                    val useCase = StartFocusSessionUseCase(
                        sessionRepository = Graph.sessionRepository,
                        context = context
                    )
                    scope.launch {
                        useCase(
                            durationMillis = selectedDurationMinutes * 60_000L,
                            isStrict = isStrict
                        )
                        onSessionStarted()
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 32.dp)
            ) {
                Text("Start session")
            }
        }
    }
}

@Composable
private fun DurationOption(
    label: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    androidx.compose.foundation.layout.Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
    ) {
        RadioButton(
            selected = selected,
            onClick = onClick
        )
        Text(
            text = label,
            modifier = Modifier.padding(start = 8.dp)
        )
    }
}

