package org.hyperskill.morsetranslator

import android.content.Intent
import android.graphics.Typeface
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.StyleSpan
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.core.text.inSpans
import androidx.core.text.toSpannable
import androidx.core.text.toSpanned
import androidx.recyclerview.widget.RecyclerView
import org.hyperskill.morsetranslator.internals.AbstractUnitTest
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class Stage3UnitTest : AbstractUnitTest<MainActivity>(MainActivity::class.java){

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

    fun getAlphabetActivityIntent(): Intent {
        return testActivity {
            btGuide.clickAndRun()

            val nextStartedActivityIntent = shadowActivity.nextStartedActivity ?: throw AssertionError("No intent for starting activity found")
            val expectedStartedActivityShortClassName = ".AlphabetActivity"
            val actualStartedActivityShortClassName = nextStartedActivityIntent.component?.shortClassName ?: "null"
            assertEquals("The new activity was expected to be AlphabetActivity", expectedStartedActivityShortClassName, actualStartedActivityShortClassName)

            return@testActivity nextStartedActivityIntent
        }
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
    fun `Check new activity starts after Guide button click`() {
        val alphabetActivityIntent = getAlphabetActivityIntent()
        activityController.pause()
        activityController.stop()
        val alphabetActivityUnitTest = object : AbstractUnitTest<AlphabetActivity>(AlphabetActivity::class.java) {}

        alphabetActivityUnitTest.testActivity(alphabetActivityIntent) { alphabetActivity ->
            val tvDesc = alphabetActivity.findViewByString<TextView>("tv_description")
            val recyclerView = alphabetActivity.findViewByString<RecyclerView>("rv_alphabet")
        }
    }

    @Test
    fun `Check RecyclerView contents are correct`() {
        val alphabetActivityIntent = getAlphabetActivityIntent()
        val alphabetActivityUnitTest = object : AbstractUnitTest<AlphabetActivity>(AlphabetActivity::class.java) {}
        alphabetActivityUnitTest.testActivity(alphabetActivityIntent) { alphabetActivity ->
            val recyclerView = alphabetActivity.findViewByString<RecyclerView>("rv_alphabet")
            recyclerView.measure(0, 0)
            recyclerView.layout(0, 0, 100, 1000)
            val adapter = recyclerView.adapter as AlphabetAdapter
            val firstLetter = recyclerView.findViewHolderForAdapterPosition(0)?.itemView?.findViewByString<TextView>("tv_letter")
            val firstMorse = recyclerView.findViewHolderForAdapterPosition(0)?.itemView?.findViewByString<TextView>("tv_morse")
            //val alphabet = adapter.currentList
            //val expectedAlphabet = emptyList<Map.Entry<String, String>>() //Translator().getAlphabet()
            val letterMessage = "The letter in the first View does not seem to match the expected one"
            val morseMessage = "The morse in the first View does not seem to match the expected one"
            val expectedLetter = "a"
            val expectedMorse = ".-"
            val actualLetter = firstLetter?.text.toString().lowercase()
            val actualMorse = firstMorse?.text.toString().lowercase()
            assertEquals(letterMessage, expectedLetter, actualLetter)
            assertEquals(morseMessage, expectedMorse, actualMorse)
        }
    }

    @Test
    fun `Check spanned string`() {
        val alphabetActivityIntent = getAlphabetActivityIntent()
        val alphabetActivityUnitTest = object : AbstractUnitTest<AlphabetActivity>(AlphabetActivity::class.java) {}
        alphabetActivityUnitTest.testActivity(alphabetActivityIntent) { alphabetActivity ->
            val tvDesc = alphabetActivity.findViewByString<TextView>("tv_description")
            val expectedDescription = "Letters are separated with one space, words are separated with 3 spaces. Unknown symbols will be displayed as '?'"
            val expectedSpannableString = SpannableStringBuilder()
                .append("Letters are separated with ")
                .inSpans(StyleSpan(Typeface.BOLD_ITALIC)) {
                    append("one space")
                }.append(", words are separated with ")
                .inSpans(StyleSpan(Typeface.BOLD_ITALIC)) {
                    append("3 spaces")
                }.append(". Unknown symbols will be displayed as '?'")
                .toSpanned()
            val actualDescription = tvDesc.text.toString()
            val actualSpannableString = tvDesc.text as Spanned

            assertEquals("The description text does not match the expected one", expectedDescription, actualDescription)

            // Check that the actual spannable string has the same spans as the expected one
            val expectedSpans = expectedSpannableString.getSpans(0, expectedSpannableString.length, StyleSpan::class.java)
            val actualSpans = actualSpannableString.getSpans(0, actualSpannableString.length, StyleSpan::class.java)
            assertEquals("The number of spans does not match the expected one", expectedSpans.size, actualSpans.size)

            for (i in expectedSpans.indices) {
                val expectedSpan = expectedSpans[i]
                val actualSpan = actualSpans[i]
                assertEquals("The span type does not match the expected one", expectedSpan.style, actualSpan.style)
                assertEquals("The span start does not match the expected one", expectedSpannableString.getSpanStart(expectedSpan), actualSpannableString.getSpanStart(actualSpan))
                assertEquals("The span end does not match the expected one", expectedSpannableString.getSpanEnd(expectedSpan), actualSpannableString.getSpanEnd(actualSpan))
            }
        }
    }

}