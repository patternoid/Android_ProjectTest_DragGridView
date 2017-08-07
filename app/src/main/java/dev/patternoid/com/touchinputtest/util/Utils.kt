package dev.patternoid.com.touchinputtest.util

import android.content.Context

/**
 * Created by Patternoid on 2017-08-07.
 */
class Utils {
    companion object {
        fun convertPixelsToDp(  pixel : Float,  context : Context) : Float {

            val resources   = context.resources
            val metrics     = resources.displayMetrics
            val dp          = pixel / (metrics.densityDpi / 160f)

            return dp
        }
    }
}