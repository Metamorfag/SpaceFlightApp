package com.example.spaceflightapp.di

import android.app.Application

object DI {

    fun initialize(app: Application) {
        NetworkDI.initialize()
        MainScreenDI.initialize(app)
    }
}