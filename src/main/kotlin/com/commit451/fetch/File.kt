package com.commit451.fetch

import java.io.File

fun File.createIfDoesNotExist() {
    if (!exists()) {
        createNewFile()
    }
}