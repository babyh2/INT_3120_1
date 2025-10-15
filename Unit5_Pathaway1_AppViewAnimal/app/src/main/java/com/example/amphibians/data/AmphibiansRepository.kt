package com.example.amphibians.data

import com.example.amphibians.model.Amphibian

/**
 * Repository interface để quản lý dữ liệu động vật lưỡng cư
 */
interface AmphibiansRepository {
    suspend fun getAmphibians(): List<Amphibian>
}
