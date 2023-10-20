package com.ccallazans.matchnotification.notification.controllers.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import software.amazon.awssdk.annotations.NotNull;

public record NotificationDTO(
        @JsonProperty("type")
        String type,
        @JsonProperty("topic")
        @NotNull String topic,
        @JsonProperty("message")
        @NotNull String message) {
}
