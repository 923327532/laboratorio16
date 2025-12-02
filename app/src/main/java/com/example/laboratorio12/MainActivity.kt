package com.example.laboratorio12

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.laboratorio12.ui.navigation.AppNavigation

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // Ya no pasamos ningún ViewModel, la navegación maneja solo el flujo de pantallas
            AppNavigation()
        }
    }
}
