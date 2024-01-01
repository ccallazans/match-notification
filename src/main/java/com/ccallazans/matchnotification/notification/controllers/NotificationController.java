package com.ccallazans.matchnotification.notification.controllers;

import com.ccallazans.matchnotification.notification.services.NotificationService;
import com.ccallazans.matchnotification.notification.controllers.dto.CreateNotificationDTO;
import com.ccallazans.matchnotification.notification.controllers.dto.NotificationResponse;
import com.ccallazans.matchnotification.notification.mappers.NotificationMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(summary = "Create a notification")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created notification", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = NotificationResponse.class))
            }),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content)})
    @PostMapping("/new")
    public ResponseEntity<NotificationResponse> createNotification(@RequestBody @Valid CreateNotificationDTO createNotificationDTO) {

        var notification = notificationService.createNotification(
                createNotificationDTO.topics(), createNotificationDTO.type(), createNotificationDTO.message());

        var location = UriComponentsBuilder
                .fromPath("/api/notifications/{notificationId}")
                .buildAndExpand(notification)
                .toUriString();

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .location(URI.create(location))
                .body(NotificationMapper.INSTANCE.toNotificationResponse(notification));
    }

    @Operation(summary = "Get a notification by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the notification", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = NotificationResponse.class))
            }),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content)})
    @GetMapping("/{id}")
    public ResponseEntity<NotificationResponse> getNotification(@PathVariable @NotBlank Long id) {
        var notification = notificationService.getNotificationById(id);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(NotificationMapper.INSTANCE.toNotificationResponse(notification));
    }

    @Operation(summary = "Get all notifications")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = NotificationResponse.class))
            }),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content)})
    @GetMapping("/")
    public ResponseEntity<List<NotificationResponse>> getAllNotifications() {
        var notifications = notificationService.getAllNotifications();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(NotificationMapper.INSTANCE.toNotificationResponses(notifications));
    }
}
