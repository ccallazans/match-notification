package com.ccallazans.matchnotification.notification.entity;

import com.ccallazans.matchnotification.enums.TypeEnum;
import com.ccallazans.matchnotification.subscription.entity.Topic;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "notifications")
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private TypeEnum type;

    @ManyToOne
    @JoinColumn(name = "topic_id")
    private Topic topic;

    private String message;
}
