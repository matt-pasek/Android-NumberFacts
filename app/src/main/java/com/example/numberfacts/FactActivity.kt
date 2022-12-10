package com.example.numberfacts

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.numberfacts.ApiRequests.Companion.getNumberFact
import com.example.numberfacts.ApiRequests.Companion.getRandomMathFact
import com.example.numberfacts.components.SecondScreenTopBar
import com.example.numberfacts.ui.theme.NumberFactsTheme
import com.google.gson.Gson

class FactActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val gson = Gson()
        val fact = gson.fromJson(intent.getStringExtra("fact"), Fact::class.java)
        setContent {
            NumberFactsTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    FactView(fact)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FactView(fact: Fact) {
    Scaffold(
        modifier = Modifier.padding(0.dp),
        topBar = { SecondScreenTopBar() },
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(MaterialTheme.colorScheme.background)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(0.5f)
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = fact.number.toString(),
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.primary
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(0.5f),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = fact.text,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
    }
}
