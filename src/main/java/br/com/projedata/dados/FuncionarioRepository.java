package br.com.projedata.dados;

import br.com.projedata.modelos.Funcionario;

import java.util.List;

public interface FuncionarioRepository {
    List<Funcionario> getFuncionarios();
}

