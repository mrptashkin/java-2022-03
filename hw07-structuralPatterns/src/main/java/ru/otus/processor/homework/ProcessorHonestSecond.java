package ru.otus.processor.homework;

import ru.otus.model.Message;
import ru.otus.processor.Processor;

public class ProcessorHonestSecond implements Processor {

    private final TimeProvider timeProvider;

    public ProcessorHonestSecond(TimeProvider timeProvider) {
        this.timeProvider = timeProvider;
    }


    @Override
    public Message process(Message message) {
        var currentSecond = timeProvider.getTime().getSecond();
        if (currentSecond % 2 == 0) {
            throw new IllegalStateException("Нечетная секунда");
        }
        return message;
    }
}
