package com.ccallazans.matchnotification.notification.controllers.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;


public record CreateTopicDTO(
        @JsonProperty("name")
        @NotBlank
        String name
) {
}
