package com.example.proyectologin005d.ui.pages

import android.net.Uri
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.proyectologin005d.viewmodel.CartViewModel
import kotlinx.coroutines.launch

private fun assetUrlFromJsonPath(path: String?): String? {
    if (path.isNullOrBlank()) return null
    val cleaned = path.removePrefix("../assets/").removePrefix("./assets/").removePrefix("assets/").removePrefix("/")
    return "file:///android_asset/img/${Uri.encode(cleaned)}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CarritoScreen(cartViewModel: CartViewModel = viewModel(), navController: NavController) {
    val cartState by cartViewModel.uiState.collectAsState()
    val scope = rememberCoroutineScope()

    Scaffold(
        topBar = { TopAppBar(title = { Text("Carrito de Compras") }) },
        bottomBar = {
            if (cartState.cart.isNotEmpty()) {
                BottomAppBar(
                    containerColor = MaterialTheme.colorScheme.surface,
                    tonalElevation = 8.dp
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Total: ${cartState.total}",
                            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
                        )
                        Spacer(modifier = Modifier.weight(1f))
                        Button(
                            onClick = { navController.navigate("payment/${cartState.total}") },
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFD1DC))
                        ) {
                            Text("Pagar", color = Color.Black)
                        }
                    }
                }
            }
        }
    ) { padding ->
        Column(modifier = Modifier.fillMaxSize().padding(padding)) {
            if (cartState.cart.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Tu carrito está vacío", style = MaterialTheme.typography.bodyLarge)
                }
            } else {
                LazyColumn(modifier = Modifier.weight(1f)) {
                    items(cartState.cart, key = { it.product.id }) { item ->
                        AnimatedVisibility(
                            visible = true,
                            enter = slideInHorizontally(initialOffsetX = { -it }) + fadeIn(),
                            exit = fadeOut(animationSpec = spring())
                        ) {
                            Card(modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 6.dp)) {
                                Row(modifier = Modifier.padding(8.dp), verticalAlignment = Alignment.CenterVertically) {
                                    AsyncImage(
                                        model = assetUrlFromJsonPath(item.product.imagen),
                                        contentDescription = item.product.nombre,
                                        modifier = Modifier.size(80.dp).padding(end = 8.dp),
                                        contentScale = ContentScale.Crop
                                    )
                                    Column(modifier = Modifier.weight(1f)) {
                                        Text(item.product.nombre, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                                        Text("$${item.product.precio}", style = MaterialTheme.typography.bodyLarge, color = MaterialTheme.colorScheme.primary)
                                    }
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        IconButton(onClick = { cartViewModel.removeFromCart(item.product) }) {
                                            Icon(Icons.Default.Remove, contentDescription = "Quitar")
                                        }
                                        Text("${item.quantity}", style = MaterialTheme.typography.bodyLarge, modifier = Modifier.padding(horizontal = 8.dp))
                                        IconButton(onClick = { cartViewModel.addToCart(item.product) }) {
                                            Icon(Icons.Default.Add, contentDescription = "Añadir")
                                        }
                                    }
                                }
                            }
                        }
                    }
                    item {
                        Spacer(modifier = Modifier.padding(vertical = 8.dp))
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                            OutlinedButton(
                                onClick = { scope.launch { cartViewModel.clearCart() } },
                            ) {
                                Text("Limpiar carrito")
                            }
                        }
                    }
                }
            }
        }
    }
}