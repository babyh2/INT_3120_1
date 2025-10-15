package com.example.amphibians.data

import com.example.amphibians.model.Amphibian

/**
 * Repository implementation sử dụng Local data source
 * Đọc dữ liệu từ XML resources thay vì từ network
 */
class LocalAmphibiansRepository(
    private val localDataSource: LocalAmphibiansDataSource
) : AmphibiansRepository {
    override suspend fun getAmphibians(): List<Amphibian> {
        return localDataSource.getAmphibians()
    }
}
