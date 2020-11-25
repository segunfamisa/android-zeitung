package com.segunfamisa.zeitung.data.remote.service

import com.segunfamisa.zeitung.data.BuildConfig
import javax.inject.Inject

class UrlProviderImpl @Inject constructor() : UrlProvider {

    override fun getBaseUrl(): String {
        return BuildConfig.BaseUrl
    }
}
