package com.commit451.kradle

import com.google.api.client.http.HttpResponseException
import com.squareup.moshi.Moshi
import org.slf4j.LoggerFactory
import spark.Spark


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
            LOGGER.info("GET with path: ${request.pathInfo()}")
            try {
                val content = GoogleCloudStorage.getAsString(request.pathInfo())
                response.body(content)
                response.body()
            } catch (e: Exception) {
                if (e is HttpResponseException) {
                    response.status(e.statusCode)
                    response.body(e.statusMessage)
                } else {
                    response.status(500)
                    response.body("error")
                }
                response.body()
            }
        }

        Spark.put("*") { request, response ->
            LOGGER.info("PUT with path: ${request.pathInfo()}")
            // Update something
            val contentType = request.headers("Content-Type")
            val content = request.bodyAsBytes()

            try {
                GoogleCloudStorage.putFile(request.pathInfo(), contentType, content)
                "ok"
            } catch (e: Exception) {
                if (e is HttpResponseException) {
                    response.status(e.statusCode)
                    response.body(e.statusMessage)
                } else {
                    response.status(500)
                    response.body("error")
                }
                LOGGER.error(e.message)
                response.body()
            }
        }
    }
}