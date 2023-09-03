package com.segunfamisa.zeitung.data.remote.utils

import com.segunfamisa.zeitung.data.remote.service.ApiKeyProvider

class FakeApiKeyProvider(private val fakeApiKey: String = "apiKey") : ApiKeyProvider {
    override fun getApiKey(): String {
        return fakeApiKey
    }
}