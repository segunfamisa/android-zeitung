package com.segunfamisa.zeitung.data.remote

import com.segunfamisa.zeitung.data.BuildConfig

class UrlProviderImpl : UrlProvider {

    override fun getBaseUrl(): String {
        return BuildConfig.BaseUrl
    }
}
