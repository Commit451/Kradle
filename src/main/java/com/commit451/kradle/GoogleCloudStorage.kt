package com.commit451.kradle

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport
import com.google.api.client.http.GenericUrl
import com.google.api.client.http.HttpRequestFactory
import com.google.api.client.http.HttpTransport
import com.google.api.client.http.InputStreamContent
import com.google.api.client.json.JsonFactory
import com.google.api.client.json.jackson2.JacksonFactory
import com.google.api.services.storage.Storage
import com.google.api.services.storage.model.ObjectAccessControl
import com.google.api.services.storage.model.StorageObject
import java.io.File
import java.io.FileInputStream
import java.net.URLEncoder
import java.util.*


/**
 * Allows access to storing and retrieving things from Google Cloud Storage
 */
object GoogleCloudStorage {

    const val BASE_URL = "https://storage.googleapis.com/"
    const val BUCKET_NAME = "repository"

    /** Global configuration of Google Cloud Storage OAuth 2.0 scope.  */
    const val STORAGE_SCOPE = "https://www.googleapis.com/auth/devstorage.read_write"

    val credential: GoogleCredential = GoogleCredential
            .getApplicationDefault()
            .createScoped(Collections.singleton(STORAGE_SCOPE))
    val httpTransport: HttpTransport = GoogleNetHttpTransport.newTrustedTransport()
    val requestFactory: HttpRequestFactory = httpTransport.createRequestFactory(credential)
    var jsonFactory: JsonFactory = JacksonFactory()
    val storage: Storage = Storage.Builder(httpTransport, jsonFactory, credential)
            .setApplicationName("Kradle")
            .build()

    fun getAsString(path: String): String {
        val url = BASE_URL + URLEncoder.encode(BUCKET_NAME + "/" + path, "UTF-8")
        val properUrl = GenericUrl(url)
        val request = requestFactory.buildGetRequest(properUrl)
        val response = request.execute()
        return response.parseAsString()
    }

    fun putFile(path: String, file: File) {
        val contentStream = InputStreamContent("text/plain", FileInputStream(file))
        // Setting the length improves upload performance
        contentStream.length = file.length()
        val objectMetadata = StorageObject()
                // Set the destination object name
                .setName(file.name)
                // Set the access control list to publicly read-only
                .setAcl(Arrays.asList(ObjectAccessControl()
                        .setEntity("allUsers")
                        .setRole("READER")))

        // Do the insert
        val insertRequest = storage.objects().insert(BUCKET_NAME, objectMetadata, contentStream)

        insertRequest.execute()
    }
}