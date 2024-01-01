package com.ccallazans.matchnotification.notification.controllers.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;

public record CreateRegisterDTO(
        @JsonProperty("email")
        @NotNull
        String email,
        @JsonProperty("password")
        @NotNull
        String password
) {
}
