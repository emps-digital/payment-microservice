package br.com.pm.api.repository

import br.com.pm.api.model.Payment
import br.com.pm.api.model.enum.PaymentMethod
import br.com.pm.api.model.enum.PaymentStatus
import br.com.pm.api.repositoty.PaymentRepository
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import java.math.BigDecimal
import java.time.LocalDateTime

@DataJpaTest
class PaymentRepositoryIntegrationTest {

    @Autowired
    private lateinit var entityManager: TestEntityManager

    @Autowired
    private lateinit var paymentRepository: PaymentRepository

    @Test
    fun whenSavePayment_thenReturnPayment() {
        val payment = Payment(
            amount = BigDecimal("100.00"),
            currency = "USD",
            paymentMethod = PaymentMethod.CREDIT_CARD,
            status = PaymentStatus.PENDING,
            createdAt = LocalDateTime.now()
        )

        val savedPayment = paymentRepository.save(payment)

        assertNotNull(savedPayment)
        assertTrue(savedPayment.id > 0)
    }

    @Test
    fun whenFindById_thenReturnPayment() {
        val payment = Payment(
            amount = BigDecimal("200.00"),
            currency = "EUR",
            paymentMethod = PaymentMethod.DEBIT_CARD,
            status = PaymentStatus.COMPLETED,
            createdAt = LocalDateTime.now()
        )
        val savedPayment = entityManager.persist(payment)
        entityManager.flush()

        val foundPayment = paymentRepository.findById(savedPayment.id).orElse(null)

        assertNotNull(foundPayment)
        assertEquals(savedPayment.id, foundPayment?.id)
        assertEquals(savedPayment.amount, foundPayment?.amount)
        assertEquals(savedPayment.currency, foundPayment?.currency)
        assertEquals(savedPayment.paymentMethod, foundPayment?.paymentMethod)
        assertEquals(savedPayment.status, foundPayment?.status)
    }

    @Test
    fun whenFindAll_thenReturnPaymentList() {
        val payment1 = Payment(
            amount = BigDecimal("300.00"),
            currency = "GBP",
            paymentMethod = PaymentMethod.BANK_TRANSFER,
            status = PaymentStatus.PENDING,
            createdAt = LocalDateTime.now()
        )
        val payment2 = Payment(
            amount = BigDecimal("400.00"),
            currency = "USD",
            paymentMethod = PaymentMethod.CREDIT_CARD,
            status = PaymentStatus.COMPLETED,
            createdAt = LocalDateTime.now()
        )
        entityManager.persist(payment1)
        entityManager.persist(payment2)
        entityManager.flush()

        val payments = paymentRepository.findAll()

        assertEquals(2, payments.size)
    }

    @Test
    fun whenDeletePayment_thenRemovePayment() {
        val payment = Payment(
            amount = BigDecimal("500.00"),
            currency = "JPY",
            paymentMethod = PaymentMethod.CREDIT_CARD,
            status = PaymentStatus.FAILED,
            createdAt = LocalDateTime.now()
        )
        val savedPayment = entityManager.persist(payment)
        entityManager.flush()

        paymentRepository.deleteById(savedPayment.id)
        val deletedPayment = paymentRepository.findById(savedPayment.id).orElse(null)

        assertNull(deletedPayment)
    }

    @Test
    fun whenUpdatePayment_thenReturnUpdatedPayment() {
        val payment = Payment(
            amount = BigDecimal("600.00"),
            currency = "USD",
            paymentMethod = PaymentMethod.PAYPAL,
            status = PaymentStatus.PENDING,
            createdAt = LocalDateTime.now()
        )
        val savedPayment = entityManager.persist(payment)
        entityManager.flush()

        savedPayment.status = PaymentStatus.COMPLETED
        savedPayment.updatedAt = LocalDateTime.now()
        val updatedPayment = paymentRepository.save(savedPayment)

        assertEquals(PaymentStatus.COMPLETED, updatedPayment.status)
        assertNotNull(updatedPayment.updatedAt)
    }
}
