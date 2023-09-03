package com.segunfamisa.zeitung.data

import com.segunfamisa.zeitung.BuildConfig
import com.segunfamisa.zeitung.data.remote.service.UrlProvider
import javax.inject.Inject

class UrlProviderImpl @Inject constructor() : UrlProvider {

    override fun getBaseUrl(): String {
        return BuildConfig.BaseUrl
    }
}
