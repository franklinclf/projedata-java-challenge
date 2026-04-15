package br.com.projedata.modelos;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@SuperBuilder(toBuilder = true)
public class Funcionario extends Pessoa {
    private BigDecimal salario;
    private String funcao;
}
