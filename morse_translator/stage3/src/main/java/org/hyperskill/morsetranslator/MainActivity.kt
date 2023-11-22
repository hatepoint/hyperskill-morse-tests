package org.hyperskill.morsetranslator

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import org.hyperskill.morsetranslator.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val translator = Translator()
    private var isUpdatingEditTexts = false
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.btClear.setOnClickListener {
            clearFields()
        }

        binding.etText.doOnTextChanged { text, _, _, _ ->
            if (!isUpdatingEditTexts) {
                isUpdatingEditTexts = true
                binding.etMorse.setText(translator.translateToMorse(text.toString()))
                isUpdatingEditTexts = false
            }
        }

        binding.etMorse.doOnTextChanged { text, _, _, _ ->
            if (!isUpdatingEditTexts) {
                isUpdatingEditTexts = true
                binding.etText.setText(translator.translateFromMorse(text.toString()))
                isUpdatingEditTexts = false
            }
        }

        binding.btGuide.setOnClickListener {
            val intent = Intent(this, AlphabetActivity::class.java)
            startActivity(intent)
        }
    }

    private fun clearFields() {
        binding.etMorse.setText("")
        binding.etText.setText("")
    }

    /*
        Tests for android can not guarantee the correctness of solutions that make use of
        mutation on "static" variables. You should avoid using those.
        Consider "static" as being anything on kotlin that is transpiled to java
        into a static variable. That includes global variables and variables inside
        singletons declared with keyword object, including companion object.
        This limitation is related to the use of JUnit on tests. JUnit re-instantiate all
        instance variable for each test method, but it does not re-instantiate static variables.
        The use of static variable to hold state can lead to state from one test to spill over
        to another test and cause unexpected results.
        Using mutation on static variables is considered a bad practice anyway and no measure
        attempting to give support to that pattern will be made.
     */

}