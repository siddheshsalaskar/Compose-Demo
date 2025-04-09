package com.example.composedemo.ui.theme

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.composedemo.R

@Preview
@Composable
fun SearchBar() {
    Row( modifier = Modifier.fillMaxWidth(1f).background(Color.White).padding(8.dp).border(BorderStroke(2.dp, Color.Gray)) .padding(4.dp)
    ) {
        var text by remember { mutableStateOf("") }

        TextField(
            text,
            onValueChange = {text = it},
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                disabledContainerColor = Color.Transparent,
                errorContainerColor = Color.Transparent,
                disabledTextColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent
                ),
            modifier = Modifier.weight(.9f).background(Color.White),
            placeholder = { Text("Search 'Channel' or 'Speedy'")},
            shape = CircleShape
        )


        Image(imageVector = Icons.Default.Search,
            "search icon",
            modifier = Modifier.align(Alignment.CenterVertically)
                .weight(.1f) )

    }
}