package ru.job4j.grabber.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;

public class SqlRuDateTimeParser implements DateTimeParser {

    private static final Map<String, String> MONTHS = Map.ofEntries(
            Map.entry("январь", "1"),
            Map.entry("февраль", "2"),
            Map.entry("март", "3"),
            Map.entry("апрель", "4"),
            Map.entry("май", "5"),
            Map.entry("июнь", "6"),
            Map.entry("июль", "7"),
            Map.entry("август", "8"),
            Map.entry("сентябрь", "9"),
            Map.entry("октябрь", "10"),
            Map.entry("ноябрь", "11"),
            Map.entry("декабрь", "12"));

    @Override
    public LocalDateTime parse(String parse) {
        LocalDate yesterday = LocalDate.now().minusDays(1L);
        LocalDate tomorrow = LocalDate.now();
        LocalDateTime ldt = null;
        String months = "";
        String[] dateTime = parse.split(", ");
        String[] date = dateTime[0].split(" ");
        String[] time = dateTime[1].split(":");
        if (date.length != 1) {
            for (var key : MONTHS.keySet()) {
                if (key.contains(date[1])) {
                    months = MONTHS.get(key);
                }
            }
            ldt = LocalDateTime.of(Integer.parseInt("20" + date[2]), Integer.parseInt(months), Integer.parseInt(date[0]), Integer.parseInt(time[0]), Integer.parseInt(time[1]));
        }
        if ("сегодня".equals(date[0])) {
            ldt = LocalDateTime.of(tomorrow.getYear(), tomorrow.getMonth(), tomorrow.getDayOfMonth(), Integer.parseInt(time[0]), Integer.parseInt(time[1]));
        } else if ("вчера".equals(date[0])) {
            ldt = LocalDateTime.of(yesterday.getYear(), yesterday.getMonth(), yesterday.getDayOfMonth(), Integer.parseInt(time[0]), Integer.parseInt(time[1]));
        }
        return ldt;
    }
}
