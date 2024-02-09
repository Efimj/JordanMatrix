package com.example.mapp.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.text.TextStyle


/**
 * @text ➜ The complete text that will be gradually revealed as if being typed.
 * @modifier ➜ The Modifier to be applied to the layout.
 * @isVisible ➜ Determines if the text should be visible or not. When set to true, the text gradually appears; when false, it stays hidden.
 * @continuing ➜ Can continue animation when text changes. Will continue from an index equal to the previous text length.
 * @spec ➜ Specifies the animation specifications. By default, it’s a linear animation with a duration proportional to the text length, revealing each symbol in 100 milliseconds.
 * @style ➜ Defines the style of the text. The default value is set to the current local text style.
 * @preoccupySpace ➜ Defines whether to preoccupy space during animation. If true, the space needed for text will be preoccupied before the animation to avoid changes during the animation.
 */
@Composable
fun TypewriteText(
    text: String,
    modifier: Modifier = Modifier,
    skipAnimation: Boolean,
    isVisible: Boolean = true,
    continuing: Boolean = true,
    spec: AnimationSpec<Int> = tween(durationMillis = text.length * 100, easing = LinearEasing),
    style: TextStyle = LocalTextStyle.current,
    preoccupySpace: Boolean = true
) {
    // State that keeps the text that is currently animated
    var textToAnimate by remember { mutableStateOf("") }

    // State to keep track of the previous text length
    var previousTextLength by remember { mutableStateOf(0) }

    // Animatable index to control the progress of the animation
    val index = remember {
        Animatable(initialValue = 0, typeConverter = Int.VectorConverter)
    }

    // Effect to handle animation when visibility changes
    LaunchedEffect(isVisible) {
        if (isVisible) {
            // Start animation if visible
            textToAnimate = text
            index.animateTo(text.length, spec)
        } else {
            // Snap to the beginning if not visible
            index.snapTo(0)
        }
    }

    // Effect to handle animation when text content changes
    LaunchedEffect(text) {
        if (isVisible) {
            // Reset animation and update text if visible
            val startIndex = previousTextLength.coerceAtMost(text.length)
            index.snapTo(startIndex)
            textToAnimate = text
            index.animateTo(text.length, spec)
            if (continuing)
                previousTextLength = text.length
        }
    }

    // Effect to handle animation when text content changes
    LaunchedEffect(skipAnimation, text) {
        if (isVisible && skipAnimation) {
            // Reset animation and update text if visible
            index.snapTo(text.length)
            if (continuing)
                previousTextLength = text.length
        }
    }

    // Box composable to contain the animated and static text
    Box(modifier = modifier) {
        if (preoccupySpace && index.isRunning) {
            // Display invisible text when preoccupation is turned on
            // and the animation is in progress.
            // Plays the role of a placeholder to occupy the space
            // that will be filled with text.
            Text(
                text = text,
                style = style,
                modifier = Modifier.alpha(0f)
            )
        }

        // Display animated text based on the current index value
        Text(
            text = textToAnimate.substring(0, index.value),
            style = style
        )
    }
}