package br.com.projedata.servicos;

import br.com.projedata.dados.FuncionarioRepository;
import br.com.projedata.modelos.Funcionario;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class ProcessoSeletivoService {

    private final List<Funcionario> funcionarios;

    public ProcessoSeletivoService(FuncionarioRepository repositorio) {
        this.funcionarios = repositorio.getFuncionarios().stream()
                .map(this::clonarFuncionario)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public List<Funcionario> listarFuncionarios() {
        return new ArrayList<>(funcionarios);
    }

    public boolean removerFuncionarioPorNome(String nome) {
        return funcionarios.removeIf(funcionario -> funcionario.getNome().equalsIgnoreCase(nome));
    }

    public void aplicarAumento(BigDecimal percentual) {
        BigDecimal fator = BigDecimal.ONE.add(percentual);
        funcionarios.forEach(funcionario -> funcionario.setSalario(funcionario.getSalario()
                .multiply(fator)
                .setScale(2, RoundingMode.HALF_UP)));
    }

    public Map<String, List<Funcionario>> agruparPorFuncao() {
        return funcionarios.stream()
                .collect(Collectors.groupingBy(Funcionario::getFuncao, LinkedHashMap::new, Collectors.toList()));
    }

    public List<Funcionario> filtrarAniversariantesPorMeses(Set<Integer> meses) {
        return funcionarios.stream()
                .filter(funcionario -> meses.contains(funcionario.getDataNascimento().getMonthValue()))
                .sorted(Comparator.comparing(Funcionario::getNome))
                .toList();
    }

    public Optional<FuncionarioComIdade> obterFuncionarioMaisVelho(LocalDate dataReferencia) {
        return funcionarios.stream()
                .min(Comparator.comparing(Funcionario::getDataNascimento))
                .map(funcionario -> new FuncionarioComIdade(
                        funcionario.getNome(),
                        Period.between(funcionario.getDataNascimento(), dataReferencia).getYears()));
    }

    public List<Funcionario> listarFuncionariosPorNome() {
        return funcionarios.stream()
                .sorted(Comparator.comparing(Funcionario::getNome, String.CASE_INSENSITIVE_ORDER))
                .toList();
    }

    public BigDecimal calcularTotalSalarios() {
        return funcionarios.stream()
                .map(Funcionario::getSalario)
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .setScale(2, RoundingMode.HALF_UP);
    }

    public Map<String, BigDecimal> calcularSalariosMinimos(BigDecimal salarioMinimo) {
        return listarFuncionariosPorNome().stream()
                .collect(Collectors.toMap(
                        Funcionario::getNome,
                        funcionario -> funcionario.getSalario().divide(salarioMinimo, 2, RoundingMode.HALF_UP),
                        (a, b) -> a,
                        LinkedHashMap::new));
    }

    private Funcionario clonarFuncionario(Funcionario funcionario) {
        return funcionario.toBuilder().build();
    }

    public record FuncionarioComIdade(String nome, int idade) {}
}

