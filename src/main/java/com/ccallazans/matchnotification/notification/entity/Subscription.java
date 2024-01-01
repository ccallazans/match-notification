package com.ccallazans.matchnotification.notification.entity;

import com.ccallazans.matchnotification.notification.entity.Topic;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "subscriptions")
public class Subscription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    @ManyToMany
    @JoinTable(name = "subscription_topics",
            joinColumns = @JoinColumn(name = "subscription_id"),
            inverseJoinColumns = @JoinColumn(name = "topic_id"))
    private Set<Topic> topics;
}
