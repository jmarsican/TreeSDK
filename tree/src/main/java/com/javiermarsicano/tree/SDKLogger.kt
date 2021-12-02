package com.javiermarsicano.tree

import android.util.Log

enum class LogLevel(
    val code: Int,
    val logger: (String, String) -> Int,
    val exceptionLogger: (String, String, Throwable) -> Int
) {
    Verbose(Log.VERBOSE, Log::v, Log::v),
    Debug(Log.DEBUG, Log::d, Log::d),
    Info(Log.INFO, Log::i, Log::i),
    Warn(Log.WARN, Log::w, Log::w),
    Error(Log.ERROR, Log::e, Log::e),
    None(Log.ASSERT + 1, { _, _ -> 0 }, { _, _, _ -> 0 })
}

var minimumLogLevel: LogLevel =
    LogLevel.Verbose

internal inline fun <reified T> T.logVerbose(message: () -> Any?) = log(LogLevel.Verbose, message)
internal inline fun <reified T> T.logDebug(message: () -> Any?) = log(LogLevel.Debug, message)
internal inline fun <reified T> T.logInfo(message: () -> Any?) = log(LogLevel.Info, message)
internal inline fun <reified T> T.logWarn(message: () -> Any?) = log(LogLevel.Warn, message)
internal inline fun <reified T> T.logError(message: () -> Any?) = log(LogLevel.Error, message)

internal inline fun <reified T> T.log(level: LogLevel, message: () -> Any?) {
    if (level >= minimumLogLevel) {
        message().let {
            val tag = T::class.java.canonicalName ?: "com.javiermarsicano"
            when (it) {
                is Throwable -> level.exceptionLogger(tag, it.message ?: "", it)
                is Unit -> Unit
                null -> Unit
                else -> level.logger(tag, it.toString())
            }
        }
    }
}
