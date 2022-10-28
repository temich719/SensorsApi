package com.lab.sensors.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class UserDTO {

    private long id;
    private String login;
    private String password;

}
