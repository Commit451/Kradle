package com.commit451.kradle

import com.squareup.moshi.Moshi
import org.slf4j.LoggerFactory
import spark.Spark
import java.io.File


object App {

    private val LOGGER = LoggerFactory.getLogger(App::class.java)

    @JvmStatic
    fun main(args: Array<String>) {

        val moshi = Moshi.Builder().build()
        val jsonAdapter = moshi.adapter<Configuration>(Configuration::class.java)
        val json = Util.loadResourceAsString("configuration.json")
        val configuration = jsonAdapter.fromJson(json)
        GoogleCloudStorage.init(configuration.bucket)

        Spark.port(8080)
        Spark.get("*") { request, response ->
            LOGGER.warn("GET with path: ${request.pathInfo()}")
            GoogleCloudStorage.getAsString(request.pathInfo())
            "Hello World!"
        }
        Spark.put("*") { request, response ->
            // Update something
            LOGGER.warn("PUT with path: ${request.pathInfo()}")

            val text = Util.loadResourceAsString("hello.txt")
            GoogleCloudStorage.putFile(request.pathInfo(), text.toByteArray())
            response.status(200)
            "ok"
        }
    }
}