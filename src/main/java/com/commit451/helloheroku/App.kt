package com.commit451.helloheroku

import spark.Spark

object App {

    @JvmStatic
    fun main(args: Array<String>) {
        Spark.port(80)
        Spark.get("/hello") { request, response -> "Hello World!" }
    }
}