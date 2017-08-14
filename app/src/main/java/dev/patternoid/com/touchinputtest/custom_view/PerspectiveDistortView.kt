package dev.patternoid.com.touchinputtest.custom_view

import android.content.Context
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.util.AttributeSet
import android.util.DisplayMetrics
import android.util.Log
import android.view.MotionEvent
import android.view.View
import dev.patternoid.com.touchinputtest.R
import dev.patternoid.com.touchinputtest.util.Utils
import android.graphics.LightingColorFilter
import android.graphics.ColorFilter
import dev.patternoid.com.touchinputtest.util.Draw


/**
 * Created by Patternoid on 2017-08-10.
 */
class PerspectiveDistortView : View, View.OnTouchListener {

    enum class CirclePosition {
        TopLeft,
        TopRight,
        BottomLeft,
        BottomRight,
        None
    }


    private var mTouchPoint     : CirclePosition = CirclePosition.None
    private var mPaintRect      : Paint? = null
    private var mPaintCircle    : Paint? = null
    private var mPaintBitmapAlpha: Paint? = null
    private var mBitmapColorfilter : LightingColorFilter? = null


    var CIRCLE_TOP_LEFT         : Point? = null
    var CIRCLE_TOP_RIGHT        : Point? = null
    var CIRCLE_BOTTOM_LEFT      : Point? = null
    var CIRCLE_BOTTOM_RIGHT     : Point? = null

    var MOVEBOX_TOP_LEFT        : Point? = null
    var MOVEBOX_TOP_RIGHT       : Point? = null
    var MOVEBOX_BOTTOM_LEFT     : Point? = null
    var MOVEBOX_BOTTOM_RIGHT    : Point? = null

    //private int lastX, lastY;
    var mPatternImageBitmap     : Bitmap? = null
    var mBitmapDrawable         : BitmapDrawable? = null
    var mPolyToPolyMatrix       : Matrix? = null


