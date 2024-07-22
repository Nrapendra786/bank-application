package com.nrapendra.bank.card;

import java.time.LocalDateTime;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import org.hibernate.annotations.GenericGenerator;
import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "cards")
public class Card {

    @Id
    @Column(name = "card_id", nullable = false, unique = true)
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private Long cardId;

    @Column(name = "card_number", nullable = false, unique = true)
    private Long customerId;

    @Column(name = "card_type", nullable = false, length = 32)
    private String cardType;

    @Column(name = "total_limit", nullable = false)
    private Long totalLimit;

    @Column(name = "amount_used", nullable = false)
    private Long amountUsed;

    @Column(name = "available_amount", nullable = false)
    private Long availableAmount;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

}
