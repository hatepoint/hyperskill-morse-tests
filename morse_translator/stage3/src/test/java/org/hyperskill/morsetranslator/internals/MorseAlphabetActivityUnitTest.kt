package org.hyperskill.morsetranslator.internals

import android.app.Activity
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView



open class MorseAlphabetActivityUnitTest<T : Activity>(clazz: Class<T>): AbstractUnitTest<T>(clazz) {

    companion object {
        const val ID_TV_DESCRIPTION = "tv_description"
        const val ID_RV_ALPHABET = "rv_alphabet"
    }
    val tvDesc by lazy {
        activity.findViewByString<TextView>(ID_TV_DESCRIPTION)
    }
    val recyclerView by lazy {
        activity.findViewByString<RecyclerView>(ID_RV_ALPHABET)
    }
}