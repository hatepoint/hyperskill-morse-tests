package org.hyperskill.morsetranslator

import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import org.hyperskill.morsetranslator.internals.AbstractUnitTest
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class Stage2UnitTest : AbstractUnitTest<MainActivity>(MainActivity::class.java){

    private val tvText: TextView by lazy {
        val view = activity.findViewByString<TextView>("tv_text")

        val messageInitialText = "The tvText has a wrong initial text"
        val expectedInitialText = "text"
        val actualInitialText = view.text.toString().lowercase()
        assertEquals(messageInitialText, expectedInitialText, actualInitialText)

        view
    }

    private val tvMorse: TextView by lazy {
        val view = activity.findViewByString<TextView>("tv_morse")

        val messageInitialText = "The tvMorse has a wrong initial text"
        val expectedInitialText = "morse"
        val actualInitialText = view.text.toString().lowercase()
        assertEquals(messageInitialText, expectedInitialText, actualInitialText)

        view
    }

    private val etText: EditText by lazy {
        val view = activity.findViewByString<EditText>("et_text")

        val messageInitialHint = "The etText has a wrong initial hint"
        val expectedInitialHint = "text"
        val actualInitialHint = view.hint.toString().lowercase()
        assertEquals(messageInitialHint, expectedInitialHint, actualInitialHint)

        view
    }

    private val etMorse: EditText by lazy {
        val view = activity.findViewByString<EditText>("et_morse")

        val messageInitialHint = "The etMorse has a wrong initial hint"
        val expectedInitialHint = "morse"
        val actualInitialHint = view.hint.toString().lowercase()
        assertEquals(messageInitialHint, expectedInitialHint, actualInitialHint)

        view
    }

    private val btClear: Button by lazy {
        val view = activity.findViewByString<Button>("bt_clear")

        val messageInitialText = "The btClear has the wrong text"
        val expectedInitialText = "clear"
        val actualInitialText = view.text.toString().lowercase()
        assertEquals(messageInitialText, expectedInitialText, actualInitialText)

        view
    }

    private val btGuide: Button by lazy {
        val view = activity.findViewByString<Button>("bt_guide")

        val messageInitialText = "The btGuide has the wrong text"
        val expectedInitialText = "guide"
        val actualInitialText = view.text.toString().lowercase()
        assertEquals(messageInitialText, expectedInitialText, actualInitialText)

        view
    }

    @Test
    fun checkTvTextInitialState() {
        testActivity {
            tvText
        }
    }

    @Test
    fun checkTvMorseInitialState() {
        testActivity {
            tvMorse
        }
    }

    @Test
    fun checkEtTextInitialState() {
        testActivity {
            etText
        }
    }

    @Test
    fun checkEtMorseInitialState() {
        testActivity {
            etMorse
        }
    }

    @Test
    fun checkBtClearInitialState() {
        testActivity {
            btClear
        }
    }

    @Test
    fun checkBtGuideInitialState() {
        testActivity {
            btGuide
        }
    }

    @Test
    fun `Check text translating to morse`() {
        testActivity {
            val messageInitialText = "The etText doesn't seem to represent the contents of etMorse correctly"
            etText.setText("HELLO")
            assertEquals(messageInitialText, ".... . .-.. .-.. ---", etMorse.text.toString())
        }
    }

    @Test
    fun `Check text translating to morse 2`() {
        testActivity {
            val messageInitialText = "The etText doesn't seem to represent the contents of etMorse correctly"
            etText.setText("ANDROID TEST")
            assertEquals(messageInitialText, ".- -. -.. .-. --- .. -..    - . ... -", etMorse.text.toString())
        }
    }

    @Test
    fun `Check morse translating to text`() {
        testActivity {
            val messageInitialText = "The etMorse doesn't seem to represent the contents of etText correctly"
            etMorse.setText("... --- ...")
            assertEquals(messageInitialText, "SOS", etText.text.toString())
        }
    }

    @Test
    fun `Check morse translating to text 2`() {
        testActivity {
            val messageInitialText = "The etMorse doesn't seem to represent the contents of etText correctly"
            etMorse.setText(".- -. -.. .-. --- .. -..    - . ... -")
            assertEquals(messageInitialText, "ANDROID TEST", etText.text.toString())
        }
    }

    @Test
    fun `Check clear button`() {
        testActivity {
            val messageInitialText = "The Clear button doesn't seem to clear the contents of on of the EditTexts"
            etText.setText("Test")
            btClear.clickAndRun()
            assertEquals(messageInitialText, "", etText.text.toString())
            assertEquals(messageInitialText, "", etMorse.text.toString())
        }
    }
}