package com.example.cityguru.feauture.map

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.core.graphics.createBitmap
import com.example.cityguru.R
import com.example.cityguru.domain.model.City

class CityMarkerCreator(private val context: Context) {

    fun createCityMarker(city: City): Bitmap {
        val markerView = LayoutInflater.from(context).inflate(
            R.layout.layout_city_marker, null)

        val textView = markerView.findViewById<TextView>(R.id.marker_text)
        textView.text = getDisplayName(city.name)

        markerView.measure(
            View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
            View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
        )

        markerView.layout(0, 0, markerView.measuredWidth, markerView.measuredHeight)

        val bitmap = createBitmap(markerView.measuredWidth, markerView.measuredHeight)

        val canvas = Canvas(bitmap)
        markerView.draw(canvas)

        return bitmap
    }

    private fun getDisplayName(cityName: String): String {
        return if (cityName.length > 10) "${cityName.substring(0, 8)}.." else cityName
    }
}