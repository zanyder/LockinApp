package com.lockinapp.focusblocker.ui.blocker

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.lockinapp.focusblocker.MainActivity
import com.lockinapp.focusblocker.ui.theme.LockinFocusTheme
import androidx.lifecycle.lifecycleScope
import com.lockinapp.focusblocker.data.Graph
import com.lockinapp.focusblocker.domain.usecase.EndFocusSessionUseCase
import kotlinx.coroutines.launch

class BlockerActivity : ComponentActivity() {

    private val endSessionUseCase by lazy {
        EndFocusSessionUseCase(
            sessionRepository = Graph.sessionRepository,
            context = this
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            LockinFocusTheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    BlockerScreen(
                        onGoHome = { navigateHome() },
                        onEndSessionAnyway = { endSessionAndNavigateHome() }
                    )
                }
            }
        }
    }

    private fun navigateHome() {
        val intent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
        }
        startActivity(intent)
        finish()
    }

    private fun endSessionAndNavigateHome() {
        lifecycleScope.launch {
            endSessionUseCase(successful = false)
            navigateHome()
        }
    }
}

@Composable
fun BlockerScreen(
    onGoHome: () -> Unit,
    onEndSessionAnyway: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Stay focused",
            style = MaterialTheme.typography.headlineMedium,
            textAlign = TextAlign.Center
        )

        Text(
            text = "This app is blocked during your current focus session.",
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(top = 16.dp)
        )

        Button(
            onClick = onGoHome,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 32.dp)
        ) {
            Text("Back to home")
        }

        Button(
            onClick = onEndSessionAnyway,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
        ) {
            Text("End session anyway")
        }
    }
}

