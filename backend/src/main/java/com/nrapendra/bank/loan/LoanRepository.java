package com.nrapendra.bank.loan;

import org.springframework.stereotype.Repository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Page;

@Repository
public interface LoanRepository extends CrudRepository<Loan, Long>, PagingAndSortingRepository<Loan, Long> {

    Page<Loan> findByCustomerId(Long customerId, PageRequest pageRequest);

}
