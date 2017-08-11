package dev.patternoid.com.touchinputtest.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dev.patternoid.com.touchinputtest.DragAndDrawFragment
import dev.patternoid.com.touchinputtest.R




class PatternChunkListAdapter : RecyclerView.Adapter<PatternChunkListHolder>{

    private var mPattern        : IntArray?             = null
    private var mLayoutInflater : LayoutInflater?       = null
    private var mFragment       : DragAndDrawFragment?  = null



    constructor(context : Context, patterns : IntArray, fragment : DragAndDrawFragment){

        mLayoutInflater = LayoutInflater.from(context)
        mPattern        = patterns
        mFragment       = fragment
    }



    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): PatternChunkListHolder {
        val view : View = mLayoutInflater!!.inflate(R.layout.button_pattern, parent, false)
        return PatternChunkListHolder(view, mFragment)
    }




    override fun onBindViewHolder(holder: PatternChunkListHolder?, position: Int) {
        holder?.setHolderInfo( mPattern!![position], position )
    }




    override fun getItemCount(): Int {
        return mPattern!!.size
    }
}