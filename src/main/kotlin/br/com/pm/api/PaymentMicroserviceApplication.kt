package br.com.pm.api

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class PaymentMicroserviceApplication

fun main(args: Array<String>) {
	runApplication<PaymentMicroserviceApplication>(*args)
}
