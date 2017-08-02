package dev.patternoid.com.touchinputtest

import android.support.v4.app.Fragment

class MainActivity : SingleFragmentActivity() {

    override fun createFragment(): Fragment {
        return DragAndDrawFragment.newInstance()
    }
}
