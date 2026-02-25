package com.lockinapp.focusblocker

import android.app.Application
import com.lockinapp.focusblocker.data.Graph

class FocusBlockerApp : Application() {

    override fun onCreate() {
        super.onCreate()
        Graph.provide(this)
    }
}


