package dev.patternoid.com.touchinputtest.adapter

import android.content.Context
import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dev.patternoid.com.touchinputtest.DragAndDrawFragment
import dev.patternoid.com.touchinputtest.R

/**
 * Created by Patternoid on 2017-08-11.
 */
class PatternColorListAdapter : RecyclerView.Adapter<PatternColorListHolder>{

    private var mLayoutInflater : LayoutInflater? = null
    private var mColorList      : IntArray?         = null
    private var mFragment       : DragAndDrawFragment? = null

    constructor( context : Context, fragment : DragAndDrawFragment){

        mFragment       = fragment
        mLayoutInflater = LayoutInflater.from(context)

        mColorList = intArrayOf( Color.WHITE
                                ,Color.GRAY
                                ,Color.BLACK
                                ,Color.RED
                                ,Color.BLUE
                                ,Color.GREEN )
    }



    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): PatternColorListHolder {
        val view : View = mLayoutInflater!!.inflate(R.layout.button_color_pick, parent, false)

        return PatternColorListHolder(view, mFragment!!)
    }


    override fun onBindViewHolder(holder: PatternColorListHolder?, position: Int) {
        holder?.setButtonColor(mColorList!![position])
    }


    override fun getItemCount(): Int {
        return mColorList!!.size
    }

}