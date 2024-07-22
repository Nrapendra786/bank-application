package com.nrapendra.bank.card;

import org.springframework.stereotype.Repository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Page;

@Repository
public interface CardRepository extends CrudRepository<Card, Long>, PagingAndSortingRepository<Card, Long> {

    Page<Card> findByCustomerId(Long customerId, PageRequest pageRequest);

}
