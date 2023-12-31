package com.ccallazans.matchnotification.notification.domain;

import com.ccallazans.matchnotification.notification.domain.TopicDomain;
import lombok.*;

import java.util.Set;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SubscriptionDomain {
    private Long id;
    private String email;
    private Set<TopicDomain> topics;
}
