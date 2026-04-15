package br.com.projedata.dados;


import br.com.projedata.modelos.Funcionario;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Optional;

public class RepositorioDados {

    @Getter
    private static final RepositorioDados instancia = new RepositorioDados();
    private final BancoFuncionarios funcionarios = BancoFuncionarios.getInstancia();

    private RepositorioDados() {}

    public ArrayList<Funcionario> getFuncionarios() {
        return funcionarios.getAll();
    }
}
