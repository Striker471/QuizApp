package com.example.quizapp.feature.domain.util.error


import com.example.quizapp.feature.domain.util.Resource
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.HttpException
import kotlinx.coroutines.flow.FlowCollector
import java.io.IOException


fun createDefaultHandler(): ExceptionHandler {
    val httpHandler = HttpExceptionHandler()
    val ioHandler = IOExceptionHandler()
    val generalHandler = GeneralExceptionHandler()

    httpHandler.withNext(ioHandler)
    ioHandler.withNext(generalHandler)

    return httpHandler
}

abstract class ExceptionHandler {

    protected var next: ExceptionHandler? = null

    fun withNext(next: ExceptionHandler) {
        this.next = next
    }

    open suspend fun <T> handle(
        flowCollector: FlowCollector<Resource<T>>,
        exception: Throwable
    ): Unit = next?.handle(flowCollector, exception) ?: throw exception
}

class HttpExceptionHandler : ExceptionHandler() {
    override suspend fun <T> handle(
        flowCollector: FlowCollector<Resource<T>>,
        exception: Throwable
    ) {
        if (exception is HttpException) {
            Resource.Error(message = exception.localizedMessage ?: "Unknown network error")
                .also { flowCollector.emit(it) }
        } else {
            super.handle(flowCollector, exception)
        }
    }
}

class IOExceptionHandler : ExceptionHandler() {
    override suspend fun <T> handle(
        flowCollector: FlowCollector<Resource<T>>,
        exception: Throwable
    ) {
        if (exception is IOException) {
            Resource.Error(
                message = exception.localizedMessage ?: "Check Your Internet Connection"
            )
                .also { flowCollector.emit(it) }
        } else {
            super.handle(flowCollector, exception)
        }
    }
}

class GeneralExceptionHandler : ExceptionHandler() {
    override suspend fun <T> handle(
        flowCollector: FlowCollector<Resource<T>>,
        exception: Throwable
    ) {
        Resource.Error(message = exception.localizedMessage.orEmpty())
            .also { flowCollector.emit(it) }
    }
}