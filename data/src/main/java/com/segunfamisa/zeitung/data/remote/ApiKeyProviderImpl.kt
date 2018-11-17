package com.segunfamisa.zeitung.data.remote

import com.segunfamisa.zeitung.data.BuildConfig
import javax.inject.Inject

class ApiKeyProviderImpl @Inject constructor() : ApiKeyProvider {

    override fun getApiKey(): String {
        return BuildConfig.ApiKey
    }
}
