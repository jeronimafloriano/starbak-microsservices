package io.github.starbank.avaliador.application;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/avaliador-credito")
public class AvaliadorCreditoController {

    @GetMapping
    public String status(){
        return "ok";
    }
}
