package com.cheiviz.casos_maltrato

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.cheiviz.casos_maltrato.Componentes.FormularioReporteMaltrato
import com.cheiviz.casos_maltrato.Componentes.ReporteEnLista
import com.cheiviz.casos_maltrato.Componentes.UserPreferencesDataStore
import com.cheiviz.casos_maltrato.Pantallas.PantallaInicio
import com.cheiviz.casos_maltrato.Pantallas.PantallaPrincipal
import com.cheiviz.casos_maltrato.ui.theme.Casos_MaltratoTheme
import kotlin.getValue

class MainActivity : ComponentActivity() {

    private val userPrefs by lazy { UserPreferencesDataStore(this) }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Casos_MaltratoTheme {
                NavegacionPantallas(/*taskDataStore*/)
            }


        }
    }
}

@Composable
fun NavegacionPantallas() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "login") {
        composable("login") {
            PantallaInicio(Siguiente = { nombre ->
                navController.navigate("home/$nombre")
            })
        }
        composable("home/{username}") { backStackEntry ->
            val nombre = backStackEntry.arguments?.getString("username") ?: ""
            PantallaPrincipal(
                nombre = nombre,
                Atras = { navController.popBackStack(route = "login", inclusive = false) },
                IrAFormulario = { navController.navigate("formulario") },
                IrAReporte = { navController.navigate("reporte") }
            )
        }
        composable("reporte") {

            Column(modifier = Modifier.padding(16.dp)) {

                Text("Lista de Reportes", style = MaterialTheme.typography.headlineMedium)
                Spacer(modifier = Modifier.height(16.dp))
                ReporteEnLista(
                    Ingresar = { }
                )
                ReporteEnLista(
                    Ingresar = { }
                )
                ReporteEnLista(
                    Ingresar = { }
                )
            }

        }
        composable("Reporte_completo"){

        }

        composable( "formulario"){
            FormularioReporteMaltrato()

        }
    }

}
