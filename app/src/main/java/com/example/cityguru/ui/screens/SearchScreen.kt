package com.example.cityguru.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.cityguru.presentation.search.SearchViewModel
import com.example.cityguru.ui.components.CityCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    viewModel: SearchViewModel = androidx.lifecycle.viewmodel.compose.viewModel(),
    onCityClick: (Int) -> Unit
) {
    val state = viewModel.state.value
    val snackbarHostState = remember { SnackbarHostState() }

    // Показываем ошибку в Snackbar
    state.error?.let { error ->
        LaunchedEffect(error) {
            val result = snackbarHostState.showSnackbar(
                message = error,
                actionLabel = "Повторить",
                duration = SnackbarDuration.Long
            )
            if (result == SnackbarResult.ActionPerformed) {
                viewModel.clearError()
                viewModel.onSearchQueryChanged("") // Перезагружаем данные
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Поиск городов") })
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // Поле поиска
            TextField(
                value = state.searchQuery, // Для демонстрации, в реальности нужно отдельное состояние для текста
                onValueChange = viewModel::onSearchQueryChanged,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                placeholder = { Text("Введите название города") },
                singleLine = true
            )

            // Список городов или состояния загрузки
            Box(modifier = Modifier.fillMaxSize()) {
                if (state.isLoading) {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                } else {
                    LazyColumn(modifier = Modifier.fillMaxSize()) { // Аналог из старого Android: RecyclerView
                        items(state.cities) { city ->
                            CityCard(
                                city = city,
                                onClick = { onCityClick(city.id) }
                            )
                        }
                    }
                }

                // Сообщение, если нет городов
                if (state.cities.isEmpty() && !state.isLoading) {
                    Text(
                        text = "Введите название города для поиска",
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }
        }
    }
}
