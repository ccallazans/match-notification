package com.ccallazans.matchnotification.notification;

import com.ccallazans.matchnotification.exceptions.ValidationException;
import com.ccallazans.matchnotification.notification.domain.TopicDomain;
import com.ccallazans.matchnotification.notification.entity.Topic;
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

        if (topicRepository.existsByName(topicName.toUpperCase())) {
            throw new ValidationException(String.format("Topic already exists: %s", topicName));
        }

        var topic = Topic.builder()
                .name(topicName.toUpperCase())
                .build();

        topicRepository.save(topic);

        return TopicMapper.INSTANCE.toTopicDomain(topic);
    }

    public List<TopicDomain> getAllTopics() {
        var topics = topicRepository.findAll();
        return TopicMapper.INSTANCE.toTopicDomains(topics);
    }
}
