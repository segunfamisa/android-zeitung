package com.segunfamisa.zeitung

import android.app.Application
import com.segunfamisa.zeitung.di.Injector
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

class App : Application(), HasAndroidInjector {

    @Inject
    lateinit var dispatchingActivityInjector: DispatchingAndroidInjector<Any>

    override fun onCreate() {
        super.onCreate()

        Injector.init(this)
    }

    override fun androidInjector(): AndroidInjector<Any> = dispatchingActivityInjector
}
