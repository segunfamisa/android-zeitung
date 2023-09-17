package com.segunfamisa.zeitung.data.local.preferences

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import com.google.protobuf.InvalidProtocolBufferException
import java.io.InputStream
import java.io.OutputStream

internal object UserPreferencesSerializer : Serializer<Prefs> {

    override val defaultValue: Prefs = Prefs.getDefaultInstance()

    override suspend fun readFrom(input: InputStream): Prefs {
        return try {
            Prefs.parseFrom(input)
        } catch (exception: InvalidProtocolBufferException) {
            throw CorruptionException("Cannot read proto.", exception)
        }
    }

    override suspend fun writeTo(t: Prefs, output: OutputStream) {
        t.writeTo(output)
    }
}
