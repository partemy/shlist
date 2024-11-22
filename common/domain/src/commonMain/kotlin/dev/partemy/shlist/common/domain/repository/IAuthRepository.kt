package dev.partemy.shlist.common.domain.repository

interface IAuthRepository {
    fun getKey()
    fun passKey()
}