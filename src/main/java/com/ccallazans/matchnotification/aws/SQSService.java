package com.ccallazans.matchnotification.aws;

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

    public void sendNotification(Object object) {
        log.info(String.format("Start sending notification: %s", object));
        var message = buildMessage(object, notificationQueue);
        sqsClient.sendMessage(message);
    }

    private SendMessageRequest buildMessage(Object object, String queue) {
        var message = parseObject(object);

        return SendMessageRequest.builder()
                .queueUrl(queue)
                .messageBody(message)
                .build();
    }

    private String parseObject(Object object) {
        var objectMapper = new ObjectMapper();

        String jsonMessage;
        try {
            jsonMessage = objectMapper.writeValueAsString(object);
        } catch (Exception e) {
            throw new IntegrationException(e.getMessage());
        }

        return jsonMessage;
    }
}
