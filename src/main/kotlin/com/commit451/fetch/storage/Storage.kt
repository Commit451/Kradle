package com.commit451.fetch.storage

/**
 * Stores and fetches things from storage
 */
object Storage {

    const val BUCKET_NAME = "maven"
    fun init() {
        GoogleCloudStorage.init(BUCKET_NAME)
    }
}