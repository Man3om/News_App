package com.example.newsapp.utils

import com.example.newsapp.ui.NewsApplication

object ApplicationUtils {
    fun isNetworkAvailable(): Boolean{
        val networkMonitor = Networking(
            NewsApplication.instance)

        return networkMonitor.isConnected.value
    }
}