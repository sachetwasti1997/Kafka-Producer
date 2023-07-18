package com.sachet.libraryeventsproducer.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sachet.libraryeventsproducer.domain.LibraryEvent;
import com.sachet.libraryeventsproducer.error.IdNotPresentException;
import com.sachet.libraryeventsproducer.producer.LibraryEventsProducer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class LibraryEventController {

    private final LibraryEventsProducer libraryEventsProducer;

    public LibraryEventController(LibraryEventsProducer libraryEventsProducer) {
        this.libraryEventsProducer = libraryEventsProducer;
    }

    @PostMapping("/v1/libraryEvent")
    public ResponseEntity<LibraryEvent> produceEvent(
            @RequestBody LibraryEvent libraryEvent
    )throws JsonProcessingException {
        log.info("LibraryEvent: {}", libraryEvent);
        libraryEventsProducer.sendLibraryEvents(libraryEvent);
        return ResponseEntity.status(HttpStatus.OK).body(libraryEvent);
    }

    @PutMapping("/v1/libraryEvent")
    public ResponseEntity<LibraryEvent> updateProductEvent(
            @RequestBody LibraryEvent libraryEvent
    )throws JsonProcessingException {
        if (libraryEvent.libraryEventId() == null) {
            throw new IdNotPresentException("Id required", HttpStatus.BAD_REQUEST);
        }
        log.info("LibraryEvent: {}", libraryEvent);
        libraryEventsProducer.sendLibraryEvents(libraryEvent);
        return ResponseEntity.status(HttpStatus.OK).body(libraryEvent);
    }

}
