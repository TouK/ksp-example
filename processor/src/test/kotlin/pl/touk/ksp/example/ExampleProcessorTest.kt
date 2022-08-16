package pl.touk.ksp.example

import com.tschuchort.compiletesting.KotlinCompilation
import com.tschuchort.compiletesting.SourceFile
import com.tschuchort.compiletesting.kspSourcesDir
import com.tschuchort.compiletesting.symbolProcessorProviders
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import pl.touk.ksp.slf4j.Slf4jProcessorProvider

class ExampleProcessorTest {

    @Test
    fun `should generate extension`() {
        val source = SourceFile.kotlin(
            "SomeService.kt", """
                @file:Example
                import pl.touk.ksp.example.Example

                class SomeService {
                    fun test() {
                        println("test")
                    }
                }

                fun main() {
                    val service = Service()
                    service.test()
                }
            """
        )

        val compilation = KotlinCompilation().apply {
            sources = listOf(source)
            symbolProcessorProviders = listOf(ExampleProcessorProvider())
            inheritClassPath = true
            messageOutputStream = System.out
        }
        val result = compilation.compile()

        assertThat(result.exitCode).isEqualTo(KotlinCompilation.ExitCode.OK)
    }

}
