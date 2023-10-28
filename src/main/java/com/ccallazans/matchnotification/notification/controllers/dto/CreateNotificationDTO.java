package com.ccallazans.matchnotification.notification.controllers.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;

import java.util.Set;


public record CreateNotificationDTO(
        @JsonProperty("type")
        @NotNull()
        String type,
        @JsonProperty("topics")
        @NotNull()
        Set<String> topics,
        @JsonProperty("message")
        @NotNull()
        String message
) {
}
