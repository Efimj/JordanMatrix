package com.example.mapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mapp.ui.theme.MAppTheme
import com.example.mapp.viewModel.SimpleMainScreenViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MAppTheme(darkTheme = true) {
                AppContent()
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    private fun AppContent(viewModel: SimpleMainScreenViewModel = viewModel()) {
        val sheetState = rememberBottomSheetScaffoldState()
        val scope = rememberCoroutineScope()

        BottomSheetScaffold(
            scaffoldState = sheetState,
            sheetContent = {
                SheetContent(viewModel)
            })
        {
            PageContent(scope, sheetState, viewModel)
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    private fun PageContent(
        scope: CoroutineScope,
        sheetState: BottomSheetScaffoldState,
        viewModel: SimpleMainScreenViewModel
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            Button(onClick = {
                scope.launch { sheetState.bottomSheetState.expand() }
            }) {
                Text("Open bottom sheet")
            }
            Spacer(modifier = Modifier.height(50.dp))
        }
    }

    @Composable
    private fun SheetContent(
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
}