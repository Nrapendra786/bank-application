package com.nrapendra.bank.notification;

import org.springframework.stereotype.Repository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.Query;

@Repository
public interface NotificationRepository
        extends CrudRepository<Notification, Long>, PagingAndSortingRepository<Notification, Long> {

    @Query(value = "FROM Notification n WHERE CURDATE() BETWEEN notificanStartDate and notificationEndDate")
    Page<Notification> findAllActiveNotifications(PageRequest pageRequest);

}
