package com.commit451.fetch

import okhttp3.MediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import org.junit.Assert
import org.junit.Test



/**
 * Test the integration
 * @see <a href="https://docs.gradle.org/current/userguide/custom_plugins.html#sec:writing_tests_for_your_plugin">https://docs.gradle.org/current/userguide/custom_plugins.html#sec:writing_tests_for_your_plugin</a>
 */
class IntegrationTest {

    @Test
    fun testHello() {
        val client = OkHttpClient()
        val request = Request.Builder()
                .url("http://localhost:8080/com/test/testmodule/maven-metadata.xml")
                .build()
        val response = client.newCall(request).execute()
        val text = response.body().string()
        System.out.println(text)
        Assert.assertTrue(text.contains("<?xml version=\"1.0\" encoding=\"UTF-8\"?>"))
        Assert.assertEquals("Hello World!", text)
    }

    @Test
    fun testPut() {
        val client = OkHttpClient()
        val request = Request.Builder()
                .url("http://localhost:8080/")
                .put(RequestBody.create(MediaType.parse("text"), "hi"))
                .build()
        val response = client.newCall(request).execute()
        Assert.assertEquals(200, response.code())
    }
}