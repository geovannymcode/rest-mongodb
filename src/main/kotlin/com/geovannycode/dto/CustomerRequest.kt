package com.geovannycode.dto

data class CustomerRequest(
    val firstName: String,
    val lastName: String,
    val email: String,
    val street: String,
    val city: String,
    val code: Int,
)
