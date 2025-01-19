package ru.kslacker.highload.service.infrastructure.utils.id.impl

import org.springframework.stereotype.Service
import ru.kslacker.highload.dao.entity.UserEntity
import ru.kslacker.highload.service.infrastructure.utils.id.DomainIdGenerator
import ru.kslacker.highload.service.infrastructure.utils.snowflake.Snowflake

@Service
class UserDomainIdGenerator(
    snowflake: Snowflake
): DomainIdGenerator<UserEntity>(
    domainPrefix = "usr-",
    snowflake = snowflake
)
