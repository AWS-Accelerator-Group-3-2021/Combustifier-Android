package com.awsgroup3.combustifier.arcore

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.material3.AlertDialog
import androidx.compose.material.*
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.material.OutlinedTextField
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import java.util.*

@Composable
fun AfterMeasuringScreen() {
    var name by remember { mutableStateOf("")}
    var address by remember { mutableStateOf("")}
    val id = UUID.randomUUID().toString()
    val openDialog = remember { mutableStateOf(true) }
    val measurementResultForReports = 69.0f
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
                Column() {
                    OutlinedTextField(
                        value = name,
                        onValueChange = { name = it },
                        label = { Text("your name") },
                    )
                    OutlinedTextField(
                        value = address,
                        onValueChange = { address = it },
                        label = { Text("address of offender") },
                    )
                    OutlinedTextField(
                        value = measurementResultForReports.toString(),
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("measurement result") },
                    )
                }
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