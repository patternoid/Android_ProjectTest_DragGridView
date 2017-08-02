package dev.patternoid.com.touchinputtest

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView

/**
 * Created by patternoid on 2017. 8. 2..
 */
class MyAdapter : BaseAdapter{

    var mContext : Context? = null
    var mLayout  : Int?      = null
    var mImageIds: IntArray? = null
    var mLayoutInflater : LayoutInflater? = null


    constructor(context : Context, layout : Int, imgIds : IntArray ) {
        mContext = context
        mLayout  = layout
        mImageIds = imgIds
        mLayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    }


    override fun getCount(): Int {
        return mImageIds!!.size
    }


    override fun getItem(p0: Int): Any {
        return mImageIds!![p0]
    }


    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }


    override fun getView(position: Int, convertView : View?, parent : ViewGroup?): View {

        var view : View

        if( convertView == null ){
            view = mLayoutInflater!!.inflate(mLayout!!, null)
        }
        else{
            view = convertView as View
        }

        val imageView = view.findViewById<ImageView>(R.id.image_view_chunk)

        imageView.setImageResource(mImageIds!![position])

        return view
    }
}