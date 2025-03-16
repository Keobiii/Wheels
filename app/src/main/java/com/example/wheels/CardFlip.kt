package com.example.wheels

import android.util.Log
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gamble.utils.FlipCard
import com.example.wheels.Dialog.CustomCardDialog
import com.example.wheels.ui.theme.pageBackground
import kotlinx.coroutines.delay

data class CardData(val imageRes: Int, val value: Int)


fun RandomImagePicker(): Pair<CardData, CardData> {
    val cardMap = mapOf(
        R.drawable.ace_of_clubs to 1,
        R.drawable.two_of_clubs to 2,
        R.drawable.three_of_clubs to 3,
        R.drawable.four_of_clubs to 4,
        R.drawable.five_of_clubs to 5,
        R.drawable.six_of_clubs to 6,
        R.drawable.seven_of_clubs to 7,
        R.drawable.eight_of_clubs to 8,
        R.drawable.nine_of_clubs to 9,
        R.drawable.ten_of_diamond to 10,
        R.drawable.jack_of_clubs to 11,
        R.drawable.queen_of_diamond to 12,
        R.drawable.king_of_clubs to 13
    )

    // Get a list of all entries and shuffle it
    val shuffledEntries = cardMap.entries.shuffled()

    // Pick the first two distinct entries
    val firstEntry = shuffledEntries[0]
    val secondEntry = shuffledEntries[1]

    return Pair(
        CardData(imageRes = firstEntry.key, value = firstEntry.value),
        CardData(imageRes = secondEntry.key, value = secondEntry.value)
    )
}

