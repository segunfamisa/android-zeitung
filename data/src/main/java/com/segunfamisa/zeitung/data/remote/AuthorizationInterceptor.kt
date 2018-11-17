package com.segunfamisa.zeitung.data.remote

import okhttp3.Interceptor
import okhttp3.Response

/**
 * Interceptor for authorization information - ApiKey in this case
 */
class AuthorizationInterceptor(private val apiKeyProvider: ApiKeyProvider) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val originalHttpUrl = originalRequest.url()

        val newUrl = originalHttpUrl.newBuilder()
            .addQueryParameter("apiKey", apiKeyProvider.getApiKey())
            .build()

        val request = originalRequest.newBuilder()
            .url(newUrl)
            .build()

        return chain.proceed(request)
    }
}
