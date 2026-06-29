package com.cypressit.vault.addCard.presentation.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
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
import com.cypressit.design.components.CyButton
import com.cypressit.design.components.CyOutlinedTextField
import com.cypressit.design.components.CyTopAppBar
import com.cypressit.vault.common.domain.model.CardType
import com.cypressit.vault.addCard.presentation.viewmodel.AddCardEffect
import com.cypressit.vault.addCard.presentation.viewmodel.AddCardIntent
import com.cypressit.vault.addCard.presentation.viewmodel.AddCardViewModel
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddCardScreen(
    onNavigateBack: () -> Unit,
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

            CyOutlinedTextField(
                value = state.cardNumber,
                onValueChange = { viewModel.onIntent(AddCardIntent.CardNumberChanged(it)) },
                label = "Card Number",
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            )

            CyOutlinedTextField(
                value = state.cvv2,
                onValueChange = { viewModel.onIntent(AddCardIntent.Cvv2Changed(it)) },
                label = "CVV2",
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            )

            CyOutlinedTextField(
                value = state.cardHolderName,
                onValueChange = { viewModel.onIntent(AddCardIntent.CardHolderNameChanged(it)) },
                label = "Card Holder Name",
                modifier = Modifier.fillMaxWidth(),
            )

            CyOutlinedTextField(
                value = state.bankName,
                onValueChange = { viewModel.onIntent(AddCardIntent.BankNameChanged(it)) },
                label = "Bank Name",
                modifier = Modifier.fillMaxWidth(),
            )

            CyOutlinedTextField(
                value = state.expiryDate,
                onValueChange = { viewModel.onIntent(AddCardIntent.ExpiryDateChanged(it)) },
                label = "Expiry Date (MM/YY)",
                placeholder = "MM/YY",
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            )

            CyOutlinedTextField(
                value = state.balance,
                onValueChange = { viewModel.onIntent(AddCardIntent.BalanceChanged(it)) },
                label = "Balance",
                prefix = { Text("€ ") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            )

            ExposedDropdownMenuBox(
                expanded = cardTypeExpanded,
                onExpandedChange = { cardTypeExpanded = it },
            ) {
                CyOutlinedTextField(
                    value = state.cardType.name,
                    onValueChange = {},
                    label = "Card Type",
                    readOnly = true,
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

            CyButton(
                text = "Save Card",
                onClick = { viewModel.onIntent(AddCardIntent.Submit) },
                isLoading = state.isLoading,
                modifier = Modifier.padding(vertical = 8.dp),
            )
        }
    }
}
