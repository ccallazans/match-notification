package com.ccallazans.matchnotification.subscription.controllers.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record SubscribeDTO(
        @JsonProperty("email")
        String email,
        @JsonProperty("topic")
        String topic) {
}
