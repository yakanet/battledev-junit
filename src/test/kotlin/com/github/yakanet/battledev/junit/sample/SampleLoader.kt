package com.github.yakanet.battledev.junit.sample

import java.io.FileInputStream
import java.util.stream.Stream
import java.util.zip.ZipEntry
import java.util.zip.ZipInputStream

private const val INDEX_POSITION = 1
fun loadSamples(path: String,
                inputRegex: Regex = Regex("input([0-9]+).txt"),
                outputRegex: Regex = Regex("output([0-9]+).txt")
): Stream<InputOutputSample> {
    val resultList: MutableMap<String, InputOutputSample> = HashMap()
    ZipInputStream(FileInputStream(path)).forEachEntry { zis, entry ->
        if (entry.isDirectory) {
            return@forEachEntry
        }
        inputRegex.find(entry.name)?.groups?.get(INDEX_POSITION)?.value?.let { index ->
            resultList.putIfAbsent(index, InputOutputSample(index))
            resultList[index]!!.input = String(zis.readAllBytes())
        }
        outputRegex.find(entry.name)?.groups?.get(INDEX_POSITION)?.value?.let { index ->
            resultList.putIfAbsent(index, InputOutputSample(index))
            resultList[index]!!.output = String(zis.readAllBytes())
        }
    }
    println(resultList.size.toString() + " samples loaded")
    return resultList.values.stream()
}

private fun ZipInputStream.forEachEntry(param: (ZipInputStream, ZipEntry) -> Unit) {
    while (true) {
        val entry = this.nextEntry ?: return
        param(this, entry)
    }
}

class InputOutputSample(val index: String) {
    lateinit var input: String
    lateinit var output: String
}
