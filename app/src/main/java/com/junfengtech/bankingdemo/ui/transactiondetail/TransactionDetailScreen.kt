package com.junfengtech.bankingdemo.ui.transactiondetail

import com.junfengtech.bankingdemo.ui.theme.BankingDemoTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.junfengtech.bankingdemo.domain.repository.TransactionRepository

@Composable
fun TransactionDetailScreen(
    onBack: () -> Unit,
    transactionRepository: TransactionRepository
) {
    val viewModel: TransactionDetailViewModel = viewModel(
        factory = TransactionDetailViewModelFactory(
            repository = transactionRepository
        )
    )

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    TransactionDetailScreenContent(
        uiState = uiState,
        onBack = onBack
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TransactionDetailScreenContent(
    uiState: TransactionDetailUiState,
    onBack: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Transaction Detail")
                },
                navigationIcon = {
                    IconButton(
                        onClick = onBack
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        TransactionDetailBody(
            uiState = uiState,
            modifier = Modifier.padding(innerPadding)
        )
    }
}
@Composable
private fun TransactionDetailBody(
    uiState: TransactionDetailUiState,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center
    ) {
        when {
            uiState.isLoading -> {
                Text(
                    text = "Loading transaction...",
                    style = MaterialTheme.typography.bodyLarge
                )
            }

            uiState.errorMessage.isNotEmpty() -> {
                Text(
                    text = uiState.errorMessage,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodyLarge
                )
            }

            else -> {
                TransactionDetailCard(uiState = uiState)
            }
        }
    }
}

@Composable
private fun TransactionDetailCard(
    uiState: TransactionDetailUiState
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(24.dp)
        ) {
            Text(
                text = uiState.title,
                style = MaterialTheme.typography.headlineSmall
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = uiState.amount,
                style = MaterialTheme.typography.headlineMedium
            )

            Spacer(modifier = Modifier.height(24.dp))

            DetailRow(label = "Date", value = uiState.date)
            DetailRow(label = "Status", value = uiState.status)
            DetailRow(label = "Merchant", value = uiState.merchant)
            DetailRow(label = "Category", value = uiState.category)
            DetailRow(label = "Transaction ID", value = uiState.transactionId)
        }
    }
}

@Composable
private fun DetailRow(
    label: String,
    value: String
) {
    Column(
        modifier = Modifier.padding(vertical = 8.dp)
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall
        )

        Text(
            text = value,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

@Preview(showBackground = true)
@Composable
fun TransactionDetailScreenPreview() {
    BankingDemoTheme {
        TransactionDetailScreenContent(
            uiState = TransactionDetailUiState(
                transactionId = "tx_rewe",
                title = "REWE",
                date = "May 22",
                amount = "-€46.80",
                status = "Completed",
                merchant = "REWE Markt",
                category = "Groceries"
            ),
            onBack = {}
        )
    }
}