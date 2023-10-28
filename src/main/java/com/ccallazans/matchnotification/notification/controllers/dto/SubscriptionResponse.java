package com.ccallazans.matchnotification.notification.controllers.dto;

import java.util.List;

public record SubscriptionResponse(
        Long id,
        String email,
        List<TopicResponse> topics) {
}
