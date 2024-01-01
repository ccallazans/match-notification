package com.ccallazans.matchnotification.notification.controllers.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;

import java.util.List;


public record CreateSubscriptionDTO(
        @JsonProperty("email")
        @NotNull
        String email,
        @JsonProperty("topics")
        @NotNull
        List<String> topics
) {
}
