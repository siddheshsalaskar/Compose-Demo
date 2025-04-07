package com.example.composedemo.ui

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import com.example.composedemo.BottomNavItem

@Composable
fun BottomNavigationBar(selectedItem: BottomNavItem, onItemSelected: (BottomNavItem) -> Unit) {
    NavigationBar(containerColor = Color(0xFF00685E)) {
        val items = listOf(
            BottomNavItem.Home,
            BottomNavItem.Favourites,
            BottomNavItem.Basket,
            BottomNavItem.Account
        )

        items.forEach { item ->
            NavigationBarItem(
                selected = selectedItem == item,
                onClick = { onItemSelected(item) },
                icon = {
                    Icon(
                        painter = painterResource(id = item.icon),
                        contentDescription = item.title,
                        tint = Color.White
                    )
                },
                label = {
                    Text(text = item.title, color = Color.White)
                }
            )
        }
    }
}