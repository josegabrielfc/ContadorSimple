package com.example.contadorsimple

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import com.example.contadorsimple.ui.theme.ContadorSimpleTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ContadorSimpleTheme {
                ContadorApp()
            }
        }
    }
}
@Composable
fun ContadorApp() {
    var contador by remember { mutableIntStateOf(0) }
    var showDialog by remember { mutableStateOf(false) }

    // Imagen de Fondo
    val backgroundImage: Painter = painterResource(id = R.drawable.background)

    // Paleta colores
    val color1 = Color(0xFFCADCEA) // Color principal 1
    val color2 = Color(0xFFCBDDEB) // Color principal 2
    val color3 = Color(0xFFC9DBE9) // Color principal 3
    val color4 = Color(0xFFCBDDE9) // Color principal 4
    val color5 = Color(0xFFAEC7DD) // Color principal 5
    val titleColor2 = Color(0xFF8B9DC3) // Color para el título (más oscuro)
    val contrastColor4 = Color(0xFF2F2E41) // Gris oscuro (ideal para texto)
    val titleColor = Color(0xFF1E3A8A) // Azul oscuro para variación

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            painter = backgroundImage,
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop // Ajusta la imagen para cubrir el área
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Contador Simple",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = titleColor // Color oscuro para el título
            )
            Box {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier = Modifier
                            .size(width = 150.dp, height = 80.dp)
                            .border(width = 2.dp, color = Color.Gray, shape = RoundedCornerShape(8.dp))
                            .background(color = Color.LightGray, shape = RoundedCornerShape(8.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "$contador",
                            fontSize = 32.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    Button(
                        onClick = { contador++ },
                        modifier = Modifier
                            .width(200.dp)
                            .height(50.dp)
                            .clip(RoundedCornerShape(15.dp))
                            .shadow(20.dp, shape = RoundedCornerShape(15.dp)),
                        colors = ButtonDefaults.buttonColors(backgroundColor = color1) // Color del botón
                    ) {
                        Text(text = "Incrementar", fontSize = 20.sp)
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    Button(
                        onClick = { showDialog = true },
                        modifier = Modifier
                            .width(200.dp)
                            .height(75.dp)
                            .clip(RoundedCornerShape(15.dp))
                            .shadow(20.dp, shape = RoundedCornerShape(15.dp)),
                        colors = ButtonDefaults.buttonColors(backgroundColor = color2) // Color del botón
                    ) {
                        Text(
                            text = "Incrementar con Valor",
                            fontSize = 18.sp,
                            textAlign = TextAlign.Center
                        )
                    }

                    if (showDialog) {
                        AumentarNumero(
                            onDismiss = { showDialog = false },
                            onConfirm = { input ->
                                contador += input
                                showDialog = false
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun AumentarNumero(
    onDismiss: () -> Unit,
    onConfirm: (Int) -> Unit
) {
    var inputText by remember { mutableStateOf(TextFieldValue("")) }
    val context = LocalContext.current

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = "Ingrese un número") },
        text = {
            Column {
                OutlinedTextField(
                    value = inputText,
                    onValueChange = { inputText = it },
                    label = { Text("Número") },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Done
                    )
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    val input = inputText.text
                    when {
                        input.isBlank() -> {
                            Toast.makeText(
                                context,
                                "Por favor ingrese un número",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                        input.toIntOrNull() == null -> {
                            Toast.makeText(context, "Solo se permiten números", Toast.LENGTH_SHORT)
                                .show()
                        }
                        else -> {
                            onConfirm(input.toInt())
                            inputText = TextFieldValue("") // Reiniciar el valor
                        }
                    }
                }
            ) {
                Text("OK")
            }
        },
        dismissButton = {
            Button(onClick = onDismiss) {
                Text("Cancelar")
            }
        },
        properties = DialogProperties(dismissOnClickOutside = false)
    )
}