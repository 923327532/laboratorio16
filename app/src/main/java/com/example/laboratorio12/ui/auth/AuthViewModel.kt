package com.example.laboratorio12.ui.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.laboratorio12.core.Resource
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AuthViewModel : ViewModel() {

    // Instancia directa de FirebaseAuth
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    // Estado inicial: Success(false) significa "no estamos cargando ni hay error, pero no hay usuario logueado aún"
    private val _authState = MutableStateFlow<Resource<Boolean>>(Resource.Success(false))
    val authState: StateFlow<Resource<Boolean>> = _authState

    fun login(email: String, password: String) {
        // Emitimos estado de carga
        _authState.value = Resource.Loading

        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Login exitoso
                    _authState.value = Resource.Success(true)
                } else {
                    // Error en login
                    _authState.value = Resource.Error(task.exception?.message ?: "Error desconocido")
                }
            }
    }

    // Función opcional para limpiar el estado al salir
    fun signOut() {
        auth.signOut()
        _authState.value = Resource.Success(false)
    }
}
