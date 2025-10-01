package com.cheiviz.casos_maltrato.Componentes

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
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
import com.google.firebase.database.FirebaseDatabase

// Modelo de datos
data class Formulario(
    val tipo: String,
    val descripcion: String,
    val ubicacion: String,
    val imagenUrl: String?
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormularioReporteMaltrato(nombreUsuario: String, anonimo: Boolean) {
    // Opciones desplegable
    val opciones = listOf("Físico", "Psicológico", "Escolar", "Animal", "Económico", "Digital")

    // Estados de los campos
    var expanded by remember { mutableStateOf(false) }
    var opcionSeleccionada by remember { mutableStateOf("") }

    var descripcion by remember { mutableStateOf("") }
    var ubicacion by remember { mutableStateOf("") }
    var imagenUrl by remember { mutableStateOf("") }



    // Estado para mostrar confirmación
    var showConfirmation by remember { mutableStateOf(false) }

    // MEJORA 1: Fondo con gradiente y scroll para formularios largos
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
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {

            // MEJORA 2: Header con icono y título
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(elevation = 8.dp, shape = RoundedCornerShape(16.dp)),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        Icons.Default.Warning,
                        contentDescription = "Reportar",
                        tint = MaterialTheme.colorScheme.error,
                        modifier = Modifier.size(48.dp)
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "Reportar Caso de Maltrato",
                        style = MaterialTheme.typography.headlineSmall,
                        color = MaterialTheme.colorScheme.onSurface,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )

                    Text(
                        text = "Tu reporte puede salvar vidas",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }
            }

            // MEJORA 3: Card para agrupar campos del formulario
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(elevation = 4.dp, shape = RoundedCornerShape(12.dp)),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    verticalArrangement = Arrangement.spacedBy(20.dp)
                ) {
                    // 🔽 Campo desplegable: Tipo de maltrato
                    Column {
                        Text(
                            text = "Tipo de maltrato *",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurface,
                            fontWeight = FontWeight.Medium,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                        ExposedDropdownMenuBox(
                            expanded = expanded,
                            onExpandedChange = { expanded = !expanded }
                        ) {
                            TextField(
                                value = opcionSeleccionada,
                                onValueChange = {},
                                readOnly = true,
                                placeholder = { Text("Selecciona el tipo de maltrato") },
                                trailingIcon = {
                                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                                },
                                modifier = Modifier
                                    .menuAnchor()
                                    .fillMaxWidth(),
                                colors = TextFieldDefaults.colors(
                                    focusedContainerColor = MaterialTheme.colorScheme.surfaceVariant
                                ),
                                shape = RoundedCornerShape(12.dp)
                            )

                            ExposedDropdownMenu(
                                expanded = expanded,
                                onDismissRequest = { expanded = false }
                            ) {
                                opciones.forEach { opcion ->
                                    DropdownMenuItem(
                                        text = {
                                            Text(
                                                opcion,
                                                style = MaterialTheme.typography.bodyMedium
                                            )
                                        },
                                        onClick = {
                                            opcionSeleccionada = opcion
                                            expanded = false
                                        }
                                    )
                                }
                            }
                        }
                    }

                    // ✍️ Descripción del caso
                    Column {
                        Text(
                            text = "Descripción del caso *",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurface,
                            fontWeight = FontWeight.Medium,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                        OutlinedTextField(
                            value = descripcion,
                            onValueChange = { descripcion = it },
                            placeholder = { Text("Describe los detalles del caso...") },

                            modifier = Modifier
                                .fillMaxWidth()
                                .height(120.dp),
                            shape = RoundedCornerShape(12.dp),
                            singleLine = false,
                            maxLines = 5
                        )
                    }

                    // 📍 Ubicación
                    Column {
                        Text(
                            text = "Ubicación *",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurface,
                            fontWeight = FontWeight.Medium,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                        OutlinedTextField(
                            value = ubicacion,
                            onValueChange = { ubicacion = it },
                            placeholder = { Text("Barrio, ciudad, dirección...") },

                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp)
                        )
                    }

                    // 🖼️ Imagen (URL simulada)
                    Column {
                        Text(
                            text = "URL de la imagen (opcional)",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurface,
                            fontWeight = FontWeight.Medium,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                        OutlinedTextField(
                            value = imagenUrl,
                            onValueChange = { imagenUrl = it },
                            placeholder = { Text("https://ejemplo.com/imagen.jpg") },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp)
                        )
                    }
                }
            }

            // MEJORA 4: Botón con mejor diseño y validación
            val isFormValid = opcionSeleccionada.isNotBlank() &&
                    descripcion.isNotBlank() &&
                    ubicacion.isNotBlank()

            Button(
                onClick = {
                    enviarReporte(
                        nombreUsuario = nombreUsuario , // luego lo obtienes de DataStore
                        anonimo = anonimo,           // también desde DataStore
                        tipo = opcionSeleccionada,
                        descripcion = descripcion,
                        ubicacion = ubicacion,
                        imagenUrl = if (imagenUrl.isNotBlank()) imagenUrl else null
                    )
                    showConfirmation = true
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(12.dp),
                enabled = isFormValid,
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                )
            ) {
                Icon(
                    Icons.Default.Send,
                    contentDescription = "Enviar",
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Enviar Reporte",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.SemiBold
                )
            }

            // MEJORA 5: Texto de campos obligatorios
            Text(
                text = "* Campos obligatorios",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f),
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }

        // MEJORA 6: Snackbar de confirmación
        if (showConfirmation) {
            AlertDialog(
                onDismissRequest = { showConfirmation = false },
                title = {
                    Text(
                        text = "Reporte Enviado",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold
                    )
                },
                text = {
                    Text("Tu reporte ha sido enviado exitosamente. Te contactaremos si necesitamos más información.")
                },
                confirmButton = {
                    Button(
                        onClick = { showConfirmation = false },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary
                        )
                    ) {
                        Text("Aceptar")
                    }
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewFormularioReporteMaltrato() {
    FormularioReporteMaltrato(
        nombreUsuario = "Juan",
        anonimo = false
    )
}

fun enviarReporte(
    nombreUsuario: String,
    anonimo: Boolean,
    tipo: String,
    descripcion: String,
    ubicacion: String,
    imagenUrl: String?
){

    val database = FirebaseDatabase.getInstance().getReference("reportes")

    val reporteId = database.push().key ?: return
    val reporte = mapOf(
        "id" to reporteId,
        "usuario" to if (anonimo) "Anónimo" else nombreUsuario,
        "tipo" to tipo,
        "descripcion" to descripcion,
        "ubicacion" to ubicacion,
        "imagenUrl" to imagenUrl,
        "fecha" to System.currentTimeMillis()
    )

    database.child(reporteId).setValue(reporte)

}