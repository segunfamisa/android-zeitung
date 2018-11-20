package com.segunfamisa.zeitung.data.sources.remote

import com.segunfamisa.zeitung.data.BuildConfig
import javax.inject.Inject

class UrlProviderImpl @Inject constructor() : UrlProvider {

    override fun getBaseUrl(): String {
        return BuildConfig.BaseUrl
    }
}
