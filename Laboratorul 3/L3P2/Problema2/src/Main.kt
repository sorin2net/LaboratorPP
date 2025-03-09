import java.io.File
import java.io.IOException
import java.util.*

fun main() {
    val fileName = "Luceafarul.txt"
    var text = try {
        File(fileName).readText(Charsets.UTF_8)
    } catch (e: IOException) {
        println("Eroare la citirea fișierului: ${e.message}")
        return
    }

    text = removeMultipleSpaces(text)
    text = removeMultipleLineBreaks(text)
    text = removePageNumbers(text)
    text = removeAuthorName(text)
    text = addLineBreaksBeforeCapitalLetters(text)
    text = replaceOldCharactersWithNew(text)

    val outputFileName = "Luceafarul_processed.txt"
    try {
        File(outputFileName).writeText(text, Charsets.UTF_8)
        println("Fișierul procesat a fost salvat în $outputFileName")
    } catch (e: IOException) {
        println("Eroare la salvarea fișierului: ${e.message}")
    }
}

fun removeMultipleSpaces(text: String): String {
    return text.replace(Regex("\\s+"), " ")
}

fun removeMultipleLineBreaks(text: String): String {
    return text.replace(Regex("(\n)+"), "\n")
}

fun removePageNumbers(text: String): String {
    return text.replace(Regex("\\s+\\d+\\s+"), " ")
}

fun removeAuthorName(text: String): String {
    val words = text.split(Regex("\\s+"))
    val wordPairs = mutableListOf<String>()

    for (i in 0 until words.size - 1) {
        wordPairs.add("${words[i]} ${words[i + 1]}")
    }

    val frequencyMap = wordPairs.groupingBy { it }.eachCount() //atribuie la fiecare pereche de 2 cuvinte numarul de aparitii
    val mostFrequentPair = frequencyMap.maxByOrNull { it.value }?.key //este atribuit nr maxim de aparitii a perechii

    return if (mostFrequentPair != null) {
        text.replace(mostFrequentPair, "")
    } else {
        text
    }
}

fun replaceOldCharactersWithNew(text: String): String {
    val replacements = mapOf(
        'ă' to 'a',
        'Ă' to 'A',
        'â' to 'a',
        'Â' to 'A',
        'î' to 'i',
        'Î' to 'I',
        'ş' to 's',
        'Ş' to 'S',
        'ţ' to 't',
        'Ţ' to 'T'
    )

    var updatedText = text
    for ((oldChar, newChar) in replacements) {
        updatedText = updatedText.replace(oldChar, newChar)
    }

    updatedText = updatedText.replace(Regex("[\\u0219\\u0218]"), "s") //in unele cazuri, nu functioneaza ca litera mare Ş, si este modificat in s
    updatedText = updatedText.replace(Regex("[\\u021B\\u021A]"), "t")  // la fel si Ţ e modificat in t

    return updatedText
}

fun addLineBreaksBeforeCapitalLetters(text: String): String {
    return text.replace(Regex("(?=[A-Z])"), "\n")
}
