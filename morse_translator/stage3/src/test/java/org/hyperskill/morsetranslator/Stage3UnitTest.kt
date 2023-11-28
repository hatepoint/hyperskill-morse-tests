package org.hyperskill.morsetranslator

import android.graphics.Typeface
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.StyleSpan
import androidx.core.text.inSpans
import androidx.core.text.toSpanned
import org.hyperskill.morsetranslator.internals.MorseAlphabetActivityUnitTest.Companion.ID_RV_ALPHABET
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
                recyclerView.doActionOnEachListItem(morseCodeMap.entries.toList())
                { itemViewSupplier, index, (letter: Char, expectedMorse: String) ->
                    ItemViewBinding(itemViewSupplier()).apply {
                        val letterMessage = "The letter in $ID_RV_ALPHABET position $index " +
                                "does not match the expected one"
                        val morseMessage = "The morse in in $ID_RV_ALPHABET position $index " +
                                "does not match the expected one"

                        val expectedLetter = "$letter"
                        val actualLetter = tvLetter.text.toString().uppercase()
                        assertEquals(letterMessage, expectedLetter, actualLetter)

                        val actualMorse = tvMorse.text.toString()
                        assertEquals(morseMessage, expectedMorse, actualMorse)
                    }
                }
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

                    val expectedSpanStart = expectedSpannableString.getSpanStart(expectedSpan)
                    val actualSpanStart = actualSpannableString.getSpanStart(actualSpan)
                    assertEquals(
                        "The span start does not match the expected one",
                        expectedSpanStart,
                        actualSpanStart
                    )

                    val expectedSpanEnd = expectedSpannableString.getSpanEnd(expectedSpan)
                    val actualSpanEnd = actualSpannableString.getSpanEnd(actualSpan)
                    assertEquals(
                        "The span end does not match the expected one",
                        expectedSpanEnd,
                        actualSpanEnd
                    )

                    assertEquals(
                        "The span type for start: $expectedSpanStart, end $expectedSpanEnd" +
                                " does not match the expected BOLD_ITALIC",
                        expectedSpan.style,
                        actualSpan.style
                    )
                }
            }
        }
    }
}