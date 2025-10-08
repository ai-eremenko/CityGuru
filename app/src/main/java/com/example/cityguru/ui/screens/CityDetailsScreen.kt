package com.example.cityguru.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScopeInstance.align
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.geocitiesapp.presentation.city_detail.CityDetailViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CityDetailScreen(
    cityId: Int,
    viewModel: CityDetailViewModel = hiltViewModel(),
    onBackClick: () -> Unit
) {
    val state = viewModel.state.value
    val context = LocalContext.current

    // Загружаем данные при открытии экрана
    LaunchedEffect(cityId) {
        viewModel.loadCityDetail(cityId)
    }

    Scaffold( //каркас экрана
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
            state.isLoading -> {
                CircularProgressIndicator(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                        .align(Alignment.CenterHorizontally)
                )
            }

            state.error != null -> {
                Text(
                    text = "Ошибка: ${state.error}",
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                        .padding(16.dp)
                )
            }

            state.city != null -> {
                Column( // значит, что в вертикально пойдет информация
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
                            // Город
                            Column {
                                Text(
                                    text = "Город",
                                    style = MaterialTheme.typography.labelMedium,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                                Text(
                                    text = state.city,
                                    style = MaterialTheme.typography.bodyMedium
                                )
                            }

                            Spacer(modifier = Modifier.height(16.dp))
                            // Страна
                            Column {
                                Text(
                                    text = "Страна",
                                    style = MaterialTheme.typography.labelMedium,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                                Text(
                                    text = state.city.country?.toString() ?: "Неизвестно",
                                    style = MaterialTheme.typography.bodyMedium
                                )
                            }

                            Spacer(modifier = Modifier.height(16.dp))
                            // Население
                            Column {
                                Text(
                                    text = "Население",
                                    style = MaterialTheme.typography.labelMedium,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                                Text(
                                    text = state.city.population?.toString() ?: "Неизвестно",
                                    style = MaterialTheme.typography.bodyMedium
                                )
                            }

                            Spacer(modifier = Modifier.height(16.dp))
                            // Высота над уровнем моря
                            Column {
                                Text(
                                    text = "Высота над уровнем моря",
                                    style = MaterialTheme.typography.labelMedium,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                                Text(
                                    text = if (state.city.elevationMeters != null)
                                        "${state.city.elevationMeters} м"
                                    else "Неизвестно",
                                    style = MaterialTheme.typography.bodyMedium
                                )
                            }

                            Spacer(modifier = Modifier.height(8.dp))

                    Spacer(modifier = Modifier.height(16.dp))

                    // Кнопка Wiki
                    if (!state.city.wikiDataId.isNullOrEmpty()) {
                        Button(
                            onClick = {
                                val intent = android.content.Intent(
                                    android.content.Intent.ACTION_VIEW,
                                    "https://www.wikidata.org/wiki/${state.city.wikiDataId}".toUri()
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