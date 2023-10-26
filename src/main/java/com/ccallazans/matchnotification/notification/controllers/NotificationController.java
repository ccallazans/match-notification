package com.ccallazans.matchnotification.notification.controllers;

import com.ccallazans.matchnotification.notification.controllers.dto.CreateNotificationDTO;
import com.ccallazans.matchnotification.notification.controllers.dto.NotificationResponse;
import com.ccallazans.matchnotification.notification.mappers.NotificationMapper;
import com.ccallazans.matchnotification.notification.services.NotificationService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/notifications")
public class NotificationController {

    private NotificationService notificationService;

    /**
     * Create a new notification.
     *
     * @param createNotificationDTO The DTO containing notification details.
     * @return ResponseEntity with the created notification and location header.
     */
    @PostMapping("/new")
    public ResponseEntity<NotificationResponse> createNotification(@RequestBody @Valid CreateNotificationDTO createNotificationDTO) {

        var notification = notificationService.createNotification(
                createNotificationDTO.topics(), createNotificationDTO.type(), createNotificationDTO.message());

        var location = UriComponentsBuilder
                .fromPath("/api/notifications/{notificationId}")
                .buildAndExpand(notification.getId())
                .toUriString();

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .location(URI.create(location))
                .body(NotificationMapper.INSTANCE.toNotificationResponse(notification));
    }

    /**
     * Get a notification by its ID.
     *
     * @param id The ID of the notification to retrieve.
     * @return ResponseEntity with the retrieved notification or NOT_FOUND if not found.
     */
    @GetMapping("/{id}")
    public ResponseEntity<NotificationResponse> getNotification(@PathVariable @NotBlank Long id) {
        var notification = notificationService.getNotificationById(id);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(NotificationMapper.INSTANCE.toNotificationResponse(notification));
    }

    /**
     * Get all notifications.
     *
     * @return ResponseEntity with a list of all notifications or NOT_FOUND if the list is empty.
     */
    @GetMapping("/")
    public ResponseEntity<List<NotificationResponse>> getAllNotifications() {
        var notifications = notificationService.getAllNotifications();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(NotificationMapper.INSTANCE.toNotificationResponses(notifications));
    }
}
