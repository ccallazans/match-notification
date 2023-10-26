package com.ccallazans.matchnotification.notification.controllers.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;

import java.util.Set;


public record CreateNotificationDTO(
        @JsonProperty("topics")
        @NotNull(message = "topic list cannot be empty")
        Set<String> topics,
        @JsonProperty("type")
        @NotNull(message = "type cannot be empty")
        String type,
        @JsonProperty("message")
        @NotNull(message = "message cannot be empty")
        String message
) {
}
