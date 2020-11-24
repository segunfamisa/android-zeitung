package com.segunfamisa.zeitung.data.di.qualifiers

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
internal annotation class DataSource(val type: String)
