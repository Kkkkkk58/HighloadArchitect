package ru.kslacker.highload.controller

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.InternalAuthenticationServiceException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.client.HttpStatusCodeException
import ru.kslacker.highload.controller.model.error.ErrorMessage

@RestControllerAdvice
class ControllerExceptionHandler {
    @ExceptionHandler(HttpStatusCodeException::class)
    fun handle(e: HttpStatusCodeException): ResponseEntity<ErrorMessage> {
        val errorMessage = ErrorMessage(
            message = e.message ?: "error",
            requestId = null,
            code = e.statusCode.value()
        )

        return ResponseEntity.status(e.statusCode)
            .body(errorMessage)
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handle(e: MethodArgumentNotValidException): ResponseEntity<ErrorMessage> {
        val errorMessage = ErrorMessage(
            message = e.message,
            requestId = null,
            code = e.statusCode.value()
        )

        return ResponseEntity.badRequest()
            .body(errorMessage)
    }

    @ExceptionHandler(Throwable::class)
    fun handle(t: Throwable): ResponseEntity<ErrorMessage> {
        return if (t.cause is HttpStatusCodeException) {
            handle(t.cause as HttpStatusCodeException)
        } else {
            ResponseEntity.internalServerError().body(
                ErrorMessage(
                    message = t.message ?: "error",
                    requestId = null,
                    code = HttpStatus.INTERNAL_SERVER_ERROR.value()
                )
            )
        }
    }
}
