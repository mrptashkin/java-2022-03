package ru.otus.hwtest;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.processor.homework.ProcessorHonestSecond;

import java.time.LocalDateTime;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.assertThrows;


class ProcessorHonestSecondExceptionTest {
    @Test
    @DisplayName("Тестируем вызов исключения в нечетную секунду")
    void honestSecondTestGood() {
        var processorHonestSecond = new ProcessorHonestSecond(
                () -> LocalDateTime.of(2022, Month.JULY, 13, 22, 47, 3)
        );
        assertThrows(IllegalStateException.class, () -> processorHonestSecond.process(null));
    }

    @Test
    @DisplayName("Тестируем вызов исключения в четную секунду")
    void honestSecondTestBad() {
        var processorHonestSecond = new ProcessorHonestSecond(
                () -> LocalDateTime.of(2022, Month.JULY, 13, 22, 47, 2)
        );
        assertThrows(IllegalStateException.class, () -> processorHonestSecond.process(null));
    }
}
