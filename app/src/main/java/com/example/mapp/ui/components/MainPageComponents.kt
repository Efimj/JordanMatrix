package com.example.mapp.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.mapp.viewModel.SimpleMainScreenViewModel
import kotlinx.coroutines.CoroutineScope

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PageContent(
    scope: CoroutineScope,
    sheetState: BottomSheetScaffoldState,
    viewModel: SimpleMainScreenViewModel
) {
    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        MatrixSizeSetter(viewModel)
    }
}

@Composable
private fun MatrixSizeSetter(viewModel: SimpleMainScreenViewModel) {
    Column(verticalArrangement = Arrangement.spacedBy(5.dp)) {
        Text(
            "Set the matrix",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.SemiBold
        )
        Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
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
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
        }
    }
}

@Composable
fun SheetContent(
    viewModel: SimpleMainScreenViewModel
) {
    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .padding(bottom = 20.dp)
            .padding(horizontal = 20.dp)
    ) {
        Text("Output", style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.SemiBold)
        Text(viewModel.states.value.output)
    }
}