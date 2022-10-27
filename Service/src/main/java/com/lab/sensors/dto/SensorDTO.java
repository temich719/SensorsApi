package com.lab.sensors.dto;

import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class SensorDTO {

    private long id;
    @NotEmpty(message = "{com.lab.sensors.constraint.emptyName.message}")
    @NotNull(message = "{com.lab.sensors.constraint.nullName.message}")
    @Size(min = 2, max = 30)
    private String name;
    @NotEmpty
    @NotNull
    @Size(min = 2, max = 15)
    private String model;
    @NotNull
    @NotEmpty
    private String type;
    @Pattern(regexp = "-?\\d+", message = "{com.lab.sensors.constraint.rangeFrom.isNumber}")
    private String rangeFrom;
    @Pattern(regexp = "-?\\d+", message = "{com.lab.sensors.constraint.rangeTo.isNumber}")
    private String rangeTo;
    private String unit;
    @Size(max = 40)
    private String location;
    @Size(max = 200)
    private String description;

}
