package com.rubico.transport;

import com.rubico.transport.domain.Driver;
import com.rubico.transport.domain.Training;
import com.rubico.transport.domain.Vehicle;
import com.rubico.transport.domain.event.AccidentEvent;
import com.rubico.transport.domain.event.Event;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;

@RunWith(SpringJUnit4ClassRunner.class)
@EnableAutoConfiguration
@SpringBootTest(classes = {TransportationApplication.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class E2ETests {
    public static final String LOCALHOST = "http://localhost:";
    @LocalServerPort
    int localPort;
    @Autowired
    private TestRestTemplate template;

    @Test
    public void addDriverTest() {
        ResponseEntity<Driver> result = getDriverResponseEntity();
        Assert.assertEquals(200, result.getStatusCodeValue());
    }


    @Test
    public void addNullDriverTest() {
        String uri = LOCALHOST + localPort + "/api/drivers";

        Driver driver = new Driver();
        driver.setId(123l);
        HttpEntity<Driver> request = new HttpEntity<>(driver);
        ResponseEntity<Driver> result = this.template.postForEntity(uri, request, Driver.class);

        Assert.assertEquals(500, result.getStatusCodeValue());
    }

    @Test
    public void addVehicleTest() {
        ResponseEntity<Vehicle> result = getVehicleResponseEntity();

        Assert.assertEquals(200, result.getStatusCodeValue());
    }

    @Test
    public void addEventTest() {

        String uri = LOCALHOST + localPort + "/api/events";
        Event event = new AccidentEvent();

        event.setDriver(getDriverResponseEntity().getBody());
        event.setVehicle(getVehicleResponseEntity().getBody());
        event.setOccur(new Date());
        event.setCity("Beer Sheba");
        event.setStreet("H");
        event.setType("AccidentEvent");
        HttpEntity<Event> request = new HttpEntity<>(event);
        ResponseEntity<Event> result = this.template.postForEntity(uri, request, Event.class);

        Assert.assertEquals(200, result.getStatusCodeValue());
    }

    @Test
    public void addTrainingTest() {

        String uri = LOCALHOST + localPort + "/api/training";
        Training training = new Training();
        training.setDescription("aefekjfnewjf");
        training.setStartDate(new Date());
        training.setTitle("Winter Driving");
        HttpEntity<Training> request = new HttpEntity<>(training);
        ResponseEntity<Training> result = this.template.postForEntity(uri, request, Training.class);

        Assert.assertEquals(200, result.getStatusCodeValue());
    }

    private ResponseEntity<Driver> getDriverResponseEntity() {
        String uri = LOCALHOST + localPort + "/api/drivers";
        Driver driver = new Driver();
        driver.setName("Moshe");
        HttpEntity<Driver> request = new HttpEntity<>(driver);
        return this.template.postForEntity(uri, request, Driver.class);
    }

    private ResponseEntity<Vehicle> getVehicleResponseEntity() {
        String uri = LOCALHOST + localPort + "/api/vehicle";

        Vehicle vehicle = new Vehicle();
        vehicle.setName("Mazda");
        HttpEntity<Vehicle> request = new HttpEntity<>(vehicle);
        return this.template.postForEntity(uri, request, Vehicle.class);
    }
}