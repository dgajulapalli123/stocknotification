package com.example.stock_monitor.repository;

import com.example.stock_monitor.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
}
