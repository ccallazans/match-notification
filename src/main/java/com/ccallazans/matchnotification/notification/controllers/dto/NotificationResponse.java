package com.ccallazans.matchnotification.notification.controllers.dto;

import java.util.List;

public record NotificationResponse(List<TopicResponse> topics, String type, String message) {

}
