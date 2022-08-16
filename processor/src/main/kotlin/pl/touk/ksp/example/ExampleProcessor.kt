package pl.touk.ksp.example

import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.processing.SymbolProcessorEnvironment
import com.google.devtools.ksp.processing.SymbolProcessorProvider
import com.google.devtools.ksp.symbol.KSAnnotated
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.symbol.KSFile
import com.google.devtools.ksp.symbol.KSFunctionDeclaration
import com.google.devtools.ksp.symbol.KSNode
import com.google.devtools.ksp.validate
import com.google.devtools.ksp.visitor.KSTopDownVisitor

class ExampleProcessor(val env: SymbolProcessorEnvironment) : SymbolProcessor {

    override fun process(resolver: Resolver): List<KSAnnotated> {
        val symbols = resolver.getSymbolsWithAnnotation(Example::class.java.name)
        val invalid = symbols
            .filter { !it.validate() }.toList()
        symbols.forEach { it.accept(ExampleProcessorVisitor(), Unit) }
        return invalid
    }

    inner class ExampleProcessorVisitor : KSTopDownVisitor<Unit, Unit>() {

        override fun defaultHandler(node: KSNode, data: Unit) {
        }

        override fun visitFile(file: KSFile, data: Unit) {
            env.logger.info("\n\nvisitFile $file\n")
            super.visitFile(file, data)
        }

        override fun visitClassDeclaration(classDeclaration: KSClassDeclaration, data: Unit) {
            env.logger.info("\n\nvisitClassDeclaration $classDeclaration\n")
            super.visitClassDeclaration(classDeclaration, data)
        }

        override fun visitFunctionDeclaration(function: KSFunctionDeclaration, data: Unit) {
            env.logger.info("\n\nvisitFunctionDeclaration $function\n")
            super.visitFunctionDeclaration(function, data)
        }
    }
}

class ExampleProcessorProvider : SymbolProcessorProvider {
    override fun create(environment: SymbolProcessorEnvironment) = ExampleProcessor(environment)
}
