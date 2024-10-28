package com.example.contadorapp

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import android.app.Activity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.compose.runtime.livedata.observeAsState

// Creamos una actividad llamada "ContadorActivity" que extiende ComponentActivity (actividad base en Compose).
class ContadorActivity : ComponentActivity() {
    // Método onCreate, donde se configura la actividad inicial del usuario.
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // setContent establece el contenido de la actividad en una UI de Jetpack Compose.
        setContent {

            // Creamos un controlador de navegación para gestionar las pantallas de la app
            val navController = rememberNavController()

            // Llamamos a NavigationGraph, que define las rutas de navegación
            NavigationGraph(navController)
        }
    }
}

// Declaramos una función composable para la pantalla del contador
@Composable
fun ContadorScreen(navController: NavHostController) {
    // Creamos un estado mutable llamado "contador" con un valor inicial de 0
    val contador = remember { mutableStateOf(0) }

    // Obtenemos el backStackEntry de la ruta "contador" para gestionar el estado guardado (savedStateHandle)
    val backStackEntry = navController.getBackStackEntry("contador")
    val savedStateHandle = backStackEntry.savedStateHandle

    // Observamos los cambios en el valor de "resultado" dentro del savedStateHandle con LiveData
    val resultado by savedStateHandle.getLiveData<Int>("resultado").observeAsState(initial = null)

    // LaunchedEffect se usa para ejecutar código que reacciona a cambios en "resultado"
    LaunchedEffect(resultado) {
        resultado?.let { valor ->
            // Si "resultado" no es nulo, se añade su valor al contador actual
            contador.value += valor
            // Removemos el valor de "resultado" del savedStateHandle después de usarlo para evitar duplicados
            savedStateHandle.remove<Int>("resultado")
        }
    }

    // Scaffold proporciona una estructura básica de diseño (típica de Material Design) con un área de contenido
    Scaffold { paddingValues ->
        // Column alinea los elementos verticalmente en el centro de la pantalla
        Column(
            modifier = Modifier
                .fillMaxSize() // Ocupa toda la pantalla
                .padding(paddingValues) // Agrega padding alrededor de Scaffold
                .padding(16.dp), // Padding adicional de 16dp
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Muestra el valor actual del contador en la pantalla
            Text(
                text = "Valor actual del contador: ${contador.value}",
                style = MaterialTheme.typography.headlineMedium
            )
            Spacer(modifier = Modifier.height(16.dp)) // Espacio de separación entre el texto y el botón
            Button(onClick = {
                // Navegamos a la pantalla de sumar pasando el valor actual del contador como parámetro
                navController.navigate("sumar/${contador.value}")
            }) {
                Text("Sumar valor") // Texto del botón
            }
        }
    }
}
