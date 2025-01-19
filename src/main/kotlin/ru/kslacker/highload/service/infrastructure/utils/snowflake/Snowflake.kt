package ru.kslacker.highload.service.infrastructure.utils.snowflake

import java.time.Instant

/**
 * Unique id generator based on Twitter Snowflake Id algorithm.
 * <a href="https://github.com/twitter-archive/snowflake/tree/snowflake-2010">...</a>
 * Snowlfake id is sortable.
 * For proper functioning, spawn only singleton instance across application.
 * Never create like pojo (like new Snowflake()). Only like a spring singleton bean with service configuration.
 */
class Snowflake(
    private val instanceId: Long
) {
    companion object {
        private const val INSTANCE_ID_BITS = 10
        private const val SEQUENCE_BITS = 12
        private const val MIN_INSTANCE_ID = 0L
        const val MAX_INSTANCE_ID = (1L shl INSTANCE_ID_BITS) - 1; // 2^10 - 1. (max number in 10 bits)
        private const val MAX_SEQUENCE = (1L shl SEQUENCE_BITS) - 1; // 2^12 - 1 (max number in 12 bits)
        private const val DEFAULT_CUSTOM_EPOCH = 1675951793L // 9.02.2023 14:09:53 GMT
    }

    @Volatile
    private var lastTimestamp = -1L

    @Volatile
    private var sequence = 0L

    init {
        if (instanceId < 0 || instanceId > MAX_INSTANCE_ID) {
            throw IllegalArgumentException(
                "Instance id must be between %d and %d".format(MIN_INSTANCE_ID, MAX_INSTANCE_ID)
            )
        }
    }

    @Synchronized
    fun next(): Long {
        var currentTimestamp = getTimestamp()

        // Check that Instant.now() > then last fixed timestamp. In normal situation it's impossible. Indicate that
        // system clock is invalid.
        if (currentTimestamp < lastTimestamp) {
            throw IllegalStateException("System clock is invalid. Current timestamp in the past.");
        }

        // If we generate more than one id per millisecond we also increase sequence by one.
        if (currentTimestamp == lastTimestamp) {
            // MAX_SEQUENCE in a bit recording always looks like some count of 1 bit. For example 1111_1111_1111
            // That's why expression "x & MAX_SEQUENCE" show as x less or equals than MAX_SEQUENCE (if result == x)
            // or bigger (if result == 0)
            sequence = (sequence + 1) and MAX_SEQUENCE
            if (sequence == 0L) {
                // Sequence became greater than max sequence length, stop it and wait next millisecond
                currentTimestamp = skipMillisecond(currentTimestamp)
            }
        } else {
            // Set sequence in zero for next millisecond.
            sequence = 0
        }

        lastTimestamp = currentTimestamp;

        /*
         * Performing the following sequential steps to fit Snowlake Id format:
         * 1) Left shift of 41-bit current timestamp by 22(length of instance_id and sequence) bits.
         *    currentTimestamp << (INSTANCE_ID_BITS + SEQUENCE_BITS)
         *
         * 2) Left shift instance_id by 12 (length of sequence) bits.
         *      instanceId << SEQUENCE_BITS
         *
         * 3) Logical OR operation on instance
         *
         * 11000111110011000100111101010010000000000000000000000
         *                                      1101000000000000
         * 11000111110011000100111101010010000001101000000000000
         *
         * 4) Logical OR operation on sequence
         */
        return currentTimestamp shl (INSTANCE_ID_BITS + SEQUENCE_BITS) or (instanceId shl SEQUENCE_BITS) or sequence
    }

    private fun getTimestamp(): Long {
        return Instant.now().toEpochMilli() - DEFAULT_CUSTOM_EPOCH
    }

    private fun skipMillisecond(currentTimestamp: Long): Long {
        var timestamp = currentTimestamp

        while (timestamp == lastTimestamp) {
            timestamp = getTimestamp()
        }
        return timestamp
    }
}
