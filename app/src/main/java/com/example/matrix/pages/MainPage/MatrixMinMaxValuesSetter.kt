package com.example.matrix.pages.MainPage

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Link
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.matrix.viewModel.MainScreenViewModel

@Composable
fun MatrixMinMaxValuesSetter(viewModel: MainScreenViewModel) {
    val isRelated = rememberSaveable { mutableStateOf(false) }

    LaunchedEffect(isRelated.value, viewModel.states.value.matrixMinValue) {
        if (isRelated.value) {
            viewModel.setMatrixMaxValue((-viewModel.states.value.matrixMinValue).toString())
        }
    }

    Column(verticalArrangement = Arrangement.spacedBy(5.dp)) {
        Text(
            "Random number range",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.SemiBold
        )
        Row(horizontalArrangement = Arrangement.spacedBy(10.dp), verticalAlignment = Alignment.CenterVertically) {
            OutlinedTextField(
                modifier = Modifier.weight(1f),
                value = viewModel.states.value.matrixMinValue.toString(),
                onValueChange = viewModel::setMatrixMinValue,
                label = {
                    Text(text = "Min")
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
            OutlinedTextField(
                modifier = Modifier.weight(1f),
                value = viewModel.states.value.matrixMaxValue.toString(),
                onValueChange = viewModel::setMatrixMaxValue,
                label = {
                    Text(text = "Max")
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