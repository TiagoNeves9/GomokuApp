package com.example.pdm2324i_gomoku_g37.domain


/** On DAW API project we have (03 nov 2023):
 *      - Domain:
 *  data class User(val userId: UUID, val username: String, val encodedPassword: String)
 *      - HTTP:
 *  data class UserInputModel(val name: String, val password: String)
 *  data class UserOutputModel(val username: String, val id: UUID, val token: String) : OutputModel
 */
data class UserId(val id: Int)