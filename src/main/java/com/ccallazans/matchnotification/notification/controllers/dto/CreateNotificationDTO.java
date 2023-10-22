package com.ccallazans.matchnotification.notification.controllers.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;


public record CreateNotificationDTO(
        @JsonProperty("topic")
        @NotBlank
        String topic,
        @JsonProperty("type")
        @NotBlank
        String type,
        @JsonProperty("message")
        @NotBlank
        String message) {
}
