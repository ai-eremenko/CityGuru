package com.example.cityguru.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
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
            .height(54.dp) // Фиксированная высота 54dp
            .padding(start = 16.dp, end = 16.dp) // Отступы слева и справа по 16dp
            .clickable(onClick = onClick),
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row( // значит, что все будет в строку идти
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp), // Внутренние отступы для контента
            verticalAlignment = Alignment.CenterVertically // Выравнивание по вертикали по центру
        ) {
            // Картинка слева
            Image(
                painter = painterResource(id = R.drawable.ic_place),
                contentDescription = "City image", // Обязательный параметр для доступности
                modifier = Modifier
                    .size(24.dp) // Размер картинки
            )

            // Название города справа от картинки
            Text(
                text = city.city,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier
                    .padding(start = 12.dp) // Отступ между картинкой и текстом
                    .weight(1f) // Занимает всё доступное пространство
            )
        }
    }
}