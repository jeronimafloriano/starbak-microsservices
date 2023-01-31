package io.github.starbank.cartoes.application;

import io.github.starbank.cartoes.domain.ClienteCartao;
import io.github.starbank.cartoes.infra.repository.ClienteCartaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClienteCartaoService {

    private final ClienteCartaoRepository repository;

    public List<ClienteCartao> listarCartoesPorCpf(String cpf){
        return repository.findByCpf(cpf);
    }
}
