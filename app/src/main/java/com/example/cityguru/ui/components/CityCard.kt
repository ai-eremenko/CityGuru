package com.example.cityguru.ui.components

import android.R.attr.type
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.cityguru.R
import com.example.cityguru.domain.model.City

@Composable
fun CityCard(
    city: City,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(54.dp)
            .padding(start = 16.dp, end = 16.dp)
            .clickable(onClick = onClick),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.surface)
                .padding(horizontal = 16.dp),
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

@Preview(showBackground = true)
@Composable
fun CityCardPreview() {
    val testCity = City(
        id = 1,
        name = "Berlin",
        country = "Germany",
        countryCode = "DE"
    )

    MaterialTheme {
        CityCard(
            city = testCity,
            onClick = { }
        )
    }
}