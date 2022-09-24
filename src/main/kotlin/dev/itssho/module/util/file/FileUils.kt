package dev.itssho.module.util.file

fun blockOfSimilar(lines: List<String>, content: String): FileSlice? {
    val first = lines.indexOfFirst { it.contains(content) }
    val last = lines.indexOfLast { it.contains(content) }

    return if (first != -1 && last != -1) {
        FileSlice(first, last)
    } else {
        null
    }
}