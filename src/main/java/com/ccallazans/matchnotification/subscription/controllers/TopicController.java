package com.ccallazans.matchnotification.subscription.controllers;

import com.ccallazans.matchnotification.subscription.controllers.dto.CreateTopicDTO;
import com.ccallazans.matchnotification.subscription.domain.TopicDomain;
import com.ccallazans.matchnotification.subscription.services.TopicService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/topics")
public class TopicController {

    private TopicService topicService;

    @PostMapping("/new")
    public ResponseEntity<TopicDomain> createTopic(@RequestBody CreateTopicDTO createTopicDTO) {
        if (createTopicDTO.name() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        var topic = topicService.createTopic(createTopicDTO.name());

        URI location = UriComponentsBuilder
                .fromPath("/api/topics/{topicId}")
                .buildAndExpand(topic.getId())
                .toUri();

        return ResponseEntity.status(HttpStatus.CREATED).location(location).body(topic);
    }

    @GetMapping("")
    public ResponseEntity<List<TopicDomain>> getAllTopics() {
        List<TopicDomain> topics = topicService.getAllTopics();
        return ResponseEntity.status(HttpStatus.OK).body(topics);
    }
}
