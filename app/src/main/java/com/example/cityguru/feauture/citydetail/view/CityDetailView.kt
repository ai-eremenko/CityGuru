package com.example.cityguru.feauture.citydetail.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
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
import com.example.cityguru.R
import com.example.cityguru.feauture.citydetail.CityDetailEvent
import com.example.cityguru.feauture.citydetail.CityDetailState
import com.example.cityguru.uikit.components.CityDetailContent
import com.example.cityguru.uikit.theme.Black
import com.example.cityguru.uikit.theme.White

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CityDetailView(
    state: CityDetailState,
    onEvent: (CityDetailEvent) -> Unit
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
                        onClick = { onEvent(CityDetailEvent.OnBackButtonClicked) },
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
                state.cityDetail != null ->
                    CityDetailContent(
                        cityDetail = state.cityDetail,
                        onEvent = onEvent
                    )
            }
        }
    }
}
