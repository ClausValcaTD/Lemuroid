package com.swordfish.lemuroid.lib.migration

import com.swordfish.lemuroid.lib.library.CoreID
import com.swordfish.lemuroid.lib.library.db.entity.Game
import com.swordfish.lemuroid.lib.storage.DirectoriesManager

class DesmumeMigrationHandler(
    private val directoriesManager: DirectoriesManager,
) {
    fun resolveSaveData(
        game: Game,
        coreID: CoreID,
        defaultData: ByteArray?,
    ): SaveDataResult {
        return SaveDataResult(defaultData, null)
    }

    data class SaveDataResult(val data: ByteArray?, val timestampOverride: Long?)

    fun hasPendingDesmumeSaves(): Boolean {
        return false
    }
}
