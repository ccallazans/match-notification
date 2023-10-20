package com.ccallazans.matchnotification.subscription.mappers;

import com.ccallazans.matchnotification.subscription.domain.SubscriptionDomain;
import com.ccallazans.matchnotification.subscription.entity.Subscription;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface SubscriptionMapper {

    SubscriptionMapper INSTANCE = Mappers.getMapper(SubscriptionMapper.class);

    SubscriptionDomain toSubscriptionDomain(Subscription subscription);

    Subscription toSubscription(SubscriptionDomain subscriptionDomain);
}
