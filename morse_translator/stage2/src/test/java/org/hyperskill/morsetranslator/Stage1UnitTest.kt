package org.hyperskill.morsetranslator

import org.hyperskill.morsetranslator.internals.MorseMainActivityUnitTest
import org.hyperskill.morsetranslator.internals.shadows.CustomShadowAsyncDifferConfig
import org.junit.FixMethodOrder
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.MethodSorters
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@Config(shadows = [CustomShadowAsyncDifferConfig::class])
class Stage1UnitTest : MorseMainActivityUnitTest<MainActivity>(MainActivity::class.java){

    @Test
    fun test00_checkTvTextInitialState() {
        testActivity {
            tvText
        }
    }

    @Test
    fun test01_checkTvMorseInitialState() {
        testActivity {
            tvMorse
        }
    }

    @Test
    fun test02_checkEtTextInitialState() {
        testActivity {
            etText
        }
    }

    @Test
    fun test03_checkEtMorseInitialState() {
        testActivity {
            etMorse
        }
    }

    @Test
    fun test04_checkBtClearInitialState() {
        testActivity {
            btClear
        }
    }

    @Test
    fun test05_checkBtGuideInitialState() {
        testActivity {
            btGuide
        }
    }
}