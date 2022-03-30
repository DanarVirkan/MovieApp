package com.moviedb.lint

import com.android.tools.lint.client.api.UElementHandler
import com.android.tools.lint.detector.api.*
import org.jetbrains.uast.UClass
import org.jetbrains.uast.UElement

@Suppress("UnstableApiUsage")
class NamingDetector : Detector(), Detector.UastScanner {
    override fun getApplicableUastTypes(): List<Class<out UElement>> = listOf(UClass::class.java)
    override fun createUastHandler(context: JavaContext): UElementHandler {
        return object : UElementHandler() {
            override fun visitClass(node: UClass) {
                if (node.name?.isCamelCase() == false){
                    context.report(
                        ISSUE_NAMING,
                        node,
                        context.getNameLocation(node),
                        "Penulisan Nama Harus CamelCase"
                    )
                }
            }
        }
    }

    companion object {
        val ISSUE_NAMING: Issue = Issue.create(
            id = "NamingPattern",
            briefDescription = "Gunakan CamelCase",
            explanation = """
                Gunkan CamelCase saat menulis kode
            """,
            category = Category.CORRECTNESS,
            priority = 6,
            severity = Severity.WARNING,
            implementation = Implementation(NamingDetector::class.java, Scope.JAVA_FILE_SCOPE)
        )
    }

    private fun String.isCamelCase(): Boolean {
        val charArr = toCharArray()
        return charArr.mapIndexed { index, current ->
            current to charArr.getOrNull(index + 1)
        }.none {
            it.first.isUpperCase() && it.second?.isLowerCase() ?: false
        }
    }
}