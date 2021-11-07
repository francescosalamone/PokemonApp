package com.francescosalamone.pokemonapp.ui.component

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun ChartSlice(
    startAngle: Float,
    sweepAngle: Float,
    modifier: Modifier = Modifier,
    strokeSize: Dp = 20.dp,
    delay: Int = 0,
    duration: Int = 1000,
    color: Color
) {
    val animator = remember { Animatable(0f) }
    val deltaBg = 5 //Used to avoid small spaces between background slices

    LaunchedEffect(animator) {
        animator.animateTo(
            targetValue = sweepAngle,
            animationSpec = tween(
                durationMillis = duration,
                easing = FastOutLinearInEasing,
                delayMillis = delay
            )
        )
    }

    Canvas(
        modifier = modifier
            .fillMaxSize()
    ) {
        drawArc(
            color = Color.White,
            startAngle = startAngle,
            sweepAngle = animator.value+deltaBg,
            useCenter = true,
            topLeft = Offset(strokeSize.toPx()/2, strokeSize.toPx()/2),
            size = this.size.copy(this.size.width - strokeSize.toPx(), this.size.height - strokeSize.toPx())
        )

        drawArc(
            color = color,
            startAngle = startAngle,
            sweepAngle = animator.value,
            useCenter = false,
            style = Stroke(
                width = strokeSize.toPx(),
                cap = StrokeCap.Round
            )
        )
    }
}