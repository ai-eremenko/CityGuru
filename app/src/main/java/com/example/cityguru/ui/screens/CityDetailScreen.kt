package com.example.cityguru.ui.screens

import android.graphics.Color
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import com.example.cityguru.R
import com.example.cityguru.presentation.citydetail.CityDetailState
import com.example.cityguru.presentation.citydetail.CityDetailViewModel
import com.example.cityguru.ui.theme.Black
import com.example.cityguru.ui.theme.Purple
import com.example.cityguru.ui.theme.White
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
            Column {
                Spacer(modifier = Modifier.height(12.dp))
                TopAppBar(
                    title = {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(24.dp),
                            contentAlignment = Alignment.BottomCenter
                        ) {
                            Text(
                                "Информация о городе",
                                style = MaterialTheme.typography.titleLarge,
                                modifier = Modifier.fillMaxWidth(),
                                textAlign = TextAlign.Center
                            )
                        }
                    },
                    navigationIcon = {
                        androidx.compose.material3.IconButton(
                            onClick = onBackClick
                        ) {
                            androidx.compose.material3.Icon(
                                painter = painterResource(id = R.drawable.ic_back),
                                contentDescription = "Back",
                                tint = Black
                            )
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = White,
                        titleContentColor = Black
                    )
                )
            }
        },
        containerColor = White
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = White)
                .padding(paddingValues)
        ) {
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
                        .background(White)
                        .padding(16.dp)
                ) {
                    Spacer(modifier = Modifier.height(18.dp))

                    Card(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(
                            modifier = Modifier.padding(horizontal = 16.dp)
                        ) {
                            Column {
                                Text(
                                    text = "Город",
                                    style = MaterialTheme.typography.labelSmall,
                                )

                                Spacer(modifier = Modifier.height(2.dp))

                                Text(
                                    text = state.cityDetail!!.name,
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
                                    text = state.cityDetail!!.country,
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
                                    text = "${state.cityDetail!!.elevationMeters} м",
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
                                    text = state.cityDetail!!.population.toString(),
                                    style = MaterialTheme.typography.bodySmall
                                )
                            }

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
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = 16.dp)
                                    .padding(bottom = 16.dp),

                                    shape = RoundedCornerShape(16.dp),

                                    colors = androidx.compose.material3.ButtonDefaults.buttonColors(
                                        containerColor = Purple
                                    )
                                ) {
                                    Text(
                                        "Открыть в Wikipedia",
                                        style = MaterialTheme.typography.labelLarge,
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
        }
    }
}