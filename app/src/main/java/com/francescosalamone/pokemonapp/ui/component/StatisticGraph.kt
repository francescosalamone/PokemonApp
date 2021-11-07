package com.francescosalamone.pokemonapp.ui.component

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.francescosalamone.pokemonapp.model.dto.Pokemon
import timber.log.Timber

class CircleAnimator(
    val startAngle: Float,
    val endAngle: Float,
    val color: Color,
    val animation: Animatable<Float, AnimationVector1D>
)

@Composable
fun StatisticGraph(
    items: List<Pokemon.StatWrapper>,
    modifier: Modifier = Modifier
) {
    val totalStats = items.sumOf { it.baseStat ?: 0 }.toFloat()
    val startAngle = -90F
    val defaultColor = 0xFFFF0000


    ConstraintLayout(
        modifier = modifier
    ) {
        val (canvas, legend) = createRefs()

        if(totalStats > 0) {
            items.foldIndexed(0) { index, accumulator, item ->
                val startSliceAngle = accumulator.times(360f).div(totalStats) + startAngle
                val sliceSweepAngle = (item.baseStat ?: 0).times(360f).div(totalStats)
                Timber.d("Chart slice for item $index from $startSliceAngle to $sliceSweepAngle")

                val constraintRef = if (index > 0) {
                    createRef()
                } else {
                    canvas
                }

                ChartSlice(
                    startAngle = startSliceAngle,
                    sweepAngle = sliceSweepAngle,
                    color = Color(item.stat?.type?.color ?: defaultColor),
                    duration = 800,
                    modifier = Modifier
                        .constrainAs(constraintRef) {
                            linkTo(
                                top = parent.top,
                                bottom = parent.bottom,
                                start = parent.start,
                                end = parent.end
                            )
                        }
                )
                accumulator + (item.baseStat ?: 0)
            }
        }

        Column(
            modifier = Modifier
                .constrainAs(legend) {
                    linkTo(
                        top = canvas.top,
                        bottom = canvas.bottom,
                        end = canvas.end,
                        start = canvas.start
                    )
                }
        ) {
            items.forEach {
                Row(
                    modifier = Modifier
                ) {
                    Box(
                        modifier = Modifier
                            .background(
                                color = Color(it.stat?.type?.color ?: defaultColor),
                                shape = CircleShape
                            )
                            .size(8.dp)
                            .align(Alignment.CenterVertically)
                    )

                    Text(
                        text = it.stat?.type?.name?.normalizeStat(it.baseStat) ?: "Unknown",
                        color = Color.Black,
                        fontSize = 14.sp,
                        modifier = Modifier
                            .padding(
                                top = 2.dp,
                                start = 6.dp,
                                bottom = 2.dp,
                                end = 0.dp
                            )
                    )
                }
            }
        }
    }
}

private fun String.normalizeStat(value: Int?): String {
    return lowercase().capitalize(Locale.current).replace("_", " ").plus(": $value")
}

@Preview
@Composable
private fun PreviewStatistics() {
    StatisticGraph(items = listOf(
        Pokemon.StatWrapper(
            effort = 3,
            baseStat = 15,
            stat = Pokemon.StatWrapper.StatDetail(
                type = Pokemon.StatWrapper.StatDetail.Stat.ACCURACY
            )
        ),
        Pokemon.StatWrapper(
            effort = 1,
            baseStat = 25,
            stat = Pokemon.StatWrapper.StatDetail(
                type = Pokemon.StatWrapper.StatDetail.Stat.ATTACK
            )
        ),
        Pokemon.StatWrapper(
            effort = 0,
            baseStat = 6,
            stat = Pokemon.StatWrapper.StatDetail(
                type = Pokemon.StatWrapper.StatDetail.Stat.DEFENCE
            )
        ),
        Pokemon.StatWrapper(
            effort = 8,
            baseStat = 32,
            stat = Pokemon.StatWrapper.StatDetail(
                type = Pokemon.StatWrapper.StatDetail.Stat.SPEED
            )
        )
    ))
}