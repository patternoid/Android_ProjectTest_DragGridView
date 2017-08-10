package dev.patternoid.com.touchinputtest

import android.os.Bundle
import android.support.v4.app.Fragment
import dev.patternoid.com.touchinputtest.abstracts.SingleFragmentActivity

class MainActivity : SingleFragmentActivity() {

    override fun createFragment(): Fragment {
        return DragAndDrawFragment.newInstance()
    }

}
