package com.mobica.rnd.parking.parkingbe.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


@NoArgsConstructor
@AllArgsConstructor
@Data
@Document
public class Parking {

    @Id
    private String id;

    @NotEmpty(message = "{msg.validation.parking.name.notempty}")
    @Size(max = 100, message = "{msg.validation.parking.name.size}")
    @NotNull(message = "{msg.validation.parking.name.notnull}")
    private String name;

    @NotNull(message = "{msg.validation.parking.capacity.notnull}")
    @Max(value = 99999, message = "{msg.validation.parking.capacity.max}")
    private Long capacity;


    public Parking(String name, Long capacity) {
        this.name = name;
        this.capacity = capacity;

    }
    public Parking(String name) {
        this.name = name;
    }
}
