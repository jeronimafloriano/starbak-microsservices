package io.github.starbank.cartoes.application;

import io.github.starbank.cartoes.application.representation.CartaoDto;
import io.github.starbank.cartoes.domain.Cartao;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/cartoes")
@RequiredArgsConstructor
public class CartoesResource {

    private final CartaoService cartaoService;
    private final ClienteCartaoService clienteCartaoService;

    @GetMapping
    public String status(){
        return "ok";
    }

    @PostMapping
    public ResponseEntity cadastrar(@RequestBody CartaoDto requestDto){
        Cartao cartao = requestDto.toCartao();
        cartaoService.save(cartao);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping(params = "renda") //só vai entrar nesse endpoint quando for passado o parâmetro de renda.
    public ResponseEntity<List<Cartao>> buscarCartoesComRendaAte(@RequestParam("renda") Long renda){
        List<Cartao> cartoes = cartaoService.getCartoesRendaMenorIgual(renda);
        return ResponseEntity.ok(cartoes);
    }

    @GetMapping(params = "cpf")
    public ResponseEntity<List<CartaoDto>> buscarCartoesPorCliente(
            @RequestParam("cpf") String cpf){
        var lista = clienteCartaoService.listarCartoesPorCpf(cpf);
        var cartoes = lista.stream()
                                                .map(CartaoDto::fromClienteCartao)
                                                .collect(Collectors.toList());
        return ResponseEntity.ok(cartoes);
    }
}
