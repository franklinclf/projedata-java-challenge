package br.com.projedata.servicos;

import br.com.projedata.dados.BancoFuncionarios;
import br.com.projedata.dados.FuncionarioRepository;
import br.com.projedata.dados.RepositorioDados;
import br.com.projedata.modelos.Funcionario;
import br.com.projedata.util.FormatadorSaida;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class ProcessoSeletivoServiceTest {

    private ProcessoSeletivoService service;

    @BeforeEach
    void setUp() {
        FuncionarioRepository repositorio = new RepositorioDados(new BancoFuncionarios());
        service = new ProcessoSeletivoService(repositorio);
        service.removerFuncionarioPorNome("Joao");
        service.removerFuncionarioPorNome("João");
    }

    @Test
    void deveRemoverJoaoDaLista() {
        List<String> nomes = service.listarFuncionarios().stream().map(Funcionario::getNome).toList();
        assertFalse(nomes.contains("João"));
        assertEquals(9, nomes.size());
    }

    @Test
    void deveAplicarAumentoDeDezPorCento() {
        service.aplicarAumento(new BigDecimal("0.10"));

        BigDecimal salarioMaria = service.listarFuncionarios().stream()
                .filter(funcionario -> funcionario.getNome().equals("Maria"))
                .findFirst()
                .orElseThrow()
                .getSalario();

        assertEquals(new BigDecimal("2210.38"), salarioMaria);
    }

    @Test
    void deveAgruparFuncionariosPorFuncao() {
        Map<String, List<Funcionario>> agrupado = service.agruparPorFuncao();

        assertEquals(2, agrupado.get("Operador").size());
        assertEquals(2, agrupado.get("Gerente").size());
        assertEquals(1, agrupado.get("Diretor").size());
    }

    @Test
    void deveRetornarAniversariantesDosMesesDezEDoze() {
        List<Funcionario> aniversariantes = service.filtrarAniversariantesPorMeses(Set.of(10, 12));

        assertEquals(2, aniversariantes.size());
        assertTrue(aniversariantes.stream().anyMatch(funcionario -> funcionario.getNome().equals("Maria")));
        assertTrue(aniversariantes.stream().anyMatch(funcionario -> funcionario.getNome().equals("Miguel")));
    }

    @Test
    void deveIdentificarFuncionarioMaisVelho() {
        ProcessoSeletivoService.FuncionarioComIdade maisVelho = service
                .obterFuncionarioMaisVelho(LocalDate.of(2026, 4, 15))
                .orElseThrow();

        assertEquals("Caio", maisVelho.nome());
        assertEquals(64, maisVelho.idade());
    }

    @Test
    void deveOrdenarFuncionariosPorNome() {
        List<String> nomes = service.listarFuncionariosPorNome().stream()
                .map(Funcionario::getNome)
                .toList();

        assertEquals("Alice", nomes.getFirst());
        assertEquals("Miguel", nomes.getLast());
    }

    @Test
    void deveCalcularTotalSalariosEAEquivalenciaEmSalarioMinimo() {
        service.aplicarAumento(new BigDecimal("0.10"));

        assertEquals(new BigDecimal("50906.82"), service.calcularTotalSalarios());

        Map<String, BigDecimal> salariosMinimos = service.calcularSalariosMinimos(new BigDecimal("1212.00"));
        assertEquals(new BigDecimal("17.35"), salariosMinimos.get("Miguel"));
    }

    @Test
    void deveFormatarDataEValorNoPadraoBrasileiro() {
        assertEquals("18/10/2000", FormatadorSaida.formatarData(LocalDate.of(2000, 10, 18)));
        assertEquals("2.009,44", FormatadorSaida.formatarDecimal(new BigDecimal("2009.44")));
    }
}

