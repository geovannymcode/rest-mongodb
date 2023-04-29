package com.geovannycode.service

import com.geovannycode.document.Address
import com.geovannycode.document.Customer
import com.geovannycode.dto.CustomerRequest
import com.geovannycode.repository.CustomerRepository
import io.micronaut.http.HttpStatus
import io.micronaut.http.exceptions.HttpStatusException
import jakarta.inject.Singleton

@Singleton
class CustomerService(private val customerRepository: CustomerRepository) {

    fun create(customerRequest: CustomerRequest): Customer =
        customerRepository.save(customerRequest.toCustomerEntity())

    fun findAll(): List<Customer> =
        customerRepository
            .findAll()
            .toList()

    fun findById(id: String): Customer =
        customerRepository.findById(id)
            .orElseThrow { HttpStatusException(HttpStatus.NOT_FOUND, "Customer with id: $id was not found.") }

    fun update(id: String, updateRequest: CustomerRequest): Customer {
        val foundCustomer = findById(id)

        val updatedEntity =
            updateRequest
                .toCustomerEntity()
                .copy(id = foundCustomer.id)
        return customerRepository.update(updatedEntity)
    }

    fun deleteById(id: String) {
        val foundCustomer = findById(id)
        customerRepository.delete(foundCustomer)
    }

    fun finadByNameLike(name: String): List<Customer> =
        customerRepository.findByFirstNameLike(name)

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
