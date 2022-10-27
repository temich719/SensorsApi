package com.lab.sensors.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class ResponseSensorDTO {

    private long id;
    private String name;
    private String model;
    private String type;
    private String range;
    private String unit;
    private String location;
    private String description;

}
