package com.commit451.fetch

import com.google.common.io.Resources
import okio.Okio
import java.io.File

/**
 * Util things
 */
object Util {

    fun fromResourcesToFile(resource: String, file: File) {
        val inputStream = Util::class.java.classLoader.getResourceAsStream(resource)
        val source = Okio.buffer(Okio.source(inputStream))
        file.createIfDoesNotExist()
        val sink = Okio.buffer(Okio.sink(file))
        sink.writeAll(source)
        sink.flush()
        sink.close()
        source.close()
    }

    fun loadResourceAsString(resourceName: String): String {
        val url = Resources.getResource(resourceName)
        return Resources.toString(url, Charsets.UTF_8)
    }
}