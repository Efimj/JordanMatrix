package com.example.mapp.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Link
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.mapp.viewModel.MainScreenViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PageContent(
    sheetState: BottomSheetScaffoldState,
    viewModel: MainScreenViewModel
) {
    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        MatrixSizeSetter(viewModel)
        MatrixActions(viewModel, sheetState)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun MatrixActions(
    viewModel: MainScreenViewModel,
    sheetState: BottomSheetScaffoldState,
) {
    val scope = rememberCoroutineScope()

    Column(verticalArrangement = Arrangement.spacedBy(5.dp)) {
        Text(
            "Actions",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.SemiBold
        )
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = { viewModel.inverseRandomMatrix(); scope.launch { sheetState.bottomSheetState.expand() } }) {
            Text("Inverse the matrix")
        }
    }
}

@Composable
private fun MatrixSizeSetter(viewModel: MainScreenViewModel) {
    val isRelated = rememberSaveable { mutableStateOf(false) }

    LaunchedEffect(isRelated.value, viewModel.states.value.matrixRows) {
        if (isRelated.value) {
            viewModel.setMatrixColumns(viewModel.states.value.matrixRows.toString())
        }
    }

    Column(verticalArrangement = Arrangement.spacedBy(5.dp)) {
        Text(
            "Set the matrix",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.SemiBold
        )
        Row(horizontalArrangement = Arrangement.spacedBy(10.dp), verticalAlignment = Alignment.CenterVertically) {
            OutlinedTextField(
                modifier = Modifier.weight(1f),
                value = viewModel.states.value.matrixRows.toString(),
                onValueChange = viewModel::setMatrixRows,
                label = {
                    Text(text = "Rows")
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
            OutlinedTextField(
                modifier = Modifier.weight(1f),
                value = viewModel.states.value.matrixColumns.toString(),
                onValueChange = viewModel::setMatrixColumns,
                label = {
                    Text(text = "Columns")
                },
                enabled = !isRelated.value,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
            Switch(
                checked = isRelated.value,
                onCheckedChange = { isRelated.value = !isRelated.value },
                thumbContent = if (isRelated.value) {
                    {
                        Icon(
                            imageVector = Icons.Outlined.Link,
                            contentDescription = null,
                            modifier = Modifier.size(SwitchDefaults.IconSize),
                        )
                    }
                } else {
                    null
                }
            )
        }
    }
}

@Composable
fun SheetContent(
    viewModel: MainScreenViewModel
) {
    Box(
        contentAlignment = Alignment.BottomCenter
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
                .padding(bottom = 70.dp)
                .padding(horizontal = 20.dp)
        ) {
            Text("Output", style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.SemiBold)
            Text(viewModel.states.value.output)
        }
        Box(
            modifier = Modifier
                .padding(bottom = 10.dp)
                .padding(horizontal = 20.dp),
            contentAlignment = Alignment.BottomCenter
        ) {
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = { viewModel.clearOutput(); }) {
                Text("Clear")
            }
        }
    }
}