package ru.kslacker.highload.dao

import ru.kslacker.highload.dao.entity.UserEntity

interface UserDao {
    fun insert(entity: UserEntity)
    fun findById(id: String): UserEntity?
    fun findByFirstNamePrefixAndSecondNamePrefix(firstNamePrefix: String, secondNamePrefix: String): List<UserEntity>
}