package ru.kslacker.highload.controller.model.user

import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Past
import java.time.LocalDate

data class RegisterUserRequest(
    @field:NotBlank
    @JsonProperty("first_name")
    val firstName: String,
    @field:NotBlank
    @JsonProperty("second_name")
    val secondName: String,
    @field:Past
    @JsonProperty("birth_date")
    val birthDate: LocalDate,
    @JsonProperty("biography")
    val biography: String?,
    @JsonProperty("city")
    val city: String?,
    @field:NotBlank
    @JsonProperty("password")
    val password: String
)

data class RegisterUserResponse(
    @JsonProperty("user_id")
    val userId: String
)
