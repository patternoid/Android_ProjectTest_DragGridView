package dev.patternoid.com.touchinputtest.adapter

import android.graphics.ColorFilter
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.widget.Button
import dev.patternoid.com.touchinputtest.DragAndDrawFragment
import dev.patternoid.com.touchinputtest.R
import dev.patternoid.com.touchinputtest.model.DataManager

/**
 * Created by Patternoid on 2017-08-11.
 */
class PatternColorListHolder : RecyclerView.ViewHolder {

    private var mFragment       : DragAndDrawFragment? = null
    private var mColorButton    : Button? = null
    private var mColor          : Int?  = null

    constructor(itemView : View, fragment : DragAndDrawFragment? ) : super(itemView){

        mFragment       = fragment
        mColorButton    = itemView.findViewById<Button>(R.id.button_color)

        val listener : View.OnClickListener = View.OnClickListener { view ->
            Log.d( "SDFJAFJA","fdklsjafaklsdjf;akldjfkl;ajf;kladjsfkl;jad;klf")

            DataManager.instance.mUserSelectedPattern.mSelectedColorID = mColor!!
            mFragment!!.setPatternColor()
        }


            mColorButton!!.setOnClickListener(listener)

    }



    fun setButtonColor( color : Int ){
        mColor = color
        mColorButton!!.setBackgroundColor(color)

    }
}