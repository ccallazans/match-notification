package com.ccallazans.matchnotification.notification.domain;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NotificationDomain {
    private Long id;
    private String type;
    private Set<TopicDomain> topics;
    private String message;
}
