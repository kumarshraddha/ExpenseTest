package com.shraddha.expensetest.core.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import com.shraddha.expensetest.core.ui.theme.ExpenseTestTheme

@Composable
fun ExpenseTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    modifier: Modifier = Modifier,
    keyboardType: KeyboardType = KeyboardType.Text,
    isError: Boolean = false,
    errorMessage: String? = null
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        modifier = modifier.fillMaxWidth(),
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        isError = isError,
        supportingText = if (isError && errorMessage != null) {
            { Text(errorMessage) }
        } else null,
        singleLine = true
    )
}

@Preview(showBackground = true)
@Composable
fun ExpenseTextFieldPreview() {
    ExpenseTestTheme {
        Surface {
            ExpenseTextField(
                value = "10.50",
                onValueChange = {},
                label = "Amount"
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ExpenseTextFieldErrorPreview() {
    ExpenseTestTheme {
        Surface {
            ExpenseTextField(
                value = "",
                onValueChange = {},
                label = "Amount",
                isError = true,
                errorMessage = "Amount is required"
            )
        }
    }
}
