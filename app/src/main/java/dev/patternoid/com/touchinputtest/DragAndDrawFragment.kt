package dev.patternoid.com.touchinputtest

import android.content.Context
import android.content.res.Resources
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.DisplayMetrics
import android.util.Log
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.*
import kotlinx.android.synthetic.main.fragment_drag_and_draw.*

/**
 * Created by patternoid on 2017. 8. 2..
 */

class DragAndDrawFragment : Fragment(){

    companion object {

        var MESSAGE_TEST : Int = 0

        var messageHandler : SendMessageHandler? = null

        var imageList : ArrayList<ImageView>? = null


        fun newInstance() : DragAndDrawFragment{
            return DragAndDrawFragment()
        }
    }


    private var patternAdapter : PatternAdapter? = null



    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view : View? = inflater?.inflate( R.layout.fragment_drag_and_draw, container, false)

        messageHandler =  SendMessageHandler(this@DragAndDrawFragment)

        updateFooterUI(view!!)

        return view
    }



    fun makePatternLayout(){
        var patternLayout       = view!!.findViewById<LinearLayout>(R.id.linear_layout_pattern_holder)
        var patternLayoutParam  =  patternLayout.layoutParams

        var region : Box        =  box_drawing_view.getBoxData()

        val left : Float = Math.min( region.mOrigin!!.x, region.mCurrent!!.x )
        val right: Float = Math.max( region.mOrigin!!.x, region.mCurrent!!.x )
        val top  : Float = Math.min( region.mOrigin!!.y, region.mCurrent!!.y )
        val bottom: Float = Math.max( region.mOrigin!!.y, region.mCurrent!!.y )


        patternLayoutParam.width = (right - left).toInt()
        patternLayoutParam.height= (bottom - top).toInt()

        patternLayout.layoutParams = patternLayoutParam

        patternLayout.x = box_drawing_view.posX
        patternLayout.y = box_drawing_view.posY
        patternLayout.weightSum = 1f


        var widthNum : Int = (right - left).toInt() / 50
        var heighNum : Int = (bottom - top).toInt() / 50
        if( (right - left).toInt() % 50 != 0)
            widthNum += 1

        if( (bottom - top).toInt() % 50 != 0)
            heighNum += 1

        imageList = ArrayList<ImageView>()

        for( i : Int in 1..heighNum ){

            //리니어 레이아웃 하나를 생성한다.
            val horizontalLayout = LinearLayout(context)
            horizontalLayout.orientation = LinearLayout.HORIZONTAL
            horizontalLayout.weightSum = 1f

            var width = convertPixelsToDp((50 * widthNum).toFloat() , activity)
            width = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, width, resources.displayMetrics)

            horizontalLayout.layoutParams = LinearLayout.LayoutParams( width.toInt() , LinearLayout.LayoutParams.WRAP_CONTENT )


            for( j : Int in 1..widthNum ){
                    val patternImageView = ImageView(context)
                    patternImageView.setImageResource(R.drawable.temp)
                    patternImageView.scaleType = ImageView.ScaleType.MATRIX
                    patternImageView.layoutParams = LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.WRAP_CONTENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT, 1f)

                    horizontalLayout.addView( patternImageView)

                imageList?.add(patternImageView)
            }

            //완성된 리니어 레이아웃을 부모 Vertical Linear Layout에 넣어준다.
            patternLayout.addView( horizontalLayout )
        }
    }



    fun convertPixelsToDp(  px : Float,  context : Context) : Float {

        val resources = context.resources
        val metrics = resources.displayMetrics

        val dp = px / (metrics.densityDpi / 160f)

        return dp

    }





    fun updateFooterUI(inflateView : View ){
        var imageIDs = intArrayOf(
                R.drawable.pattern_1
                ,R.drawable.pattern_2
                ,R.drawable.pattern_3
                ,R.drawable.pattern_4
                ,R.drawable.pattern_5
                ,R.drawable.pattern_6
                ,R.drawable.pattern_7
                ,R.drawable.pattern_8
                ,R.drawable.pattern_9
                ,R.drawable.pattern_10 )

        patternAdapter = PatternAdapter(activity, imageIDs)

        val recyclerView  = inflateView.findViewById<RecyclerView>(R.id.recycler_view_pattern_buttons)
        recyclerView.adapter = patternAdapter
    }






    private class PatternHolder : RecyclerView.ViewHolder{

        var textView : TextView? = null
        var imageButton : ImageButton? = null
        var index       : Int = 0

        constructor( itemView : View ) : super(itemView ){
            textView    = itemView.findViewById<TextView>(R.id.text_view_pattern_name)
            imageButton = itemView.findViewById<ImageButton>(R.id.image_button_patterns)


            imageButton!!.setOnClickListener( object : View.OnClickListener{
                override fun onClick(p0: View?) {
                    val resId = itemView.context.resources.getIdentifier("pattern_"+ (index+1), "drawable", itemView.context.packageName )

                    for( image : ImageView in DragAndDrawFragment.imageList!! ){
                        image.setImageResource(resId)
                    }
                }

            })
        }
    }



    private class PatternAdapter : RecyclerView.Adapter<PatternHolder>{

        private var mPattern : IntArray? = null
        private var layoutInflater : LayoutInflater? = null

        constructor(context : Context, patterns : IntArray){
            mPattern = patterns
            layoutInflater = LayoutInflater.from(context)
        }


        override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): PatternHolder {

            val view : View = layoutInflater!!.inflate(R.layout.button_pattern, parent, false)

            return PatternHolder(view)
        }



        override fun onBindViewHolder(holder: PatternHolder?, position: Int) {
            holder?.imageButton!!.setImageResource( mPattern!![position] )
            holder?.textView!!.text = "패턴_" + (position+1)

            holder?.index = position
        }



        override fun getItemCount(): Int {
            return mPattern!!.size
        }
    }
}