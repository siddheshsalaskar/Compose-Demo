package com.example.composedemo.ui.drawer

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun CustomDrawerContent(onItemClick: () -> Unit = {}) {
    Surface(
        modifier = Modifier
            .fillMaxHeight()
            .width(280.dp),
        color = Color.White,
        shape = RoundedCornerShape(
            topEnd = 24.dp,
            bottomEnd = 24.dp
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .width(280.dp)
                .background(Color.White)
        ) {
            Spacer(modifier = Modifier.height(8.dp))

            DrawerItem("PRODUCT", onClick = onItemClick)
            DrawerItem("DESIGNER", onClick = onItemClick)
            DrawerItem("COLLECTIONS", onClick = onItemClick)
            DrawerItem("NEW ARRIVALS", onClick = onItemClick)
            DrawerItem("MENSWEAR", showArrow = false, onClick = onItemClick)
            DrawerItem("REDUCTIONS", isBold = true, onClick = onItemClick)
            DrawerItem("STORES", onClick = onItemClick)
            DrawerItem("COUNTRY", onClick = onItemClick)

            Spacer(modifier = Modifier.height(8.dp))

            DrawerItem("HELP CENTRE", showArrow = false, onClick = onItemClick)
            DrawerItem("CONTACT", showArrow = false, onClick = onItemClick)

            Spacer(modifier = Modifier.height(8.dp))

            DrawerItem("SELL TO US", showArrow = false, isBold = true, onClick = onItemClick)
            DrawerItem("SIGN IN", showArrow = false, onClick = onItemClick)
        }
    }
}
