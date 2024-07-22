package com.nrapendra.bank.transaction;

import org.springframework.stereotype.Repository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Page;

@Repository
public interface TransactionRepository
        extends CrudRepository<Transaction, String>, PagingAndSortingRepository<Transaction, String> {

    Page<Transaction> findByCustomerId(Long customerId, PageRequest pageRequest);
}
