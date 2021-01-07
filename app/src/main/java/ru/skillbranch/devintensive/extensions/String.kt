package ru.skillbranch.devintensive.extensions

fun String.truncate(count: Int = 16): String {
    val result = this.trim()

    return if (result.length <= count) {
        result
    } else {
        result.substring(0, count).trim() + "..."
    }
}

fun String.stripHtml(): String{
    val htmlRegex = Regex("(<.*?>)|(&[^ а-я]{1,4}?;)")
    val spaceRegex = Regex(" {2,}")
    return this.replace(htmlRegex, "").replace(spaceRegex, " ")
}