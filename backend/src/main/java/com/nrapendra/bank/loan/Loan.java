package com.nrapendra.bank.loan;

import java.sql.Date;
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
@Table(name = "loans")
public class Loan {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    @Column(name = "loan_number", nullable = false, unique = true)
    private Long loanNumber;

    @Column(name = "customer_id", nullable = false)
    private Long customerId;

    @Column(name = "start_date", nullable = false)
    private Date startDate;

    @Column(name = "loan_type", nullable = false, length = 64)
    private String loanType;

    @Column(name = "amount_total", nullable = false)
    private Long amountTotal;

    @Column(name = "amount_paid", nullable = false)
    private Long amountPaid;

    @Column(name = "amount_outstanding", nullable = false)
    private Long amountOutstanding;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

}
