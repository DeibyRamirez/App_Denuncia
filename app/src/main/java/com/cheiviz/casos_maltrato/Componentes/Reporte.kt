package com.cheiviz.casos_maltrato.Componentes


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun formatearFecha(timestamp: Long): String {
    val date = Date(timestamp) // Convierte el número en una fecha
    val formato = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
    return formato.format(date) // Devuelve la fecha como texto
}

@Composable
fun ReporteEnLista(
    titulo: String,
    descripcion: String,
    ubicacion: String,
    usuario: String,
    fecha: String,
    onClick: () -> Unit = {}
) {
    Card(
        modifier = Modifier
            .padding(10.dp)
            .width(400.dp)
            .shadow(
                elevation = 4.dp,
                shape = MaterialTheme.shapes.medium
            ),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.onSurface
        ),
        shape = MaterialTheme.shapes.medium
    ) {
        Box(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.surface)
                .padding(10.dp)
        ) {
            Row(
                modifier = Modifier.align(Alignment.CenterStart),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // ICONO Y TÍTULO
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.weight(1f)
                ) {
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = titulo, // 👈 Aquí va el título dinámico
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Medium,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }

                Spacer(modifier = Modifier.width(20.dp))

                // FECHA
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.weight(1f)
                ) {
                    Icon(
                        Icons.Default.DateRange,
                        contentDescription = "Fecha",
                        tint = MaterialTheme.colorScheme.secondary,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = formatearFecha(System.currentTimeMillis()), // 👈 Fecha dinámica
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                Spacer(modifier = Modifier.width(20.dp))

                // BOTÓN DE ACCIÓN
                IconButton(
                    onClick = onClick,
                    modifier = Modifier.size(40.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowForward,
                        contentDescription = "Ir",
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
        }
    }
}


@Composable
fun ReporteCompleto(
    titulo: String,
    descripcion: String,
    ubicacion: String,
    fecha: Long,
    usuario: String
) {
    Box(
        modifier = Modifier
            .padding(20.dp)
            .shadow(
                elevation = 8.dp,
                shape = MaterialTheme.shapes.medium
            )
            .clip(MaterialTheme.shapes.medium)
            .background(MaterialTheme.colorScheme.surface)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Título
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    Icons.Default.KeyboardArrowRight,
                    contentDescription = "Título",
                    tint = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    titulo,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Descripción
            Row(verticalAlignment = Alignment.Top) {
                Icon(
                    Icons.Default.Create,
                    contentDescription = "Descripción",
                    tint = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(descripcion, style = MaterialTheme.typography.bodyLarge)
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Ubicación
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    Icons.Default.LocationOn,
                    contentDescription = "Ubicación",
                    tint = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(ubicacion, style = MaterialTheme.typography.bodyLarge)
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Fecha
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    Icons.Default.DateRange,
                    contentDescription = "Fecha",
                    tint = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(formatearFecha(fecha), style = MaterialTheme.typography.bodyLarge)
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Usuario
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    Icons.Default.Person,
                    contentDescription = "Usuario",
                    tint = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(usuario, style = MaterialTheme.typography.bodyLarge)
            }
        }
    }
}


@Preview
@Composable
fun PreviewReportes() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp)
    ) {
        ReporteEnLista(
            titulo = "Titulo",
            descripcion = "Descripcion",
            ubicacion = "Ubicacion",
            usuario = "Usuario",
            fecha = "Fecha"


        )
    }
}

@Preview
@Composable
fun PreviewReporteCompleto() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        ReporteCompleto(
            titulo = "Titulo",
            descripcion = "Descripcion",
            ubicacion = "Ubicacion",
            fecha = System.currentTimeMillis(),
            usuario = "Usuario"
        )
    }
}


