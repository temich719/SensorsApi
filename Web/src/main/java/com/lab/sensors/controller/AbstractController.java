package com.lab.sensors.controller;

import com.lab.sensors.answer.AnswerMessage;
import org.springframework.beans.factory.annotation.Autowired;

public class AbstractController {

    protected final AnswerMessage answerMessage;

    @Autowired
    public AbstractController(AnswerMessage answerMessage) {
        this.answerMessage = answerMessage;
    }
}
