package com.example.laboratorio12.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

// Paleta institucional TECSUP
private val AzulTecs = Color(0xFF0169B2)
private val CelesteFondo = Color(0xFFF6FAFD)
private val AzulSecundario = Color(0xFF01BCD8)

@Composable
fun HomeScreen(
    onLogout: () -> Unit
) {
    Scaffold(
        modifier = Modifier.background(CelesteFondo)
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(CelesteFondo),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "¡Bienvenido!",
                style = MaterialTheme.typography.headlineLarge,
                color = AzulTecs,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Has iniciado sesión correctamente.",
                style = MaterialTheme.typography.bodyLarge,
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = onLogout,
                shape = RoundedCornerShape(50),
                colors = ButtonDefaults.buttonColors(containerColor = AzulSecundario)
            ) {
                Text("Cerrar sesión", color = Color.White)
            }
        }
    }
}
