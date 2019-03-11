package com.rubico.transport.rest;


import com.rubico.transport.domain.event.Event;
import com.rubico.transport.service.EventService;
import com.rubico.transport.service.dto.EventStatisticsDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping("/api")
public class EventResource {

    private final Logger log = LoggerFactory.getLogger(EventResource.class);
    private final EventService eventService;

    @Autowired
    public EventResource(EventService eventService) {
        this.eventService = eventService;
    }

    @PostMapping("/events")
    public ResponseEntity<Event> addEvent(@Valid @RequestBody Event event) throws Exception {
        if (event.getId() != null) {
            throw new Exception("A new event cannot already have an ID");
        }
        //log.trace("REST request to add event with the type: {}", event.getType());
        Event result = eventService.save(event);
        if (result == null) {
            throw new EntityNotFoundException();
        }
        return ResponseEntity.ok()
                .body(result);
    }

    @GetMapping("/events/{eventType}")
    public ResponseEntity<List<Event>> showEventsByType(@NotNull @PathVariable String eventType) {
        //log.trace("REST request to show all events by a given type: {}", eventType);
        List<Event> events = eventService.getEventsByType(eventType);
        return ResponseEntity.ok(events);
    }

    @GetMapping("/events")
    public ResponseEntity<List<EventStatisticsDTO>> showEventStatistic() {
        //log.trace("REST request to show event statistics");
        List<EventStatisticsDTO> statisticsDTO = eventService.getStatistics();
        return ResponseEntity.ok(statisticsDTO);
    }
}
