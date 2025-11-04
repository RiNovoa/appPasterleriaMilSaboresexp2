package com.example.proyectologin005d.ui.pages

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter

// Transformación para el número de tarjeta (#### #### #### ####)
class CardNumberVisualTransformation : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        val trimmed = if (text.text.length >= 16) text.text.substring(0..15) else text.text
        var out = ""
        for (i in trimmed.indices) {
            out += trimmed[i]
            if (i % 4 == 3 && i < 15) out += " "
        }
        val offsetTranslator = object : OffsetMapping {
            override fun originalToTransformed(offset: Int): Int {
                if (offset <= 3) return offset
                if (offset <= 7) return offset + 1
                if (offset <= 11) return offset + 2
                if (offset <= 16) return offset + 3
                return 19
            }

            override fun transformedToOriginal(offset: Int): Int {
                if (offset <= 4) return offset
                if (offset <= 9) return offset - 1
                if (offset <= 14) return offset - 2
                if (offset <= 19) return offset - 3
                return 16
            }
        }
        return TransformedText(AnnotatedString(out), offsetTranslator)
    }
}

// Transformación para la fecha de expiración (MM/AA)
class ExpiryDateVisualTransformation : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        val trimmed = if (text.text.length >= 4) text.text.substring(0..3) else text.text
        var out = ""
        for (i in trimmed.indices) {
            out += trimmed[i]
            if (i == 1) out += "/"
        }
        val offsetTranslator = object : OffsetMapping {
            override fun originalToTransformed(offset: Int): Int {
                if (offset <= 1) return offset
                if (offset <= 4) return offset + 1
                return 5
            }

            override fun transformedToOriginal(offset: Int): Int {
                if (offset <= 2) return offset
                if (offset <= 5) return offset - 1
                return 4
            }
        }
        return TransformedText(AnnotatedString(out), offsetTranslator)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PaymentScreen(navController: NavController, total: Int) {
    var cardName by remember { mutableStateOf("") }
    var cardNumber by remember { mutableStateOf("") }
    var expiryDate by remember { mutableStateOf("") }
    var cvv by remember { mutableStateOf("") }
    var isCvvFocused by remember { mutableStateOf(false) }

    var cardNameError by remember { mutableStateOf<String?>(null) }
    var cardNumberError by remember { mutableStateOf<String?>(null) }
    var expiryDateError by remember { mutableStateOf<String?>(null) }
    var cvvError by remember { mutableStateOf<String?>(null) }

    val isFormValid by derivedStateOf {
        cardName.isNotBlank() && cardNameError == null &&
        cardNumber.length == 16 && cardNumberError == null &&
        expiryDate.length == 4 && expiryDateError == null &&
        cvv.length in 3..4 && cvvError == null
    }

    fun validateCardName() {
        cardNameError = if (cardName.isBlank()) "El nombre no puede estar vacío" else null
    }

    fun validateCardNumber() {
        cardNumberError = when {
            !cardNumber.all { it.isDigit() } -> "Debe contener solo números"
            else -> null
        }
    }

    fun validateExpiryDate() {
        expiryDateError = if (!expiryDate.all { it.isDigit() }) {
            "Formato MM/AA inválido"
        } else {
            null
        }
    }

    fun validateCvv() {
        cvvError = when {
            !cvv.all { it.isDigit() } -> "Debe ser numérico"
            else -> null
        }
    }

    val rotation by animateFloatAsState(targetValue = if (isCvvFocused) 180f else 0f, label = "")

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .graphicsLayer {
                    rotationY = rotation
                    cameraDistance = 8 * density
                },
            shape = RoundedCornerShape(8.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        brush = Brush.linearGradient(
                            colors = listOf(Color(0xFF6F1E51), Color(0xFFED4C67), Color(0xFFF79F1F)),
                        )
                    )
            ) {
                Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
                    if (rotation < 90f) {
                        Image(painter = rememberAsyncImagePainter(model = "file:///android_asset/img/logo.png"), contentDescription = null, modifier = Modifier.height(40.dp))
                        Spacer(modifier = Modifier.weight(1f))
                        Text(text = CardNumberVisualTransformation().filter(AnnotatedString(cardNumber)).text.text, color = Color.White)
                        Row {
                            Text(text = cardName.ifEmpty { "NOMBRE APELLIDO" }, color = Color.White)
                            Spacer(modifier = Modifier.weight(1f))
                            Text(text = ExpiryDateVisualTransformation().filter(AnnotatedString(expiryDate)).text.text, color = Color.White)
                        }
                    } else {
                        Spacer(modifier = Modifier.height(40.dp))
                        Text(text = "CVV: ${cvv.ifEmpty { "###" }}", modifier = Modifier.align(Alignment.End), color = Color.White)
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = cardName,
            onValueChange = { cardName = it; validateCardName() },
            label = { Text("Nombre en la tarjeta") },
            modifier = Modifier.fillMaxWidth(),
            isError = cardNameError != null,
            supportingText = { if (cardNameError != null) Text(cardNameError!!) }
        )

        OutlinedTextField(
            value = cardNumber,
            onValueChange = { 
                if (it.length <= 16) {
                    cardNumber = it.filter { char -> char.isDigit() }
                    validateCardNumber()
                }
            },
            label = { Text("Número de tarjeta") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            visualTransformation = CardNumberVisualTransformation(),
            modifier = Modifier.fillMaxWidth(),
            isError = cardNumberError != null,
            supportingText = { if (cardNumberError != null) Text(cardNumberError!!) }
        )

        Row(modifier = Modifier.fillMaxWidth()) {
            OutlinedTextField(
                value = expiryDate,
                onValueChange = { 
                    if (it.length <= 4) {
                        expiryDate = it.filter { char -> char.isDigit() }
                        validateExpiryDate()
                    }
                },
                label = { Text("Expira (MM/AA)") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                visualTransformation = ExpiryDateVisualTransformation(),
                modifier = Modifier.weight(1f),
                isError = expiryDateError != null,
                supportingText = { if (expiryDateError != null) Text(expiryDateError!!) }
            )
            Spacer(modifier = Modifier.width(16.dp))
            OutlinedTextField(
                value = cvv,
                onValueChange = { 
                    if (it.length <= 4) {
                        cvv = it.filter { char -> char.isDigit() }
                        validateCvv()
                    }
                },
                label = { Text("CVV") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.weight(1f).onFocusChanged { isCvvFocused = it.isFocused },
                isError = cvvError != null,
                supportingText = { if (cvvError != null) Text(cvvError!!) }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { /* Lógica de pago */ },
            modifier = Modifier.fillMaxWidth(),
            enabled = isFormValid,
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFD1DC))
        ) {
            Text("Pagar", color = Color.Black)
        }
    }
}