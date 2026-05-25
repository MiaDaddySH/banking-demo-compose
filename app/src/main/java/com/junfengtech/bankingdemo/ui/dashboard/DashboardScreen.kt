package com.junfengtech.bankingdemo.ui.dashboard

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.junfengtech.bankingdemo.ui.theme.BankingDemoTheme

@Composable
fun DashboardScreen(
    onLogout: () -> Unit,
    onTransactionClick: (String) -> Unit,
    viewModel: DashboardViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.event.collect { event ->
            when (event) {
                DashboardEvent.LoggedOut -> {
                    onLogout()
                }

                is DashboardEvent.NavigateToTransactionDetail -> {
                    onTransactionClick(event.transactionId)
                }
            }
        }
    }

    DashboardScreenContent(
        uiState = uiState,
        onAction = viewModel::onAction
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreenContent(
    uiState: DashboardUiState,
    onAction: (DashboardAction) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Banking Demo")
                },
                actions = {
                    TextButton(
                        onClick = {
                            onAction(DashboardAction.LogoutClicked)
                        }
                    ) {
                        Text("Logout")
                    }
                }
            )
        }
    ) { innerPadding ->
        DashboardBody(
            uiState = uiState,
            onAction = onAction,
            modifier = Modifier.padding(innerPadding)
        )
    }
}

@Composable
private fun DashboardBody(
    uiState: DashboardUiState,
    onAction: (DashboardAction) -> Unit,
    modifier: Modifier = Modifier
) {
    when {
        uiState.isLoading -> {
            Column(
                modifier = modifier
                    .fillMaxSize()
                    .padding(24.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Loading dashboard...",
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }

        uiState.errorMessage.isNotEmpty() -> {
            Column(
                modifier = modifier
                    .fillMaxSize()
                    .padding(24.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = uiState.errorMessage,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }

        else -> {
            DashboardContent(
                uiState = uiState,
                onAction = onAction,
                modifier = modifier
            )
        }
    }
}

@Composable
private fun DashboardContent(
    uiState: DashboardUiState,
    onAction: (DashboardAction) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            DashboardHeader(
                userName = uiState.userName
            )
        }

        item {
            BalanceCard(
                balance = uiState.balance,
                accountName = uiState.accountName
            )
        }

        item {
            QuickActions()
        }

        item {
            Text(
                text = "Recent transactions",
                style = MaterialTheme.typography.titleMedium
            )
        }

        items(uiState.transactions) { transaction ->
            TransactionRow(
                transaction = transaction,
                onClick = {
                    onAction(
                        DashboardAction.TransactionClicked(
                            transactionId = transaction.id
                        )
                    )
                }
            )
        }
    }
}

@Composable
private fun DashboardHeader(
    userName: String
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = "Good morning",
                style = MaterialTheme.typography.bodyMedium
            )

            Text(
                text = userName,
                style = MaterialTheme.typography.headlineMedium
            )
        }
    }
}

@Composable
private fun BalanceCard(
    balance: String,
    accountName: String
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(24.dp)
        ) {
            Text(
                text = accountName,
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = balance,
                style = MaterialTheme.typography.headlineLarge
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Available balance",
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}

@Composable
private fun QuickActions() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Button(
            onClick = {},
            modifier = Modifier.weight(1f)
        ) {
            Text("Transfer")
        }

        OutlinedButton(
            onClick = {},
            modifier = Modifier.weight(1f)
        ) {
            Text("Pay")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DashboardScreenPreview() {
    BankingDemoTheme {
        DashboardScreenContent(
            uiState = DashboardUiState(
                userName = "Alex",
                accountName = "Main Checking Account",
                balance = "€8,420.50",
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
                    )
                )
            ),
            onAction = {}
        )
    }
}