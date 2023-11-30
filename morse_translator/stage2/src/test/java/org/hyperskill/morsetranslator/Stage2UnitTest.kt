package org.hyperskill.morsetranslator

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
class Stage2UnitTest : MorseMainActivityUnitTest<MainActivity>(MainActivity::class.java){


    @Test
    fun `test00_Check text translating to morse`() {
        testActivity {
            assertTranslateTextToMorse(
                text = "HELLO",
                expectedMorse = ".... . .-.. .-.. ---"
            )
        }
    }

    @Test
    fun `test01_Check text translating to morse 2`() {
        testActivity {
            assertTranslateTextToMorse(
                text = "ANDROID TEST",
                expectedMorse = ".- -. -.. .-. --- .. -..    - . ... -"
            )
        }
    }

    @Test
    fun `test02_Check morse translating to text`() {
        testActivity {
            assertTranslateMorseToText(
                morse = "... --- ...",
                expectedText = "SOS"
            )
        }
    }

    @Test
    fun `test03_Check morse translating to text 2`() {
        testActivity {
            assertTranslateMorseToText(
                morse = ".- -. -.. .-. --- .. -..    - . ... -",
                expectedText = "ANDROID TEST"
            )
        }
    }

    @Test
    fun `test04_Check clear button`() {
        testActivity {
            etText.setText("Test")
            btClear.clickAndRun()

            val messageEtText = "The $ID_BT_CLEAR doesn't seem to clear the contents on $ID_ET_TEXT"
            assertEquals(messageEtText, "", etText.text.toString())
            val messageEtMorse = "The $ID_BT_CLEAR doesn't seem to clear the contents on $ID_ET_MORSE"
            assertEquals(messageEtMorse, "", etMorse.text.toString())
        }
    }

    @Test
    fun `test05_Check text translating to morse lowercase`() {
        testActivity {
            assertTranslateTextToMorse(
                text = "a lowercase text",
                expectedMorse = ".-    .-.. --- .-- . .-. -.-. .- ... .    - . -..- -"
            )
        }
    }

    @Test
    fun `test06_Check text translating to morse mixed with unknowns`() {
        testActivity {
            assertTranslateTextToMorse(
                text = "This TEXT, has some unknown chars like $ # and !",
                expectedMorse = "- .... .. ...    - . -..- - ?    .... .- ...    ... --- -- .    ..- -. -.- -. --- .-- -.    -.-. .... .- .-. ...    .-.. .. -.- .    ?    ?    .- -. -..    ?"
            )
        }
    }

    @Test
    fun `test07_Check text translating to text with incorrect chars`() {
        testActivity {
            assertTranslateMorseToText(
                morse = "This is not   a morse   text, but it is separated   like morse text",
                expectedText = "??? ?? ????? ???"
            )
        }
    }
}