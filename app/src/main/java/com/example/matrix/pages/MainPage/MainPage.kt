package com.example.matrix.pages.MainPage

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.matrix.ui.components.PageContent
import com.example.matrix.viewModel.MainScreenViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainPage(viewModel: MainScreenViewModel = viewModel()) {
    val sheetState =
        rememberBottomSheetScaffoldState()

    BottomSheetScaffold(
        modifier = Modifier.fillMaxSize(),
        scaffoldState = sheetState,
        sheetContent = {
            SheetContent(viewModel)
        },
        containerColor = MaterialTheme.colorScheme.background
    )

    {
        PageContent(sheetState, viewModel)
    }
}