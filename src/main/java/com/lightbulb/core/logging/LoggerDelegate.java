package com.lightbulb.core.logging;

import java.util.logging.Level;

public class LoggerDelegate implements Logger {

    private final java.util.logging.Logger logger;

    public LoggerDelegate(java.util.logging.Logger logger) {
        this.logger = logger;
    }

    @Override
    public void log(Level level, String s, Object... params) {
        this.logger.log(level, s, params);
    }
}
