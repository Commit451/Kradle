package com.commit451.kradle

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport
import com.google.api.client.http.GenericUrl
import com.google.api.client.http.HttpRequestFactory
import com.google.api.client.http.HttpTransport
import com.google.cloud.storage.BlobId
import com.google.cloud.storage.BlobInfo
import com.google.cloud.storage.Storage
import com.google.cloud.storage.StorageOptions
import java.net.URLEncoder
import java.util.*

/**
 * Allows access to storing and retrieving things from Google Cloud Storage
 */
object GoogleCloudStorage {

    const val BASE_URL = "https://storage.googleapis.com/"

    /** Global configuration of Google Cloud Storage OAuth 2.0 scope.  */
    const val STORAGE_SCOPE = "https://www.googleapis.com/auth/devstorage.full_control"

    val credential: GoogleCredential = GoogleCredential
            .getApplicationDefault()
            .createScoped(Collections.singleton(STORAGE_SCOPE))
    val httpTransport: HttpTransport = GoogleNetHttpTransport.newTrustedTransport()
    val requestFactory: HttpRequestFactory = httpTransport.createRequestFactory(credential)
    var storage: Storage = StorageOptions.getDefaultInstance().service

    lateinit var bucketName: String

    fun init(bucketName: String) {
        this.bucketName = bucketName
    }

    fun getAsString(path: String): String {
        val url = BASE_URL + URLEncoder.encode(bucketName + "/" + path, "UTF-8")
        val properUrl = GenericUrl(url)
        val request = requestFactory.buildGetRequest(properUrl)
        val response = request.execute()
        return response.parseAsString()
    }

    fun putFile(path: String, contentType: String?, bytes: ByteArray) {
        val blobId = BlobId.of(bucketName, path)
        val blobInfoBuilder = BlobInfo.newBuilder(blobId)
        contentType?.let {
            blobInfoBuilder.setContentType(it)
        }
        storage.create(blobInfoBuilder.build(), bytes)
    }
}