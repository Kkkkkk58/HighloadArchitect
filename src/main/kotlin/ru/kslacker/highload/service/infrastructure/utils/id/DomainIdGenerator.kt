package ru.kslacker.highload.service.infrastructure.utils.id

import ru.kslacker.highload.service.infrastructure.utils.snowflake.Snowflake

abstract class DomainIdGenerator<T>(
    private val domainPrefix: String,
    private val snowflake: Snowflake
): IdGenerator<T> {
    override fun getNextId(): String {
        return domainPrefix + snowflake.next().toString()
    }
}
