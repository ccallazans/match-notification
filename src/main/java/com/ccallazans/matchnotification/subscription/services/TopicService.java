package com.ccallazans.matchnotification.subscription.services;

import com.ccallazans.matchnotification.exceptions.NotFoundException;
import com.ccallazans.matchnotification.exceptions.ValidationException;
import com.ccallazans.matchnotification.subscription.domain.TopicDomain;
import com.ccallazans.matchnotification.subscription.entity.Topic;
import com.ccallazans.matchnotification.subscription.mappers.TopicMapper;
import com.ccallazans.matchnotification.subscription.repository.TopicRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class TopicService {
    @Autowired
    private final TopicRepository topicRepository;

    public TopicDomain createTopic(String topicName) {
        validateTopic(topicName);

        if (existsTopic(topicName)) {
            throw new ValidationException(String.format("Topic already exists: %s", topicName));
        }

        var topic = Topic.builder()
                .name(topicName)
                .build();

        topicRepository.saveAndFlush(topic);

        return TopicMapper.INSTANCE.toTopicDomain(topic);
    }

    public List<TopicDomain> getAllTopics() {
        List<Topic> topics = topicRepository.findAll();
        if (topics.isEmpty()) {
            throw new NotFoundException();
        }

        return TopicMapper.INSTANCE.toTopicDomains(topics);
    }

    private void validateTopic(String topicName) {
        if (topicName.isBlank()) {
            throw new ValidationException("Topic cannot be empty");
        }
    }

    private boolean existsTopic(String topicName) {
        return topicRepository.existsByName(topicName);
    }
}
