package com.packt.footballobs.controller;

import java.util.List;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.packt.footballobs.service.FileLoader;

@RestController
@RequestMapping("/football")
public class FootballController {

    private final Logger logger = LoggerFactory.getLogger(FootballController.class.getName());

    private FileLoader fileLoader;

    private final Random random = new Random();

    public FootballController(FileLoader fileLoader){
        this.fileLoader = fileLoader;
    }

    @GetMapping
    public List<String> getTeams(){
        return fileLoader.getTeams();
    }

    @GetMapping("/ranking/{player}")
    public int getRanking(@PathVariable String player) {
        logger.info("preparing ranking for player {}", player);

        if (random.nextInt(100) > 97) {
            throw new RuntimeException("its not possible to get the ranking for player " + player +
                    " at this moment, please try again later");
        }

        return random.nextInt(1000);
    }
    
}
