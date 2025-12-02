package com.example.laboratorio12

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.laboratorio12.ui.navigation.AppNavigation
import com.example.laboratorio12.ui.theme.MisCursosTheme
import dagger.hilt.android.AndroidEntryPoint // <--- 1. AGREGA ESTE IMPORT

@AndroidEntryPoint // <--- 2. AGREGA ESTA ETIQUETA AQUÍ ARRIBA
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MisCursosTheme(darkTheme = false){
                AppNavigation()
            }
        }
    }
}
