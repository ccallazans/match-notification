package com.ccallazans.matchnotification.config;

import com.ccallazans.matchnotification.exceptions.IntegrationException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.SendMessageRequest;

@Service
@Slf4j
public class SQSService {
    @Autowired
    private SqsClient sqsClient;
    @Value("${cloud.aws.sqs.notification-queue}")
    private String notificationQueue;

    private final ObjectMapper objectMapper = new ObjectMapper();

    public void sendNotification(Object object) {
        log.info("Start sending notification: {}", object);
        String message = parseObject(object);
        sqsClient.sendMessage(buildMessage(message, notificationQueue));
    }

    private SendMessageRequest buildMessage(String message, String queue) {
        return SendMessageRequest.builder()
                .queueUrl(queue)
                .messageBody(message)
                .build();
    }

    private String parseObject(Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (Exception e) {
            throw new IntegrationException(e.getMessage());
        }
    }
}