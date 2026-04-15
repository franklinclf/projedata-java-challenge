package br.com.projedata;

import br.com.projedata.dados.BancoFuncionarios;
import br.com.projedata.dados.FuncionarioRepository;
import br.com.projedata.dados.RepositorioDados;
import br.com.projedata.modelos.Funcionario;
import br.com.projedata.servicos.ProcessoSeletivoService;
import br.com.projedata.util.FormatadorSaida;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Principal {

    public static void main(String[] args) {
        BancoFuncionarios banco = new BancoFuncionarios();
        FuncionarioRepository repositorio = new RepositorioDados(banco);
        ProcessoSeletivoService processo = new ProcessoSeletivoService(repositorio);

        processo.removerFuncionarioPorNome("Joao");
        processo.removerFuncionarioPorNome("João");

        imprimirSecao("3.3 - Funcionarios");
        imprimirFuncionarios(processo.listarFuncionarios());

        processo.aplicarAumento(new BigDecimal("0.10"));

        imprimirSecao("3.4 - Funcionarios com aumento de 10%");
        imprimirFuncionarios(processo.listarFuncionarios());

        Map<String, List<Funcionario>> agrupadoPorFuncao = processo.agruparPorFuncao();
        imprimirSecao("3.6 - Funcionarios agrupados por funcao");
        agrupadoPorFuncao.forEach((funcao, funcionarios) -> {
            System.out.println("Funcao: " + funcao);
            imprimirFuncionarios(funcionarios);
        });

        imprimirSecao("3.8 - Aniversariantes dos meses 10 e 12");
        imprimirFuncionarios(processo.filtrarAniversariantesPorMeses(Set.of(10, 12)));

        processo.obterFuncionarioMaisVelho(LocalDate.now()).ifPresent(maisVelho ->
                System.out.println("3.9 - Funcionario mais velho: "
                        + maisVelho.nome()
                        + " | Idade: " + maisVelho.idade()));

        imprimirSecao("3.10 - Funcionarios em ordem alfabetica");
        imprimirFuncionarios(processo.listarFuncionariosPorNome());

        System.out.println("3.11 - Total de salarios: "
                + FormatadorSaida.formatarDecimal(processo.calcularTotalSalarios()));

        imprimirSecao("3.12 - Salarios minimos por funcionario");
        BigDecimal salarioMinimo = new BigDecimal("1212.00");
        processo.calcularSalariosMinimos(salarioMinimo)
                .forEach((nome, qtdSalarios) ->
                        System.out.println(nome + " ganha " + FormatadorSaida.formatarDecimal(qtdSalarios)
                                + " salarios minimos"));
    }

    private static void imprimirSecao(String titulo) {
        System.out.println();
        System.out.println("=== " + titulo + " ===");
    }

    private static void imprimirFuncionarios(List<Funcionario> funcionarios) {
        funcionarios.stream()
                .map(FormatadorSaida::formatarFuncionario)
                .forEach(System.out::println);
    }
}

