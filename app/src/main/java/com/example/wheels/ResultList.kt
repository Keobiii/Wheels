package com.example.wheels

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

data class GameItem(val gameNo: Int, val result: String)

@Preview
@Composable
fun GameListWithAdd() {
    val games = remember {
        mutableStateOf(
            listOf(
                GameItem(1, "Higher"),
                GameItem(2, "Lower")
            )
        )
    }

    Column {
        Button(onClick = {
            val newGameNo = games.value.size + 1
            val newResult = if (newGameNo % 2 == 0) "Lower" else "Higher"
            games.value += GameItem(newGameNo, newResult)
        }) {
            Text("Add Game")
        }

        // Display the list using LazyColumn
        LazyColumn {
            items(games.value) { game ->
                Text(
                    text = "Game ${game.gameNo}: ${game.result}",
                    modifier = Modifier.padding(8.dp)
                )
            }
        }
    }
}
