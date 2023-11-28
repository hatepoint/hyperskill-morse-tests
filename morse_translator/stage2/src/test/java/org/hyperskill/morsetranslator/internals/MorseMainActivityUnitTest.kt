package org.hyperskill.morsetranslator.internals

import android.app.Activity
import android.content.Intent
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import org.junit.Assert.assertEquals

open class MorseMainActivityUnitTest<T : Activity>(clazz: Class<T>): AbstractUnitTest<T>(clazz) {

    companion object {
        const val ID_TV_TEXT = "tv_text"
        const val ID_TV_MORSE = "tv_morse"
        const val ID_ET_TEXT = "et_text"
        const val ID_ET_MORSE = "et_morse"
        const val ID_BT_CLEAR = "bt_clear"
        const val ID_BT_GUIDE = "bt_guide"
    }

    val tvText: TextView by lazy {
        val view = activity.findViewByString<TextView>(ID_TV_TEXT)

        val messageInitialText = "The $ID_TV_TEXT has a wrong initial text"
        val expectedInitialText = "text"
        val actualInitialText = view.text.toString().lowercase()
        assertEquals(messageInitialText, expectedInitialText, actualInitialText)

        view
    }

    val tvMorse: TextView by lazy {
        val view = activity.findViewByString<TextView>(ID_TV_MORSE)

        val messageInitialText = "The $ID_TV_MORSE has a wrong initial text"
        val expectedInitialText = "morse"
        val actualInitialText = view.text.toString().lowercase()
        assertEquals(messageInitialText, expectedInitialText, actualInitialText)

        view
    }

    val etText: EditText by lazy {
        val view = activity.findViewByString<EditText>(ID_ET_TEXT)

        val messageInitialHint = "The $ID_ET_TEXT has a wrong initial hint"
        val expectedInitialHint = "text"
        val actualInitialHint = view.hint.toString().lowercase()
        assertEquals(messageInitialHint, expectedInitialHint, actualInitialHint)

        view
    }

    val etMorse: EditText by lazy {
        val view = activity.findViewByString<EditText>(ID_ET_MORSE)

        val messageInitialHint = "The $ID_ET_MORSE has a wrong initial hint"
        val expectedInitialHint = "morse"
        val actualInitialHint = view.hint.toString().lowercase()
        assertEquals(messageInitialHint, expectedInitialHint, actualInitialHint)

        view
    }

    val btClear: Button by lazy {
        val view = activity.findViewByString<Button>(ID_BT_CLEAR)

        val messageInitialText = "The $ID_BT_CLEAR has the wrong text"
        val expectedInitialText = "clear"
        val actualInitialText = view.text.toString().lowercase()
        assertEquals(messageInitialText, expectedInitialText, actualInitialText)

        view
    }

    val btGuide: Button by lazy {
        val view = activity.findViewByString<Button>(ID_BT_GUIDE)

        val messageInitialText = "The $ID_BT_GUIDE has the wrong text"
        val expectedInitialText = "guide"
        val actualInitialText = view.text.toString().lowercase()
        assertEquals(messageInitialText, expectedInitialText, actualInitialText)

        view
    }

    fun assertTranslateTextToMorse(text: String, expectedMorse: String) {
        val messageInitialText =
            "The $ID_ET_MORSE doesn't seem to represent the contents of $ID_ET_TEXT correctly"
        etText.setText(text)
        assertEquals(messageInitialText, expectedMorse, etMorse.text.toString())
    }

    fun assertTranslateMorseToText(morse: String, expectedText: String) {
        val messageInitialText =
            "The $ID_ET_TEXT doesn't seem to represent the contents of $ID_ET_MORSE correctly"
        etMorse.setText(morse)
        assertEquals(messageInitialText, expectedText, etText.text.toString())
    }

    fun <A: Activity>navigateToAlphabetActivity(clazz: Class<A>): Pair<MorseAlphabetActivityUnitTest<A>, Intent> {
        val intent = testActivity {
            btGuide.clickAndRun()

            val nextStartedActivityIntent = shadowActivity.nextStartedActivity
                ?: throw AssertionError("No intent for starting activity found")
            val expectedStartedActivityShortClassName = ".AlphabetActivity"
            val actualStartedActivityShortClassName =
                nextStartedActivityIntent.component?.shortClassName ?: "null"
            assertEquals(
                "The new activity was expected to be AlphabetActivity",
                expectedStartedActivityShortClassName,
                actualStartedActivityShortClassName
            )

            return@testActivity nextStartedActivityIntent
        }

        activityController.pause()
        activityController.stop()
        val alphabetActivityUnitTest = MorseAlphabetActivityUnitTest(clazz)

        return alphabetActivityUnitTest to intent
    }
}