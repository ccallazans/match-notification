package com.ccallazans.matchnotification.notification.services;

import com.ccallazans.matchnotification.exceptions.ValidationException;
import com.ccallazans.matchnotification.notification.repository.TopicRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

class TopicServiceTest {

    @Mock
    private TopicRepository topicRepository;

    private TopicService topicService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        topicService = new TopicService(topicRepository);
    }

    @Test
    void shouldCreateTopicSuccessfully() {
        String topicName = "test_topic";
        var topicDomain = topicService.createTopic(topicName);

        assertEquals(topicName.toUpperCase(), topicDomain.getName());
    }

    @Test
    void shouldThrowValidationExceptionWhenTopicNameIsEmpty() {
        String topicName = "";

        var exception = assertThrows(ValidationException.class, () -> topicService.createTopic(topicName));

        assertEquals("Topic cannot be empty", exception.getMessage());
    }

    @Test
    void shouldThrowValidationExceptionWhenTopicAlreadyExists() {
        String topicName = "TEST_TOPIC";
        when(topicRepository.existsByName(topicName)).thenReturn(true);

        var exception = assertThrows(ValidationException.class, () -> topicService.createTopic(topicName));

        assertEquals(String.format("Topic already exists: %s", topicName), exception.getMessage());
    }

}