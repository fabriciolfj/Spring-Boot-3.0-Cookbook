package com.packt.footballobs.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.availability.AvailabilityChangeEvent;
import org.springframework.boot.availability.LivenessState;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class TradingService {

    private ApplicationEventPublisher publisher;

    private static final Logger logger = LoggerFactory.getLogger(TradingService.class);

    public TradingService(ApplicationEventPublisher publisher) {
        this.publisher = publisher;
    }

    public int getPendingOrders() {
        logger.debug("ensuring that pending orders can be calculated");
        final var random = new Random();
        var result = random.nextInt();
        logger.debug(result + "pending orders found");

        return result;
    }

    public int tradeCards(int orders) {
        if (getPendingOrders() > 90) {
            logger.warn("there are more than 90 orders, this can cause the system to crash");
            AvailabilityChangeEvent.publish(publisher,
                    new Exception("there are more than 90 pending orders"), LivenessState.BROKEN);
        } else {
            logger.debug("there are more less than 90 orders, can manage it");
            AvailabilityChangeEvent.publish(publisher, new Exception("working fine"), LivenessState.CORRECT);
        }

        return orders;
    }
}
