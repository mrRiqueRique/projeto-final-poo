package projetofinal.model;

import java.time.DayOfWeek;
import java.util.Locale;
import java.util.Map;

/**
 * Classe responsável por traduzir os dias da semana de texto para o formato `DayOfWeek`.
 * <p>
 * Esta classe utiliza um mapa estático para realizar a conversão de nomes de dias da semana
 * em português para os valores correspondentes da enumeração `DayOfWeek`.
 */
public class DiaSemanaRepository {

    private static final Map<String, DayOfWeek> MAP_DIAS = Map.ofEntries(Map.entry("segunda", DayOfWeek.MONDAY), Map.entry("terça", DayOfWeek.TUESDAY), Map.entry("quarta", DayOfWeek.WEDNESDAY), Map.entry("quinta", DayOfWeek.THURSDAY), Map.entry("sexta", DayOfWeek.FRIDAY), Map.entry("sábado", DayOfWeek.SATURDAY), Map.entry("domingo", DayOfWeek.SUNDAY));

    /**
     * Traduz o nome de um dia da semana em português para o formato `DayOfWeek`.
     *
     * @param dia O nome do dia da semana em português (ex.: "segunda", "terça").
     * @return O valor correspondente da enumeração `DayOfWeek`, ou `null` se o dia for inválido.
     */
    public static DayOfWeek traduzir(String dia) {
        if (dia == null) return null;
        return MAP_DIAS.get(dia.trim().toLowerCase(Locale.ROOT));
    }
}