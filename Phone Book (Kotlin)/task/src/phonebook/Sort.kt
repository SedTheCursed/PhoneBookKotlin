package phonebook

import java.io.File

/**
 * Bubble sorting algorithm: Sort a list by exchanging adjacent value.
 *
 * @param phonebook File needed to be sorted
 * @return Pair with the sorted List and the time needed to sort it.
 */
fun bubbleSort(phonebook: File): Pair<List<String>, String> {
    val start = System.currentTimeMillis()
    val book = phonebook.readLines().toMutableList()

    // Initialize a flag for swaps
    var swapped = true

    // Continue until no more swaps are needed
    while (swapped) {
        swapped = false
        for (i in 1..book.lastIndex) {
            val name = book[i].name()
            val previousName = book[i -1].name()
            if (name < previousName) {
                // Compare adjacent elements and swap if necessary (for ascending order)
                book[i] = book[i - 1].also { book[i - 1] = book[i] }
                swapped = true
            }
        }
    }

    val sortingTime = "Sorting time: %s".format(calculateTime(start))
    return book to sortingTime
}

/**
 * Quicksort algorithm: Sort a list by recursively sorting half the list around a pivot's value.
 *
 * @param phonebook File needed to be sorted
 * @return Pair with the sorted List and the time needed to sort it.
 */
fun quickSort(phonebook: File): Pair<List<String>, String> {
    val start = System.currentTimeMillis()

    fun partition(book: MutableList<String>, left: Int, right: Int): Int {
        val pivot = book[right].name()
        var i = left -1
        for (j in left until right) { // process each element except the pivot
            val current = book[j].name()
            if (current < pivot) { // does it belong to the lower side?
                i++
                book[i] = book[j].also { book[j] = book[i] } // put the element to the lower side
            }
        }
        book[i + 1] = book[right].also { book[right] = book[ i + 1] } // put the pivot to the right of the lower side
        return i + 1
    }

    fun sort(book: MutableList<String>, left: Int, right: Int): List<String> {
        if (left < right) {
            // partition the array around the pivot
            val pivot = partition(book, left, right)
            sort(book, left, pivot - 1) // recursively sort the lower side
            sort(book, pivot + 1, right) // recursively sort the upper side
        }
        return book
    }

    val book = phonebook.readLines()
        .toMutableList()
        .run {
            sort(this, 0, lastIndex)
        }

    val sortingTime = "Sorting time: %s".format(calculateTime(start))
    return book to sortingTime
}

/**
 * Bubble sorting algorithm: Sort a list by exchanging adjacent value.
 *

 *
 * Convert the content of a file containing a phonebook into a map with the name of a person as key
 * and the phone number as value
 *
 * @param phonebook File needed to converted
 * @return Pair with the map and the time needed to create it.
 */
fun turnIntoHashMap(phonebook: File): Pair<Map<String, Long>, String> {
    val start = System.currentTimeMillis()
    val book = phonebook.readLines()
        .associateBy ({ it.name() }, { it.split(" ")[0].toLongOrNull() ?: 0L }).toMutableMap()

    val sortingTime = "Creating time: %s".format(calculateTime(start))
    return book to sortingTime
}