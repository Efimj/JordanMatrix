package com.example.matrix.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.matrix.pages.MainPage.MatrixActions
import com.example.matrix.pages.MainPage.MatrixMinMaxValuesSetter
import com.example.matrix.pages.MainPage.MatrixSizeSetter
import com.example.matrix.viewModel.MainScreenViewModel

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
        verticalArrangement = Arrangement.spacedBy(15.dp)
    ) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = "Showcase",
            style = MaterialTheme.typography.headlineLarge,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold
        )
        MatrixSizeSetter(viewModel)
        MatrixMinMaxValuesSetter(viewModel)
        MatrixActions(viewModel, sheetState)
    }
}