package com.github.yakanet.battledev.junit

import com.github.yakanet.battledev.junit.executor.JavaExecutor
import com.github.yakanet.battledev.junit.executor.KotlinExecutor
import com.github.yakanet.battledev.junit.executor.PhpExecutor
import com.github.yakanet.battledev.junit.executor.Python3Executor
import com.github.yakanet.battledev.junit.sample.InputOutputSample
import com.github.yakanet.battledev.junit.sample.loadSamples
import com.isograd.exercise.IsoContest
import main
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.TestFactory
import java.util.stream.Stream


const val ANSI_RESET = "\u001B[0m"
const val ANSI_BLUE = "\u001B[34m";

class SampleRunnerTest {

    /**
     * Configure the downloaded zip file location
     */
    private val zipSamplePath = "download\\sample-aVlqVXJqdzZnbnJEdXJMVDlEMnA2Zz09Ojqgl8NyhOxIq9MdJq32k8IZ.zip"

    /**
     * File being tested with the Executor wrapper.
     * You can comment any executor you are not using.
     */
    private val executors = listOf(
            KotlinExecutor(::main),
            JavaExecutor(IsoContest::main),
            Python3Executor("src\\main\\python\\main.py"),
            PhpExecutor("src\\main\\php\\main.php")
    )


    /**
     * Display output
     */
    private val showOutput = true

    @TestFactory
    fun sampleCases(): Stream<DynamicTest> {
        return loadSamples(zipSamplePath).map { sample: InputOutputSample ->
            executors.stream().map { executor ->
                val executorName = executor::class.simpleName!!.substringBeforeLast("Executor")
                DynamicTest.dynamicTest("[$executorName] Test " + sample.index) {
                    println("${ANSI_BLUE}[$executorName] Running Test ${sample.index}${ANSI_RESET}")

                    val output = executor.execute(sample.input)
                    if (showOutput) {
                        println(output)
                    }
                    Assertions.assertLinesMatch(sample.output.lines(), output.lines())
                    println("")
                }
            }
        }.flatMap { it }
    }
}
