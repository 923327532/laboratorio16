package com.example.laboratorio12.ui.home

import androidx.lifecycle.ViewModel
import com.example.laboratorio12.data.model.Event
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor() : ViewModel() {
    private val firestore = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

    private val _events = MutableStateFlow<List<Event>>(emptyList())
    val events: StateFlow<List<Event>> = _events

    init {
        fetchEvents()
    }

    private fun fetchEvents() {
        val userId = auth.currentUser?.uid ?: return
        firestore.collection("events")
            .whereEqualTo("userId", userId)
            .addSnapshotListener { snapshot, _ ->
                if (snapshot != null) {
                    val list = snapshot.documents.mapNotNull { doc ->
                        doc.toObject(Event::class.java)?.copy(id = doc.id)
                    }
                    _events.value = list
                }
            }
    }

    fun saveEvent(title: String, date: String, desc: String) {
        val userId = auth.currentUser?.uid ?: return
        val event = Event(userId = userId, title = title, date = date, description = desc)
        firestore.collection("events").add(event)
    }

    fun deleteEvent(eventId: String) {
        firestore.collection("events").document(eventId).delete()
    }
}
