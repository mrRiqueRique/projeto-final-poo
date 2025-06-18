package projetofinal.model;


import java.time.DayOfWeek;
import java.util.Locale;
import java.util.Map;

public class DiaSemanaRepository {

    private static final Map<String, DayOfWeek> MAP_DIAS = Map.ofEntries(
        Map.entry("segunda", DayOfWeek.MONDAY),
        Map.entry("terça",   DayOfWeek.TUESDAY),
        Map.entry("quarta",  DayOfWeek.WEDNESDAY),
        Map.entry("quinta",  DayOfWeek.THURSDAY),
        Map.entry("sexta",   DayOfWeek.FRIDAY),
        Map.entry("sábado",  DayOfWeek.SATURDAY),
        Map.entry("domingo", DayOfWeek.SUNDAY)
    );

    public static DayOfWeek traduzir(String dia) {
        if (dia == null) return null;
        return MAP_DIAS.get(dia.trim().toLowerCase(Locale.ROOT));
    }
}
