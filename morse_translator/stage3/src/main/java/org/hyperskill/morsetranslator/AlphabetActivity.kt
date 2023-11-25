package org.hyperskill.morsetranslator

import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.text.style.StyleSpan
import androidx.core.text.inSpans
import androidx.core.text.toSpannable
import androidx.recyclerview.widget.GridLayoutManager
import org.hyperskill.morsetranslator.databinding.ActivityAlphabetBinding

class AlphabetActivity : AppCompatActivity() {
    companion object {
        val spanDescriptionText = SpannableStringBuilder()
            .append("Letters are separated with ")
            .inSpans(StyleSpan(Typeface.BOLD_ITALIC)) {
                append("one space")
            }.append(", words are separated with ")
            .inSpans(StyleSpan(Typeface.BOLD_ITALIC)) {
                append("3 spaces")
            }.append(". Unknown symbols will be displayed as '?'")
            .toSpannable()
    }

    private lateinit var binding: ActivityAlphabetBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAlphabetBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val alphabetAdapter = AlphabetAdapter()
        binding.rvAlphabet.adapter = alphabetAdapter
        binding.rvAlphabet.layoutManager = GridLayoutManager(this, 3)
        alphabetAdapter.submitList(Translator().getAlphabet())
        binding.tvDescription.text = spanDescriptionText
    }
}