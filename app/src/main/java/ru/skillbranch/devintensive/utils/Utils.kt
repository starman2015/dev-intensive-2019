package ru.skillbranch.devintensive.utils

object Utils {
    fun parseFullName(fullName: String?): Pair<String?, String?> {

        if (fullName == null || fullName.isEmpty() || fullName.trim().isEmpty()) {
            return null to null
        }

        val parts: List<String>? = fullName.split(" ")

        val firstName = parts?.getOrNull(0)
        val lastName = parts?.getOrNull(1)

        return firstName to lastName
    }

    fun transliteration(payload: String, divider: String = " "): String {
        val parts = payload.split(" ")
        val partsStringBuilder = StringBuilder()

        for (part in parts) {
            val letterStringBuilder = StringBuilder()
            val letterParts = part.toCharArray()
            for (letter in letterParts) {
                letterStringBuilder.append(fromRuToEng(letter.toString()))
            }
            letterStringBuilder.append(divider)
            partsStringBuilder.append(letterStringBuilder.toString())
        }
        return partsStringBuilder.substring(0, partsStringBuilder.length - 1)
    }

    fun toInitials(firstName: String?, lastName: String?): String? {
        val firstNameInitial: String? = firstName?.trim()?.getOrNull(0)?.toString()?.toUpperCase()
        val lastNameInitial: String? = lastName?.trim()?.getOrNull(0)?.toString()?.toUpperCase()

        return if (firstNameInitial == null && lastNameInitial == null) {
            null
        } else {
            "${firstNameInitial ?: ""}${lastNameInitial ?: ""}"
        }
    }

    private fun fromRuToEng(ruLetter: String): String {
        val pairs = mapOf(
            "а" to "a",
            "б" to "b",
            "в" to "v",
            "г" to "g",
            "д" to "d",
            "е" to "e",
            "ё" to "e",
            "ж" to "zh",
            "з" to "z",
            "и" to "i",
            "й" to "i",
            "к" to "k",
            "л" to "l",
            "м" to "m",
            "н" to "n",
            "о" to "o",
            "п" to "p",
            "р" to "r",
            "с" to "s",
            "т" to "t",
            "у" to "u",
            "ф" to "f",
            "х" to "h",
            "ц" to "c",
            "ч" to "ch",
            "ш" to "sh",
            "щ" to "sh'",
            "ъ" to "",
            "ы" to "i",
            "ь" to "",
            "э" to "e",
            "ю" to "yu",
            "я" to "ya"
        )

        val letter: String = (pairs[ruLetter.toLowerCase()] ?: return ruLetter) ?: return ruLetter

        return if (ruLetter[0].isUpperCase()) {
            letter.capitalize()
        } else {
            letter
        }
    }
}