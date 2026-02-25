package com.lockinapp.focusblocker.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lockinapp.focusblocker.data.Graph
import com.lockinapp.focusblocker.domain.model.FocusSession
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

data class HomeUiState(
    val activeSession: FocusSession? = null
)

class HomeViewModel : ViewModel() {

    private val sessionRepository = Graph.sessionRepository

    val uiState: MutableState<HomeUiState> = mutableStateOf(HomeUiState())

    init {
        viewModelScope.launch {
            sessionRepository.observeActiveSession().collectLatest { session ->
                uiState.value = uiState.value.copy(activeSession = session)
            }
        }
    }
}

