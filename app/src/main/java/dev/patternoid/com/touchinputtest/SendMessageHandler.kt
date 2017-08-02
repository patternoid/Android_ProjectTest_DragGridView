package dev.patternoid.com.touchinputtest

import android.os.Handler
import android.os.Message
import android.view.View

/**
 * Created by patternoid on 2017. 8. 2..
 */

class SendMessageHandler : Handler{

    private var targetFragment : DragAndDrawFragment? = null

    constructor( fragment: DragAndDrawFragment ) : super(){
        targetFragment = fragment
    }



    override fun handleMessage(msg: Message?) {
        super.handleMessage(msg)

        when( msg?.what ){

            DragAndDrawFragment.MESSAGE_TEST->
            {
                targetFragment!!.setGridView()

            }
        }


    }

}