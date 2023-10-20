package com.ccallazans.matchnotification.subscription.controllers.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CreateTopicDTO(
        @JsonProperty("name")
        String name) {
}
