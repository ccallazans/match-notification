package com.ccallazans.matchnotification.notification.domain;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NotificationDomain {
    private Long id;
    private String topic;
    private String type;
    private String message;
}
