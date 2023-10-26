package com.ccallazans.matchnotification.notification.mappers;

import com.ccallazans.matchnotification.notification.controllers.dto.NotificationResponse;
import com.ccallazans.matchnotification.notification.domain.NotificationDomain;
import com.ccallazans.matchnotification.notification.entity.Notification;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface NotificationMapper {

    NotificationMapper INSTANCE = Mappers.getMapper(NotificationMapper.class);

    NotificationResponse toNotificationResponse(NotificationDomain notificationDomain);

    List<NotificationResponse> toNotificationResponses(List<NotificationDomain> notificationDomains);

    NotificationDomain toNotificationDomain(Notification notification);

    List<NotificationDomain> toNotificationDomains(List<Notification> notifications);

    Notification toNotification(NotificationDomain notificationDomain);
}
