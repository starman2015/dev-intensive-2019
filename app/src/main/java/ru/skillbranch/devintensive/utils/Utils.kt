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
        TODO("not implemented")
    }

    fun toInitials(firstName: String?, lastName: String?): String? {
        var firstNameInitial: String? = firstName?.trim()
        var lastNameInitial: String? = lastName?.trim()

        if (firstNameInitial != null) {
            firstNameInitial =
                if (firstNameInitial.isNotBlank()) firstNameInitial.substring(0, 1).toUpperCase() else null
        }

        if (lastNameInitial != null) {
            lastNameInitial = if (lastNameInitial.isNotBlank()) lastNameInitial.substring(0, 1).toUpperCase() else null
        }

        if (firstNameInitial == null && lastNameInitial == null) {
            return null
        } else {
            return "${firstNameInitial ?: ""}${lastNameInitial ?: ""}"
        }
    }
}