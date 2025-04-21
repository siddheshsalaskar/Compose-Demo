package com.example.composedemo.ui.product

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.composedemo.model.AlgoliaProduct

@Composable
fun ProductCarousel(products: List<AlgoliaProduct>, onProductClick: (String) -> Unit) {
    LazyRow(modifier = Modifier.fillMaxWidth()) {
        items(products) { product ->
            ProductGridCard(
                product = product,
                onClick = { onProductClick(product.boxId.toString()) })
        }
    }
}