package com.ccallazans.matchnotification.notification.services;

import com.ccallazans.matchnotification.aws.SQSService;
import com.ccallazans.matchnotification.enums.TypeEnum;
import com.ccallazans.matchnotification.exceptions.NotFoundException;
import com.ccallazans.matchnotification.exceptions.ValidationException;
import com.ccallazans.matchnotification.notification.domain.NotificationDomain;
import com.ccallazans.matchnotification.notification.entity.Notification;
import com.ccallazans.matchnotification.notification.mappers.NotificationMapper;
import com.ccallazans.matchnotification.notification.repository.NotificationRepository;
import com.ccallazans.matchnotification.subscription.entity.Topic;
import com.ccallazans.matchnotification.subscription.repository.TopicRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.EnumUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class NotificationService {

    private final TopicRepository topicRepository;
    private final NotificationRepository notificationRepository;
    private final SQSService sqsService;

    @Transactional
    public NotificationDomain createNotification(String typeName, String topicName, String message) {
        validateTypeName(typeName);
        validateMessage(message);

        Topic topic = getValidTopic(topicName.toUpperCase());

        Notification notification = Notification.builder()
                .type(TypeEnum.valueOf(typeName.toUpperCase()))
                .topic(topic)
                .message(message)
                .build();

        var savedNotification = notificationRepository.saveAndFlush(notification);

        sqsService.sendNotification(NotificationMapper.INSTANCE.toNotificationDomain(savedNotification));

        return NotificationMapper.INSTANCE.toNotificationDomain(savedNotification);
    }

    public NotificationDomain getNotificationById(Long id) {
        Notification notification = getValidNotificationById(id);
        return NotificationMapper.INSTANCE.toNotificationDomain(notification);
    }

    public List<NotificationDomain> getAllNotifications() {
        List<Notification> notifications = notificationRepository.findAll();
        if (notifications.isEmpty()) {
            throw new NotFoundException();
        }

        return NotificationMapper.INSTANCE.toNotificationDomains(notifications);
    }

    private void validateTypeName(String typeName) {
        if (!EnumUtils.isValidEnum(TypeEnum.class, typeName)) {
            throw new ValidationException("Invalid type: " + typeName);
        }
    }

    private void validateMessage(String message) {
        if (message.isBlank()) {
            throw new ValidationException("Message cannot be empty");
        }
    }

    private Topic getValidTopic(String topicName) {
        return Optional.ofNullable(topicRepository.findByName(topicName))
                .orElseThrow(() -> new ValidationException("Invalid topic: " + topicName));
    }

    private Notification getValidNotificationById(Long id) {
        return notificationRepository.findById(id)
                .orElseThrow(NotFoundException::new);
    }
}
