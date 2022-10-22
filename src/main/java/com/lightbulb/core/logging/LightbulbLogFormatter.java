package com.lightbulb.core.logging;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.NoSuchElementException;
import java.util.logging.Formatter;
import java.util.logging.Level;
import java.util.logging.LogRecord;

import static com.lightbulb.core.logging.Color.*;

public class LightbulbLogFormatter extends Formatter {

    public static final String LOG_FORMAT = "{0} {1:7} - [{2:15}] {3:-41}: {4}{5}\n";
    public static final String DATE_TIME_FORMAT = "dd-MM-yyyy HH:mm:ss.SSS";
    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern(DATE_TIME_FORMAT);
    public static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat(DATE_TIME_FORMAT);
    private final Date date = new Date();

    @Override
    public String format(LogRecord record) {
        date.setTime(record.getMillis());
        String source = shortenClassName(record.getLoggerName(), 41);
        String message = formatMessage(record.getMessage(), record.getParameters());
        String throwable = getThrowable(record);
        String thread = getThreadName();
        Level level = record.getLevel();

        String levelStr;
        if (Level.SEVERE.equals(level)) {
            levelStr = red(level.getName());
        } else if (Level.WARNING.equals(level)) {
            levelStr = yellow(level.getName());
        } else if (Level.INFO.equals(level)) {
            levelStr = blue(level.getName());
        } else if (Level.CONFIG.equals(level)) {
            levelStr = green(level.getName());
        } else if (Level.FINE.equals(level) || Level.FINER.equals(level) || Level.FINEST.equals(level)) {
            levelStr = purple(level.getName());
        } else {
            levelStr = level.getName();
        }
        return formatMessage(LOG_FORMAT, SIMPLE_DATE_FORMAT.format(date), levelStr, thread, cyan(source), message, throwable);
    }

    private String getThrowable(LogRecord record) {
        if (record.getThrown() == null) {
            return "";
        }
        StringWriter sw = new StringWriter();
        try (PrintWriter pw = new PrintWriter(sw)) {
            pw.println();
            record.getThrown().printStackTrace();
        }
        return sw.toString();
    }

    private String getThreadName() {
        return (Thread.currentThread().getName() != null) ? Thread.currentThread().getName() : "";
    }

    private static String shortenClassName(String className, int length) {
        if (className.length() < length) {
            return className;
        }
        String[] parts = className.split("\\.");
        if (parts[parts.length - 2].length() == 1) {
            String join = String.join(".", parts);
            if (join.length() > length) {
                return join.substring(join.length() - length);
            }
            return join;
        }
        for (int i = 0; i < parts.length; ++i) {
            if (parts[i].length() != 1) {
                parts[i] = parts[i].substring(0, 1);
                break;
            }
        }
        return shortenClassName(String.join(".", parts), length);
    }

    private static String formatMessage(String format, Object... params) {
        if (params == null || params.length == 0) return format;
        StringBuilder builder = new StringBuilder();
        FormatIterator iter = new FormatIterator(format);
        int currParam = 0;
        try {
            while (iter.hasNext()) {
                char next = iter.next();
                int padLength = 0;
                boolean padRight = false;
                int precision = 0;
                if (next == '{' && !iter.isPeek('{')) {
                    Object toPrint;
                    if (Character.isDigit(iter.peek())) {
                        int index = Integer.parseInt(iter.eatWhile(Character::isDigit));
                        if (index >= params.length) {
                            toPrint = "";
                        } else {
                            toPrint = params[index];
                        }
                    } else {
                        toPrint = params[currParam++];
                    }
                    String toPrintStr = toPrint == null ? "null" : toPrint.toString();
                    if (iter.isPeek(':')) {
                        iter.next();
                        if (iter.isPeek('-')) {
                            padRight = true;
                            iter.next(); // skip -
                        }
                        if (Character.isDigit(iter.peek())) {
                            padLength = Integer.parseInt(iter.eatWhile(Character::isDigit));
                        }
                    }
                    if (iter.isPeek('.')) {
                        iter.next(); // skip .
                        precision = Integer.parseInt(iter.eatWhile(Character::isDigit));
                        // @Temporary do proper formatting of floats
                        toPrintStr = String.format("%." + precision + "f", toPrint);
                    }
                    int len = toPrintStr.length();
                    // @Temporary nasty hack to remove ANSI escape chars
                    // used for colored output
                    if (toPrintStr.startsWith("\u001B")) {
                        len -= 9;
                    }
                    String pad = " ".repeat(Math.max(0, padLength - len));
                    if (!padRight)
                        builder.append(pad);
                    builder.append(toPrintStr);
                    if (padRight)
                        builder.append(pad);
                    if (iter.isPeek('}'))
                        iter.next(); // skip ending }
                } else {
                    builder.append(next);
                }
            }
            return builder.toString();
        } catch (NoSuchElementException e) {
            return builder.toString();
        }
    }
}
