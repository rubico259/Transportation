package com.rubico.transport.schedular;

import com.rubico.transport.service.DriverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;


@Component
public class CalculateBonusTask {
    @Autowired
    private DriverService driverService;

    @Scheduled(cron = "0 0 0 1 1/1 *")
    public void execute() {

        Calendar calendar = Calendar.getInstance();
        calendar.roll(Calendar.MONTH, false);
        Calendar aCalendar = Calendar.getInstance();
        aCalendar.add(Calendar.MONTH, -1);
        aCalendar.set(Calendar.DATE, 1);
        Date firstDateOfPreviousMonth = aCalendar.getTime();
        aCalendar.set(Calendar.DATE, aCalendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        Date lastDateOfPreviousMonth = aCalendar.getTime();

        //TODO: for each driver
    }

}
