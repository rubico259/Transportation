package com.rubico.transport.service;


import com.rubico.transport.domain.event.Event;
import com.rubico.transport.repository.EventRepository;
import com.rubico.transport.service.dto.EventStatisticsDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class EventService {

    private final Logger log = LoggerFactory.getLogger(EventService.class);
    private final EventRepository eventRepository;

    @Autowired
    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public Event save(Event event) {
        return eventRepository.save(event);
    }

    public List<Event> getEventsByType(String eventType) {
        //log.trace("***Start: get events by type***");
        try {
            return eventRepository.findByType(eventType);
        } catch (Exception e) {
            log.error("get events by type failed");
        }
        return null;
    }

    public List<EventStatisticsDTO> getStatistics() {
        //log.trace("***Start: calculate the percentage for each event type***");
        try {
            return eventRepository.findByNativeQuery().
                    stream().
                    map(p -> new EventStatisticsDTO(p.getEventType(), p.getPercentage())).
                    collect(Collectors.toList());
        } catch (Exception e) {
            log.error("get statistics failed");
        }
        return null;
    }
}
