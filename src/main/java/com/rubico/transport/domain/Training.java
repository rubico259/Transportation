package com.rubico.transport.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
public class Training implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private Long id;
    @Size(max = 15)
    @NotNull
    private String title;
    @Size(max = 1000)
    private String description;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    @NotNull
    private Date startDate;
    private int maxParticipant;

    @OneToMany(cascade = CascadeType.DETACH)
    private Set<Driver> drivers;

    public void addDriver(Driver driver) {
        if (drivers != null) {
            drivers.add(driver);
        } else {
            new HashSet<>().add(driver);
        }
    }
}
