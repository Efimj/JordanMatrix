package com.example.mapp.pages.MainPage

import androidx.compose.animation.*
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.mapp.ui.components.TypewriteText
import com.example.mapp.viewModel.MainScreenViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun SheetContent(
    viewModel: MainScreenViewModel
) {
    val scope = rememberCoroutineScope()
    val defaultTextAnimation = tween<Int>(
        durationMillis = viewModel.states.value.output.length * 10,
        easing = LinearEasing
    )
    val skipAnimation = remember { mutableStateOf(false) }

    Box(
        modifier = Modifier.fillMaxWidth(),
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
            TypewriteText(
                text = viewModel.states.value.output,
                modifier = Modifier.animateContentSize(),
                spec = defaultTextAnimation,
                preoccupySpace = false,
                skipAnimation = skipAnimation.value
            )
        }
        AnimatedVisibility(
            visible = viewModel.states.value.output.isNotBlank(),
            enter = slideInVertically { it / 2 } + expandVertically(
                expandFrom = Alignment.Top,
                clip = false
            ) + fadeIn(),
            exit = slideOutVertically { -it / 2 } + shrinkVertically(
                shrinkTowards = Alignment.Top,
                clip = false
            ) + fadeOut(),
        ) {
            Box(
                modifier = Modifier
                    .padding(bottom = 10.dp)
                    .padding(horizontal = 20.dp),
                contentAlignment = Alignment.BottomCenter
            ) {
                Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    Button(
                        modifier = Modifier.weight(1f),
                        onClick = { viewModel.clearOutput(); }) {
                        Text("Clear")
                    }
                    OutlinedButton(
                        onClick = {
                            skipAnimation.value = true
                            scope.launch {
                                delay(100)
                                skipAnimation.value = false
                            }
                        }) {
                        Text("Skip")
                    }
                }
            }
        }
    }
}
