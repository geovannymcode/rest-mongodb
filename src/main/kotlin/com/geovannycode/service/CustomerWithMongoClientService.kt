package com.geovannycode.service

import com.geovannycode.document.Address
import com.geovannycode.document.Customer
import com.geovannycode.dto.CustomerRequest
import com.mongodb.client.MongoClient
import com.mongodb.client.MongoCollection
import io.micronaut.http.HttpStatus
import io.micronaut.http.exceptions.HttpStatusException
import jakarta.inject.Singleton
import org.bson.BsonValue

@Singleton
class CustomerWithMongoClientService(private val mongoClient: MongoClient) {

    fun create(customerRequest: CustomerRequest): BsonValue {
        val createResult = getCollection()
            .insertOne(customerRequest.toCustomerEntity())

        return createResult.insertedId
            ?: throw HttpStatusException(HttpStatus.BAD_REQUEST, "User was not created.")
    }

    fun findAll(): List<Customer> =
        getCollection()
            .find()
            .toList()

    private fun getCollection(): MongoCollection<Customer> =
        mongoClient
            .getDatabase("restaurant")
            .getCollection("customer", Customer::class.java)

    private fun CustomerRequest.toCustomerEntity(): Customer {
        val address = Address(
            street = this.street,
            city = this.city,
            code = this.code,
        )
        return Customer(
            id = null,
            firstName = this.firstName,
            lastName = this.lastName,
            email = this.email,
            address = address,
        )
    }
}
