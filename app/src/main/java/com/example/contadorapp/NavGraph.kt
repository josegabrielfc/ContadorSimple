package com.example.contadorapp

// Importamos las dependencias necesarias para la navegación en Jetpack Compose
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

// Creamos una función composable llamada "NavigationGraph" que recibe un NavHostController como parámetro.
@Composable
fun NavigationGraph(navController: NavHostController) {
    // Configuramos el NavHost, que es el contenedor de navegación de Jetpack Compose.
    NavHost(navController = navController, startDestination = "contador") {

        // Definimos una ruta composable para la pantalla "contador".
        composable("contador") { ContadorScreen(navController) }

        // Definimos otra ruta composable para una pantalla "sumar" que acepta un parámetro "contador".
        composable("sumar/{contador}") { backStackEntry ->

            // Extraemos el parámetro "contador" de los argumentos pasados en la ruta.
            val contador = backStackEntry.arguments?.getString("contador")?.toInt() ?: 0

            // Llamamos a la función composable "SumarScreen", pasando el valor "contador" y el navController.
            SumarScreen(contador, navController)
        }
    }
}
