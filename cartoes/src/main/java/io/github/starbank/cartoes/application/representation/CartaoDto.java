package io.github.starbank.cartoes.application.representation;

import io.github.starbank.cartoes.domain.BandeiraCartao;
import io.github.starbank.cartoes.domain.Cartao;
import io.github.starbank.cartoes.domain.ClienteCartao;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartaoDto {

    private String nome;
    private BandeiraCartao bandeira;
    private BigDecimal renda;
    private BigDecimal limiteBasico;

    public CartaoDto(String nome, BandeiraCartao bandeiraCartao, BigDecimal limite) {
        this.nome = nome;
        this.bandeira = bandeiraCartao;
        this.limiteBasico = limite;
    }

    public Cartao toCartao(){
        return new Cartao(nome, bandeira, renda, limiteBasico);
    }

    public static CartaoDto fromClienteCartao(ClienteCartao model){
        return new CartaoDto(
                model.getCartao().getNome(),
                model.getCartao().getBandeiraCartao(),
                model.getLimite()
        );
    }

}
