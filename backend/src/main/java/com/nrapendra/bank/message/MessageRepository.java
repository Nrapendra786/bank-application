package com.nrapendra.bank.message;

import org.springframework.stereotype.Repository;
import org.springframework.data.repository.CrudRepository;

@Repository
public interface MessageRepository extends CrudRepository<Message, String> {
}
