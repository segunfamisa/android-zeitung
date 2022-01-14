package com.segunfamisa.zeitung.di

import android.app.Activity
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.segunfamisa.zeitung.App
import com.segunfamisa.zeitung.util.LifecycleCallbacks
import dagger.android.AndroidInjection
import dagger.android.HasAndroidInjector

/**
 * Injection helper class pre-configures activities so don't have to manually call AndroidInjection.inject(this)
 */
object Injector {

    fun init(application: App) {
        DaggerAppComponent.builder()
            .app(application)
            .build()
            .inject(application)

        application.registerActivityLifecycleCallbacks(object : LifecycleCallbacks() {
            override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
                if (activity is HasAndroidInjector || activity is AppCompatActivity) {
                    AndroidInjection.inject(activity)
                }
            }
        })
    }
}
