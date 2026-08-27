package com.swordfish.lemuroid.app.shared.game

import android.content.Context
import com.swordfish.lemuroid.app.shared.settings.HDModeQuality
import com.swordfish.lemuroid.app.utils.android.getGLSLVersion
import com.swordfish.lemuroid.lib.library.GameSystem
import com.swordfish.lemuroid.lib.library.SystemID
import com.swordfish.libretrodroid.ShaderConfig
import timber.log.Timber

object ShaderChooser {
    fun getShaderForSystem(
        context: Context,
        hdMode: Boolean,
        requestedHdModeQuality: HDModeQuality,
        screenFilter: String,
        system: GameSystem,
    ): ShaderConfig {
        Timber.i(
            "Choosing shader for this config: screenFilter= $screenFilter hdMode=$hdMode hdModeQuality=$requestedHdModeQuality",
        )
        val hdModeQuality =
            if (context.getGLSLVersion() >= 3) {
                requestedHdModeQuality
            } else {
                HDModeQuality.LOW
            }
        return when {
            hdMode -> getHDShaderForSystem(system, hdModeQuality)
            else ->
                when (screenFilter) {
                    "crt" -> ShaderConfig.CRT
                    "lcd" -> ShaderConfig.LCD
                    "smooth" -> ShaderConfig.Default
                    "sharp" -> ShaderConfig.Sharp
                    else -> getDefaultShaderForSystem(system)
                }
        }
    }

    private fun getDefaultShaderForSystem(system: GameSystem): ShaderConfig {
        return when (system.id) {
            SystemID.PC_88 -> ShaderConfig.CRT
            SystemID.PC_98 -> ShaderConfig.CRT
            SystemID.PC_ENGINE -> ShaderConfig.CRT
            SystemID.PCE_CD -> ShaderConfig.CRT
            SystemID.SUPERGRAFX -> ShaderConfig.CRT
        }
    }

    private fun getHDShaderForSystem(
        system: GameSystem,
        hdModeQuality: HDModeQuality,
    ): ShaderConfig {
        return when (hdModeQuality) {
            HDModeQuality.LOW -> getLowQualityHdMode(system)
            HDModeQuality.MEDIUM -> getMediumQualityHdMode(system)
            HDModeQuality.HIGH -> getHighQualityHdMode(system)
        }
    }

    private fun getLowQualityHdMode(system: GameSystem): ShaderConfig {
        val upscale8BitsMobile =
            ShaderConfig.CUT(
                blendMinContrastEdge = 0.00f,
                blendMaxContrastEdge = 0.50f,
                blendMaxSharpness = 0.85f,
            )

        val upscale8Bits =
            ShaderConfig.CUT(
                blendMinContrastEdge = 0.00f,
                blendMaxContrastEdge = 0.50f,
                blendMaxSharpness = 0.75f,
            )

        val upscale16BitsMobile =
            ShaderConfig.CUT(
                blendMinContrastEdge = 0.10f,
                blendMaxContrastEdge = 0.60f,
                blendMaxSharpness = 0.85f,
            )

        val upscale16Bits =
            ShaderConfig.CUT(
                blendMinContrastEdge = 0.10f,
                blendMaxContrastEdge = 0.60f,
                blendMaxSharpness = 0.75f,
            )

        val upscale32Bits =
            ShaderConfig.CUT(
                blendMinContrastEdge = 0.25f,
                blendMaxContrastEdge = 0.75f,
                blendMaxSharpness = 0.75f,
            )

        val modern =
            ShaderConfig.CUT(
                blendMinContrastEdge = 0.25f,
                blendMaxContrastEdge = 0.75f,
                blendMaxSharpness = 0.50f,
            )

        return getConfigForSystem(
            system,
            upscale16BitsMobile,
            upscale8BitsMobile,
            upscale32Bits,
            upscale16Bits,
            upscale8Bits,
            modern,
        )
    }

