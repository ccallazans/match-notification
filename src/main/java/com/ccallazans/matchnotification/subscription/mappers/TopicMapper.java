package com.ccallazans.matchnotification.subscription.mappers;

import com.ccallazans.matchnotification.subscription.domain.TopicDomain;
import com.ccallazans.matchnotification.subscription.entity.Topic;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TopicMapper {
    TopicMapper INSTANCE = Mappers.getMapper(TopicMapper.class);

    TopicDomain toTopicDomain(Topic topic);

    List<TopicDomain> toTopicDomains(List<Topic> topics);

    Topic toTopic(TopicDomain topicDomain);
}
