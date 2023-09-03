package com.segunfamisa.zeitung.data

import com.segunfamisa.zeitung.BuildConfig
import com.segunfamisa.zeitung.data.remote.service.ApiKeyProvider
import javax.inject.Inject

internal class ApiKeyProviderImpl @Inject constructor() : ApiKeyProvider {

    override fun getApiKey(): String {
        return BuildConfig.APPLICATION_ID
    }
}
