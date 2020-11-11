# How to use

You can use this project with the [IsoGrad](https://www.isograd.com/FR/solutionconcours.php) or [BattleDev](https://battledev.blogdumoderateur.com/) platforms.

Once you pick-up the exercice you want to solve, download the sample using the "Download" button on the website IDE.

Open the [SampleRunnerTest](src/test/kotlin/com/github/yakanet/battledev/junit/SampleRunnerTest.kt) file.

Fill in the `executors` variable with the technology you want to use (you can comment lines with the technology your aren't using)

```kotlin
// Example for PHP
private val executors = listOf(
        PhpExecutor("src\\main\\php\\main.php")
)

// Example for Python
private val executors = listOf(
        Python3Executor("src\\main\\python\\main.py"),
)


// Example for Java
private val executors = listOf(
        JavaExecutor(IsoContest::main),
)


// Example for Kotlin
private val executors = listOf(
        KotlinExecutor(::main),
)
```

Define the zip file location where you downloaded the sample using the `zipSamplePath` variable.

Then run your code with the sample using the command
```
mvnw test
```

> Tips: If you are using Intellij, you can use the green arrow in the [SampleRunnerTest](src/test/kotlin/com/github/yakanet/battledev/junit/SampleRunnerTest.kt) file and enjoy the JUnit report integrated in Intellij.

> Tips: If you only use one technology, you can delete the others folders in the src/main folder to speed up the compilation time.
