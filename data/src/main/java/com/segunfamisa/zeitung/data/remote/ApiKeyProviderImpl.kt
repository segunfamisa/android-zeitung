package com.segunfamisa.zeitung.data.remote

import com.segunfamisa.zeitung.data.BuildConfig

class ApiKeyProviderImpl : ApiKeyProvider {

    override fun getApiKey(): String {
        return BuildConfig.ApiKey
    }
}
