package com.commit451.fetch

import com.commit451.fetch.storage.GoogleCloudStorage
import com.commit451.fetch.storage.Storage
import com.google.api.client.http.HttpResponseException
import org.slf4j.LoggerFactory
import spark.Spark
import com.google.cloud.storage.StorageOptions


object App {

    private val LOGGER = LoggerFactory.getLogger(App::class.java)

    @JvmStatic
    fun main(args: Array<String>) {

        Storage.init()

        Spark.port(8080)
        Spark.get("maven/*") { request, response ->
            LOGGER.info("GET with path: ${request.pathInfo()}")
            try {
                val content = GoogleCloudStorage.getAsString(request.pathInfo())
                System.out.println(content)
                content
            } catch (e: Exception) {
                LOGGER.error("Failed on GET", e)
                if (e is HttpResponseException) {
                    response.status(e.statusCode)
                    e.content
                } else {
                    response.status(500)
                    "Error"
                }
            }
        }

        Spark.put("maven/*") { request, response ->
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