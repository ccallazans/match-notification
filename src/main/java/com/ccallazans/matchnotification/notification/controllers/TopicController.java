package com.ccallazans.matchnotification.notification.controllers;

import com.ccallazans.matchnotification.notification.TopicService;
import com.ccallazans.matchnotification.notification.controllers.dto.CreateTopicDTO;
import com.ccallazans.matchnotification.notification.controllers.dto.TopicResponse;
import com.ccallazans.matchnotification.notification.domain.TopicDomain;
import com.ccallazans.matchnotification.notification.mappers.TopicMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
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

    @Operation(summary = "Create a topic")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created topic", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = TopicResponse.class))
            }),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content)})
    @PostMapping("/new")
    public ResponseEntity<TopicResponse> createTopic(
            @RequestBody @Valid CreateTopicDTO createTopicDTO
    ) {
        var topic = topicService.createTopic(createTopicDTO.name());

        URI location = UriComponentsBuilder
                .fromPath("/api/topics/{topicId}")
                .buildAndExpand(topic.getId())
                .toUri();

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .location(location)
                .body(TopicMapper.INSTANCE.toTopicResponse(topic));
    }

    @Operation(summary = "Get all topics")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = TopicResponse.class))
            }),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content)})
    @GetMapping("/")
    public ResponseEntity<List<TopicResponse>> getAllTopics() {
        List<TopicDomain> topics = topicService.getAllTopics();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(TopicMapper.INSTANCE.toTopicResponses(topics));
    }
}
