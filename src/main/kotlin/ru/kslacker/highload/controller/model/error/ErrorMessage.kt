package ru.kslacker.highload.controller.model.error

import com.fasterxml.jackson.annotation.JsonProperty

data class ErrorMessage(
    @JsonProperty("message")
    val message: String,
    @JsonProperty("request_id")
    val requestId: String?,
    @JsonProperty("code")
    val code: Int
)
