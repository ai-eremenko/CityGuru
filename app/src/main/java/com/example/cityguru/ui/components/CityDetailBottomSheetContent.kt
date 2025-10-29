package com.example.cityguru.ui.components

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
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.cityguru.domain.model.City
import com.example.cityguru.presentation.citydetail.CityDetailState
import com.example.cityguru.presentation.citydetail.CityDetailViewModel
import com.example.cityguru.ui.theme.White
import kotlinx.coroutines.flow.StateFlow

private val Any.population: Any
private val Any.elevationMeters: String
private val Any.country: String
private val Any.name: String
private val StateFlow<CityDetailState>.cityDetail: Any

@Composable
fun CityDetailBottomSheetContent(
    city: City?,
    viewModel: CityDetailViewModel,
    onDismiss: () -> Unit
) {
    val state = viewModel.state
    val  context = LocalContext.current

    LaunchedEffect(city?.id) {
        city?.id?.let { viewModel.loadCityDetail(it) }
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