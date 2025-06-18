package projetofinal.model;


import java.time.DayOfWeek;
import java.util.Map;

public class DiaSemanaRepository {

    private static final Map<String, DayOfWeek> MAP_DIAS = Map.ofEntries(
        Map.entry("SEGUNDA", DayOfWeek.MONDAY),
        Map.entry("SEGUNDA‑FEIRA", DayOfWeek.MONDAY),
        Map.entry("TERÇA", DayOfWeek.TUESDAY),
        Map.entry("TERÇA‑FEIRA", DayOfWeek.TUESDAY),
        Map.entry("QUARTA", DayOfWeek.WEDNESDAY),
        Map.entry("QUARTA‑FEIRA", DayOfWeek.WEDNESDAY),
        Map.entry("QUINTA", DayOfWeek.THURSDAY),
        Map.entry("QUINTA‑FEIRA", DayOfWeek.THURSDAY),
        Map.entry("SEXTA", DayOfWeek.FRIDAY),
        Map.entry("SEXTA‑FEIRA", DayOfWeek.FRIDAY),
        Map.entry("SÁBADO", DayOfWeek.SATURDAY),
        Map.entry("DOMINGO", DayOfWeek.SUNDAY)
    );

    public static DayOfWeek traduzir(String dia) {
        if (dia == null) return null;
        return MAP_DIAS.get(dia.trim().toUpperCase());
    }
    
}
