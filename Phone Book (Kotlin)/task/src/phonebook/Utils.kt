package phonebook

/**
 * Calculate a duration and format it.
 * @param startDate Start of the duration
 * @return Duration formatted in minutes, seconds, and milliseconds
 */
fun calculateTime(startDate: Long): String {
    val spentMilli = System.currentTimeMillis() - startDate
    return StringBuilder().apply {
        append("%d min. ".format(spentMilli / MILLI_IN_MINUTE))
        append("%d sec. ".format((spentMilli % MILLI_IN_MINUTE) / MILLI_IN_SECOND))
        append("%d ms.".format(spentMilli % MILLI_IN_SECOND))
    }.toString()
}

/**
 * Get the name of a person out of a phonebook line
 *
 * @receiver Phonebook line
 * @return Person name
 */
fun String.name() = this.split(" ")
    .run {
        this.subList(1, this.size).joinToString(" ")
    }