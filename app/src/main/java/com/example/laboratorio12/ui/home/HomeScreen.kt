package com.example.laboratorio12.ui.home

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.*
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.laboratorio12.data.model.Event
import com.google.firebase.auth.FirebaseAuth

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    onLogout: () -> Unit
) {
    val events by viewModel.events.collectAsState()

    val user = FirebaseAuth.getInstance().currentUser
    val userName = user?.displayName ?: user?.email ?: "Usuario"

    var showDialog by remember { mutableStateOf(false) }
    var selectedEvent by remember { mutableStateOf<Event?>(null) }

    Scaffold(
        topBar = {
            HomeTopBar(userName = userName, onLogout = onLogout)
        },

        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    selectedEvent = null
                    showDialog = true
                },
                containerColor = Color(0xFFFF6A00),
                contentColor = Color.White,
                shape = CircleShape,
                modifier = Modifier.size(65.dp)
            ) {
                Icon(Icons.Default.Add, contentDescription = null, modifier = Modifier.size(30.dp))
            }
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(horizontal = 18.dp, vertical = 12.dp)
        ) {

            if (events.isEmpty()) {
                EmptyState()
            } else {
                LazyColumn(verticalArrangement = Arrangement.spacedBy(18.dp)) {
                    items(events) { event ->
                        EventCard(
                            event = event,
                            onEdit = {
                                selectedEvent = event
                                showDialog = true
                            },
                            onDelete = { viewModel.deleteEvent(event.id) }
                        )
                    }
                }
            }
        }

        if (showDialog) {
            EventDialogPRO(
                event = selectedEvent,
                onDismiss = { showDialog = false },
                onConfirm = { title, date, desc ->
                    viewModel.saveEvent(title, date, desc)
                    showDialog = false
                }
            )
        }
    }
}

@Composable
fun HomeTopBar(
    userName: String,
    onLogout: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                Brush.verticalGradient(
                    listOf(
                        Color(0xFFFF6A00),
                        Color(0xFFFF8C3A)
                    )
                )
            )
            .padding(vertical = 28.dp)
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {

            Text(
                text = "Hola, $userName 👋",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )

            Text(
                text = "Administra tus eventos fácilmente",
                fontSize = 14.sp,
                color = Color.White.copy(alpha = 0.9f)
            )
        }

        IconButton(
            onClick = {
                FirebaseAuth.getInstance().signOut()
                onLogout()
            },
            modifier = Modifier.align(Alignment.CenterEnd).padding(end = 16.dp)
        ) {
            Icon(Icons.Default.ExitToApp, contentDescription = null, tint = Color.White)
        }
    }
}

@Composable
fun EmptyState() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {

            Icon(
                Icons.Default.EventBusy,
                contentDescription = null,
                tint = Color.Gray,
                modifier = Modifier.size(60.dp)
            )
            Spacer(Modifier.height(12.dp))
            Text("No tienes eventos aún", fontSize = 17.sp, color = Color.Gray)
            Text("Toca el botón + para agregar uno", fontSize = 13.sp, color = Color.LightGray)
        }
    }
}

@Composable
fun EventCard(
    event: Event,
    onEdit: () -> Unit,
    onDelete: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        modifier = Modifier
            .fillMaxWidth()
            .animateContentSize()
    ) {
        Column(modifier = Modifier.padding(20.dp)) {

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.Event, contentDescription = null, tint = Color(0xFFFF6A00))
                Spacer(Modifier.width(10.dp))
                Text(event.title, fontSize = 20.sp, fontWeight = FontWeight.Bold)
            }

            Spacer(Modifier.height(6.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.CalendarToday, contentDescription = null, tint = Color.Gray, modifier = Modifier.size(16.dp))
                Spacer(Modifier.width(6.dp))
                Text(event.date, fontSize = 13.sp, color = Color.Gray)
            }

            if (event.description.isNotBlank()) {
                Spacer(Modifier.height(12.dp))
                Text(event.description, fontSize = 15.sp)
            }

            Spacer(Modifier.height(14.dp))
            Divider(color = Color(0xFFF0F0F0))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                TextButton(onClick = onEdit) {
                    Icon(Icons.Default.Edit, contentDescription = null, modifier = Modifier.size(18.dp))
                    Spacer(Modifier.width(4.dp))
                    Text("Editar")
                }

                TextButton(
                    onClick = onDelete,
                    colors = ButtonDefaults.textButtonColors(contentColor = Color.Red)
                ) {
                    Icon(Icons.Default.Delete, contentDescription = null, modifier = Modifier.size(18.dp))
                    Spacer(Modifier.width(4.dp))
                    Text("Eliminar")
                }
            }
        }
    }
}


@Composable
fun EventDialogPRO(
    event: Event?,
    onDismiss: () -> Unit,
    onConfirm: (String, String, String) -> Unit
) {
    var title by remember { mutableStateOf(event?.title ?: "") }
    var date by remember { mutableStateOf(event?.date ?: "") }
    var desc by remember { mutableStateOf(event?.description ?: "") }

    AlertDialog(
        onDismissRequest = onDismiss,

        title = {
            Text(
                if (event == null) "Nuevo evento" else "Editar evento",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )
        },

        text = {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {

                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("Título") },
                    shape = RoundedCornerShape(14.dp),
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = date,
                    onValueChange = { date = it },
                    label = { Text("Fecha (DD/MM/AAAA)") },
                    shape = RoundedCornerShape(14.dp),
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = desc,
                    onValueChange = { desc = it },
                    label = { Text("Descripción") },
                    singleLine = false,
                    maxLines = 4,
                    shape = RoundedCornerShape(14.dp),
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },

        confirmButton = {
            Button(
                onClick = { onConfirm(title, date, desc) },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFFF6A00),
                    contentColor = Color.White
                )
            ) {
                Text("Guardar", fontWeight = FontWeight.Bold)
            }
        },

        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancelar")
            }
        }
    )
}