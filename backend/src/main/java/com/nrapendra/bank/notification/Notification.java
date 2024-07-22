package com.nrapendra.bank.notification;

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
@Table(name = "notifications")
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    @Column(name = "notification_id", nullable = false, unique = true)
    private Long notificationId;

    @Column(name = "notification_summary", nullable = false, length = 256)
    private String notificationSummary;

    @Column(name = "notification_details", nullable = false, length = 1024)
    private String notificationDetails;

    @Column(name = "notification_start_date", nullable = false)
    private Date notificationStartDate;

    @Column(name = "notification_end_date", nullable = false)
    private Date notificationEndDate;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

}
