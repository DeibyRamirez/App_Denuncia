package com.cheiviz.casos_maltrato.Pantallas

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.cheiviz.casos_maltrato.Componentes.ReporteEnLista
import com.google.firebase.database.FirebaseDatabase

// Necesitamos importar estos adicionales
import androidx.compose.foundation.layout.size


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

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        MaterialTheme.colorScheme.primaryContainer,
                        MaterialTheme.colorScheme.background
                    )
                )
            )
            .statusBarsPadding()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            // MEJORA 1: Header con diseño mejorado
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(
                        elevation = 8.dp,
                        shape = RoundedCornerShape(16.dp)
                    ),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Icono y título
                    Box(
                        modifier = Modifier
                            .size(60.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(MaterialTheme.colorScheme.primary),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            Icons.Default.Person,
                            contentDescription = "Lista de reportes",
                            tint = Color.White,
                            modifier = Modifier.size(30.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "Lista de Reportes",
                        style = MaterialTheme.typography.headlineSmall,
                        color = MaterialTheme.colorScheme.onSurface,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )

                    Text(
                        text = "${reportes.size} reportes encontrados",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // MEJORA 2: Contenedor para la lista de reportes
            if (reportes.isNotEmpty()) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .shadow(
                            elevation = 4.dp,
                            shape = RoundedCornerShape(16.dp)
                        ),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surface
                    )
                ) {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp)
                    ) {
                        items(reportes) { reporte ->
                            ReporteEnLista(
                                titulo = (reporte["tipo"] ?: "Sin tipo").toString(),
                                descripcion = (reporte["descripcion"]
                                    ?: "Sin descripción").toString(),
                                ubicacion = (reporte["ubicacion"] ?: "Sin ubicación").toString(),
                                usuario = (reporte["usuario"] ?: "Anónimo").toString(),
                                fecha = (reporte["fecha"] ?: "").toString(),
                                onClick = { onClickReporte(reporte["id"].toString()) }
                            )
                            Spacer(modifier = Modifier.height(12.dp))
                        }
                    }
                }
            } else {
                // MEJORA 3: Estado vacío con diseño mejorado
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .shadow(
                            elevation = 4.dp,
                            shape = RoundedCornerShape(16.dp)
                        ),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surface
                    )
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(40.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            Icons.Default.Person,
                            contentDescription = "Sin reportes",
                            tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f),
                            modifier = Modifier.size(80.dp)
                        )

                        Spacer(modifier = Modifier.height(24.dp))

                        Text(
                            text = "No hay reportes",
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
                            fontWeight = FontWeight.Medium
                        )

                        Text(
                            text = "Los reportes aparecerán aquí cuando sean creados",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(top = 8.dp)
                        )
                    }
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewReportesScreen() {
    ReportesScreen()
}

@Preview(showBackground = true)
@Composable
fun PreviewReportesScreenEmpty() {
    ReportesScreen()
}