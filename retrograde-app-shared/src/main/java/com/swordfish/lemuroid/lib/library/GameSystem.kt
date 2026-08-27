/*
 * GameSystem.kt
 *
 * Copyright (C) 2017 Retrograde Project
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.swordfish.lemuroid.lib.library

import androidx.annotation.StringRes
import com.swordfish.lemuroid.lib.R
import java.util.Locale

data class GameSystem(
    val id: SystemID,
    val libretroFullName: String,
    @StringRes
    val titleResId: Int,
    @StringRes
    val shortTitleResId: Int,
    val systemCoreConfigs: List<SystemCoreConfig>,
    val uniqueExtensions: List<String>,
    val scanOptions: ScanOptions = ScanOptions(),
    val supportedExtensions: List<String> = uniqueExtensions,
    val hasMultiDiskSupport: Boolean = false,
    val fastForwardSupport: Boolean = true,
    val hasTouchScreen: Boolean = false,
) {
    companion object {
        private val SYSTEMS =
            listOf(
                GameSystem(
                    SystemID.PC_88,
                    "NEC PC-8801",
                    R.string.game_system_title_pc88,
                    R.string.game_system_abbr_pc88,
                    listOf(
                        SystemCoreConfig(
                            coreID = CoreID.QUASI88,
                            controllerConfigs =
                                hashMapOf(
                                    0 to arrayListOf(ControllerConfigs.PCE),
                                ),
                        ),
                    ),
                    uniqueExtensions = listOf("d88"),
                    supportedExtensions = listOf("d88", "m3u", "t88", "cmt"),
                    scanOptions = ScanOptions(scanByFilename = false),
                ),
                GameSystem(
                    SystemID.PC_98,
                    "NEC PC-9801",
                    R.string.game_system_title_pc98,
                    R.string.game_system_abbr_pc98,
                    listOf(
                        SystemCoreConfig(
                            coreID = CoreID.NP2KAI,
                            controllerConfigs =
                                hashMapOf(
                                    0 to arrayListOf(ControllerConfigs.PCE),
                                ),
                        ),
                    ),
                    uniqueExtensions = listOf("hdi", "fdi"),
                    supportedExtensions = listOf("hdi", "fdi", "hdm", "thr", "nhd"),
                    scanOptions = ScanOptions(scanByFilename = false),
                ),
                GameSystem(
                    SystemID.PC_ENGINE,
                    "NEC - PC Engine - TurboGrafx 16",
                    R.string.game_system_title_pce,
                    R.string.game_system_abbr_pce,
                    listOf(
                        SystemCoreConfig(
                            CoreID.MEDNAFEN_PCE_FAST,
                            controllerConfigs =
                                hashMapOf(
                                    0 to arrayListOf(ControllerConfigs.PCE),
                                ),
                        ),
                    ),
                    uniqueExtensions = listOf("pce"),
                    supportedExtensions = listOf("pce", "bin"),
                ),
                GameSystem(
                    SystemID.PCE_CD,
                    "NEC - PC Engine CD - TurboGrafx-CD",
                    R.string.game_system_title_pce_cd,
                    R.string.game_system_abbr_pce_cd,
                    listOf(
                        SystemCoreConfig(
                            CoreID.MEDNAFEN_PCE_FAST,
                            controllerConfigs =
                                hashMapOf(
                                    0 to arrayListOf(ControllerConfigs.PCE),
                                ),
                        ),
                    ),
                    uniqueExtensions = listOf(),
                    supportedExtensions = listOf("cue", "iso", "chd"),
                    scanOptions =
                        ScanOptions(
                            scanByFilename = false,
                            scanByUniqueExtension = false,
                            scanByPathAndSupportedExtensions = true,
                        ),
                    hasMultiDiskSupport = true,
                ),
                GameSystem(
                    SystemID.SUPERGRAFX,
                    "NEC - PC Engine SuperGrafx",
                    R.string.game_system_title_supergrafx,
                    R.string.game_system_abbr_supergrafx,
                    listOf(
                        SystemCoreConfig(
                            CoreID.BEETLE_SUPERGRAFX,
                            controllerConfigs =
                                hashMapOf(
                                    0 to arrayListOf(ControllerConfigs.PCE),
                                ),
                        ),
                    ),
                    uniqueExtensions = listOf("sgx"),
                    supportedExtensions = listOf("sgx"),
                ),
            )

        private val byIdCache by lazy { mapOf(*SYSTEMS.map { it.id.dbname to it }.toTypedArray()) }
        private val byExtensionCache by lazy {
            val mutableMap = mutableMapOf<String, GameSystem>()
            for (system in SYSTEMS) {
                for (extension in system.uniqueExtensions) {
                    mutableMap[extension.toLowerCase(Locale.US)] = system
                }
            }
            mutableMap.toMap()
        }

        fun findById(id: String): GameSystem = byIdCache.getValue(id)

        fun all() = SYSTEMS

        fun getSupportedExtensions(): List<String> {
            return SYSTEMS.flatMap { it.supportedExtensions }
        }

        fun findSystemForCore(coreID: CoreID): List<GameSystem> {
            return all().filter { system -> system.systemCoreConfigs.any { it.coreID == coreID } }
        }

        fun findByUniqueFileExtension(fileExtension: String): GameSystem? =
            byExtensionCache[fileExtension.toLowerCase(Locale.US)]

        data class ScanOptions(
            val scanByFilename: Boolean = true,
            val scanByUniqueExtension: Boolean = true,
            val scanByPathAndFilename: Boolean = false,
            val scanByPathAndSupportedExtensions: Boolean = true,
            val scanBySimilarSerial: Boolean = false,
        )
    }
}
