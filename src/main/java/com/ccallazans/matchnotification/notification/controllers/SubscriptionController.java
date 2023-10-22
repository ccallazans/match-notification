package com.ccallazans.matchnotification.notification.controllers;

import com.ccallazans.matchnotification.notification.controllers.dto.CreateSubscriptionDTO;
import com.ccallazans.matchnotification.notification.domain.SubscriptionDomain;
import com.ccallazans.matchnotification.notification.services.SubscriptionService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/subscriptions")
public class SubscriptionController {

    private SubscriptionService subscriptionService;

    /**
     * Subscribe an email to a topic.
     *
     * @param createSubscriptionDTO The DTO containing email and topic.
     * @return ResponseEntity with the created subscription and location header.
     */
    @PostMapping("/new")
    public ResponseEntity<SubscriptionDomain> subscribe(@RequestBody @Valid CreateSubscriptionDTO createSubscriptionDTO) {
        var subscription = subscriptionService.subscribe(createSubscriptionDTO.email(), createSubscriptionDTO.topic());

        var location = UriComponentsBuilder
                .fromPath("/api/subscriptions/{subscriptionId}")
                .buildAndExpand(subscription.getId())
                .toUriString();

        return ResponseEntity.status(HttpStatus.CREATED).location(URI.create(location)).body(subscription);
    }

    /**
     * Get a subscription by its ID.
     *
     * @param id The ID of the notification to retrieve.
     * @return ResponseEntity with the retrieved subscription or NOT_FOUND if not found.
     */
    @GetMapping("/{id}")
    public ResponseEntity<SubscriptionDomain> getSubscription(@PathVariable @NotBlank Long id) {
        var subscription = subscriptionService.getSubscriptionById(id);
        return ResponseEntity.status(HttpStatus.OK).body(subscription);
    }

    /**
     * Get all notifications.
     *
     * @return ResponseEntity with a list of all notifications or NOT_FOUND if the list is empty.
     */
    @GetMapping
    public ResponseEntity<List<SubscriptionDomain>> getAllNotifications(
            @RequestParam(name = "topic", required = false) String topic
    ) {
        List<SubscriptionDomain> subscriptions;

        if (topic != null) {
            subscriptions = subscriptionService.getSubscriptionsByTopic(topic);
        } else {
            subscriptions = subscriptionService.getAllSubscriptions();
        }

        return ResponseEntity.status(HttpStatus.OK).body(subscriptions);
    }
}
