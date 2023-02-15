package io.github.starbank.avaliador.domain.model;

import io.github.starbank.avaliador.application.representation.CartaoDto;
import io.github.starbank.avaliador.application.representation.ClienteDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SituacaoCliente {
    private ClienteDto cliente;
    private List<CartaoDto> cartoes;

}