    private fun getMediumQualityHdMode(system: GameSystem): ShaderConfig {
        val upscale8BitsMobile =
            ShaderConfig.CUT2(
                blendMinContrastEdge = 0.00f,
                blendMaxContrastEdge = 0.30f,
                blendMaxSharpness = 0.75f,
                hardEdgesSearchMaxError = 0.50f,
            )

        val upscale8Bits =
            ShaderConfig.CUT2(
                blendMinContrastEdge = 0.00f,
                blendMaxContrastEdge = 0.30f,
                blendMaxSharpness = 0.75f,
                hardEdgesSearchMaxError = 0.50f,
            )

        val upscale16BitsMobile =
            ShaderConfig.CUT2(
                blendMinContrastEdge = 0.10f,
                blendMaxContrastEdge = 0.50f,
                blendMaxSharpness = 0.75f,
                hardEdgesSearchMaxError = 0.75f,
            )

        val upscale16Bits =
            ShaderConfig.CUT2(
                blendMinContrastEdge = 0.10f,
                blendMaxContrastEdge = 0.50f,
                blendMaxSharpness = 0.75f,
                hardEdgesSearchMaxError = 0.25f,
            )

        val upscale32Bits =
            ShaderConfig.CUT2(
                blendMinContrastEdge = 0.10f,
                blendMaxContrastEdge = 0.50f,
                blendMaxSharpness = 0.75f,
                hardEdgesSearchMaxError = 0.25f,
            )

        val modern =
            ShaderConfig.CUT2(
                blendMinContrastEdge = 0.10f,
                blendMaxContrastEdge = 0.50f,
                blendMaxSharpness = 0.50f,
                hardEdgesSearchMaxError = 0.25f,
            )

        return getConfigForSystem(
            system,
            upscale16BitsMobile,
            upscale8BitsMobile,
            upscale32Bits,
            upscale16Bits,
            upscale8Bits,
            modern,
        )
    }

    private fun getHighQualityHdMode(system: GameSystem): ShaderConfig {
        val upscale8BitsMobile =
            ShaderConfig.CUT3(
                blendMinContrastEdge = 0.00f,
                blendMaxContrastEdge = 0.30f,
                blendMaxSharpness = 0.75f,
                hardEdgesSearchMaxError = 0.50f,
            )

        val upscale8Bits =
            ShaderConfig.CUT3(
                blendMinContrastEdge = 0.00f,
                blendMaxContrastEdge = 0.30f,
                blendMaxSharpness = 0.75f,
                hardEdgesSearchMaxError = 0.50f,
            )

        val upscale16BitsMobile =
            ShaderConfig.CUT3(
                blendMinContrastEdge = 0.10f,
                blendMaxContrastEdge = 0.50f,
                blendMaxSharpness = 0.75f,
                hardEdgesSearchMaxError = 0.25f,
            )

        val upscale16Bits =
            ShaderConfig.CUT3(
                blendMinContrastEdge = 0.10f,
                blendMaxContrastEdge = 0.50f,
                blendMaxSharpness = 0.75f,
                hardEdgesSearchMaxError = 0.25f,
            )

        val upscale32Bits =
            ShaderConfig.CUT3(
                blendMinContrastEdge = 0.10f,
                blendMaxContrastEdge = 0.50f,
                blendMaxSharpness = 0.75f,
                hardEdgesSearchMaxError = 0.25f,
            )

        val modern =
            ShaderConfig.CUT3(
                blendMinContrastEdge = 0.10f,
                blendMaxContrastEdge = 0.50f,
                blendMaxSharpness = 0.75f,
                hardEdgesSearchMaxError = 0.25f,
            )

        return getConfigForSystem(
            system,
            upscale16BitsMobile,
            upscale8BitsMobile,
            upscale32Bits,
            upscale16Bits,
            upscale8Bits,
            modern,
        )
    }

    private fun getConfigForSystem(
        system: GameSystem,
        upscale16BitsMobile: ShaderConfig,
        upscale8BitsMobile: ShaderConfig,
        upscale32Bits: ShaderConfig,
        upscale16Bits: ShaderConfig,
        upscale8Bits: ShaderConfig,
        modern: ShaderConfig,
    ): ShaderConfig {
        return when (system.id) {
            SystemID.PC_88 -> upscale16Bits
            SystemID.PC_98 -> upscale16Bits
            SystemID.PC_ENGINE -> upscale16Bits
            SystemID.PCE_CD -> upscale16Bits
            SystemID.SUPERGRAFX -> upscale16Bits
        }
    }
}
