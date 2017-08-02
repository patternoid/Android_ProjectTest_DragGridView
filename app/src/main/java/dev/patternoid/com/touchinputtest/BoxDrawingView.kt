package dev.patternoid.com.touchinputtest

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.PointF
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import kotlinx.android.synthetic.main.fragment_drag_and_draw.view.*

/**
 * Created by patternoid on 2017. 8. 2..
 */
class BoxDrawingView : View {

    companion object {
        const val TAG : String = "BoxDrawingView"
    }

    private var mCurrentBox : Box?              = null
    private var mBoxen      : ArrayList<Box>    = ArrayList<Box>()
    private var mBoxPaint   : Paint?            = null
    private var mBackgroundPaint : Paint?       = null

    private var mTestBoolean : Boolean          = false


    var posX : Float = 0f
    var posY : Float = 0f

    constructor( context: Context ) : this( context, null )
    constructor( context : Context , attrs : AttributeSet? ) : super( context, attrs )

    init {

        //반투명의 붉은색으로 박스를 그린다
        mBoxPaint = Paint()
        mBoxPaint!!.color = (0xff000000).toInt()
        mBoxPaint!!.style = Paint.Style.STROKE
        mBoxPaint!!.strokeWidth = 5.0f

        mBackgroundPaint = Paint()
        mBackgroundPaint!!.color = (0xfff8efe0).toInt()
    }



    override fun onTouchEvent(event: MotionEvent?): Boolean {
        var current : PointF = PointF( event?.x!!, event?.y!! )
        var action : String = ""

        when( event?.action )
        {
            MotionEvent.ACTION_DOWN ->{
                action = "ACTION_DOWN"
                mCurrentBox = Box(current, current)
                mBoxen.add(mCurrentBox!!)

                posX = event.getX()
                posY = event.getY()
            }

            MotionEvent.ACTION_MOVE -> {
                action = "ACTION_MOVE"

                mCurrentBox?.apply{
                    mCurrent = current

                    //사용자가 화면을 드래깅하는 동안 박스가 그려지는 것을 볼 수 있게 하기 위한 구문
                    //BoxDrawingView가 자신을 다시 그리게 하는 구문
                    invalidate()
                }
            }

            MotionEvent.ACTION_UP   -> {
                action = "ACTION_UP"

                mTestBoolean = true
                //그리드 레이아웃을 설정하여
                DragAndDrawFragment.messageHandler!!.sendEmptyMessage(DragAndDrawFragment.MESSAGE_TEST)
                //mCurrentBox = null
            }

            MotionEvent.ACTION_CANCEL->{
                action = "ACTION_CANCEL"
                //mCurrentBox = null
            }
        }

        Log.i( TAG, action + " at x=" + current.x + ", y=" + current.y )

        return true
    }


    override fun onDraw(canvas: Canvas?) {

        //배경을 채운다
        //canvas?.drawPaint(mBackgroundPaint)

        for( box : Box in mBoxen ){
            val left : Float = Math.min( box.mOrigin!!.x, box.mCurrent!!.x )
            val right: Float = Math.max( box.mOrigin!!.x, box.mCurrent!!.x )
            val top  : Float = Math.min( box.mOrigin!!.y, box.mCurrent!!.y )
            val bottom: Float = Math.max( box.mOrigin!!.y, box.mCurrent!!.y )

            canvas?.drawRect(left, top, right, bottom, mBoxPaint)
        }

    }



    fun getBoxData() : Box{
        return mBoxen[0]
    }
}