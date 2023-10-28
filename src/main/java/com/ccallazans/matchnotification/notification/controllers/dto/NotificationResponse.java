package com.ccallazans.matchnotification.notification.controllers.dto;

import java.util.List;

public record NotificationResponse(
        Long id,
        String type,
        List<TopicResponse> topics,
        String message) {

}
