package com.example.wheels.Dialog

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp


@Composable
fun CustomCardDialog(
    onDismiss: () -> Unit,
    fontFamily: FontFamily,
    title: String,
    description: String,
) {

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = title,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = fontFamily,
                modifier = Modifier.fillMaxWidth()
            )
        },
        text = {
            Text(
                text = description,
                fontSize = 14.sp,
                fontWeight = FontWeight.Normal,
                fontFamily = fontFamily,
                modifier = Modifier.fillMaxWidth()
            )
        },
        confirmButton = {
            Button(
                onClick = {
                    onDismiss() // Close dialog after adding card
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Okay", fontFamily = fontFamily)
            }
        }
    )
}