package com.telegrambot.jd501.service;

import org.awaitility.Duration;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;

import static org.awaitility.Awaitility.await;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.verify;

// it works just with TEST-profile cron-expressions (every minute expression)
@SpringBootTest
class BotSheduleServiceTest {

    @SpyBean
    private BotSheduleService out;

    @Test
    void whenWaitTwoMinutesThenSendToVolunteerIfSomethingWrongWithReportsCats() {
        await()
                .atMost(Duration.TWO_MINUTES)
                .untilAsserted(() -> verify(out, atLeast(1)).sendToVolunteerIfSomethingWrongWithReportsCats());
    }

    @Test
    void whenWaitTwoMinutesThensendToVolunteerIfSomethingWrongWithReportsDogs() {
        await()
                .atMost(Duration.TWO_MINUTES)
                .untilAsserted(() -> verify(out, atLeast(1)).sendToVolunteerIfSomethingWrongWithReportsDogs());
    }

    @Test
    void whenWaitTwoMinutesThensendToVolunteerToDecideAboutDogsAdopter() {
        await()
                .atMost(Duration.TWO_MINUTES)
                .untilAsserted(() -> verify(out, atLeast(1)).sendToVolunteerToDecideAboutDogsAdopter());
    }

    @Test
    void whenWaitTwoMinutesThensendToVolunteerToDecideAboutCatsAdopter() {
        await()
                .atMost(Duration.TWO_MINUTES)
                .untilAsserted(() -> verify(out, atLeast(1)).sendToVolunteerToDecideAboutCatsAdopter());
    }

    @Test
    void sendToUsersReminders() {
        await()
                .atMost(Duration.TWO_MINUTES)
                .untilAsserted(() -> verify(out, atLeast(1)).sendToUsersReminders());
    }
}