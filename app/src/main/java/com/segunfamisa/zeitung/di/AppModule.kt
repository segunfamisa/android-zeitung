package com.segunfamisa.zeitung.di

import com.segunfamisa.zeitung.data.di.DataModule
import dagger.Module

@Module(includes = [DataModule::class])
class AppModule {

}
