package io.github.starbank.avaliador.infra.clients;

import io.github.starbank.avaliador.application.representation.ClienteDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "msclientes", path = "/clientes")
public interface ClienteResourceClient {

    @GetMapping(params = "cpf")
    ResponseEntity<ClienteDto> dadosCliente(@RequestParam("cpf") String cpf);

}
