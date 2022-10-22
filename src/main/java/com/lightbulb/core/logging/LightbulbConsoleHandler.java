package com.lightbulb.core.logging;

import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.StreamHandler;

public class LightbulbConsoleHandler extends StreamHandler {

    public LightbulbConsoleHandler() {
        super(System.out, new LightbulbLogFormatter());
        setLevel(Level.ALL);
    }

    @Override
    public synchronized void publish(LogRecord record) {
        super.publish(record);
        flush();
    }

    @Override
    public synchronized void close() throws SecurityException {
        flush();
    }
}
