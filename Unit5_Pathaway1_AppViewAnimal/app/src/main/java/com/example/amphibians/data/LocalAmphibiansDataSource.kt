package com.example.amphibians.data

import android.content.Context
import com.example.amphibians.R
import com.example.amphibians.model.Amphibian

/**
 * Local data source đọc dữ liệu từ XML resources
 */
class LocalAmphibiansDataSource(private val context: Context) {
    
    /**
     * Đọc dữ liệu động vật lưỡng cư từ XML resources
     */
    fun getAmphibians(): List<Amphibian> {
        val names = context.resources.getStringArray(R.array.amphibian_names)
        val types = context.resources.getStringArray(R.array.amphibian_types)
        val descriptions = context.resources.getStringArray(R.array.amphibian_descriptions)
        val images = context.resources.getStringArray(R.array.amphibian_images)
        
        return names.indices.map { index ->
            Amphibian(
                name = names[index],
                type = types[index],
                description = descriptions[index],
                imgSrc = images[index]
            )
        }
    }
}
