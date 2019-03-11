package com.rubico.transport.repository;

import com.rubico.transport.domain.event.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {

    List<Event> findByType(String eventType);

    @Query(value = "select type as eventType, count(id) * 100.0 / ( select count(id) from event) as percentage from event e  group by e.type", nativeQuery = true)
    List<Statistic> findByNativeQuery();

    interface Statistic {
        String getEventType();

        long getPercentage();
    }

    @Query(
            "SELECT " +
                    " sum(" +
                    " case " +
                    " type" +
                    " when 'AccidentEvent' then -3" +
                    " when 'ParkingTicketEvent' then -2" +
                    " when 'TrafficTicketEvent' then-2" +
                    " else 0 end " +
                    ") as fine" +
                    " from " +
                    " Event e" +
                    " where " +
                    " e.occur between ?1 and ?2"
    )
    Integer calcByStartDateBetween(Date start, Date end);
}
