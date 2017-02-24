package com.commit451.kradle

import org.slf4j.LoggerFactory
import spark.Spark

object App {

    private val LOGGER = LoggerFactory.getLogger(App::class.java)

    @JvmStatic
    fun main(args: Array<String>) {
        Spark.port(8080)
        Spark.get("*") { request, response ->
            LOGGER.warn("GET with path: ${request.pathInfo()}")
            "Hello World!"
        }
        Spark.put("*") { request, response ->
            // Update something
            LOGGER.warn("PUT with path: ${request.pathInfo()}")
            response.status(200)
        }
    }
}