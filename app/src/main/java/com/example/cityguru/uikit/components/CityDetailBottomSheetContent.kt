package com.example.cityguru.uikit.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.cityguru.feauture.citydetail.CityDetailViewModel
import com.example.cityguru.uikit.theme.White
import org.koin.androidx.compose.koinViewModel

@Composable
fun CityDetailBottomSheetContent(
    cityId: Int,
    viewModel: CityDetailViewModel = koinViewModel(),
    onDismiss: () -> Unit
) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(cityId) {
         viewModel.loadCityDetail(cityId)
    }

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
                        text = state.cityDetail?.name?: "",
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
                        text = state.cityDetail?.country ?: "",
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
                        text = "${state.cityDetail?.elevationMeters ?: 0} м",
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
                        text = state.cityDetail?.population?.toString() ?: "0",
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }
        }
    }
}