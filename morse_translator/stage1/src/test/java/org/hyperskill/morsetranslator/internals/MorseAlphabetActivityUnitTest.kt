package org.hyperskill.morsetranslator.internals

import android.app.Activity
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView



open class MorseAlphabetActivityUnitTest<T : Activity>(clazz: Class<T>): AbstractUnitTest<T>(clazz) {

    companion object {
        const val ID_TV_DESCRIPTION = "tv_description"
        const val ID_RV_ALPHABET = "rv_alphabet"
        const val ID_TV_LETTER = "tv_letter"
        const val ID_TV_MORSE = "tv_morse"
    }
    val tvDesc by lazy {
        activity.findViewByString<TextView>(ID_TV_DESCRIPTION)
    }
    val recyclerView by lazy {
        activity.findViewByString<RecyclerView>(ID_RV_ALPHABET)
    }

    inner class ItemViewBinding(root: View) {
        val tvLetter = root.findViewByString<TextView>(ID_TV_LETTER)
        val tvMorse = root.findViewByString<TextView>(ID_TV_MORSE)
    }

    val morseCodeMap = mapOf(
        'A' to ".-",
        'B' to "-...",
        'C' to "-.-.",
        'D' to "-..",
        'E' to ".",
        'F' to "..-.",
        'G' to "--.",
        'H' to "....",
        'I' to "..",
        'J' to ".---",
        'K' to "-.-",
        'L' to ".-..",
        'M' to "--",
        'N' to "-.",
        'O' to "---",
        'P' to ".--.",
        'Q' to "--.-",
        'R' to ".-.",
        'S' to "...",
        'T' to "-",
        'U' to "..-",
        'V' to "...-",
        'W' to ".--",
        'X' to "-..-",
        'Y' to "-.--",
        'Z' to "--..",
        '0' to "-----",
        '1' to ".----",
        '2' to "..---",
        '3' to "...--",
        '4' to "....-",
        '5' to ".....",
        '6' to "-....",
        '7' to "--...",
        '8' to "---..",
        '9' to "----.9"
    )
}