    var mIsTouchInMoveBoxArea   : Boolean = false
    var mPrevTouchPoint         : Point?  = null
    var mPaintMoveBoxLine       : Paint? = null

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }


    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {
        init()
    }


    private fun init() {
        this.setOnTouchListener(this)

        mPrevTouchPoint     = Point()

        mBitmapColorfilter = LightingColorFilter(Color.WHITE, 1)
        mPaintBitmapAlpha = Paint()
        mPaintBitmapAlpha!!.setColorFilter(mBitmapColorfilter)
        mPaintBitmapAlpha!!.alpha = 190

        mPaintRect = Paint()
        mPaintRect!!.color = 0xffff0000.toInt()
        mPaintRect!!.isAntiAlias = true
        mPaintRect!!.isDither = true
        mPaintRect!!.style = Paint.Style.STROKE
        mPaintRect!!.strokeJoin = Paint.Join.BEVEL
        mPaintRect!!.strokeCap = Paint.Cap.BUTT
        mPaintRect!!.strokeWidth = 3f

        mPaintCircle = Paint()
        mPaintCircle!!.color = 0xffff0000.toInt()
        mPaintCircle!!.isAntiAlias = true
        mPaintCircle!!.isDither = true
        mPaintCircle!!.style = Paint.Style.FILL_AND_STROKE
        mPaintCircle!!.strokeJoin = Paint.Join.BEVEL
        mPaintCircle!!.strokeCap = Paint.Cap.BUTT
        mPaintCircle!!.strokeWidth = 5f

        mPaintMoveBoxLine = Paint()
        mPaintMoveBoxLine!!.color = 0xffffff00.toInt()
        mPaintMoveBoxLine!!.isAntiAlias = true
        mPaintMoveBoxLine!!.isDither = true
        mPaintMoveBoxLine!!.style = Paint.Style.STROKE
        mPaintMoveBoxLine!!.strokeJoin = Paint.Join.BEVEL
        mPaintMoveBoxLine!!.strokeCap = Paint.Cap.BUTT
        mPaintMoveBoxLine!!.strokeWidth = 6f


        mPatternImageBitmap = BitmapFactory.decodeResource(resources, R.drawable.pattern_1)
        mBitmapDrawable = BitmapDrawable(mPatternImageBitmap)
        mBitmapDrawable!!.setTileModeXY(Shader.TileMode.REPEAT, Shader.TileMode.REPEAT)

        mPolyToPolyMatrix = Matrix()
    }



    fun setBoxWidthHeight( widthPixels : Int, heightPixels : Int ){

        val left    : Int   = widthPixels/2 - 100
        val right   : Int   = widthPixels/2 + 100
        val top     : Int   = heightPixels/2 - 100
        val bottom  : Int   = heightPixels/2 + 100

        CIRCLE_TOP_LEFT     = Point(left, top)
        CIRCLE_TOP_RIGHT    = Point(right, top)
        CIRCLE_BOTTOM_LEFT  = Point(left, bottom)
        CIRCLE_BOTTOM_RIGHT = Point(right, bottom)

        MOVEBOX_TOP_LEFT    = Point(left, top)
        MOVEBOX_TOP_RIGHT   = Point(right, top)
        MOVEBOX_BOTTOM_LEFT = Point(left, bottom)
        MOVEBOX_BOTTOM_RIGHT= Point(right, bottom)
    }





    override fun onDraw(canvas: Canvas) {
        val bw = mPatternImageBitmap!!.width
        val bh = mPatternImageBitmap!!.height

        val pts = floatArrayOf(
                //source
                0f, 0f, 0f, bh.toFloat(), bw.toFloat(), bh.toFloat(), bw.toFloat(), 0f,
                //destination
                0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f)

        pts[8] = CIRCLE_TOP_LEFT!!.x.toFloat()
        pts[9] = CIRCLE_TOP_LEFT!!.y.toFloat()
        pts[10] = CIRCLE_BOTTOM_LEFT!!.x.toFloat()
        pts[11] = CIRCLE_BOTTOM_LEFT!!.y.toFloat()
        pts[12] = CIRCLE_BOTTOM_RIGHT!!.x.toFloat()
        pts[13] = CIRCLE_BOTTOM_RIGHT!!.y.toFloat()
        pts[14] = CIRCLE_TOP_RIGHT!!.x.toFloat()
        pts[15] = CIRCLE_TOP_RIGHT!!.y.toFloat()

        mPolyToPolyMatrix!!.setPolyToPoly(pts, 0, pts, 8, 4)


        canvas.drawBitmap(mPatternImageBitmap, mPolyToPolyMatrix, mPaintBitmapAlpha)

        Draw.drawBoxLine( canvas, CIRCLE_TOP_LEFT, CIRCLE_TOP_RIGHT, CIRCLE_BOTTOM_LEFT, CIRCLE_BOTTOM_RIGHT, mPaintRect )
        Draw.drawBoxTouchCircle( canvas, CIRCLE_TOP_LEFT, CIRCLE_TOP_RIGHT, CIRCLE_BOTTOM_LEFT, CIRCLE_BOTTOM_RIGHT, mPaintCircle , 15f)

        if( mIsTouchInMoveBoxArea ){
            Draw.drawBoxLine( canvas, MOVEBOX_TOP_LEFT, MOVEBOX_TOP_RIGHT, MOVEBOX_BOTTOM_LEFT, MOVEBOX_BOTTOM_RIGHT, mPaintMoveBoxLine )
        }

    }





    override fun onTouch(view: View, event: MotionEvent): Boolean {

        val curTouchPoint = Point(event.x.toInt(), event.y.toInt())

        when (event.action) {

            MotionEvent.ACTION_DOWN ->

                if (inCircle(curTouchPoint, CirclePosition.TopLeft)) {
                    mTouchPoint = CirclePosition.TopLeft
                    UpdateTouchInCircle(mTouchPoint, CIRCLE_TOP_LEFT!!, curTouchPoint)
                    UpdateMoveTouchArea()

                } else if (inCircle(curTouchPoint, CirclePosition.TopRight)) {
                    mTouchPoint = CirclePosition.TopRight
                    UpdateTouchInCircle(mTouchPoint, CIRCLE_TOP_RIGHT!!, curTouchPoint)
                    UpdateMoveTouchArea()

                } else if (inCircle(curTouchPoint, CirclePosition.BottomLeft)) {
                    mTouchPoint = CirclePosition.BottomLeft
                    UpdateTouchInCircle(mTouchPoint, CIRCLE_BOTTOM_LEFT!!, curTouchPoint)
                    UpdateMoveTouchArea()

                } else if (inCircle(curTouchPoint, CirclePosition.BottomRight)) {
                    mTouchPoint = CirclePosition.BottomRight
                    UpdateTouchInCircle(mTouchPoint, CIRCLE_BOTTOM_RIGHT!!, curTouchPoint)
                    UpdateMoveTouchArea()
                } else if( inMoveBoxArea(curTouchPoint)){

                    mIsTouchInMoveBoxArea = true

                    //현재 터치지점을 기억해야 한다.
                    mPrevTouchPoint = curTouchPoint

                    //또한 영역을 그려주어야 한다.
                    invalidate()
                }



            MotionEvent.ACTION_MOVE ->

                if (mTouchPoint != CirclePosition.None) {
                    UpdateTouchInCircle(mTouchPoint, curTouchPoint, curTouchPoint)
                    UpdateMoveTouchArea()

                } else if( mIsTouchInMoveBoxArea){

                    //박스 지점들을 이동한 만큼 이동시켜주고
                    updateMoveBoxPosition(curTouchPoint)

                    //이전 터치 포인트를 현재 포인트로 갱신시켜주고
                    mPrevTouchPoint = curTouchPoint

                    //다시 그리라고 명령해야 한다.
                    invalidate()
                }

            MotionEvent.ACTION_UP ->
            {
                mTouchPoint = CirclePosition.None

                mIsTouchInMoveBoxArea = false

                invalidate()
            }
        }

        return true
    }





    private fun UpdateTouchInCircle(kindOfTouchCircle: CirclePosition, basePoint: Point, curTouchPoint: Point) {
        val curAngle: Double
        val curAngle2: Double
        val curAngle3: Double
        val baseAngleOutside = 181.0
        val baseAngleInside = 179.0


        when (kindOfTouchCircle) {
            PerspectiveDistortView.CirclePosition.TopLeft -> {
                curAngle    = Utils.getAngle(basePoint, CIRCLE_TOP_RIGHT!!, CIRCLE_BOTTOM_LEFT!!)
                curAngle2   = Utils.getAngle(CIRCLE_TOP_RIGHT!!, basePoint, CIRCLE_BOTTOM_RIGHT!!)
                curAngle3   = Utils.getAngle(CIRCLE_BOTTOM_LEFT!!, basePoint, CIRCLE_BOTTOM_RIGHT!!)

                if (curAngle >= baseAngleOutside && curAngle2 <= baseAngleInside && curAngle3 >= baseAngleOutside &&
                        curTouchPoint.x < CIRCLE_TOP_RIGHT!!.x && curTouchPoint.y < CIRCLE_BOTTOM_LEFT!!.y) {
                    CIRCLE_TOP_LEFT!!.set(curTouchPoint.x, curTouchPoint.y)
                }
            }

            PerspectiveDistortView.CirclePosition.TopRight -> {
                curAngle    = Utils.getAngle(basePoint, CIRCLE_TOP_LEFT!!, CIRCLE_BOTTOM_RIGHT!!)
                curAngle2   = Utils.getAngle(CIRCLE_TOP_LEFT!!, basePoint, CIRCLE_BOTTOM_LEFT!!)
                curAngle3   = Utils.getAngle(CIRCLE_BOTTOM_RIGHT!!, basePoint, CIRCLE_BOTTOM_LEFT!!)


                if (curAngle <= baseAngleInside && curAngle2 >= baseAngleOutside && curAngle3 <= baseAngleInside &&
                        curTouchPoint.x > CIRCLE_TOP_LEFT!!.x && curTouchPoint.y < CIRCLE_BOTTOM_RIGHT!!.y) {
                    CIRCLE_TOP_RIGHT!!.set(curTouchPoint.x, curTouchPoint.y)
                }
            }

            PerspectiveDistortView.CirclePosition.BottomLeft -> {
                curAngle    = Utils.getAngle(basePoint, CIRCLE_TOP_LEFT!!, CIRCLE_BOTTOM_RIGHT!!)
                curAngle2   = Utils.getAngle(CIRCLE_TOP_LEFT!!, CIRCLE_TOP_RIGHT!!, basePoint)
                curAngle3   = Utils.getAngle(CIRCLE_BOTTOM_RIGHT!!, CIRCLE_TOP_RIGHT!!, basePoint)


                if (curAngle >= baseAngleOutside && curAngle2 >= baseAngleOutside && curAngle3 <= baseAngleInside &&
                        curTouchPoint.x < CIRCLE_BOTTOM_RIGHT!!.x && curTouchPoint.y > CIRCLE_TOP_LEFT!!.y) {

                    CIRCLE_BOTTOM_LEFT!!.set(curTouchPoint.x, curTouchPoint.y)
                }
            }

            PerspectiveDistortView.CirclePosition.BottomRight -> {
                curAngle    = Utils.getAngle(basePoint, CIRCLE_TOP_RIGHT!!, CIRCLE_BOTTOM_LEFT!!)
                curAngle2   = Utils.getAngle(CIRCLE_TOP_RIGHT!!, CIRCLE_TOP_LEFT!!, basePoint)
                curAngle3   = Utils.getAngle(CIRCLE_BOTTOM_LEFT!!, CIRCLE_TOP_LEFT!!, basePoint)

                if (curAngle <= baseAngleInside && curAngle2 <= baseAngleInside && curAngle3 >= baseAngleOutside &&
                        curTouchPoint.x > CIRCLE_BOTTOM_LEFT!!.x && curTouchPoint.y > CIRCLE_TOP_RIGHT!!.y) {
                    CIRCLE_BOTTOM_RIGHT!!.set(curTouchPoint.x, curTouchPoint.y)
                }
            }
        }

        invalidate()
    }



    private fun UpdateMoveTouchArea(){

        var leftX   : Int = 0
        var rightX  : Int = 0
        var topY    : Int = 0
        var bottomY : Int = 0

        //가장 왼쪽의 점을 구한다.
        leftX   = if( CIRCLE_TOP_LEFT!!.x <= CIRCLE_BOTTOM_LEFT!!.x )    CIRCLE_TOP_LEFT!!.x    else CIRCLE_BOTTOM_LEFT!!.x
        rightX  = if( CIRCLE_TOP_RIGHT!!.x >= CIRCLE_BOTTOM_RIGHT!!.x)   CIRCLE_TOP_RIGHT!!.x   else CIRCLE_BOTTOM_RIGHT!!.x
        topY    = if( CIRCLE_TOP_LEFT!!.y <= CIRCLE_TOP_RIGHT!!.y)       CIRCLE_TOP_LEFT!!.y    else CIRCLE_TOP_RIGHT!!.y
        bottomY = if( CIRCLE_BOTTOM_LEFT!!.y >= CIRCLE_BOTTOM_RIGHT!!.y) CIRCLE_BOTTOM_LEFT!!.y else CIRCLE_BOTTOM_RIGHT!!.y

        MOVEBOX_TOP_LEFT!!.x    = leftX
        MOVEBOX_TOP_LEFT!!.y    = topY

        MOVEBOX_TOP_RIGHT!!.x   = rightX
        MOVEBOX_TOP_RIGHT!!.y   = topY

        MOVEBOX_BOTTOM_LEFT!!.x = leftX
        MOVEBOX_BOTTOM_LEFT!!.y = bottomY

        MOVEBOX_BOTTOM_RIGHT!!.x = rightX
        MOVEBOX_BOTTOM_RIGHT!!.y = bottomY
    }



    private fun updateMoveBoxPosition( curTouchPoint: Point ){

        val moveX : Int = curTouchPoint.x - mPrevTouchPoint!!.x
        val moveY : Int = curTouchPoint.y - mPrevTouchPoint!!.y

        MOVEBOX_TOP_LEFT!!.x += moveX
        MOVEBOX_TOP_LEFT!!.y += moveY
        MOVEBOX_TOP_RIGHT!!.x += moveX
        MOVEBOX_TOP_RIGHT!!.y += moveY
        MOVEBOX_BOTTOM_LEFT!!.x += moveX
        MOVEBOX_BOTTOM_LEFT!!.y += moveY
        MOVEBOX_BOTTOM_RIGHT!!.x += moveX
        MOVEBOX_BOTTOM_RIGHT!!.y += moveY

        CIRCLE_TOP_LEFT!!.x += moveX
        CIRCLE_TOP_LEFT!!.y += moveY

        CIRCLE_TOP_RIGHT!!.x += moveX
        CIRCLE_TOP_RIGHT!!.y += moveY

        CIRCLE_BOTTOM_LEFT!!.x += moveX
        CIRCLE_BOTTOM_LEFT!!.y += moveY

        CIRCLE_BOTTOM_RIGHT!!.x += moveX
        CIRCLE_BOTTOM_RIGHT!!.y += moveY
    }




    private fun inMoveBoxArea( curTouchPoint: Point ) : Boolean{

        if( MOVEBOX_TOP_LEFT!!.x < curTouchPoint.x && MOVEBOX_TOP_RIGHT!!.x > curTouchPoint.x
                && MOVEBOX_TOP_LEFT!!.y < curTouchPoint.y && MOVEBOX_BOTTOM_RIGHT!!.y > curTouchPoint.y)
            return true

        return false
    }



    private fun inCircle(curTouchPoint: Point, kindOfTouchCircle: CirclePosition): Boolean {

        val circleRadius = 40f
        val isInCircle: Boolean

        when (kindOfTouchCircle) {
            PerspectiveDistortView.CirclePosition.TopLeft -> isInCircle = inCircle(curTouchPoint.x.toFloat(), curTouchPoint.y.toFloat(), CIRCLE_TOP_LEFT!!.x.toFloat(), CIRCLE_TOP_LEFT!!.y.toFloat(), circleRadius)
            PerspectiveDistortView.CirclePosition.TopRight -> isInCircle = inCircle(curTouchPoint.x.toFloat(), curTouchPoint.y.toFloat(), CIRCLE_TOP_RIGHT!!.x.toFloat(), CIRCLE_TOP_RIGHT!!.y.toFloat(), circleRadius)
            PerspectiveDistortView.CirclePosition.BottomLeft -> isInCircle = inCircle(curTouchPoint.x.toFloat(), curTouchPoint.y.toFloat(), CIRCLE_BOTTOM_LEFT!!.x.toFloat(), CIRCLE_BOTTOM_LEFT!!.y.toFloat(), circleRadius)
            PerspectiveDistortView.CirclePosition.BottomRight -> isInCircle = inCircle(curTouchPoint.x.toFloat(), curTouchPoint.y.toFloat(), CIRCLE_BOTTOM_RIGHT!!.x.toFloat(), CIRCLE_BOTTOM_RIGHT!!.y.toFloat(), circleRadius)
            else -> isInCircle = false
        }

        return isInCircle
    }



    private fun inCircle(x: Float, y: Float, circleCenterX: Float, circleCenterY: Float, circleRadius: Float): Boolean {
        val dx = Math.pow((x - circleCenterX).toDouble(), 2.0)
        val dy = Math.pow((y - circleCenterY).toDouble(), 2.0)

        if (dx + dy < Math.pow(circleRadius.toDouble(), 2.0)) {
            return true
        }

        return false
    }




    fun setPatternImage( resID : Int){

        mPatternImageBitmap = BitmapFactory.decodeResource(resources, resID)
        invalidate()
    }



    fun setPatternImageAlpha( progress : Int ){
       mPaintBitmapAlpha!!.alpha = progress
        invalidate()
    }



    fun setPatternColor( color : Int){

        mBitmapColorfilter = LightingColorFilter(color, 1)
        mPaintBitmapAlpha!!.setColorFilter(mBitmapColorfilter)
        invalidate()
    }
}
