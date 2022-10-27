package com.lab.sensors.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "sensors")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Sensor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String model;
    private String type;
    @Column(name = "range_from_to")
    private String range;
    private String unit;
    private String location;
    private String description;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Sensor sensor = (Sensor) o;

        if (id != sensor.id) return false;
        if (!Objects.equals(name, sensor.name)) return false;
        if (!Objects.equals(model, sensor.model)) return false;
        if (!Objects.equals(type, sensor.type)) return false;
        if (!Objects.equals(range, sensor.range)) return false;
        if (!Objects.equals(unit, sensor.unit)) return false;
        if (!Objects.equals(location, sensor.location)) return false;
        return Objects.equals(description, sensor.description);
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (model != null ? model.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (range != null ? range.hashCode() : 0);
        result = 31 * result + (unit != null ? unit.hashCode() : 0);
        result = 31 * result + (location != null ? location.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        return result;
    }

}
