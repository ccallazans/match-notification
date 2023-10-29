package com.ccallazans.matchnotification.subscription.controllers.dto;

import com.ccallazans.matchnotification.notification.controllers.dto.TopicResponse;

import java.util.List;

public record SubscriptionResponse(
        Long id,
        String email,
        List<TopicResponse> topics) {
}
