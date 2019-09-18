package com.justpinch

import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import org.gradle.api.GradleException
import org.gradle.api.Project
import org.gradle.process.internal.ExecException
import java.util.Random

private val charPool: List<Char> = ('a'..'z') + ('A'..'Z') + ('0'..'9')

internal fun generateRandomString(length: Int = 16, chars: List<Char> = charPool) =
        (1..length)
                .map { Random().nextInt(chars.size) }
                .map(chars::get)
                .joinToString("")

/**
 * Extract current git branch name.
 * If used with Gitlab CI, uses its environment variable.
 */
internal fun Project.gitBranchName(): String? {
    val gitlabCIBranchName = System.getenv("CI_COMMIT_REF_NAME")
    if (gitlabCIBranchName != null) return gitlabCIBranchName

    return try {
        ByteArrayOutputStream().use { outputStream ->
            exec { ex ->
                ex.executable = "git"
                ex.args = listOf("rev-parse", "--abbrev-ref", "HEAD")
                ex.standardOutput = outputStream
            }
            outputStream.toString()
        }
    } catch (e: ExecException) {
        println("Error occured while reading git branch name: ${e.message}")
        null
    }
}

/**
 * Throw [GradleException] with a given message, failing the build
 */
internal fun fail(message: String): Nothing = throw GradleException(message)

/**
 * Execute a [request] failing the task if it fails
 */
internal inline fun <R> OkHttpClient.execute(request: Request, failureMessage: String, transformation: Response.() -> R): R {
    return newCall(request).execute().use { response ->
        if (response.isSuccessful) {
            transformation(response)
        } else {
            println("ERROR ${response.code()}")
            println(response.body()?.string())
            fail(failureMessage)
        }
    }
}