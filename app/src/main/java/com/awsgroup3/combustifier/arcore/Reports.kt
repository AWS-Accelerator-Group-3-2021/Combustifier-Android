package com.awsgroup3.combustifier.arcore

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.text.input.TextFieldValue
import java.util.*

@Composable
fun AfterMeasuringScreen() {
    val name = remember {
        mutableStateOf(TextFieldValue())
    }
    val address = remember {
        mutableStateOf(TextFieldValue())
    }
    val id = UUID.randomUUID().toString()
    val openDialog = remember { mutableStateOf(true) }

    if (openDialog.value) {
        AlertDialog(
            icon = {Icon(Icons.Filled.Warning, contentDescription = null)},
            onDismissRequest = {
                // Dismiss the dialog when the user clicks outside the dialog or on the back
                // button. If you want to disable that functionality, simply use an empty
                // onDismissRequest.
                openDialog.value = false
            },
            title = {
                Text(text = "Submit a Report")
            },
            text = {
                Text(text = "Found someone with less than 1.2m of space available?")
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        openDialog.value = false
                    }
                ) {
                    Text("Submit!")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        openDialog.value = false
                    }
                ) {
                    Text("Cancel")
                }
            }
        )
    }
}



@Preview
@Composable
fun Preview() {
    AfterMeasuringScreen()
}