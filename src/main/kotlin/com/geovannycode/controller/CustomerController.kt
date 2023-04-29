package com.geovannycode.controller

import com.geovannycode.document.Customer
import com.geovannycode.dto.CustomerRequest
import com.geovannycode.dto.SearchRequest
import com.geovannycode.service.CustomerService
import io.micronaut.http.HttpStatus.CREATED
import io.micronaut.http.HttpStatus.NO_CONTENT
import io.micronaut.http.annotation.*
import io.micronaut.scheduling.TaskExecutors
import io.micronaut.scheduling.annotation.ExecuteOn

@Controller("customer")
@ExecuteOn(TaskExecutors.IO)
class CustomerController(private val customerService: CustomerService) {

    @Get
    fun findAllUsers(): List<Customer> =
        customerService.findAll()

    @Get("/{id}")
    fun findById(@PathVariable id: String): Customer =
        customerService.findById(id)

    @Post
    @Status(CREATED)
    fun createCustomer(@Body request: CustomerRequest): Customer =
        customerService.create(request)

    @Post("/search")
    fun searchCustomer(@Body searchRequest: SearchRequest): List<Customer> =
        customerService.findByNameLike(
            name = searchRequest.name,
        )

    @Put("/{id}")
    fun updateById(
        @PathVariable id: String,
        @Body request: CustomerRequest,
    ): Customer = customerService.update(id, request)

    @Delete("/{id}")
    @Status(NO_CONTENT)
    fun deleteById(@PathVariable id: String) =
        customerService.deleteById(id)
}
