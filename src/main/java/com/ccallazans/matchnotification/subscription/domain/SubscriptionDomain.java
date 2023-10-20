package com.ccallazans.matchnotification.subscription.domain;

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
