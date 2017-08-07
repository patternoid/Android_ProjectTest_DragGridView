package dev.patternoid.com.touchinputtest.patternchunk

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import dev.patternoid.com.touchinputtest.DragAndDrawFragment
import dev.patternoid.com.touchinputtest.R

/**
 * Created by Patternoid on 2017-08-07.
 */
class PatternChunkListHolder : RecyclerView.ViewHolder {

    var textView: TextView? = null
    var imageButton: ImageButton? = null
    var index: Int = 0

    constructor(itemView: View) : super(itemView) {

        textView = itemView.findViewById<TextView>(R.id.text_view_pattern_name)
        imageButton = itemView.findViewById<ImageButton>(R.id.image_button_patterns)
        imageButton!!.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                val resId = itemView.context.resources.getIdentifier("pattern_" + (index + 1), "drawable", itemView.context.packageName)

                for (image: ImageView in DragAndDrawFragment.mPatternChunkImageViews!!) {
                    image.setImageResource(resId)
                }
            }

        })
    }
}