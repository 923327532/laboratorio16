package com.example.laboratorio12.ui.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.laboratorio12.core.Resource
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    // Inyección simple directa para examen, idealmente usar Repository
) : ViewModel() {

    private val _authState = MutableStateFlow<Resource<Boolean>?>(null)
    val authState: StateFlow<Resource<Boolean>?> = _authState

    private val auth = FirebaseAuth.getInstance()

    fun login(email: String, pass: String) = viewModelScope.launch {
        _authState.value = Resource.Loading
        auth.signInWithEmailAndPassword(email, pass)
            .addOnSuccessListener { _authState.value = Resource.Success(true) }
            .addOnFailureListener { _authState.value = Resource.Failure(it) }
    }

    fun register(email: String, pass: String) = viewModelScope.launch {
        _authState.value = Resource.Loading
        auth.createUserWithEmailAndPassword(email, pass)
            .addOnSuccessListener { _authState.value = Resource.Success(true) }
            .addOnFailureListener { _authState.value = Resource.Failure(it) }
    }
}
