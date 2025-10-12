package com.example.cityguru.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import com.example.cityguru.presentation.citydetail.CityDetailViewModel
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CityDetailScreen(
    cityId: Int,
    viewModel: CityDetailViewModel = koinViewModel(),
    onBackClick: () -> Unit
) {
    val state by viewModel.state.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(cityId) {
        viewModel.loadCityDetail(cityId)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Информация о городе") },
                navigationIcon = {
                    Button(onClick = onBackClick) {
                        Text("Назад")
                    }
                }
            )
        }
    ) { paddingValues ->
        when {
            state.isLoading -> CircularProgressIndicator()

            state.error != null -> {
                Text(
                    text = "Ошибка: ${state.error}",
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                        .padding(16.dp)
                )
            }

            state.cityDetail != null -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                        .padding(16.dp)
                ) {
                    Card(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp)
                        ) {

                            Spacer(modifier = Modifier.height(16.dp))
                            Column {
                                Text(
                                    text = "Город",
                                    style = MaterialTheme.typography.labelMedium,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                                Text(
                                    text = state.cityDetail!!.name,
                                    style = MaterialTheme.typography.bodyMedium
                                )
                            }

                            Spacer(modifier = Modifier.height(16.dp))
                            Column {
                                Text(
                                    text = "Страна",
                                    style = MaterialTheme.typography.labelMedium,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                                Text(
                                    text = state.cityDetail!!.country,
                                    style = MaterialTheme.typography.bodyMedium
                                )
                            }

                            Spacer(modifier = Modifier.height(16.dp))
                            Column {
                                Text(
                                    text = "Высота над уровнем моря",
                                    style = MaterialTheme.typography.labelMedium,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                                Text(
                                    text = "${state.cityDetail!!.elevationMeters} м",
                                    style = MaterialTheme.typography.bodyMedium
                                )
                            }

                            Spacer(modifier = Modifier.height(16.dp))
                            Column {
                                Text(
                                    text = "Население",
                                    style = MaterialTheme.typography.labelMedium,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                                Text(
                                    text = state.cityDetail!!.population.toString(),
                                    style = MaterialTheme.typography.bodyMedium
                                )
                            }

                            Spacer(modifier = Modifier.height(8.dp))

                            Spacer(modifier = Modifier.height(16.dp))
                            if (!state.cityDetail!!.wikiDataId.isNullOrEmpty()) {
                                Button(
                                    onClick = {
                                        val intent = android.content.Intent(
                                            android.content.Intent.ACTION_VIEW,
                                            ("https://www.wikidata.org/wiki/" +
                                                    "${state.cityDetail!!.wikiDataId}")
                                                .toUri()
                                        )
                                        context.startActivity(intent)
                                    },
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Text("Открыть в Wikipedia")
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}