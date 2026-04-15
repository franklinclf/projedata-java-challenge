package br.com.projedata.util;

import br.com.projedata.modelos.Funcionario;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public final class FormatadorSaida {

    private static final DateTimeFormatter DATA_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final NumberFormat MOEDA_FORMATTER = NumberFormat.getNumberInstance(Locale.forLanguageTag("pt-BR"));

    static {
        MOEDA_FORMATTER.setMinimumFractionDigits(2);
        MOEDA_FORMATTER.setMaximumFractionDigits(2);
    }

    private FormatadorSaida() {
    }

    public static String formatarData(LocalDate data) {
        return DATA_FORMATTER.format(data);
    }

    public static String formatarDecimal(BigDecimal valor) {
        return MOEDA_FORMATTER.format(valor);
    }

    public static String formatarFuncionario(Funcionario funcionario) {
        return "Nome: " + funcionario.getNome()
                + " | Nascimento: " + formatarData(funcionario.getDataNascimento())
                + " | Salario: " + formatarDecimal(funcionario.getSalario())
                + " | Funcao: " + funcionario.getFuncao();
    }
}

