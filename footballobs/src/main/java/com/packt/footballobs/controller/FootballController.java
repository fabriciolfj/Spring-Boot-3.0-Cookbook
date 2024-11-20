package com.packt.footballobs.controller;

import java.util.List;
import java.util.Random;

import com.packt.footballobs.service.AuctionService;
import com.packt.footballobs.service.DataService;
import com.packt.footballobs.service.TradingService;
import io.micrometer.observation.Observation;
import io.micrometer.observation.ObservationRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import com.packt.footballobs.service.FileLoader;

@RestController
@RequestMapping("/football")
public class FootballController {

    private final Logger logger = LoggerFactory.getLogger(FootballController.class.getName());

    private FileLoader fileLoader;

    private final Random random = new Random();
    private final ObservationRegistry observationRegistry;
    private final DataService dataService;
    private final AuctionService auctionService;
    private final TradingService tradingService;

    public FootballController(FileLoader fileLoader, ObservationRegistry observationRegistry, DataService dataService, AuctionService auctionService,
                              TradingService tradingService){
        this.fileLoader = fileLoader;
        this.observationRegistry = observationRegistry;
        this.dataService = dataService;
        this.auctionService = auctionService;
        this.tradingService = tradingService;
    }

    @GetMapping("/pending_orders")
    public int getPendingOrders() {
        return tradingService.getPendingOrders();
    }

    @GetMapping("/trade/{orders}")
    public int getPendingOrders(@PathVariable("orders") int orders) {
        return tradingService.tradeCards(orders);
    }

    @PostMapping("/bid/{player}")
    public void addBid(@PathVariable String player, @RequestBody String bid) {
        auctionService.addBid(player, bid);
    }

    @GetMapping("/stats/{player}")
    public String getPlayerStats(@PathVariable String player) {
        return dataService.getPlayerStats(player);
    }

    @GetMapping
    public List<String> getTeams(){
        return fileLoader.getTeams();
    }

    @GetMapping("/ranking/{player}")
    public int getRanking(@PathVariable String player) {
        Observation collectionObservation = Observation.createNotStarted("collection", observationRegistry);
        collectionObservation.lowCardinalityKeyValue("player", player);
        collectionObservation.observe(() -> {
            try {
                logger.info("preparing ranking for player {}", player);
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        Observation processObservation = Observation.createNotStarted("process", observationRegistry);
        processObservation.lowCardinalityKeyValue("player", player);
        processObservation.observe(() -> {
            try {
                logger.info("simulate a data processing for player {}", player);
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        if (random.nextInt(100) > 97) {
            throw new RuntimeException("its not possible to get the ranking for player " + player +
                    " at this moment, please try again later");
        }

        return random.nextInt(1000);
    }
    
}
