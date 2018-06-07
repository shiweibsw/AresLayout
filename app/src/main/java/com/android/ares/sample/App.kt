package com.android.ares.sample

import android.app.Application

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        AresLayout.init(this)
        AresLayout.setEmptyLayout(R.layout.layout_empty)
        AresLayout.setLoadingLayout(R.layout.layout_loading)
        AresLayout.setNetworkErrorLayout(R.layout.layout_network_error)
    }
}