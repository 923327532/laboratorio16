package com.example.laboratorio12.core
// Esta clase es necesaria para manejar los estados de carga, éxito y error
sealed class Resource<out T> {
    object Loading : Resource<Nothing>()
    data class Success<out T>(val data: T) : Resource<T>()
    data class Error(val message: String) : Resource<Nothing>()
}
