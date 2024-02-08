package com.example.mapp.ui.pages

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mapp.ui.components.PageContent
import com.example.mapp.ui.components.SheetContent
import com.example.mapp.viewModel.SimpleMainScreenViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainPage(viewModel: SimpleMainScreenViewModel = viewModel()) {
    val sheetState = rememberBottomSheetScaffoldState()
    val scope = rememberCoroutineScope()

    BottomSheetScaffold(
        modifier = Modifier.fillMaxSize(),
        scaffoldState = sheetState,
        sheetContent = {
            SheetContent(viewModel)
        },
        containerColor = MaterialTheme.colorScheme.background
    )

    {
        PageContent(scope, sheetState, viewModel)
    }
}