package com.example.newsapp.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width

import androidx.compose.material3.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Brush
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Language
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.OutlinedButton

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun DrawerContent(onItemClicked: () -> Unit = {}) {
    var selectedTheme by remember { mutableStateOf("Dark") }
    var selectedLang by remember { mutableStateOf("English") }

    Column(
        modifier = Modifier
            .fillMaxHeight()
            .width(260.dp)
            .background(Color.Black)
            .padding(16.dp)
    ) {
        Box(
            modifier = Modifier
                .background(Color.White)
                .fillMaxWidth()
                .fillMaxHeight(0.15f), contentAlignment = Alignment.Center
        ) {
            Text(
                "News App", color = Color.Black,
                fontSize = 22.sp,
                modifier = Modifier.padding(bottom = 20.dp),
                fontWeight = FontWeight.Bold
            )
        }
        // Go to Home
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onItemClicked() }
                .padding(vertical = 12.dp)) {
            Icon(Icons.Filled.Home, contentDescription = null, tint = Color.White)
            Text(" Go To Home", color = Color.White, fontSize = 16.sp)
        }

        Spacer(Modifier.height(20.dp))
        HorizontalDivider(color = Color.White)
        Spacer(Modifier.height(20.dp))


        // Theme Dropdown
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(Icons.Default.Brush, contentDescription = null, tint = Color.White)
            Text("Theme", color = Color.White,modifier = Modifier.padding(start = 10.dp))
        }
        DropdownMenuBox(
            options = listOf("Light", "Dark"),
            selected = selectedTheme,
            onSelect = { selectedTheme = it }
        )

        Spacer(Modifier.height(20.dp))
        HorizontalDivider(color = Color.White)
        Spacer(Modifier.height(20.dp))

        // Language Dropdown
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(Icons.Default.Language, contentDescription = null, tint = Color.White)
            Text("Language", color = Color.White,modifier = Modifier.padding(start = 10.dp))
        }
        DropdownMenuBox(
            options = listOf("English", "Arabic"),
            selected = selectedLang,
            onSelect = { selectedLang = it }
        )
    }
}

@Composable
fun DropdownMenuBox(options: List<String>, selected: String, onSelect: (String) -> Unit) {
    var expanded by remember { mutableStateOf(false) }

    Box {
        OutlinedButton(
            onClick = { expanded = true },
            modifier = Modifier.fillMaxWidth(),
            border = BorderStroke(1.dp, Color.White),
            ) {
            Row(
                modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(selected)
                Icon(Icons.Default.ArrowDropDown, contentDescription = null, tint = Color.White)
            }
        }

        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            options.forEach {
                DropdownMenuItem(
                    text = { Text(it) },
                    onClick = {
                        onSelect(it)
                        expanded = false
                    }
                )
            }
        }
    }
}
