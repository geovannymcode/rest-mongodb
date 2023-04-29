package com.geovannycode.controller

import com.geovannycode.document.Customer
import com.geovannycode.dto.CustomerRequest
import com.geovannycode.service.CustomerWithMongoClientService
import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpStatus.CREATED
import io.micronaut.http.annotation.*
import io.micronaut.scheduling.TaskExecutors
import io.micronaut.scheduling.annotation.ExecuteOn
import java.net.URI

@Controller("/customer-client")
@ExecuteOn(TaskExecutors.IO)
class CustomerWithMongoClientController(
    private val service: CustomerWithMongoClientService,
) {
    @Get
    fun findAllCustomers(): List<Customer> =
        service.findAll()

    @Post
    @Status(CREATED)
    fun createUser(@Body request: CustomerRequest): HttpResponse<Void> {
        val createdId = service.create(request)
        return HttpResponse.created(
            URI.create(
                createdId.asObjectId().value.toHexString(),
            ),
        )
    }
}
