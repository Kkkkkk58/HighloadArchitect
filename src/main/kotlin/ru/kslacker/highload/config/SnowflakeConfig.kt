package ru.kslacker.highload.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import ru.kslacker.highload.service.infrastructure.utils.snowflake.Snowflake
import java.util.concurrent.ThreadLocalRandom

@Configuration
class SnowflakeConfig {
    @Bean
    fun snowflake(): Snowflake {
        return Snowflake(ThreadLocalRandom.current().nextInt(0, Snowflake.MAX_INSTANCE_ID.toInt() + 1).toLong())
    }
}
