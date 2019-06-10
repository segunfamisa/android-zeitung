package com.segunfamisa.zeitung.di

import android.app.Activity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import com.segunfamisa.zeitung.App
import com.segunfamisa.zeitung.ui.common.BaseActivity
import com.segunfamisa.zeitung.util.LifecycleCallbacks
import dagger.android.AndroidInjection
import dagger.android.HasActivityInjector
import dagger.android.support.AndroidSupportInjection

/**
 * Injection helper class pre-configures activities and fragments so don't have to manually call AndroidInjection.inject(this)
 */
object Injector {

    fun init(application: App) {
        DaggerAppComponent.builder()
            .app(application)
            .build()
            .inject(application)

        application.registerActivityLifecycleCallbacks(object : LifecycleCallbacks() {
            override fun onActivityCreated(activity: Activity?, savedInstanceState: Bundle?) {
                if (activity is HasActivityInjector || activity is BaseActivity) {
                    AndroidInjection.inject(activity)
                }

                if (activity is FragmentActivity) {
                    activity.supportFragmentManager
                        .registerFragmentLifecycleCallbacks(object : FragmentManager.FragmentLifecycleCallbacks() {
                            override fun onFragmentPreCreated(
                                fm: FragmentManager,
                                fragment: Fragment,
                                savedInstanceState: Bundle?
                            ) {
                                AndroidSupportInjection.inject(fragment)
                            }
                        }, true)
                }
            }

        })
    }
}