package com.nrapendra.bank.transaction;

import java.sql.Date;
import java.time.LocalDateTime;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "account_transactions")
public class Transaction {

    @Id
    @Column(name = "transaction_id", nullable = false, unique = true, length = 256)
    private String transactionId;

    @Column(name = "account_number", nullable = false)
    private Long accountNumner;

    @Column(name = "customer_id", nullable = false)
    private Long customerId;

    @Column(name = "transaction_date", nullable = false)
    private Date transactionDate;

    @Column(name = "transaction_summary", nullable = false, length = 128)
    private String transactionSummary;

    @Column(name = "transaction_type", nullable = false, length = 64)
    private String transactionType;

    @Column(name = "transactionAmount", nullable = false)
    private Long transactionAmount;

    @Column(name = "closing_balance", nullable = false)
    private Long closingBalance;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

}
