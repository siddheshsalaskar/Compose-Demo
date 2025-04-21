package com.example.composedemo.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Sort
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun FloatingFilterSortBar(
    onFilterClick: () -> Unit,
    onSortClick: () -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .shadow(4.dp),
        color = Color.White
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            FilterSortButton(
                icon = Icons.Default.FilterList,
                label = "Filters",
                onClick = onFilterClick,
                modifier = Modifier.weight(1f)
            )

            Divider(
                color = Color(0xFFE0E0E0),
                modifier = Modifier
                    .fillMaxHeight()
                    .width(1.dp)
            )

            FilterSortButton(
                icon = Icons.Default.Sort,
                label = "Sort By",
                onClick = onSortClick,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
private fun FilterSortButton(
    icon: ImageVector,
    label: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    TextButton(
        onClick = onClick,
        modifier = modifier.fillMaxHeight()
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = Color.Black
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = label,
            fontSize = 16.sp,
            color = Color.Black
        )
    }
}

@Composable
fun FilterBottomSheetContent() {
    Column(modifier = Modifier.padding(16.dp)) {
        Text("Filter Options", style = MaterialTheme.typography.titleMedium)
        Spacer(modifier = Modifier.height(8.dp))
        Text("• Category")
        Text("• Price Range")
        Text("• Rating")
    }
}

@Composable
fun SortBottomSheetContent() {
    Column(modifier = Modifier.padding(16.dp)) {
        Text("Sort By", style = MaterialTheme.typography.titleMedium)
        Spacer(modifier = Modifier.height(8.dp))
        Text("• Newest First")
        Text("• Price Low to High")
        Text("• Price High to Low")
    }
}

