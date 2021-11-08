package com.francescosalamone.pokemonapp.ui.component

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.francescosalamone.pokemonapp.model.dto.Pokemon

@Composable
fun Chips(
    items: List<Pokemon.TypeWrapper.TypeDetail.Type>,
    modifier: Modifier = Modifier
) {
    val chipSize = 80.dp
    val marginBetweenChips = 6.dp
    val textPadding = 4.dp

    BoxWithConstraints {

        val columns = (this.maxWidth / (chipSize + marginBetweenChips*2 + textPadding*2)).toInt()
        val chunkedList = items.chunked(if(columns < 1) 1 else columns)

        Column(
            modifier = modifier
        ) {
            chunkedList.forEach { chunk ->
                Row(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                ) {
                    chunk.forEach {
                        Surface(
                            color = Color(it.color),
                            shape = RoundedCornerShape(25.dp),
                            elevation = 4.dp,
                            modifier = Modifier
                                .align(Alignment.CenterVertically)
                                .padding(marginBetweenChips)
                        ) {
                            Text(
                                text = it.name.lowercase().capitalize(Locale.current),
                                color = Color(it.textColor),
                                textAlign = TextAlign.Center,
                                modifier = Modifier
                                    .padding(textPadding)
                                    .sizeIn(minWidth = chipSize)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun PreviewChips() {
    Chips(
        items = listOf(
            Pokemon.TypeWrapper.TypeDetail.Type.FIRE,
            Pokemon.TypeWrapper.TypeDetail.Type.BUG,
            Pokemon.TypeWrapper.TypeDetail.Type.ELECTRIC,
            Pokemon.TypeWrapper.TypeDetail.Type.GHOST,
            Pokemon.TypeWrapper.TypeDetail.Type.GROUND
        )
    )
}