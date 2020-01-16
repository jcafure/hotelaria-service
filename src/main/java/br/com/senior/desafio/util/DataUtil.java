package br.com.senior.desafio.util;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class DataUtil {

    public static LocalDate convertDateToLocalDate(Date data) {
       return data.toInstant().atZone( ZoneId.systemDefault() ).toLocalDate();
    }

    public static boolean isWeekend(LocalDate inputDate) {
        return DayOfWeek.SATURDAY.equals(inputDate.getDayOfWeek())
                || DayOfWeek.SUNDAY.equals(inputDate.getDayOfWeek());
    }

    public static boolean validaData(LocalDate date1, LocalDate date2) {
        if (date2.isBefore(date1)) {
            throw new RuntimeException("Não é permitido a data de checkout ser antes da data de checkin.");
        } else
            return true;
    }
}
