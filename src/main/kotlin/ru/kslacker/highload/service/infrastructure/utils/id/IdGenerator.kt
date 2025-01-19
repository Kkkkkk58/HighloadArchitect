package ru.kslacker.highload.service.infrastructure.utils.id

interface IdGenerator<T> {
    fun getNextId(): String
}
