package pl.touk.ksp.slf4j

import com.tschuchort.compiletesting.KotlinCompilation
import com.tschuchort.compiletesting.SourceFile
import com.tschuchort.compiletesting.kspSourcesDir
import com.tschuchort.compiletesting.symbolProcessorProviders
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class Slf4jProcessorTest {

    @Test
    fun `should generate extension`() {
        val source = SourceFile.kotlin(
            "SomeService.kt", """
                import pl.touk.ksp.slf4j.Slf4j
                
                @Slf4j
                class SomeService {
                    fun test() {
                        logger.info("test1")
                    }
                }
            """
        )

        val compilation = KotlinCompilation().apply {
            sources = listOf(source)
            symbolProcessorProviders = listOf(Slf4jProcessorProvider())
            inheritClassPath = true
            messageOutputStream = System.out
        }
        val result = compilation.compile()

        assertThat(result.exitCode).isEqualTo(KotlinCompilation.ExitCode.OK)
        assertThat(compilation.kspSourcesDir)
            .isDirectoryRecursivelyContaining { file -> file.name == "SomeServiceExt.kt" }
    }

}
