package com.geovannycode.document

import io.micronaut.serde.annotation.Serdeable.Serializable
import io.micronaut.serde.annotation.Serdeable.Deserializable

@Serializable
@Deserializable
data class Address(val street: String, val city: String, val code: Int)
