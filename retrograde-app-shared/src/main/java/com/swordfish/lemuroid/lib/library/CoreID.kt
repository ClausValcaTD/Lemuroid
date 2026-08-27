package com.swordfish.lemuroid.lib.library

import android.content.SharedPreferences
import com.swordfish.lemuroid.lib.core.CoreUpdater
import com.swordfish.lemuroid.lib.core.assetsmanager.NoAssetsManager
import com.swordfish.lemuroid.lib.storage.DirectoriesManager

enum class CoreID(
    val coreName: String,
    val coreDisplayName: String,
    val libretroFileName: String,
) {
    QUASI88(
        "quasi88",
        "QUASI88",
        "libquasi88_libretro_android.so",
    ),
    NP2KAI(
        "np2kai",
        "NP2kai",
        "libnp2kai_libretro_android.so",
    ),
    MEDNAFEN_PCE_FAST(
        "mednafen_pce_fast",
        "Beetle PCE",
        "libmednafen_pce_fast_libretro_android.so",
    ),
    BEETLE_SUPERGRAFX(
        "beetle_supergrafx",
        "Beetle SuperGrafx",
        "libmednafen_supergrafx_libretro_android.so",
    ),
    ;

    companion object {
        fun getAssetManager(coreID: CoreID): AssetsManager {
            return NoAssetsManager()
        }
    }

    interface AssetsManager {
        suspend fun retrieveAssetsIfNeeded(
            coreUpdaterApi: CoreUpdater.CoreManagerApi,
            directoriesManager: DirectoriesManager,
            sharedPreferences: SharedPreferences,
        )

        suspend fun clearAssets(directoriesManager: DirectoriesManager)
    }
}

fun findByName(query: String): CoreID? = CoreID.values().firstOrNull { it.coreName == query }
