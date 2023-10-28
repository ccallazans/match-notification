package com.ccallazans.matchnotification.notification.services;

import com.ccallazans.matchnotification.config.SQSService;
import com.ccallazans.matchnotification.notification.domain.NotificationDomain;
import com.ccallazans.matchnotification.notification.entity.Notification;
import com.ccallazans.matchnotification.notification.entity.Topic;
import com.ccallazans.matchnotification.notification.enums.TypeEnum;
import com.ccallazans.matchnotification.notification.repository.NotificationRepository;
import com.ccallazans.matchnotification.notification.repository.TopicRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

class NotificationServiceTest {

    @Mock
    private TopicRepository topicRepository;
    @Mock
    private NotificationRepository notificationRepository;
    @Mock
    private SQSService sqsService;

    private NotificationService notificationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        notificationService = new NotificationService(topicRepository, notificationRepository, sqsService);
    }

    @Test
    void shouldCreateNotificationWithValidInput() {
        Set<String> topics = new HashSet<>(Arrays.asList("Topic1", "Topic2"));
        String typeName = TypeEnum.GOAL.name();
        String message = "Test message";

        when(topicRepository.findByName(anyString())).thenReturn(new Topic());
        when(notificationRepository.save(any())).thenReturn(new Notification());

        NotificationDomain result = notificationService.createNotification(topics, typeName, message);
        assertNotNull(result);
    }
}