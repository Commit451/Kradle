package com.commit451.kradle

import spark.Spark

object App {

    @JvmStatic
    fun main(args: Array<String>) {
        Spark.port(8080)
        Spark.get("/hello") { request, response ->
            "Hello World!"
        }
        Spark.put("/") { request, response ->
            // Update something
            response.status(200)
        }
    }
}