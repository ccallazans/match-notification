package com.ccallazans.matchnotification.notification.repository;

import com.ccallazans.matchnotification.notification.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
}
