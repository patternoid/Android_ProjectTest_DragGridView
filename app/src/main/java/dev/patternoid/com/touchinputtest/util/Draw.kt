package dev.patternoid.com.touchinputtest.util

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Point

/**
 * Created by Patternoid on 2017-08-14.
 */
class Draw {

    companion object {

        fun drawBoxLine(canvas : Canvas, topLeft : Point?, topRight : Point?, bottomLeft : Point?, bottomRight : Point?, drawPaint : Paint? ){
            // line left
            canvas.drawLine(topLeft!!.x.toFloat(), topLeft!!.y.toFloat(), bottomLeft!!.x.toFloat(), bottomLeft!!.y.toFloat(), drawPaint!!)

            // line top
            canvas.drawLine(topLeft!!.x.toFloat(), topLeft!!.y.toFloat(), topRight!!.x.toFloat(), topRight!!.y.toFloat(), drawPaint!!)

            // line right
            canvas.drawLine(topRight!!.x.toFloat(), topRight!!.y.toFloat(), bottomRight!!.x.toFloat(), bottomRight!!.y.toFloat(), drawPaint!!)

            // line bottom
            canvas.drawLine(bottomLeft!!.x.toFloat(), bottomLeft!!.y.toFloat(), bottomRight!!.x.toFloat(), bottomRight!!.y.toFloat(), drawPaint!!)
        }


        fun drawBoxTouchCircle( canvas : Canvas , topLeft : Point?, topRight : Point?, bottomLeft : Point?, bottomRight : Point?, drawPaint : Paint? ){
            // circle top left
            canvas.drawCircle(topLeft!!.x.toFloat(), topLeft!!.y.toFloat(), 10f, drawPaint!!)

            // circle top right
            canvas.drawCircle(topRight!!.x.toFloat(), topRight!!.y.toFloat(), 10f, drawPaint!!)

            // circle bottom left
            canvas.drawCircle(bottomLeft!!.x.toFloat(), bottomLeft!!.y.toFloat(), 10f, drawPaint!!)

            // circle bottom right
            canvas.drawCircle(bottomRight!!.x.toFloat(), bottomRight!!.y.toFloat(), 10f, drawPaint!!)
        }
    }

}