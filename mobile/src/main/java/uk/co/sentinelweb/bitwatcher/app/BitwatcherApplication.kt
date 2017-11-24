package uk.co.sentinelweb.bitwatcher.app

import android.app.Application

class BitwatcherApplication: Application() {

    lateinit var component:BitwatcherAppComponent

    override fun onCreate() {
        super.onCreate()
        component = DaggerBitwatcherAppComponent.builder().appContext(this).build()
        component.inject(this)
    }
}