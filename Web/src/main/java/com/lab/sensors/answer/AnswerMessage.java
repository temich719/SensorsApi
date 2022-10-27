package com.lab.sensors.answer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@EqualsAndHashCode
@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class AnswerMessage {

    private String message;
    private String code;
    private String status;

}
