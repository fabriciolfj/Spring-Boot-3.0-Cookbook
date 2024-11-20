package com.packt.footballobs.service;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import io.micrometer.observation.annotation.Observed;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

//@Observed(name = "football.auction")
@Service
public class AuctionService {

    private Map<String, String> bids = new ConcurrentHashMap<>();
    private Random random = new Random();
    private Counter bidReceivedCounter;
    private Timer bidDuration;

    public AuctionService(final MeterRegistry meterRegistry) {
        meterRegistry.gauge("football.bids.pending", bids, Map::size);
        this.bidReceivedCounter = meterRegistry.counter("football.bids.received");
        this.bidDuration = meterRegistry.timer("football.bids.duration");
    }

    public void addBid(String player, String bid) {
        bids.put(player, bid);

        try {
            Thread.sleep(random.nextInt(20));
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        //bids.remove(player);
        bidDuration.record(() -> {
           bids.put(player, bid);
           bidReceivedCounter.increment();

            try {
                Thread.sleep(random.nextInt(20));
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            //bids.remove(player);
        });

    }

}
