package me.ks.chan.material.symbols.ksp.validator

import com.google.devtools.ksp.getDeclaredFunctions
import com.google.devtools.ksp.getDeclaredProperties
import com.google.devtools.ksp.isOpen
import com.google.devtools.ksp.processing.KSPLogger
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.symbol.KSNode
import com.google.devtools.ksp.visitor.KSDefaultVisitor
import me.ks.chan.material.symbols.ksp.validator.ClassValidator.Result.Ext.errorResult
import me.ks.chan.material.symbols.ksp.validator.ClassValidator.Result.Ext.filterResult
import me.ks.chan.material.symbols.ksp.validator.ClassValidator.Result.Ext.passResult

class ClassValidator(
    private val kspLogger: KSPLogger,
    private val propertyValidator: PropertyValidator,
): KSDefaultVisitor<Unit, ClassValidator.Result>() {

    sealed class Result(val classDeclaration: KSClassDeclaration) {
        class Pass(classDeclaration: KSClassDeclaration): Result(classDeclaration)
        class Filter(classDeclaration: KSClassDeclaration): Result(classDeclaration)
        class Error(classDeclaration: KSClassDeclaration): Result(classDeclaration)

        companion object Ext {
            inline val KSClassDeclaration.passResult: Pass
                get() = Pass(this)
            inline val KSClassDeclaration.filterResult: Filter
                get() = Filter(this)
            inline val KSClassDeclaration.errorResult: Error
                get() = Error(this)
        }
    }

    override fun defaultHandler(node: KSNode, data: Unit): Result =
        throw IllegalAccessError()

    override fun visitClassDeclaration(
        classDeclaration: KSClassDeclaration, data: Unit,
    ): Result {
        if (classDeclaration.getDeclaredFunctions().any { it.isAbstract }) {
            kspLogger.abstractFunctionError(classDeclaration)
            return classDeclaration.errorResult
        }

        if (classDeclaration.isOpen().not()) {
            kspLogger.nonOpenClassInfo(classDeclaration)
            return classDeclaration.filterResult
        }

        val propertyValidationResultList = classDeclaration.getDeclaredProperties()
            .map { propertyDeclaration -> propertyDeclaration.accept(propertyValidator, Unit) }

        if (propertyValidationResultList.any { it == PropertyValidator.Result.Error }) {
            // Detail content is logged in PropertyValidator
            return classDeclaration.errorResult
        }

        if (propertyValidationResultList.none { it == PropertyValidator.Result.Valid }) {
            kspLogger.noneOverridablePropertyInfo(classDeclaration)
            return classDeclaration.filterResult
        }

        return classDeclaration.passResult
    }

}

private fun KSPLogger.abstractFunctionError(
    classDeclaration: KSClassDeclaration
) {
    error(
        message = "MaterialSymbol class should not contain any abstract function.",
        classDeclaration,
    )
}

private fun KSPLogger.nonOpenClassInfo(
    classDeclaration: KSClassDeclaration
) {
    info(
        message = "MaterialSymbol class should be declaration as open/abstract class or interface.",
        classDeclaration,
    )
}

private fun KSPLogger.noneOverridablePropertyInfo(
    classDeclaration: KSClassDeclaration
) {
    info(
        message = "MaterialSymbol class has not declared overridable property.",
        classDeclaration,
    )
}
