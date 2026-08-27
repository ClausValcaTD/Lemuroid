package com.swordfish.lemuroid.lib.library

import com.swordfish.lemuroid.common.graphics.ColorUtils
import com.swordfish.lemuroid.lib.R

fun GameSystem.metaSystemID() = MetaSystemID.fromSystemID(id)

/** Meta systems represents a collection of systems which appear the same to the user. */
enum class MetaSystemID(val titleResId: Int, val imageResId: Int, val systemIDs: List<SystemID>) {
    PC_88(
        R.string.game_system_title_pc88,
        R.drawable.game_system_pce,
        listOf(SystemID.PC_88),
    ),
    PC_98(
        R.string.game_system_title_pc98,
        R.drawable.game_system_pce,
        listOf(SystemID.PC_98),
    ),
    PC_ENGINE(
        R.string.game_system_title_pce,
        R.drawable.game_system_pce,
        listOf(SystemID.PC_ENGINE, SystemID.PCE_CD, SystemID.SUPERGRAFX),
    ),
    ;

    fun color(): Int {
        return ColorUtils.color(ordinal.toFloat() / values().size)
    }

    companion object {
        fun fromSystemID(systemID: SystemID): MetaSystemID {
            return when (systemID) {
                SystemID.PC_88 -> PC_88
                SystemID.PC_98 -> PC_98
                SystemID.PC_ENGINE -> PC_ENGINE
                SystemID.PCE_CD -> PC_ENGINE
                SystemID.SUPERGRAFX -> PC_ENGINE
            }
        }
    }
}
