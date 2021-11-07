package com.francescosalamone.pokemonapp.ui.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun TopBar(
    title: String,
    imageVector: ImageVector? = null,
    contentDescription: String? = null,
    onIconClick: () -> Unit = {}
) {
    Row(
        Modifier
            .height(56.dp)
    ) {
        imageVector?.let {
            IconButton(
                onClick = onIconClick,
                modifier = Modifier
                    .fillMaxHeight()
                    .align(Alignment.CenterVertically)
            ) {
                Icon(
                    imageVector = imageVector,
                    contentDescription = contentDescription
                )
            }
        }

        ProvideTextStyle(value = MaterialTheme.typography.h6) {
            Text(
                text = title,
                color = MaterialTheme.colors.onSurface,
                modifier = Modifier
                    .padding(
                        start = if (imageVector != null) 0.dp else 16.dp,
                        top = 0.dp,
                        end = 0.dp,
                        bottom = 0.dp
                    )
                    .align(Alignment.CenterVertically)
            )
        }
    }
}

@Preview
@Composable
private fun Preview() {
    TopBar(title = "Page", imageVector = Icons.Filled.ArrowBack, contentDescription = "") {

    }
}