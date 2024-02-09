package com.example.mapp.pages.MainPage

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.mapp.viewModel.MainScreenViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MatrixActions(
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
        Row(horizontalArrangement = Arrangement.spacedBy(5.dp)) {
            Button(
                modifier = Modifier.weight(1f),
                onClick = { viewModel.inverseRandomMatrix(); scope.launch { sheetState.bottomSheetState.expand() } }) {
                Text("Inverse the matrix")
            }
            Button(
                modifier = Modifier.weight(1f),
                onClick = { viewModel.getRandomMatrixRank(); scope.launch { sheetState.bottomSheetState.expand() } }) {
                Text("Get matrix rank")
            }
        }
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = { viewModel.getSolveRandomMatrix(); scope.launch { sheetState.bottomSheetState.expand() } }) {
            Text("Solve random matrix")
        }
    }
}