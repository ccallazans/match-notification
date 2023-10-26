package com.ccallazans.matchnotification.notification.controllers.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;


public record CreateTopicDTO(
        @JsonProperty("name")
        @NotNull
        String name
) {
}
