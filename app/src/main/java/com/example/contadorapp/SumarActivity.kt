package com.example.contadorapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

// Declaramos la clase SumarActivity, una actividad de Android que extiende ComponentActivity
class SumarActivity : ComponentActivity() {
    // Método onCreate se llama al crear la actividad
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Configuramos el contenido de la actividad usando Compose
        setContent {
            // Obtenemos el valor del contador desde los extras de la intención (Intent)

            val contador = intent.getIntExtra("contador", 0)

            // Llamamos a SumarScreen pasando el contador y un NavController (usando `rememberNavController` para manejar la navegación)
            SumarScreen(contador, navController = rememberNavController())
        }
    }
}

// Función composable SumarScreen para la pantalla donde se incrementa el contador
@Composable
fun SumarScreen(contador: Int, navController: NavHostController) {
    // `valorASumar` es una variable mutable que mantiene el valor ingresado por el usuario
    var valorASumar by remember { mutableStateOf("") }

    // Scaffold establece una estructura básica de diseño para la pantalla
    Scaffold { paddingValues ->
        // Column alinea los elementos en una columna centrada en la pantalla
        Column(
            modifier = Modifier
                .fillMaxSize() // Ocupa toda la pantalla
                .padding(paddingValues) // Aplica el padding de Scaffold
                .padding(16.dp), // Añade un padding adicional de 16dp
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Texto que muestra el valor actual del contador
            Text(text = "Valor actual del contador: $contador", style = MaterialTheme.typography.headlineMedium)

            Spacer(modifier = Modifier.height(16.dp)) // Espacio de separación

            // Campo de texto donde el usuario ingresa el valor a sumar
            TextField(
                value = valorASumar, // El valor actual de `valorASumar`
                onValueChange = { valorASumar = it }, // Actualiza `valorASumar` con cada cambio en el texto
                label = { Text("Ingrese valor a sumar") },
                modifier = Modifier.fillMaxWidth(), // Hace que el campo ocupe todo el ancho
            )

            Spacer(modifier = Modifier.height(16.dp)) // Otro espacio de separación

            // Botón para realizar la suma
            Button(onClick = {
                // Convierte `valorASumar` a un entero o usa 0 si no es un número válido
                val suma = valorASumar.toIntOrNull() ?: 0
                // Calcula el nuevo valor sumando el valor ingresado a `contador`
                val nuevoValor = contador + suma

                // Guarda `nuevoValor` en el savedStateHandle del backStackEntry previo
                // Esto permite pasar `nuevoValor` de vuelta a la pantalla anterior
                navController.previousBackStackEntry?.savedStateHandle?.set("resultado", nuevoValor)

                // Navega hacia atrás en la pila de navegación para regresar a la pantalla anterior
                navController.popBackStack()
            }) {
                Text("Sumar") // Texto del botón
            }
        }
    }
}
