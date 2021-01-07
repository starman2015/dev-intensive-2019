package ru.skillbranch.devintensive.extensions

import java.text.SimpleDateFormat
import java.util.*

const val SECOND = 1000L
const val MINUTE = 60 * SECOND
const val HOUR = 60 * MINUTE
const val DAY = 24 * HOUR

fun Date.format(pattern: String = "HH:mm:ss dd.MM.yy"): String {
    val dateFormat = SimpleDateFormat(pattern, Locale("ru"))
    return dateFormat.format(this)
}

fun Date.add(value: Int, units: TimeUnits = TimeUnits.SECOND): Date {
    var time = this.time

    time += when (units) {
        TimeUnits.SECOND -> value * SECOND
        TimeUnits.MINUTE -> value * MINUTE
        TimeUnits.HOUR -> value * HOUR
        TimeUnits.DAY -> value * DAY
    }

    this.time = time

    return this
}

fun Date.humanizeDiff(date: Date = Date()): String {
    val isPast: Boolean = date.after(this)
    val milliseconds: Long = if (isPast) date.time - this.time else this.time - date.time
    val timeUnit = getTimeUnit(milliseconds)
    val indexAndValue = getDeclineIndexAndValue(milliseconds, timeUnit.milliseconds)

    return getHumanizeDiffResult(timeUnit, indexAndValue.second, milliseconds, indexAndValue.first, isPast)
}


enum class TimeUnits(val declines: List<String>, val milliseconds: Long) {
    SECOND(declines = listOf("секунду", "секунды", "секунд"), milliseconds = 1000),
    MINUTE(declines = listOf("минуту", "минуты", "минут"), milliseconds = SECOND.milliseconds * 60),
    HOUR(declines = listOf("час", "часа", "часов"), milliseconds = MINUTE.milliseconds * 60),
    DAY(declines = listOf("день", "дня", "дней"), milliseconds = HOUR.milliseconds * 24);

    fun decline(index: Int): String {
        return declines[index]
    }

    fun transform(value: Long) = value / milliseconds

    fun plural(value: Int): String {
        val pair = getDeclineIndexAndValue(value.toLong(), 1L)
        return "$value ${decline(pair.first)}"
    }
}

private fun getTimeUnit(value: Long): TimeUnits {

    return when {
        value > TimeUnits.DAY.milliseconds -> TimeUnits.DAY
        value > TimeUnits.HOUR.milliseconds -> TimeUnits.HOUR
        value > TimeUnits.MINUTE.milliseconds -> TimeUnits.MINUTE
        else -> TimeUnits.SECOND
    }
}

private fun getDeclineIndexAndValue(value: Long, milliseconds: Long): Pair<Int, Long> {
    var expr = value / milliseconds
    val unitValue = value / milliseconds
    if (expr > 100) {
        expr %= 100
    }
    if (expr > 20) {
        expr %= 10
    }

    return when (expr) {
        1L -> 0 to unitValue
        in 2L..4L -> 1 to unitValue
        else -> 2 to unitValue
    }
}

private fun getHumanizeDiffResult(timeUnit: TimeUnits, value: Long, milliseconds: Long , declineIndex: Int, isPast: Boolean): String {
    if (TimeUnits.SECOND.transform(milliseconds) in 0 .. 1) {
        return "только что"
    } else if (TimeUnits.SECOND.transform(milliseconds) in 1 .. 45) {
        return "${if (!isPast) "через " else ""}несколько секунд${if (isPast) " назад" else ""}"
    } else if (TimeUnits.SECOND.transform(milliseconds) in 45 .. 75) {
        return "${if (!isPast) "через " else ""}минуту${if (isPast) " назад" else ""}"
    } else if (TimeUnits.SECOND.transform(milliseconds) > 75 && TimeUnits.MINUTE.transform(milliseconds) < 45) {
        return "${if (!isPast) "через " else ""}$value ${timeUnit.decline(declineIndex)}${if (isPast) " назад" else ""}"
    } else if (TimeUnits.MINUTE.transform(milliseconds) in 45 .. 75) {
        return "${if (!isPast) "через " else ""}час${if (isPast) " назад" else ""}"
    } else if (TimeUnits.MINUTE.transform(milliseconds) > 75 && TimeUnits.HOUR.transform(milliseconds) < 22) {
        return "${if (!isPast) "через " else ""}$value ${timeUnit.decline(declineIndex)}${if (isPast) " назад" else ""}"
    } else if (TimeUnits.HOUR.transform(milliseconds) in 22 .. 26) {
        return "${if (!isPast) "через " else ""}день${if (isPast) " назад" else ""}"
    } else if (TimeUnits.HOUR.transform(milliseconds) > 26 && TimeUnits.DAY.transform(milliseconds) < 360) {
        return "${if (!isPast) "через " else ""}$value ${timeUnit.decline(declineIndex)}${if (isPast) " назад" else ""}"
    } else {
        return "более ${if (isPast) "года назад" else "чем через год"}"
    }
}