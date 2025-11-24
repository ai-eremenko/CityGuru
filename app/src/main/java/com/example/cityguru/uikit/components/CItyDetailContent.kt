package com.example.cityguru.uikit.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.cityguru.domain.model.CityDetail
import com.example.cityguru.feauture.citydetail.CityDetailEvent
import com.example.cityguru.uikit.theme.Purple
import com.example.cityguru.uikit.theme.White

@Composable
fun CityDetailContent(
    cityDetail: CityDetail,
    onEvent: (CityDetailEvent) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
            .background(White)
    ) {
        Spacer(modifier = Modifier.height(8.dp))

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            colors = CardDefaults.cardColors(
                containerColor = White
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .background(White)
            ) {
                Column {
                    Text(
                        text = "Город",
                        style = MaterialTheme.typography.labelSmall,
                    )

                    Spacer(modifier = Modifier.height(2.dp))

                    Text(
                        text = cityDetail.name,
                        style = MaterialTheme.typography.bodySmall
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))
                Column {
                    Text(
                        text = "Страна",
                        style = MaterialTheme.typography.labelSmall,
                    )

                    Spacer(modifier = Modifier.height(2.dp))

                    Text(
                        text = cityDetail.country,
                        style = MaterialTheme.typography.bodySmall
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))
                Column {
                    Text(
                        text = "Высота над уровнем моря",
                        style = MaterialTheme.typography.labelSmall,
                    )

                    Spacer(modifier = Modifier.height(2.dp))

                    Text(
                        text = "${cityDetail.elevationMeters} м",
                        style = MaterialTheme.typography.bodySmall
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))
                Column {
                    Text(
                        text = "Население",
                        style = MaterialTheme.typography.labelSmall,
                    )

                    Spacer(modifier = Modifier.height(2.dp))

                    Text(
                        text = cityDetail.population.toString(),
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }
        }

        if (!cityDetail.wikiDataId.isNullOrEmpty()) {
            Button(
                onClick = { onEvent(CityDetailEvent.OnWikidataButtonClicked) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .height(56.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Purple
                )
            ) {
                Text(
                    "Открыть в Wikidata",
                    style = MaterialTheme.typography.labelLarge,
                )
            }
        }
    }
}
