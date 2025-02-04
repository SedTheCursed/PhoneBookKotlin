package phonebook

import java.io.File
import kotlin.system.exitProcess

const val MILLI_IN_MINUTE = 60_000
const val MILLI_IN_SECOND = 1000

fun main() {
    // Production data
    val phonebook = Document("directory.txt").file
    val targets = Document("find.txt").file
    if (!targets.exists() || !phonebook.exists()) {
        exitProcess(1)
    }
    
    linearSearch(targets, phonebook)
    bubbleSortAndJumpSearch(targets, phonebook)
    quickSortAndBinarySearch(targets, phonebook)
    hashMapSearch(targets, phonebook)
}

/**
 * Search people on a phonebook using the linear search algorithm and display to time it took.
 *
 * @param targets File with the people to find
 * @param phonebook File to be searched
 */
private fun linearSearch(targets: File, phonebook: File) {
    val startDate = startSearch("linear search")
    val result = findPeopleWithLinear(targets, phonebook)
    endLinearSearch(startDate, result)
}

/**
 * Search people on a phonebook, sorted with the bubble algorithm, using the jump search algorithm
 * and display to time the whole operation took.
 *
 * @param targets File with the people to find
 * @param phonebook File to be searched
 */
private fun bubbleSortAndJumpSearch(targets: File, phonebook: File) {
    sortAndSearch(targets, phonebook, "bubble sort + jump search", ::bubbleSort, ::findPeopleWithJumping)
}

/**
 * Search people on a phonebook, sorted with the quick algorithm, using the binary search algorithm
 * and display to time the whole operation took.
 *
 * @param targets File with the people to find
 * @param phonebook File to be searched
 */
private fun quickSortAndBinarySearch(targets: File, phonebook: File) {
    sortAndSearch(targets, phonebook, "quick sort + binary search", ::quickSort, ::findPeopleWithBinary)
}

/**
 * Search people on a phonebook, converted in a map, and display to time the whole operation took.
 *
 * @param targets File with the people to find
 * @param phonebook File to be searched
 */
private fun hashMapSearch(targets: File, phonebook: File) {
    val startDate = startSearch("hash table")
    val (sortedPhonebook, sortingTime) = turnIntoHashMap(phonebook)
    val (result, searchTime) = findPeopleInTable(targets, sortedPhonebook)
    endSortAndSearch(startDate, result, sortingTime, searchTime)
}

/**
 * Search people on a phonebook, sorted with the specific algorithm, using a specific search algorithm
 * and display to time the whole operation took.
 *
 * @param targets File with the people to find
 * @param phonebook File to be searched
 * @param types name of the algorithms used
 * @param sortAlgorithm Sorting algorithm to use
 * @param searchAlgorithm Searching algorithm to use
 */
private fun sortAndSearch(
    targets: File,
    phonebook: File,
    types: String,
    sortAlgorithm: (File) -> Pair<List<String>, String>,
    searchAlgorithm: (File, List<String>) -> Pair<String, String>
) {
    val startDate = startSearch(types)
    val (sortedPhonebook, sortingTime) = sortAlgorithm(phonebook)
    val (result, searchTime) = searchAlgorithm(targets, sortedPhonebook)
    endSortAndSearch(startDate, result, sortingTime, searchTime)
}

/**
 * Set the beginning of a timer
 *
 * @param type Describe the type of timer being set up that is used in the sentence printed
 * @return the current time in milliseconds
 */
private fun startSearch(type: String): Long {
    println("Start searching (%s)...".format(type))
    return System.currentTimeMillis()
}

/**
 * End the timer of the linear search and print the result of the search and the time it needed.
 *
 * @param startDate Start of the timer
 * @param result Result of the search
 */
private fun endLinearSearch(startDate: Long, result: String) {
    val time = calculateTime(startDate)
    println("Found %s entries. Time taken: %s".format(result, time))
}

/**
 * End the timer of the jumping search and print the result of the search and the time it needed.
 *
 * @param startDate Start of the timer
 * @param result Result of the search
 * @param sortingTime Time needed by the sorting algorithm
 * @param searchingTime Time needed by the searching algorithm
 */
private fun endSortAndSearch(startDate: Long, result: String, sortingTime: String, searchingTime: String) {
    endLinearSearch(startDate, result)
    println(sortingTime)
    println(searchingTime)
}
