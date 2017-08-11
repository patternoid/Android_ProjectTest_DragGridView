package dev.patternoid.com.touchinputtest.adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import dev.patternoid.com.touchinputtest.DragAndDrawFragment
import dev.patternoid.com.touchinputtest.R
import dev.patternoid.com.touchinputtest.model.DataManager

/**
 * Created by Patternoid on 2017-08-07.
 */
class PatternChunkListHolder : RecyclerView.ViewHolder {

    private var mFragment       : DragAndDrawFragment? = null
    private var mNameTextView   : TextView? = null
    private var mPatternButton  : ImageButton? = null

    private var mIndex          : Int = 0



    constructor(itemView: View, fragment: DragAndDrawFragment? ) : super(itemView) {

        mNameTextView   = itemView.findViewById<TextView>(R.id.text_view_pattern_name)
        mPatternButton  = itemView.findViewById<ImageButton>(R.id.image_button_patterns)
        mFragment       = fragment


        mPatternButton!!.setOnClickListener(object : View.OnClickListener {

            override fun onClick(p0: View?) {

                val resId = itemView.context.resources.getIdentifier("pattern_" + (mIndex + 1), "drawable", itemView.context.packageName)
                DataManager.instance.mUserSelectedPattern.mSelectedPatternID = resId

                mFragment!!.setPatternImage()
            /*for (image: ImageView in DragAndDrawFragment.mPatternChunkImageViews!!) {
                image.setImageResource(resId)
            }*/
        }
        })
    }



    fun setHolderInfo( resID : Int, index : Int ){

        val nameText = "패턴_" + (index+1).toString()

        mPatternButton!!.setImageResource(resID)
        mNameTextView!!.setText(nameText)
        mIndex = index
    }
}