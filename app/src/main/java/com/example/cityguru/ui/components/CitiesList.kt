package com.example.cityguru.ui.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.cityguru.domain.model.City

@Composable
fun CitiesList(
    cities: List<City>,
    onCityClick: (Int) -> Unit
) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(cities) { city ->
            CityCard(
                city = city,
                onClick = { onCityClick(city.id) }
            )
        }
    }
}