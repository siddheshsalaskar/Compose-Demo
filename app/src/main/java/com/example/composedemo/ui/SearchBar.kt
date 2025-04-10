package com.example.composedemo.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.composedemo.model.AlgoliaProduct
import com.example.composedemo.viewmodel.SearchViewModel

@Composable
fun SearchBar(navController: NavController, viewModel: SearchViewModel = viewModel()) {
    var query by remember { mutableStateOf("") }
    var suggestions by remember { mutableStateOf<List<AlgoliaProduct>>(emptyList()) }
    val focusManager = LocalFocusManager.current

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .border(BorderStroke(2.dp, Color.Gray))
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(4.dp)
            ) {
                TextField(
                    value = query,
                    onValueChange = {
                        query = it
                        if (it.length >= 3) {
                            viewModel.search(it) { result ->
                                suggestions = result.take(5)
                            }
                        } else {
                            suggestions = emptyList()
                        }
                    },
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        disabledContainerColor = Color.Transparent,
                        errorContainerColor = Color.Transparent,
                        disabledTextColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent
                    ),
                    modifier = Modifier
                        .weight(0.9f)
                        .background(Color.White),
                    placeholder = { Text("Search 'Channel' or 'Speedy'") },
                    shape = CircleShape,
                    singleLine = true
                )

                Image(
                    imageVector = Icons.Default.Search,
                    contentDescription = "search icon",
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .weight(0.1f)
                        .clickable {
                            if (query.length >= 3) {
                                focusManager.clearFocus()
                                suggestions = emptyList()
                                navController.navigate("searchResults/${query}")
                            }
                        }
                )
            }

            if (suggestions.isNotEmpty()) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White)
                        .border(BorderStroke(1.dp, Color.LightGray))
                ) {
                    items(suggestions.take(5)) { product ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    query = product.boxName ?: ""
                                    suggestions = emptyList()
                                }
                                .padding(8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            AsyncImage(
                                model = product.imageUrls?.medium,
                                contentDescription = product.boxName,
                                modifier = Modifier
                                    .padding(end = 12.dp)
                                    .size(40.dp)
                            )

                            Column {
                                Text(
                                    text = product.boxName ?: "Unnamed Product",
                                    color = Color.Black
                                )
                                Text(
                                    text = product.categoryName ?: "",
                                    color = Color.Gray,
                                    modifier = Modifier.padding(top = 2.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}