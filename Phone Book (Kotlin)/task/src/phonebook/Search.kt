package phonebook

import java.io.File
import kotlin.math.floor
import kotlin.math.sqrt

/**
 * Search elements of a list in another list using a linear search algorithm.
 *
 * @param targets Elements to be found
 * @param phonebook List to be searched
 * @return String indicating the number of found elements
 */
fun findPeopleWithLinear(targets: File, phonebook: File): String {
    val searched = targets.readLines()
    var found = 0
    for (target in searched) {
        for (person in phonebook.readLines()) {
            if (person.contains(target)) {
                found++
                break
            }
        }
    }
    return "%d/%d".format(found, searched.size)
}

/**
 * Search elements of a list in another list using a jumping search algorithm.
 *
 * @param targets Elements to be found
 * @param phonebook List to be searched
 * @return Pair of Strings indicating the number of found elements and the time needed for the search.
 */
fun findPeopleWithJumping(targets: File, phonebook: List<String>): Pair<String, String> {
    return findPeople(targets, phonebook, ::jumpSearch)
}

/**
 * Search elements of a list in another list using a binary search algorithm.
 *
 * @param targets Elements to be found
 * @param phonebook List to be searched
 * @return Pair of Strings indicating the number of found elements and the time needed for the search.
 */
fun findPeopleWithBinary(targets: File, phonebook: List<String>): Pair<String, String> {
    return findPeople(targets, phonebook, ::binarySearch)
}

/**
 * Search elements of a list in a map by checking the presence of the key.
 *
 * @param targets Elements to be found
 * @param phonebook List to be searched
 * @return Pair of Strings indicating the number of found elements and the time needed for the search.
 */
fun findPeopleInTable(targets: File, phonebook: Map<String, Long>): Pair<String, String> {
    val start = System.currentTimeMillis()
    val searched = targets.readLines()
    var found = 0
    for (target in searched) {
        if (phonebook.containsKey(target)) found++
    }

    val result = "%d/%d".format(found, searched.size)
    val searchingTime = "Searching time: %s".format(calculateTime(start))
    return result to searchingTime
}

/**
 * Search elements of a list in another list using a specific search algorithm.
 *
 * @param targets Elements to be found
 * @param phonebook List to be searched
 * @param searchAlgorithm Algorithm to use
 * @return Pair of Strings indicating the number of found element and the time needed for the search.
 */
fun findPeople(
    targets: File,
    phonebook: List<String>,
    searchAlgorithm: (String, List<String>) -> Int
): Pair<String, String> {
    val start = System.currentTimeMillis()
    val searched = targets.readLines()
    var found = 0
    for (target in searched) {
        if (searchAlgorithm(target, phonebook) != -1)  found++
    }

    val result = "%d/%d".format(found, searched.size)
    val searchingTime = "Searching time: %s".format(calculateTime(start))
    return result to searchingTime
}

/**
 * Jumping search algorithm :
 * Search block by block in an ascending ordered list for the closest value superior to the target,
 * then goes backward in the block to find the target's index.
 *
 * @param target Element to be found
 * @param list List to be searched
 * @return index of the target, or -1 if not found
 */
private fun jumpSearch(target: String, list: List<String>): Int {
    if (list.isEmpty()) return -1

    var current = 0
    var previous = 0
    val step = floor(sqrt(list.size.toDouble())).toInt()    // size of blocks

    while (list[current].name() < target) {          // moving from block to block
        if (current == list.lastIndex) return -1
        previous = current                                  // store position before moving forward
        current = minOf(current + step, list.lastIndex)  // move to the next block or the last element if we already
    }                                                       // are in the last block


    while (list[current].name() > target) {  // performing backwards linear search until we find it
        current--                                           // move backwards
        if (current <= previous) return -1                  // if we move to the previous block and still not found
    }                                                       // it means the element is not there

    if (list[current].name() == target) return current

    println("%s - %s".format(list[current].name(), target))
    return -1
}

/**
 * Binary Search algorithm:
 * Compare the target with the middle of an ordered list and continue search recursively in the half of the list
 * that can contain the target till it finds it.
 *
 * @param target Element to be found
 * @param list List to be searched
 * @return index of the target, or -1 if not found
 */
private fun binarySearch(target: String, list: List<String>): Int {
    if (list.isEmpty()) return -1

    var left = 0 // the starting value of the left border
    var right = list.lastIndex // the starting value of the right border

    while (left <= right) { // while the left border is to the left of the right one (or if they match)
        val middle = (left + right) / 2 // finding the middle of the array
        val current = list[middle].name()
        when {
            // if the value from the middle is greater than the target, setting a new value to the right border
            // (the one to the left of the middle one)
            target < current -> right = middle - 1
            // if the value from the middle is less than the target, setting a new value to the left border
            // (the one to the right of the middle one)
            target > current -> left = middle + 1
            // if the value from the middle of the array is equal to the target, returning the index of this element
            else -> return middle
        }
    }
    return -1 // If the value was not found in the loop, it is not in the list.
}
