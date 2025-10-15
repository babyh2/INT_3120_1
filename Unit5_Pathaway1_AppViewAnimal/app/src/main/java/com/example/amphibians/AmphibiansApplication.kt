package com.example.amphibians

import android.app.Application
import com.example.amphibians.data.AppContainer
import com.example.amphibians.data.DefaultAppContainer

/**
 * Lớp Application tùy chỉnh để khởi tạo và giữ AppContainer
 */
class AmphibiansApplication : Application() {
    /**
     * AppContainer instance được sử dụng bởi các lớp khác để lấy dependencies
     */
    lateinit var container: AppContainer
    
    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer(applicationContext)
    }
}
