package ru.kslacker.highload.dao.impl

import org.springframework.jdbc.core.RowMapper
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.stereotype.Repository
import ru.kslacker.highload.dao.UserDao
import ru.kslacker.highload.dao.entity.UserEntity
import ru.kslacker.highload.dao.extensions.getLocalDate
import ru.kslacker.highload.dao.extensions.getOffsetDateTime

@Repository
class JdbcUserDao(
    private val jdbcTemplate: NamedParameterJdbcTemplate
): UserDao {
    companion object {
        private const val INSERT = """
            insert into users 
            (id, first_name, second_name, birth_date, biography, city, password_hash, 
                created_at, created_by, updated_at, updated_by)
            values
            (:id, :firstName, :secondName, :birthDate, :biography, :city, :passwordHash,
                :createdAt, :createdBy, :updatedAt, :updatedBy)
        """

        private const val SELECT = """
            select id,
                first_name,
                second_name,
                birth_date,
                biography,
                city,
                password_hash,
                created_at, 
                created_by, 
                updated_at, 
                updated_by
            from users u
        """

        private const val SELECT_BY_ID = """
            $SELECT
            where u.id = :id
        """

        private const val SELECT_BY_FIRST_NAME_PREFIX_AND_SECOND_NAME_PREFIX = """
            $SELECT
            where u.first_name like :firstNamePrefix
                and u.second_name like :secondNamePrefix
            order by u.id
        """

        private val MAPPER = RowMapper { rs, _ ->
            UserEntity(
                id = rs.getString("id"),
                firstName = rs.getString("first_name"),
                secondName = rs.getString("second_name"),
                birthDate = rs.getLocalDate("birth_date"),
                biography = rs.getString("biography"),
                city = rs.getString("city"),
                passwordHash = rs.getString("password_hash"),
                createdAt = rs.getOffsetDateTime("created_at"),
                createdBy = rs.getString("created_by"),
                updatedAt = rs.getOffsetDateTime("updated_at"),
                updatedBy = rs.getString("updated_by")
            )
        }
    }

    override fun insert(entity: UserEntity) {
        val params = MapSqlParameterSource()
            .addValue("id", entity.id)
            .addValue("firstName", entity.firstName)
            .addValue("secondName", entity.secondName)
            .addValue("birthDate", entity.birthDate)
            .addValue("biography", entity.biography)
            .addValue("city", entity.city)
            .addValue("passwordHash", entity.passwordHash)
            .addValue("createdAt", entity.createdAt)
            .addValue("createdBy", entity.createdBy)
            .addValue("updatedAt", entity.updatedAt)
            .addValue("updatedBy", entity.updatedBy)

        jdbcTemplate.update(INSERT, params)
    }

    override fun findById(id: String): UserEntity? {
        return jdbcTemplate.query(
            SELECT_BY_ID,
            mapOf("id" to id),
            MAPPER
        ).firstOrNull()
    }

    override fun findByFirstNamePrefixAndSecondNamePrefix(
        firstNamePrefix: String,
        secondNamePrefix: String
    ): List<UserEntity> {
        return jdbcTemplate.query(
            SELECT_BY_FIRST_NAME_PREFIX_AND_SECOND_NAME_PREFIX,
            mapOf(
                "firstNamePrefix" to "$firstNamePrefix%",
                "secondNamePrefix" to "$secondNamePrefix%"
            ),
            MAPPER
        )
    }
}
