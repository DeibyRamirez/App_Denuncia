package com.cheiviz.casos_maltrato

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.cheiviz.casos_maltrato.Componentes.FormularioReporteMaltrato
import com.cheiviz.casos_maltrato.Componentes.UserPreferences
import com.cheiviz.casos_maltrato.Componentes.UserPreferencesDataStore
import com.cheiviz.casos_maltrato.Pantallas.PantallaInicio
import com.cheiviz.casos_maltrato.Pantallas.PantallaPrincipal
import com.cheiviz.casos_maltrato.Pantallas.ReportesScreen
import com.cheiviz.casos_maltrato.ui.theme.Casos_MaltratoTheme


class MainActivity : ComponentActivity() {

    private val userPrefs by lazy { UserPreferencesDataStore(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Casos_MaltratoTheme {
                NavegacionPantallas( userPrefs)
            }
        }
    }
}

@Composable
fun NavegacionPantallas( userPrefs: UserPreferencesDataStore) {

    val navController = rememberNavController()
    val userData by userPrefs.getUserData().collectAsState(
        initial = UserPreferences("", false) // valor por defecto
    )


                NavHost(navController = navController, startDestination = "login") {

        // Pantalla de inicio (login / nombre usuario)
        composable("login") {
            PantallaInicio( userPrefs = userPrefs,  Siguiente = { nombre ->
                navController.navigate("home/$nombre")
            })
        }

        // Pantalla principal
        composable("home/{username}") { backStackEntry ->
            val nombre = backStackEntry.arguments?.getString("username") ?: ""
            PantallaPrincipal(
                nombre = nombre,
                Atras = { navController.popBackStack(route = "login", inclusive = false) },
                IrAFormulario = { navController.navigate("formulario") },
                IrAReporte = { navController.navigate("reporte") }
            )
        }

        // Pantalla de lista de reportes
        composable("reporte") {
            ReportesScreen { reporteId ->
                navController.navigate("Reporte_completo/$reporteId")
            }
        }

        // Pantalla de detalle de un reporte
        composable("Reporte_completo/{id}") { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id") ?: ""
            // Aquí puedes traer de Firebase el reporte por ID y mostrarlo
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Detalle del reporte con ID: $id", style = MaterialTheme.typography.headlineMedium)
            }
        }

        // Pantalla de formulario
        composable("formulario") {
            FormularioReporteMaltrato(
                nombreUsuario = userData.nombre,
                anonimo = userData.anonimo
            )
        }
    }
}

