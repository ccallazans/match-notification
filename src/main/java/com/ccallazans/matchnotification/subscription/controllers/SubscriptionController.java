package com.ccallazans.matchnotification.subscription.controllers;

import com.ccallazans.matchnotification.subscription.SubscriptionService;
import com.ccallazans.matchnotification.subscription.controllers.dto.CreateSubscriptionDTO;
import com.ccallazans.matchnotification.subscription.controllers.dto.SubscriptionResponse;
import com.ccallazans.matchnotification.subscription.mappers.SubscriptionMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(summary = "Create a subscription")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created subscription", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = SubscriptionResponse.class))
            }),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content)})
    @PostMapping("/new")
    public ResponseEntity<SubscriptionResponse> subscribe(
            @RequestBody @Valid CreateSubscriptionDTO createSubscriptionDTO
    ) {
        var subscription = subscriptionService.subscribe(createSubscriptionDTO.email(), createSubscriptionDTO.topics());

        var location = UriComponentsBuilder
                .fromPath("/api/subscriptions/{subscriptionId}")
                .buildAndExpand(subscription.getId())
                .toUriString();

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .location(URI.create(location))
                .body(SubscriptionMapper.INSTANCE.toSubscriptionResponse(subscription));
    }

    @Operation(summary = "Get a subscription by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the subscription", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = SubscriptionResponse.class))
            }),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content)})
    @GetMapping("/{id}")
    public ResponseEntity<SubscriptionResponse> getSubscription(@PathVariable @NotBlank Long id) {
        var subscription = subscriptionService.getSubscriptionById(id);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(SubscriptionMapper.INSTANCE.toSubscriptionResponse(subscription));
    }

    @Operation(summary = "Get all subscriptions")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = SubscriptionResponse.class))
            }),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content)})
    @GetMapping
    public ResponseEntity<List<SubscriptionResponse>> getAllNotifications(
            @RequestParam(name = "topic", required = false) List<String> topic
    ) {

        var subscriptions = (topic != null)
                ? subscriptionService.getSubscriptionsByTopics(topic)
                : subscriptionService.getAllSubscriptions();

        return ResponseEntity.ok(
                SubscriptionMapper.INSTANCE.toSubscriptionResponses(subscriptions));
    }
}
