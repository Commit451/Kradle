package com.commit451.kradle

import java.io.File

fun File.createIfDoesNotExist() {
    if (!exists()) {
        createNewFile()
    }
}