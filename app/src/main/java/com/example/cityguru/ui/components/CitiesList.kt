package com.example.cityguru.ui.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.cityguru.domain.model.City
import com.example.cityguru.ui.theme.GrayLight

@Composable
fun CitiesList(
    cities: List<City>,
    onCityClick: (Int) -> Unit
) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        itemsIndexed(cities) {index, city ->
            CityCard(
                city = city,
                onClick = { onCityClick(city.id) }
            )
            if (index < cities.lastIndex) {
                Divider(
                    color = GrayLight,
                    thickness = 1.dp,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CitiesListPreview() {
    val testCities = listOf(
        City(
            id = 1,
            name = "Berlin",
            country = "Germany",
            countryCode = "DE"
        ),
        City(
            id = 2,
            name = "Paris",
            country = "France",
            countryCode = "FR"
        ),
        City(
            id = 3,
            name = "London",
            country = "United Kingdom",
            countryCode = "GB"
        )
    )

    MaterialTheme {
        CitiesList(
            cities = testCities,
            onCityClick = { }
        )
    }
}