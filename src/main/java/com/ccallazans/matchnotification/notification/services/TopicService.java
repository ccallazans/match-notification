package com.ccallazans.matchnotification.notification.services;

import com.ccallazans.matchnotification.exceptions.ValidationException;
import com.ccallazans.matchnotification.notification.domain.TopicDomain;
import com.ccallazans.matchnotification.notification.mappers.TopicMapper;
import com.ccallazans.matchnotification.notification.repository.TopicRepository;
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
        if (topicName.isBlank()) {
            throw new ValidationException("Topic cannot be empty");
        }

        var topicDomain = TopicDomain.builder()
                .name(topicName.toUpperCase())
                .build();

        if (topicRepository.existsByName(topicName.toUpperCase())) {
            throw new ValidationException(String.format("Topic already exists: %s", topicName));
        }

        topicRepository.save(TopicMapper.INSTANCE.toTopic(topicDomain));

        return topicDomain;
    }

    public List<TopicDomain> getAllTopics() {
        var topics = topicRepository.findAll();
        return TopicMapper.INSTANCE.toTopicDomains(topics);
    }
}
