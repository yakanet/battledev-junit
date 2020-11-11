@file:Suppress("DEPRECATION")

package com.github.yakanet.battledev.junit.executor

import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.PrintStream
import kotlin.reflect.KFunction

abstract class Executor(val name: String) {
    abstract fun execute(input: String): String
}

class KotlinExecutor(private val mainMethod: KFunction<Unit>) : Executor("kotlin") {
    override fun execute(input: String): String {
        val originalIn = System.`in`
        val originalOut = System.out
        try {
            val consoleBuffer = ByteArrayOutputStream()
            System.setIn(ByteArrayInputStream(input.toByteArray()))
            System.setOut(PrintStream(consoleBuffer))
            when (mainMethod.parameters.size) {
                1 -> mainMethod.call(emptyArray<String>())
                else -> mainMethod.call()
            }
            return consoleBuffer.toString().stripTrailing()
        } finally {
            System.setIn(originalIn)
            System.setOut(originalOut)
        }
    }
}

class JavaExecutor(private val mainMethod: Function1<Array<String>, Unit>) : Executor("java") {
    override fun execute(input: String): String {
        val originalIn = System.`in`
        val originalOut = System.out
        try {
            val consoleBuffer = ByteArrayOutputStream()
            System.setIn(ByteArrayInputStream(input.toByteArray()))
            System.setOut(PrintStream(consoleBuffer))
            mainMethod.invoke(emptyArray())
            return consoleBuffer.toString().stripTrailing()
        } finally {
            System.setIn(originalIn)
            System.setOut(originalOut)
        }
    }
}

class Python3Executor(private val sourceFile: String) : Executor("python") {

    // val engine = ScriptEngineManager().getEngineByExtension("py")

    override fun execute(input: String): String {
        val process = ProcessBuilder()
                .command("python3", sourceFile)
                .start()
        process.outputStream.bufferedWriter().use {
            it.write(input)
        }
        process.waitFor()
        process.errorStream.copyTo(System.err)
        return String(process.inputStream.readAllBytes()).stripTrailing()
    }
}


class PhpExecutor(private val sourceFile: String) : Executor("php") {

    override fun execute(input: String): String {
        val builder = ProcessBuilder().command("php", sourceFile)
//        if (System.getProperty("intellij.debug.agent")=="true") {
//            builder.environment()["XDEBUG_CONFIG"] = "idekey=${java.util.UUID.randomUUID()}  remote_host=localhost"
//            println("")
//        }
        val process = builder.start()
        process.outputStream.bufferedWriter().use { bw ->
            input.lines().forEach {
                bw.write(it);
                bw.newLine();
            }
        }
        process.waitFor()
        process.errorStream.copyTo(System.err)
        return String(process.inputStream.readAllBytes()).stripTrailing()
    }
}
