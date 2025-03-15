package com.example.wheels

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.wheels.ui.theme.WheelsTheme
import com.example.wheels.ui.theme.pageBackground
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.random.Random

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            WheelsTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    CardFlip()
                }
            }
        }
    }
}





@Composable
fun Scratch() {
    val (bombCount, diamondCount) = generateRandomCounts()

    val imageResList by remember { mutableStateOf(getCustomRandomImages(R.drawable.diamond, R.drawable.bomb, diamondCount, bombCount)) }
    val revealedList = remember { mutableStateListOf<Boolean>().apply {
        addAll(List(imageResList.size) { false })
    } }

    val totalCount = diamondCount + bombCount
    Log.i("Bomb Count: ", bombCount.toString())
    Log.i("Diamond Count: ", diamondCount.toString())
    Log.i("Total Count: ", totalCount.toString())

    Box(
        modifier = Modifier
            .fillMaxHeight(0.5f)
            .fillMaxWidth()
            .background(pageBackground)
            .layoutId("mainContent")
            .border(1.dp, Color.Yellow)
    ) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            modifier = Modifier
                .fillMaxHeight()
                .border(1.dp, Color.Red),
            horizontalArrangement = Arrangement.Center,
            verticalArrangement = Arrangement.Center,
            userScrollEnabled = false,
            content = {
                items(9) { index ->
                    ScracthCardScreen(
                        baseImage = imageResList[index],
                        overlayImage = R.drawable.coin,
                    )
                }
            }
        )
    }
}

fun generateRandomCounts(): Pair<Int, Int> {
    val diamondCount = Random.nextInt(1, 4)
    val bombCount = 9 - diamondCount
    return Pair(bombCount, diamondCount)
}

fun getCustomRandomImages(image1: Int, image2: Int, count1: Int, count2: Int): List<Int> {
    // Creating a new list
    val imageList = mutableListOf<Int>()

    // Store the specific count of images in the list
    repeat(count1) { imageList.add(image1) }
    repeat(count2) { imageList.add(image2) }

    // Shuffle to randomize the order
    return imageList.shuffled()
}

@Composable
fun ScracthCardScreen(
    baseImage: Int,
    overlayImage: Int,
) {

    val baseImage = ImageBitmap.imageResource(id = baseImage)
    val overlayImage = ImageBitmap.imageResource(id = overlayImage)
    val currentPathState = remember { mutableStateOf(DraggedPath(path = Path())) }
    val movedOffsetState = remember { mutableStateOf<Offset?>(null) }

    ScratchCard(
        overlayImage = overlayImage,
        baseImage = baseImage,
        movedOffset = movedOffsetState.value,
        onMovedoffset =  { x, y ->
            movedOffsetState.value = Offset(x, y)
        },
        currentPath = currentPathState.value.path,
        currentPathThickness = currentPathState.value.width,

        )

}
