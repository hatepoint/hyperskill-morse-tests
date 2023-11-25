package org.hyperskill.morsetranslator

import android.graphics.Typeface
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.StyleSpan
import android.widget.TextView
import androidx.core.text.inSpans
import androidx.core.text.toSpanned
import androidx.recyclerview.widget.RecyclerView
import org.hyperskill.morsetranslator.internals.MorseMainActivityUnitTest
import org.hyperskill.morsetranslator.internals.shadows.CustomShadowAsyncDifferConfig
import org.junit.Assert.assertEquals
import org.junit.FixMethodOrder
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.MethodSorters
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@Config(shadows = [CustomShadowAsyncDifferConfig::class])
class Stage3UnitTest : MorseMainActivityUnitTest<MainActivity>(MainActivity::class.java){


    @Test
    fun `test00_Check new activity starts after Guide button click`() {
        val (alphabetActivityUnitTest, alphabetActivityIntent) = navigateToAlphabetActivity(
            AlphabetActivity::class.java
        )

        alphabetActivityUnitTest.apply {
            testActivity(alphabetActivityIntent) {
                tvDesc
                recyclerView
            }
        }
    }

    @Test
    fun `test01_Check RecyclerView contents are correct`() {
        val (alphabetActivityUnitTest, alphabetActivityIntent) = navigateToAlphabetActivity(
            AlphabetActivity::class.java
        )
        alphabetActivityUnitTest.apply {
            testActivity(alphabetActivityIntent) {
                val recyclerView = activity.findViewByString<RecyclerView>("rv_alphabet")
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
    }

    @Test
    fun `test02_Check spanned string`() {
        val (alphabetActivityUnitTest, alphabetActivityIntent) = navigateToAlphabetActivity(
            AlphabetActivity::class.java
        )
        alphabetActivityUnitTest.apply {
            testActivity(alphabetActivityIntent) {
                val tvDesc = activity.findViewByString<TextView>("tv_description")
                val expectedDescription =
                    "Letters are separated with one space, words are separated with 3 spaces. " +
                            "Unknown symbols will be displayed as '?'"
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

                assertEquals(
                    "The description text does not match the expected one",
                    expectedDescription,
                    actualDescription
                )

                // Check that the actual spannable string has the same spans as the expected one
                val expectedSpans = expectedSpannableString.getSpans(0, expectedSpannableString.length, StyleSpan::class.java)
                val actualSpans = actualSpannableString.getSpans(0, actualSpannableString.length, StyleSpan::class.java)
                assertEquals(
                    "The number of spans does not match the expected one",
                    expectedSpans.size,
                    actualSpans.size
                )

                for (i in expectedSpans.indices) {
                    val expectedSpan = expectedSpans[i]
                    val actualSpan = actualSpans[i]
                    assertEquals(
                        "The span type does not match the expected one",
                        expectedSpan.style,
                        actualSpan.style
                    )
                    assertEquals(
                        "The span start does not match the expected one",
                        expectedSpannableString.getSpanStart(expectedSpan),
                        actualSpannableString.getSpanStart(actualSpan)
                    )
                    assertEquals(
                        "The span end does not match the expected one",
                        expectedSpannableString.getSpanEnd(expectedSpan),
                        actualSpannableString.getSpanEnd(actualSpan)
                    )
                }
            }
        }
    }
}