package dev.patternoid.com.touchinputtest.patternchunk

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dev.patternoid.com.touchinputtest.R




class PatternChunkListAdapter : RecyclerView.Adapter<PatternChunkListHolder>{

    private var mPattern : IntArray? = null
    private var layoutInflater : LayoutInflater? = null

    constructor(context : Context, patterns : IntArray){
        mPattern = patterns
        layoutInflater = LayoutInflater.from(context)
    }


    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): PatternChunkListHolder {

        val view : View = layoutInflater!!.inflate(R.layout.button_pattern, parent, false)

        return PatternChunkListHolder(view)
    }



    override fun onBindViewHolder(holder: PatternChunkListHolder?, position: Int) {
        holder?.imageButton!!.setImageResource( mPattern!![position] )
        holder?.textView!!.text = "패턴_" + (position+1)

        holder?.index = position
    }



    override fun getItemCount(): Int {
        return mPattern!!.size
    }
}