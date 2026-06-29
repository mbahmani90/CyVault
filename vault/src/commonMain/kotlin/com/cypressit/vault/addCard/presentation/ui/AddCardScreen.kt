package com.cypressit.vault.addCard.presentation.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.cypressit.vault.common.domain.model.CardType
import com.cypressit.vault.addCard.presentation.viewmodel.AddCardEffect
import com.cypressit.vault.addCard.presentation.viewmodel.AddCardIntent
import com.cypressit.vault.addCard.presentation.viewmodel.AddCardViewModel
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddCardScreen(
    onBack: () -> Unit,
    onCardSaved: () -> Unit,
    viewModel: AddCardViewModel = koinViewModel(),
) {
    val state by viewModel.state.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    var cardTypeExpanded by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                AddCardEffect.CardSaved -> onCardSaved()
                is AddCardEffect.ShowError -> snackbarHostState.showSnackbar(effect.message)
            }
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            OutlinedTextField(
                value = state.bankName,
                onValueChange = { viewModel.onIntent(AddCardIntent.BankNameChanged(it)) },
                label = { Text("Bank Name") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
            )

            OutlinedTextField(
                value = state.cardHolderName,
                onValueChange = { viewModel.onIntent(AddCardIntent.CardHolderNameChanged(it)) },
                label = { Text("Card Holder Name") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
            )

            OutlinedTextField(
                value = state.cardNumber,
                onValueChange = { viewModel.onIntent(AddCardIntent.CardNumberChanged(it)) },
                label = { Text("Card Number") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            )

            OutlinedTextField(
                value = state.cvv2,
                onValueChange = { viewModel.onIntent(AddCardIntent.Cvv2Changed(it)) },
                label = { Text("CVV2") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            )

            OutlinedTextField(
                value = state.expiryDate,
                onValueChange = { viewModel.onIntent(AddCardIntent.ExpiryDateChanged(it)) },
                label = { Text("Expiry Date (MM/YY)") },
                placeholder = { Text("MM/YY") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            )

            OutlinedTextField(
                value = state.balance,
                onValueChange = { viewModel.onIntent(AddCardIntent.BalanceChanged(it)) },
                label = { Text("Balance") },
                prefix = { Text("€ ") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            )

            ExposedDropdownMenuBox(
                expanded = cardTypeExpanded,
                onExpandedChange = { cardTypeExpanded = it },
            ) {
                OutlinedTextField(
                    value = state.cardType.name,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Card Type") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = cardTypeExpanded) },
                    modifier = Modifier
                        .menuAnchor()
                        .fillMaxWidth(),
                )
                ExposedDropdownMenu(
                    expanded = cardTypeExpanded,
                    onDismissRequest = { cardTypeExpanded = false },
                ) {
                    CardType.entries.forEach { type ->
                        DropdownMenuItem(
                            text = { Text(type.name) },
                            onClick = {
                                viewModel.onIntent(AddCardIntent.CardTypeChanged(type))
                                cardTypeExpanded = false
                            }
                        )
                    }
                }
            }

            Button(
                onClick = { viewModel.onIntent(AddCardIntent.Submit) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                enabled = !state.isLoading,
            ) {
                if (state.isLoading) {
                    CircularProgressIndicator()
                } else {
                    Text("Save Card")
                }
            }
        }
    }
}
