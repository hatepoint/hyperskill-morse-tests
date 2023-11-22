package org.hyperskill.morsetranslator

import java.util.Locale

class Translator {

    private val morseCodeMap = mapOf(
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
        '9' to "----."
    )

    fun getAlphabet(): List<Map.Entry<String, String>> {
        return morseCodeMap.entries.associate { (key, value) ->
            key.toString() to value
        }.entries.toList()
    }

    fun translateToMorse(text: String): String {
        val upperCaseText = text.uppercase(Locale.getDefault())
        val morseCodeBuilder = StringBuilder()

        for (char in upperCaseText) {
            if (char == ' ') {
                morseCodeBuilder.append("   ")  // 3 spaces for word separation in Morse code
            } else if (morseCodeMap.containsKey(char)) {
                morseCodeBuilder.append(morseCodeMap[char]).append(" ")
            } else {
                morseCodeBuilder.append("? ")  // If character is not found, add '?' with space for unknown symbol
            }
        }
        return morseCodeBuilder.toString().trim()
    }

    fun translateFromMorse(morseCode: String): String {
        val words = morseCode.split("   ")  // Split words by 3 spaces
        val textBuilder = StringBuilder()

        for (word in words) {
            val letters = word.split(" ")  // Split letters by space
            for (letter in letters) {
                if (letter.isNotEmpty()) {
                    val char = morseCodeMap.entries.find { it.value == letter }
                    if (char != null) {
                        textBuilder.append(char.key)
                    } else {
                        textBuilder.append("?")  // If Morse code is not found, add '?' for unknown symbol
                    }
                }
            }
            textBuilder.append(" ")  // Add space between words
        }
        return textBuilder.toString().trim()
    }
}