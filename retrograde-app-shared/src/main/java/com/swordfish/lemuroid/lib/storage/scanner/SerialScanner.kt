package com.swordfish.lemuroid.lib.storage.scanner

import com.swordfish.lemuroid.common.files.FileUtils
import com.swordfish.lemuroid.common.kotlin.kiloBytes
import com.swordfish.lemuroid.lib.library.SystemID
import timber.log.Timber
import java.io.InputStream

object SerialScanner {
    private val READ_BUFFER_SIZE = 64.kiloBytes()

    data class DiskInfo(val serial: String?, val systemID: SystemID?)

    fun extractInfo(
        fileName: String,
        inputStream: InputStream,
    ): DiskInfo {
        Timber.d("Extracting disk info for $fileName")
        return DiskInfo(null, null)
    }
}
