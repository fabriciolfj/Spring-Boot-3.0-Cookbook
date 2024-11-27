package com.opackt.footballnative;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping( "/teams")
public class TeamController {

    @GetMapping
    public List<String> getTemas() {
        return List.of("spain", "zambia", "Brasil");
    }
}
