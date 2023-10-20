package com.ccallazans.matchnotification.subscription.controllers;

import com.ccallazans.matchnotification.subscription.controllers.dto.SubscribeDTO;
import com.ccallazans.matchnotification.subscription.domain.SubscriptionDomain;
import com.ccallazans.matchnotification.subscription.services.SubscriptionService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/subscriptions")
public class SubscriptionController {

    private SubscriptionService subscriptionService;

    /**
     * Subscribe an email to a topic.
     *
     * @param subscribeDTO The DTO containing email and topic.
     * @return ResponseEntity with the created subscription and location header.
     */
    @PostMapping("/new")
    public ResponseEntity<SubscriptionDomain> subscribe(@RequestBody SubscribeDTO subscribeDTO) {
        if (subscribeDTO.email() == null || subscribeDTO.topic() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        var subscription = subscriptionService.subscribe(subscribeDTO.email(), subscribeDTO.topic());

        var location = UriComponentsBuilder
                .fromPath("/api/subscriptions/{subscriptionId}")
                .buildAndExpand(subscription.getId())
                .toUriString();

        return ResponseEntity.status(HttpStatus.CREATED).location(URI.create(location)).body(subscription);
    }
}
