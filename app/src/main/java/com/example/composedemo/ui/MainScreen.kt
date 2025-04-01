//package com.example.composedemo.ui
//
//import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.lazy.LazyColumn
//import androidx.compose.runtime.*
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.unit.dp
//import com.example.composedemo.viewmodel.ProductViewModel
//
//@Composable
//fun MainScreen(viewModel: ProductViewModel) {
//    val products by viewModel.products.collectAsState()
//    val banners by viewModel.banners.collectAsState()
//
//    LazyColumn(
//        modifier = Modifier.fillMaxWidth()
//    ) {
//        item { BannerCarousel(banners) }
//        item { Spacer(modifier = Modifier.height(16.dp)) }
//        item { NewArrivalsSection() }
//        item { Spacer(modifier = Modifier.height(16.dp)) }
//        item { ProductCarousel(products) }
//    }
//}