@Composable
fun CardFlip() {
    var flipCard1 by remember { mutableStateOf(FlipCard.Previous) }
    var flipCard2 by remember { mutableStateOf(FlipCard.Forward) }
    var cardData by remember { mutableStateOf(RandomImagePicker()) }
    var resetRequested by remember { mutableStateOf(false) }
    var betButton by remember { mutableStateOf(true) }
    var userPick by remember { mutableStateOf("") }
    var showDialog by remember { mutableStateOf(false) }

    var firstCardVal by remember { mutableStateOf(0) }
    var secondCardVal by remember { mutableStateOf(0) }

    var gameStatus by remember { mutableStateOf("") }
    var gameStatusDescription by remember { mutableStateOf("") }

    var trialCount by remember { mutableStateOf(3) }

    firstCardVal = cardData.first.value
    secondCardVal = cardData.second.value


    if (flipCard1 == FlipCard.Previous) {
        Log.d("CardFlip 1", "Card Value: ${cardData.first.value}")
        Log.d("CardFlip 2", "Card Value: ${cardData.second.value}")
    }

    LaunchedEffect(flipCard1, resetRequested) {
        if (resetRequested && flipCard1 == FlipCard.Forward && flipCard2 == FlipCard.Forward) {
            delay(2000)
            cardData = RandomImagePicker()
            flipCard1 = FlipCard.Previous
            betButton = true
            resetRequested = false
            Log.d("CardFlip 1", "New Card Value: ${cardData.first.value}")
            Log.d("CardFlip 2", "New Card Value: ${cardData.second.value}")
        }
    }

    fun betButtonClick(){
        betButton = false

        if (trialCount <= 0 || trialCount >= 4) {
            gameStatus = "Inefficient Balance"
            gameStatusDescription = "Please make you have enough balance to play this game!"
            betButton = true
        } else {


            flipCard2 = flipCard2.next

            if (userPick == "lower" && firstCardVal > secondCardVal || userPick == "higher" && firstCardVal < secondCardVal) {
                gameStatus = "You Won"
                gameStatusDescription = "Trial Count: ${trialCount} :  Bet Val: ${userPick} :  First Card Val: ${firstCardVal} : Second Card Val: ${secondCardVal}"
            } else{
                gameStatus = "You Lost"
                gameStatusDescription = "Trial Count: ${trialCount} :  Bet Val: ${userPick} :  First Card Val: ${firstCardVal} : Second Card Val: ${secondCardVal}"
                trialCount - 1
            }
        }



        showDialog = true
        trialCount--
    }



    if (showDialog) {
        CustomCardDialog(
            onDismiss = { showDialog = false },
            fontFamily = FontFamily.SansSerif,
            title = gameStatus,
            description = gameStatusDescription
        )
    }

    fun resetGame() {
        if (trialCount <= 0 || trialCount >= 4) {
            gameStatus = "Inefficient Balance"
            gameStatusDescription = "Please make you have enough balance to play this game!"

            showDialog = true

        } else {
            flipCard1 = FlipCard.Forward
            flipCard2 = FlipCard.Forward
            resetRequested = true
        }

    }

    fun resetTrial() {
        trialCount = 3
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.LightGray),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(0.95f)
                .fillMaxHeight(0.50f)
                .background(Color.Transparent)
                .border(1.dp, Color.Red),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Card(
                    modifier = Modifier
                        .width(100.dp)
                        .height(150.dp),
                ) {
                    FlipRotate(
                        flipCard = flipCard1,
                        forward = {
                            FlipCardFace(R.drawable.back, "Forward")
                        },
                        previous = {
                            FlipCardFace(cardData.first.imageRes, "Previous")
                        }
                    )
                }

                Card(
                    modifier = Modifier
                        .width(100.dp)
                        .height(150.dp),
                ) {
                    FlipRotate(
                        flipCard = flipCard2,
                        forward = {
                            FlipCardFace(R.drawable.back, "Forward")
                        },
                        previous = {
                            FlipCardFace(cardData.second.imageRes, "Previous")
                        }
                    )
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Button(
                    enabled = if (betButton) true else false,
                    onClick = {
                        userPick = "lower"
                        betButtonClick()
                    }
                ) {
                    Text(
                        text = "Lower",
                        color = Color.LightGray
                    )
                }

                Button(
                    enabled = if (betButton) true else false,
                    onClick = {
                        userPick = "higher"
                        betButtonClick()
                    }
                ) {
                    Text(
                        text = "Higher",
                        color = Color.Black
                    )
                }
            }

            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                if (trialCount <= 0 || trialCount >= 4) {
                    Button(
                        onClick = {
                            resetTrial()
                        }
                    ) {
                        Text(
                            text = "Reset Trial",
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black

                        )
                    }
                } else {

                    Text(
                        text = if (trialCount <= 0 || trialCount >= 4) "No more trial" else "Trial Game: ${trialCount}",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (trialCount <= 0 || trialCount >= 4) Color.Red else Color.Black

                    )
                }

            }


        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Box(
                modifier = Modifier
                    .weight(0.4f)
                    .padding(16.dp)
                    .background(Color.Red),
                contentAlignment = Alignment.Center
            ) {
                Button(
                    onClick = {
                        resetGame()
                        betButton = false
                    },
                    modifier = Modifier.fillMaxWidth(0.5f)
                ) {
                    Text("Game")
                }
            }
        }
    }
}


@Composable
fun FlipCardFace(imageRes: Int, description: String) {
    Card(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF7F4F3)),
    ) {
        Image(
            painter = painterResource(id = imageRes),
            contentDescription = description,
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF7F4F3)),
            contentScale = ContentScale.Fit
        )
    }
}

@Composable
fun FlipRotate(
    flipCard: FlipCard,
    modifier: Modifier = Modifier,
    previous: @Composable () -> Unit = {},
    forward: @Composable () -> Unit = {}
) {
    val rotation = animateFloatAsState(
        targetValue = flipCard.angle,
        animationSpec = tween(
            durationMillis = 500,
            easing = FastOutSlowInEasing
        )
    )

    Card(
        modifier = modifier
            .graphicsLayer {
                rotationY = rotation.value
                cameraDistance = 16f * density
            }
            .background(Color(0xFFF7F4F3)),
        shape = RoundedCornerShape(0.dp)
    ) {
        if (rotation.value <= 90f) {
            forward()
        } else {
            Box(
                modifier = Modifier.graphicsLayer { rotationY = 180f }
            ) {
                previous()
            }
        }
    }
}
