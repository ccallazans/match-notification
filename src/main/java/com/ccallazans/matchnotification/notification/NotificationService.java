package com.ccallazans.matchnotification.notification;

import com.ccallazans.matchnotification.config.SQSService;
import com.ccallazans.matchnotification.exceptions.NotFoundException;
import com.ccallazans.matchnotification.exceptions.ValidationException;
import com.ccallazans.matchnotification.notification.domain.NotificationDomain;
import com.ccallazans.matchnotification.notification.entity.Notification;
import com.ccallazans.matchnotification.notification.enums.TypeEnum;
import com.ccallazans.matchnotification.notification.mappers.NotificationMapper;
import com.ccallazans.matchnotification.notification.mappers.TopicMapper;
import com.ccallazans.matchnotification.notification.repository.NotificationRepository;
import com.ccallazans.matchnotification.notification.repository.TopicRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.EnumUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class NotificationService {

    private final TopicRepository topicRepository;
    private final NotificationRepository notificationRepository;
    private final SQSService sqsService;

    @Transactional
    public NotificationDomain createNotification(Set<String> topics, String typeName, String message) {

        if (topics.isEmpty()) {
            throw new ValidationException("Empty topic list!");
        }

        if (!EnumUtils.isValidEnum(TypeEnum.class, typeName)) {
            throw new ValidationException("Invalid type: " + typeName);
        }

        if (message.isBlank()) {
            throw new ValidationException("Message cannot be empty");
        }

        var validTopics = topics.stream()
                .map(topic -> Optional.ofNullable(topicRepository.findByName(topic))
                        .orElseThrow(() -> new ValidationException("Topic is not valid: " + topic.toUpperCase()))
                )
                .collect(Collectors.toSet());

        var notification = Notification.builder()
                .type(TypeEnum.valueOf(typeName))
                .topics(validTopics)
                .message(message)
                .build();

        var savedNotification = notificationRepository.save(notification);

        sqsService.sendNotification(NotificationMapper.INSTANCE.toNotificationDomain(savedNotification));

        return NotificationMapper.INSTANCE.toNotificationDomain(notification);
    }

    public NotificationDomain getNotificationById(Long id) {
        var notification = notificationRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Invalid notification id: " + id));

        return NotificationMapper.INSTANCE.toNotificationDomain(notification);
    }

    public List<NotificationDomain> getAllNotifications() {
        List<Notification> notifications = notificationRepository.findAll();
        return NotificationMapper.INSTANCE.toNotificationDomains(notifications);
    }
}
