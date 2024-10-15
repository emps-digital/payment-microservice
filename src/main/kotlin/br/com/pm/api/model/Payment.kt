package br.com.pm.api.model

import br.com.pm.api.model.enum.PaymentMethod
import br.com.pm.api.model.enum.PaymentStatus
import jakarta.persistence.*
import java.math.BigDecimal
import java.time.LocalDateTime

@Entity
@Table(name = "payments")
data class Payment (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(nullable = false)
    val amount: BigDecimal,

    @Column(nullable = false)
    val currency: String,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    var status: PaymentStatus = PaymentStatus.PENDING,

    @Column(nullable = false)
    val paymentMethod: PaymentMethod,

    @Column(nullable = false)
    val createdAt: LocalDateTime = LocalDateTime.now(),

    @Column(nullable = true)
    var updatedAt: LocalDateTime? = null
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Payment

        return id == other.id
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }

    override fun toString(): String {
        return "Payment(amount=$amount, currency='$currency', status=$status, paymentMethod=$paymentMethod, createdAt=$createdAt, updatedAt=$updatedAt)"
    }
}