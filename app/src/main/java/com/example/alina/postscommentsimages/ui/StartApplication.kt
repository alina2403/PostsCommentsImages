package com.example.alina.postscommentsimages.ui

import android.app.Application
import com.example.alina.postscommentsimages.ui.utils.Constant
import data.ForumService
import data.Network


class StartApplication : Application() {
    companion object {
        @Volatile
        lateinit var INSTANCE: StartApplication
        lateinit var service: ForumService
        lateinit var weatherService: ForumService
    }

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
        service = Network.initService(Constant.BASE_URL)
        weatherService = Network.initService(Constant.WEATHER_URL)
    }

}