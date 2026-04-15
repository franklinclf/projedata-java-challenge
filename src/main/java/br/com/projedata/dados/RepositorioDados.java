package br.com.projedata.dados;
import br.com.projedata.modelos.Funcionario;

import java.util.List;

public class RepositorioDados implements FuncionarioRepository {

    private final BancoFuncionarios funcionarios;

    public RepositorioDados(BancoFuncionarios funcionarios) {
        this.funcionarios = funcionarios;
    }

    @Override
    public List<Funcionario> getFuncionarios() {
        return funcionarios.getAll();
    }
}
