package dev.patternoid.com.touchinputtest.model

/**
 * Created by Patternoid on 2017-08-07.
 */
class DataManager private constructor(){

    private object Holder { val INSTANCE = DataManager() }

    companion object {
        val instance : DataManager by lazy { Holder.INSTANCE }
    }

    var mUserSelectedPattern : UserSelectedPattern = UserSelectedPattern(0)
}