package com.ccallazans.matchnotification.subscription.domain;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TopicDomain {
    private Long id;
    private String name;
}
