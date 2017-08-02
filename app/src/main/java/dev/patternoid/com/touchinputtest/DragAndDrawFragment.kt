package dev.patternoid.com.touchinputtest

import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridView
import kotlinx.android.synthetic.main.fragment_drag_and_draw.*

/**
 * Created by patternoid on 2017. 8. 2..
 */

class DragAndDrawFragment : Fragment(){

    companion object {

        var MESSAGE_TEST : Int = 0

        var messageHandler : SendMessageHandler? = null

        fun newInstance() : DragAndDrawFragment{
            return DragAndDrawFragment()
        }
    }



    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view : View? = inflater?.inflate( R.layout.fragment_drag_and_draw, container, false)

        messageHandler =  SendMessageHandler(this@DragAndDrawFragment)

        var imgIds = IntArray(200)
        for( i : Int in 0..199){
            imgIds[i] = R.drawable.temp
        }

        var adapter : MyAdapter = MyAdapter( context , R.layout.chunk, imgIds)
        view!!.findViewById<GridView>(R.id.grid_view_blind_holder).adapter = adapter
        //grid_view_blind_holder.adapter = adapter


        return view
    }




    fun setGridView(){
        var param = grid_view_blind_holder.layoutParams

        var box : Box =  box_drawing_view.getBoxData()


        val left : Float = Math.min( box.mOrigin!!.x, box.mCurrent!!.x )
        val right: Float = Math.max( box.mOrigin!!.x, box.mCurrent!!.x )
        val top  : Float = Math.min( box.mOrigin!!.y, box.mCurrent!!.y )
        val bottom: Float = Math.max( box.mOrigin!!.y, box.mCurrent!!.y )

        param.width = (right - left).toInt()
        param.height= (bottom - top).toInt()


        grid_view_blind_holder.layoutParams = param
        grid_view_blind_holder.visibility = View.VISIBLE

        grid_view_blind_holder.x = box_drawing_view.posX
        grid_view_blind_holder.y = box_drawing_view.posY
        grid_view_blind_holder.columnWidth = 70
    }


}