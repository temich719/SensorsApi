package com.lab.sensors.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class RoleUserDTO {

    private String login;
    private String password;
    private String role;

}
