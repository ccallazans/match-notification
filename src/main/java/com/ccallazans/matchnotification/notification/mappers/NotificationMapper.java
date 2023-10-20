package com.ccallazans.matchnotification.notification.mappers;

import com.ccallazans.matchnotification.notification.domain.NotificationDomain;
import com.ccallazans.matchnotification.notification.entity.Notification;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface NotificationMapper {

    NotificationMapper INSTANCE = Mappers.getMapper(NotificationMapper.class);

    @Mapping(target = "topic", source = "topic.name")
    NotificationDomain toNotificationDomain(Notification notification);

    @Mapping(target = "topic", source = "topic.name")
    List<NotificationDomain> toNotificationDomains(List<Notification> notifications);

    @Mapping(target = "topic.name", source = "topic")
    Notification toNotification(NotificationDomain notificationDomain);
}
