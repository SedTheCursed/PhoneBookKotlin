package phonebook

import java.io.File

/**
 * Class for the files used in the app
 *
 * @param name Name of the Document
 * @property directory Shared directory of the app Documents
 * @property file File usable by the app
 */
data class Document(private val name: String) {
    private val directory = "/home/sed/IdeaProjects/hyperskil/Phone Book (Kotlin)/assets"
    val file = File("$directory/$name")
}
