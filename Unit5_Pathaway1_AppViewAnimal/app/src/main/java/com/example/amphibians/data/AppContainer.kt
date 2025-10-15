package com.example.amphibians.data

import android.content.Context

/**
 * Dependency Injection container ở cấp ứng dụng
 * Chứa các phần phụ thuộc cần thiết cho ứng dụng
 */
interface AppContainer {
    val amphibiansRepository: AmphibiansRepository
}

/**
 * Implementation của AppContainer cung cấp instance của các phần phụ thuộc
 * Sử dụng local data source đọc từ XML resources
 */
class DefaultAppContainer(private val context: Context) : AppContainer {
    
    /**
     * Local data source đọc dữ liệu từ XML resources
     */
    private val localDataSource: LocalAmphibiansDataSource by lazy {
        LocalAmphibiansDataSource(context)
    }
    
    /**
     * Implementation của AmphibiansRepository sử dụng local data
     */
    override val amphibiansRepository: AmphibiansRepository by lazy {
        LocalAmphibiansRepository(localDataSource)
    }
}
