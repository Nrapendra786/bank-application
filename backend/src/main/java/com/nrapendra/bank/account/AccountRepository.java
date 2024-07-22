package com.nrapendra.bank.account;

import java.util.Optional;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.CrudRepository;

@Repository
public interface AccountRepository extends CrudRepository<Account, Long> {

    Optional<Account> findByCustomerId(Long customerId);
}
