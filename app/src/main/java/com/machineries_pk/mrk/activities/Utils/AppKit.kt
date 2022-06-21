package com.machineries_pk.mrk.activities.Utils

import android.app.Application
import io.paperdb.Paper

class AppKit: Application() {
    override fun onCreate() {
        super.onCreate()
        Paper.init(this)
    }
}