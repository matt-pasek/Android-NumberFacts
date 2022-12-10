package com.example.numberfacts.components

import android.app.Activity
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainTopBar() {
    TopAppBar(
        modifier = Modifier.fillMaxWidth(),
        title = {
            Text(text = "Number Facts", fontSize = 25.sp)
        },
        colors = topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            titleContentColor = MaterialTheme.colorScheme.onPrimary
        )
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SecondScreenTopBar() {
    val activity = (LocalContext.current as? Activity)
    TopAppBar(
        modifier = Modifier.fillMaxWidth(),
        title = {
            Text(text = "Number Facts", fontSize = 25.sp)
        },
        navigationIcon = {
            IconButton(
                onClick = { activity?.finish() },
                enabled = true,
            ) {
                Icon(Icons.Filled.ArrowBack , "backIcon", tint = MaterialTheme.colorScheme.onPrimary)
            }
        },
        colors = topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            titleContentColor = MaterialTheme.colorScheme.onPrimary
        )
    )
}