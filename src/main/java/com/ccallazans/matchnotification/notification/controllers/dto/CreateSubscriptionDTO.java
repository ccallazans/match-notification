package com.ccallazans.matchnotification.notification.controllers.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;


public record CreateSubscriptionDTO(
        @JsonProperty("email")
        @NotBlank
        String email,
        @JsonProperty("topic")
        @NotBlank
        String topic) {
}
