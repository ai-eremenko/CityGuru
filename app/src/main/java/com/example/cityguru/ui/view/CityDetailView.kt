package com.example.cityguru.ui.view

import android.content.Intent
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
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import com.example.cityguru.R
import com.example.cityguru.domain.model.CityDetail
import com.example.cityguru.presentation.citydetail.CityDetailState
import com.example.cityguru.ui.components.LoadingIndicator
import com.example.cityguru.ui.theme.Black
import com.example.cityguru.ui.theme.Purple
import com.example.cityguru.ui.theme.White

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CityDetailView(
    state: CityDetailState,
    onBackClick: () -> Unit,
    onRetry: () -> Unit
) {
    val context = LocalContext.current

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        "Информация о городе",
                        style = MaterialTheme.typography.titleLarge,
                        textAlign = TextAlign.Center,
                    )
                },
                navigationIcon = {
                    IconButton(
                        modifier = Modifier.padding(start = 16.dp),
                        onClick = onBackClick,
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_back),
                            contentDescription = "Back",
                            tint = Black,
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = White,
                    titleContentColor = Black
                )
            )
        },
        containerColor = White
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(White)
                .padding(innerPadding)
        ) {
            when {
                state.isLoading -> LoadingIndicator()
                state.error != null -> ErrorView(
                    message = state.error,
                    onRetry = onRetry
                )
                state.cityDetail != null -> CityDetailContent(
                    cityDetail = state.cityDetail,
                    onWikipediaClick = {
                        val intent = Intent(
                            Intent.ACTION_VIEW,
                            ("https://www.wikidata.org/wiki/" +
                                    "${state.cityDetail.wikiDataId}")
                                .toUri()
                        )
                        context.startActivity(intent)
                    }
                )
            }
        }
    }
}

@Composable
fun CityDetailContent(
    cityDetail: CityDetail,
    onWikipediaClick: () -> Unit
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
                onClick = onWikipediaClick,
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
