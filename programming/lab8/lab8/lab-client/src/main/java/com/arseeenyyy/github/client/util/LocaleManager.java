package com.arseeenyyy.github.client.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Date;
import java.util.Locale;

public class LocaleManager {
    private static Locale locale = Locale.getDefault();

    public static Locale getLocale() {
        return locale;
    }
    public static void setLocale(Locale locale) {
        LocaleManager.locale = locale;
    }
    public static String getFormattedDate(LocalDateTime dateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.LONG).withLocale(locale);
        return dateTime.format(formatter);
    }
}
