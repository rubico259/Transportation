package com.rubico.transport.service.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EventStatisticsDTO {

    private String eventType;
    private long percentage;

    public EventStatisticsDTO(String eventType, long percentage) {
        this.eventType = eventType;
        this.percentage = percentage;
    }
}
