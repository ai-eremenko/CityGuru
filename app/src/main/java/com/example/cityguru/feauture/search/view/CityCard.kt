package com.example.cityguru.feauture.search.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.cityguru.R
import com.example.cityguru.domain.model.City
import com.example.cityguru.feauture.search.SearchEvent

@Composable
fun CityCard(
    modifier: Modifier = Modifier,
    city: City,
    onEvent: (SearchEvent) -> Unit
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(54.dp)
            .padding(start = 16.dp, end = 16.dp)
            .clickable(onClick = {onEvent(SearchEvent.OnCityClicked(city))}),
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.surface),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Image(
                painter = painterResource(id = R.drawable.ic_place),
                contentDescription = "City image",
                modifier = Modifier
                    .size(24.dp)
            )

            Text(
                text = city.name + ", " + city.country,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier
                    .padding(start = 12.dp)
                    .weight(1f)
            )
        }
    }
}
