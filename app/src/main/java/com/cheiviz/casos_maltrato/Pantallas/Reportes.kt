package com.cheiviz.casos_maltrato.Pantallas

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.cheiviz.casos_maltrato.Componentes.ReporteEnLista
import com.google.firebase.database.FirebaseDatabase

@Composable
fun ReportesScreen(onClickReporte: (String) -> Unit = {}) {
    val database = FirebaseDatabase.getInstance().getReference("reportes")
    val reportes = remember { mutableStateListOf<Map<String, Any>>() }

    // Cargar los reportes al iniciar
    LaunchedEffect(Unit) {
        database.get().addOnSuccessListener { snapshot ->
            reportes.clear()
            snapshot.children.forEach { child ->
                child.value?.let { value ->
                    if (value is Map<*, *>) {
                        reportes.add(value as Map<String, Any>)
                    }
                }
            }
        }
    }

    Column(modifier = Modifier.padding(16.dp)) {
        Text("Lista de Reportes", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn {
            items(reportes) { reporte ->
                ReporteEnLista(
                    titulo = (reporte["tipo"] ?: "Sin tipo").toString(),
                    descripcion = (reporte["descripcion"] ?: "Sin descripción").toString(),
                    ubicacion = (reporte["ubicacion"] ?: "Sin ubicación").toString(),
                    usuario = (reporte["usuario"] ?: "Anónimo").toString(),
                    fecha = (reporte["fecha"] ?: "").toString(),
                    onClick = { onClickReporte(reporte["id"].toString()) }
                )
            }
        }
    }
}