package com.example.numberfacts

import android.content.Intent
import android.os.Bundle
import android.os.StrictMode
import android.os.StrictMode.ThreadPolicy
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.example.numberfacts.ApiRequests.Companion.getNumberFact
import com.example.numberfacts.ApiRequests.Companion.getRandomMathFact
import com.example.numberfacts.components.MainTopBar
import com.example.numberfacts.ui.theme.NumberFactsTheme
import com.google.gson.Gson


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NumberFactsTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AppView()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppView() {
    // allow network requests on main, shouldn't be done, but requests are small and fast
    val policy = ThreadPolicy.Builder().permitAll().build()
    StrictMode.setThreadPolicy(policy)

    val context = LocalContext.current
    var number by remember { mutableStateOf(TextFieldValue("")) }
    val history = remember { mutableStateListOf<Fact>() }
    Scaffold(
        modifier = Modifier.padding(0.dp),
        topBar = { MainTopBar() },
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
            ) {
                Column(
                    modifier = Modifier
                        .padding(10.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        OutlinedTextField(
                            value = number,
                            onValueChange = { number = it },
                            label = { Text("Number") },
                            placeholder = { Text("Enter a number") },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            singleLine = true,
                            textStyle = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier
                                .weight(0.5f),
                        )
                        Button(
                            modifier = Modifier
                                .weight(0.3f)
                                .padding(10.dp, 0.dp),
                            onClick = {
                                if (number.text.isNotEmpty()) {
                                    val fact = getNumberFact(number.text.toInt(), context)
                                    if (fact != null) {
                                        history.add(fact)
                                    }
                                }
                            },
                        ) {
                            Text("Get Fact")
                        }
                    }
                    Row {
                        Button(
                            modifier = Modifier
                                .fillMaxWidth(),
                            onClick = {
                                val fact = getRandomMathFact(context)
                                if (fact != null) {
                                    history.add(fact)
                                }
                            },
                        ) {
                            Text("Get Random Fact")
                        }
                    }
                }
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp, 10.dp),
            ) {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    items(history) { fact ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    val gson = Gson()
                                    val intent = Intent(context, FactActivity::class.java)
                                    intent.putExtra("fact", gson.toJson(fact))
                                    context.startActivity(intent)
                                },
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,

                            ) {
                            Text(
                                modifier = Modifier.weight(0.2f),
                                text = fact.number.toString(),
                                style = MaterialTheme.typography.displayLarge,
                                color = MaterialTheme.colorScheme.primary,
                            )
                            Text(
                                modifier = Modifier.weight(0.9f),
                                text = fact.text,
                                style = MaterialTheme.typography.bodyMedium,
                                maxLines = 1,
                            )
                        }
                    }
                    if (history.isEmpty()) {
                        item {
                            Text(
                                text = "No history yet",
                                style = MaterialTheme.typography.bodyLarge,
                            )
                        }
                    }
                }
            }
        }
    }
}
