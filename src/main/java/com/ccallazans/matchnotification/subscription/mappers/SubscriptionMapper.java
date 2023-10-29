package com.ccallazans.matchnotification.subscription.mappers;

import com.ccallazans.matchnotification.subscription.controllers.dto.SubscriptionResponse;
import com.ccallazans.matchnotification.subscription.domain.SubscriptionDomain;
import com.ccallazans.matchnotification.subscription.entity.Subscription;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SubscriptionMapper {

    SubscriptionMapper INSTANCE = Mappers.getMapper(SubscriptionMapper.class);

    SubscriptionResponse toSubscriptionResponse(SubscriptionDomain subscriptionDomain);

    List<SubscriptionResponse> toSubscriptionResponses(List<SubscriptionDomain> subscriptionDomains);

    SubscriptionDomain toSubscriptionDomain(Subscription subscription);

    List<SubscriptionDomain> toSubscriptionDomains(List<Subscription> subscriptions);

    Subscription toSubscription(SubscriptionDomain subscriptionDomain);
}
