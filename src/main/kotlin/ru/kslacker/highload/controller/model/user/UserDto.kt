package ru.kslacker.highload.controller.model.user

import com.fasterxml.jackson.annotation.JsonProperty
import java.time.LocalDate

data class UserDto(
    @JsonProperty("first_name")
    val firstName: String,
    @JsonProperty("second_name")
    val secondName: String,
    @JsonProperty("birth_date")
    val birthDate: LocalDate,
    @JsonProperty("biography")
    val biography: String?,
    @JsonProperty("city")
    val city: String?
)
