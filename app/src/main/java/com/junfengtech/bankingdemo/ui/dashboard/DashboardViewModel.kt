package com.junfengtech.bankingdemo.ui.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class DashboardViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(
        DashboardUiState(
            transactions = listOf(
                TransactionUiModel(
                    id = "tx_salary",
                    title = "Salary",
                    subtitle = "Today",
                    amount = "+€3,200.00"
                ),
                TransactionUiModel(
                    id = "tx_rent",
                    title = "Rent",
                    subtitle = "Yesterday",
                    amount = "-€1,250.00"
                ),
                TransactionUiModel(
                    id = "tx_rewe",
                    title = "REWE",
                    subtitle = "May 22",
                    amount = "-€46.80"
                ),
                TransactionUiModel(
                    id = "tx_netflix",
                    title = "Netflix",
                    subtitle = "May 20",
                    amount = "-€12.99"
                )
            )
        )
    )

    val uiState: StateFlow<DashboardUiState> = _uiState.asStateFlow()
    private val _event = MutableSharedFlow<DashboardEvent>()
    val event: SharedFlow<DashboardEvent> = _event.asSharedFlow()

    fun onAction(action: DashboardAction) {
        when (action) {
            DashboardAction.LogoutClicked -> {
                logout()
            }

            is DashboardAction.TransactionClicked -> {
                openTransactionDetail(action.transactionId)
            }
        }
    }
    private fun openTransactionDetail(transactionId: String) {
        viewModelScope.launch {
            _event.emit(
                DashboardEvent.NavigateToTransactionDetail(
                    transactionId = transactionId
                )
            )
        }
    }

    private fun logout() {
        viewModelScope.launch {
            // Later:
            // authRepository.logout()
            // sessionManager.clear()
            _event.emit(DashboardEvent.LoggedOut)
        }
    }
}