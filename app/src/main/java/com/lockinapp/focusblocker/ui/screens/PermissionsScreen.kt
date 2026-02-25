package com.lockinapp.focusblocker.ui.screens

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.lockinapp.focusblocker.util.PermissionUtils

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PermissionsScreen(
    onBack: () -> Unit
) {
    val context = LocalContext.current
    var hasUsageAccess by remember {
        mutableStateOf(PermissionUtils.hasUsageStatsPermission(context))
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Permissions") }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(24.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = "To block apps during a focus session, Lockin needs access to see which app is currently open.",
                style = MaterialTheme.typography.bodyMedium
            )

            Text(
                text = if (hasUsageAccess) {
                    "Usage access is enabled."
                } else {
                    "Usage access is not enabled yet."
                },
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(top = 16.dp)
            )

            Button(
                onClick = { openUsageAccessSettings(context) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 24.dp)
            ) {
                Text("Open usage access settings")
            }

            Button(
                onClick = {
                    hasUsageAccess = PermissionUtils.hasUsageStatsPermission(context)
                    if (hasUsageAccess) {
                        onBack()
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
            ) {
                Text("I have granted access")
            }

            Text(
                text = "You can optionally enable an Accessibility service later for more precise detection.",
                style = MaterialTheme.typography.bodySmall,
                textAlign = TextAlign.Start,
                modifier = Modifier.padding(top = 24.dp)
            )
        }
    }
}

private fun openUsageAccessSettings(context: Context) {
    val intent = PermissionUtils.usageAccessSettingsIntent(context)
    context.startActivity(intent)
}

