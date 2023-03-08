package ch.duong.jmd.#APP_ABBREVIATION.utils;

import lombok.NonNull;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalUnit;
import java.util.Date;

public class Dates {

    public static final String DATE_TIME_FORMATTER = "yyyy-MM-dd HH:mm:ss";
    public static final String TIME_FORMATTER = "HH:mm";

    public static String format(LocalDateTime time) {
        return format(time, DATE_TIME_FORMATTER);
    }

    public static String format(LocalDateTime time, String format) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        return formatter.format(time);
    }

    public static final ZoneId ZURICH = ZoneId.of("Europe/Zurich");

    public static Instant toInstant(LocalDateTime d) {
        if (d == null) {
            return null;
        }

        return d.atZone(ZURICH).toInstant();
    }

    public static LocalDateTime toLocalDateTime(Instant i) {
        if (i == null) {
            return null;
        }

        return LocalDateTime.ofInstant(i, ZURICH);
    }

    public static LocalDateTime toLocalDateTime(Instant i, PeriodOfTime p) {
        if (i == null) {
            return null;
        }
        return LocalDateTime.ofInstant(i, ZURICH).with(LocalTime.of(p.hour, p.minute, p.second));
    }

    public enum PeriodOfTime {
        START(0, 0, 0), END(23, 59, 59);

        int hour;
        int minute;
        int second;

        PeriodOfTime(int hour, int minute, int second) {
            this.hour = hour;
            this.minute = minute;
            this.second = second;
        }
    }

    public static LocalDateTime toLocalDateTime(Date dateToConvert) {
        return dateToConvert.toInstant()
                .atZone(ZURICH)
                .toLocalDateTime();
    }

    public static LocalDateTime getLocalDateTime(String d, LocalDateTime... defaultDate) {
        LocalDateTime date;

        if (defaultDate != null && defaultDate.length == 1) {
            date = defaultDate[0];
        } else {
            date = LocalDateTime.now();
        }

        if (d != null) {
            try {
                date = LocalDateTime.parse(d);
            } catch (Exception ignore) {
                return date;
            }
        }
        return date;
    }

    public static LocalDateTime getLocalDateTime(DateTimeFormatter formatter, final String expression, final LocalDateTime defaultDate, final String time) {
        LocalDateTime date;
        if (!Strings.isNullOrBlank(expression)) {
            try {
                date = LocalDateTime.parse(expression + " " + time, formatter);
            } catch (Exception ignore) {
                date = defaultDate;
            }
        } else {
            date = defaultDate;
        }
        return date;
    }

    public static LocalDateTime maxIfNull(LocalDateTime d) {
        return d == null ? LocalDateTime.of(3000, 12, 31, 23, 59) : d;
    }

    public static IntervalCompareResult compareInterval(@NonNull TemporalUnit unit, @NonNull LocalDateTime start1, @NonNull LocalDateTime end1, @NonNull LocalDateTime start2, @NonNull LocalDateTime end2) {
        if (start1.isAfter(end1)) {
            throw new IllegalArgumentException("Start1 is after end1");
        }
        if (start2.isAfter(end2)) {
            throw new IllegalArgumentException("Start2 is after end2");
        }

        // Before
        if (start1.truncatedTo(unit).isBefore(start2.truncatedTo(unit))
                && end1.truncatedTo(unit).isBefore(start2.truncatedTo(unit))) {
            return IntervalCompareResult.IS_BEFORE;
        }
        // After
        if (start1.truncatedTo(unit).isAfter(start2.truncatedTo(unit))
                && end1.truncatedTo(unit).isAfter(end2.truncatedTo(unit))) {
            return IntervalCompareResult.IS_BEFORE;
        }
        // Part before inside
        if (start1.truncatedTo(unit).isBefore(start2.truncatedTo(unit))
                && end1.truncatedTo(unit).isAfter(start2.truncatedTo(unit))
                && end1.truncatedTo(unit).isBefore(end2.truncatedTo(unit))) {
            return IntervalCompareResult.IS_PART_BEFORE_INSIDE;
        }
        // Part inside after
        if (start1.truncatedTo(unit).isAfter(start2.truncatedTo(unit))
                && start1.truncatedTo(unit).isBefore(end2.truncatedTo(unit))
                && end1.truncatedTo(unit).isAfter(end2.truncatedTo(unit))) {
            return IntervalCompareResult.IS_PART_INSIDE_AFTER;
        }
        // inside
        if (start1.truncatedTo(unit).isAfter(start2.truncatedTo(unit))
                && start1.truncatedTo(unit).isBefore(end2.truncatedTo(unit))
                && end1.truncatedTo(unit).isAfter(start2.truncatedTo(unit))
                && end1.truncatedTo(unit).isBefore(end2.truncatedTo(unit))
        ) {
            return IntervalCompareResult.IS_INSIDE;
        }
        // Outside
        if (start1.truncatedTo(unit).isBefore(start2.truncatedTo(unit))
                && start1.truncatedTo(unit).isBefore(end2.truncatedTo(unit))
                && end1.truncatedTo(unit).isAfter(start2.truncatedTo(unit))
                && end1.truncatedTo(unit).isAfter(end2.truncatedTo(unit))
        ) {
            return IntervalCompareResult.IS_OUTSIDE;
        }
        return IntervalCompareResult.IS_EQUAL;
    }

    public enum IntervalCompareResult {
        IS_BEFORE,
        IS_PART_BEFORE_INSIDE,
        IS_INSIDE,
        IS_PART_INSIDE_AFTER,
        IS_AFTER,
        IS_OUTSIDE,
        IS_EQUAL
    }
}
