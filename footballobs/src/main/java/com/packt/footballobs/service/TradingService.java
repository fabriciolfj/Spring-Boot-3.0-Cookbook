package com.packt.footballobs.service;

import org.springframework.boot.availability.AvailabilityChangeEvent;
import org.springframework.boot.availability.LivenessState;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class TradingService {

    private ApplicationEventPublisher publisher;

    public TradingService(ApplicationEventPublisher publisher) {
        this.publisher = publisher;
    }

    public int getPendingOrders() {
        final var random = new Random();
        return random.nextInt();
    }

    public int tradeCards(int orders) {
        if (getPendingOrders() > 90) {
            AvailabilityChangeEvent.publish(publisher,
                    new Exception("there are more than 90 pending orders"), LivenessState.BROKEN);
        } else {
            AvailabilityChangeEvent.publish(publisher, new Exception("working fine"), LivenessState.CORRECT);
        }

        return orders;
    }
}
