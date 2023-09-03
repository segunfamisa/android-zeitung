package com.segunfamisa.zeitung.data.remote.service

import com.segunfamisa.zeitung.data.remote.common.calladapter.ApiErrorParser
import com.segunfamisa.zeitung.data.remote.common.calladapter.ApiResponseCallAdapterFactory
import com.squareup.moshi.FromJson
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonReader
import com.squareup.moshi.JsonWriter
import com.squareup.moshi.Moshi
import com.squareup.moshi.ToJson
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.text.SimpleDateFormat
import java.util.Date
import javax.inject.Inject

internal class ApiServiceCreator @Inject constructor(
    private val urlProvider: UrlProvider,
    private val authorizationInterceptor: AuthorizationInterceptor,
    private val errorParser: ApiErrorParser,
) {

    // region Public Api

    fun createService(): ApiService {
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(authorizationInterceptor)
            .build()

        val moshi = Moshi.Builder()
            .add(Date::class.java, DateAdapterFactory())
            .build()

        val moshiConverter = MoshiConverterFactory.create(moshi)

        val retrofit = Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(urlProvider.getBaseUrl())
            .addConverterFactory(moshiConverter)
            .addCallAdapterFactory(ApiResponseCallAdapterFactory(errorParser = errorParser))
            .build()

        return retrofit.create(ApiService::class.java)
    }

    // endregion

    // region Inner Classes

    private inner class DateAdapterFactory : JsonAdapter<Date>() {
        private val adapter: DateAdapter = DateAdapter()

        override fun fromJson(reader: JsonReader): Date? {
            val dateString = reader.nextString()
            return adapter.parseFromString(dateString)
        }

        override fun toJson(writer: JsonWriter, value: Date?) {
            writer.value(adapter.convertToString(value!!))
        }
    }

    private inner class DateAdapter {
        private val sdf = SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss'Z'")

        @ToJson
        @Synchronized
        fun parseFromString(string: String): Date = sdf.parse(string)

        @FromJson
        @Synchronized
        fun convertToString(obj: Date): String = sdf.format(obj)
    }

    // endregion
}
