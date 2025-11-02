package com.example.cityguru.ui.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.cityguru.R
import com.example.cityguru.presentation.search.SearchState
import com.example.cityguru.ui.components.CitiesList
import com.example.cityguru.ui.components.LoadingIndicator
import com.example.cityguru.ui.theme.AccentBrand
import com.example.cityguru.ui.theme.Black
import com.example.cityguru.ui.theme.Gray
import com.example.cityguru.ui.theme.White

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchView(
    uiState: SearchState,
    searchQuery: String,
    onSearchQueryChanged: (String) -> Unit,
    onCityClick: (Int) -> Unit,
    loadCities: () -> Unit
) {
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
                                "Список городов",
                                style = MaterialTheme.typography.titleLarge,
                                modifier = Modifier.fillMaxWidth(),
                                textAlign = TextAlign.Center
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
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = searchQuery,
                onValueChange = onSearchQueryChanged,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .background(
                        color = Gray,
                        shape = RoundedCornerShape(16.dp)
                    ),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    containerColor = Color.Transparent,
                    unfocusedBorderColor = Color.Transparent,
                    focusedBorderColor = AccentBrand,
                    cursorColor = AccentBrand
                ),

                shape = RoundedCornerShape(16.dp),
                textStyle = MaterialTheme.typography.titleMedium ,

                placeholder = {
                    Text(
                        "Введите название города",
                        style = MaterialTheme.typography.titleSmall,
                    ) },

                trailingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_search),
                        contentDescription = "Search icon",
                        tint = Black,
                        modifier = Modifier
                            .size(24.dp)

                    )
                },

                singleLine = true
            )

            Spacer(modifier = Modifier.height(8.dp))

            when {
                uiState.isLoading -> LoadingIndicator()
                uiState.error != null -> ErrorView(
                    message = uiState.error,
                    onRetry = loadCities
                )

                else -> CitiesList(
                    cities = uiState.cities,
                    onCityClick = onCityClick
                )
            }
        }
    }
}
