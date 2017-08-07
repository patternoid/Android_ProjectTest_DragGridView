package dev.patternoid.com.touchinputtest.abstracts

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.support.v4.app.FragmentManager
import dev.patternoid.com.touchinputtest.R

/**
 * Created by patternoid on 2017. 8. 2..
 */
abstract class SingleFragmentActivity : FragmentActivity(){

    protected abstract fun createFragment() : Fragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fragment)

        val fm : FragmentManager = supportFragmentManager
        var fragment : Fragment?  = fm.findFragmentById(R.id.fragment_container)

        if(fragment == null){
            fragment = createFragment()
            fm.beginTransaction()
                    .add(R.id.fragment_container, fragment)
                    .commit()
        }
    }
}