package com.geovannycode.repository

import com.geovannycode.document.Customer
import io.micronaut.data.mongodb.annotation.MongoFindQuery
import io.micronaut.data.mongodb.annotation.MongoRepository
import io.micronaut.data.repository.CrudRepository

@MongoRepository
interface CustomerRepository : CrudRepository<Customer, String> {
    fun findByFirstNameEquals(firstName: String): List<Customer>
    @MongoFindQuery("{ firstName:  { \$regex:  :name}}")
    fun findByFirstNameLike(name: String): List<Customer>
}