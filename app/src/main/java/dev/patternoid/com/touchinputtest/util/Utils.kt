package dev.patternoid.com.touchinputtest.util

import android.content.Context
import android.graphics.Point

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


        fun getAngle(center: Point, first: Point, second: Point): Double {
            val lega1: Double
            val lega2: Double
            val legb1: Double
            val legb2: Double
            val norm: Double
            val norm1: Double
            val norm2: Double
            val angle: Double
            val prod: Double
            val curl: Double
            val x1: Int
            val y1: Int
            val x2: Int
            val y2: Int
            val x3: Int
            val y3: Int

            x2 = center.x
            y2 = center.y
            x1 = first.x
            y1 = first.y
            x3 = second.x
            y3 = second.y

            lega1 = (x1 - x2).toDouble()
            legb1 = (y1 - y2).toDouble()
            lega2 = (x3 - x2).toDouble()
            legb2 = (y3 - y2).toDouble()

            norm1 = Math.sqrt(lega1 * lega1 + legb1 * legb1) //두 벡터의 크기
            norm2 = Math.sqrt(lega2 * lega2 + legb2 * legb2) //두 벡터의 크기

            norm = norm1 * norm2
            prod = lega1 * lega2 + legb1 * legb2 //두 벡터의 내적
            angle = Math.acos(prod / norm)

            curl = lega1 * legb2 - legb1 * lega2 //두 벡터의 외적

            if (curl <= 0)
                return angle / Math.PI * 180
            else
                return 360 - angle / Math.PI * 180
        }

    }
}