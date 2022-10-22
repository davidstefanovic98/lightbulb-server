package com.lightbulb.core.logging;

public interface Logger {

    void log(java.util.logging.Level level, String s, Object... params);

    /**
     * Logs a message at the TRACE level.
     *
     * @param s      The message to log.
     * @param params The parameters to use when formatting the message.
     */
    default void trace(String s, Object... params) {
        log(Level.TRACE, s, params);
    }

    /**
     * Logs a message at the INFO level.
     *
     * @param s      The message to log.
     * @param params The parameters to use when formatting the message.
     */
    default void info(String s, Object... params) {
        log(java.util.logging.Level.INFO, s, params);
    }

    /**
     * Logs a message at the WARNING level.
     *
     * @param s      The message to log.
     * @param params The parameters to use when formatting the message.
     */
    default void warn(String s, Object... params) {
        log(java.util.logging.Level.WARNING, s, params);
    }

    /**
     * Logs a message at the DEBUG level.
     *
     * @param s      The message to log.
     * @param params The parameters to use when formatting the message.
     */
    default void debug(String s, Object... params) {
        log(Level.DEBUG, s, params);
    }

    /**
     * Logs a message at the ERROR level.
     *
     * @param s      The message to log.
     * @param params The parameters to use when formatting the message.
     */
    default void error(String s, Object... params) {
        log(Level.ERROR, s, params);
    }

}
