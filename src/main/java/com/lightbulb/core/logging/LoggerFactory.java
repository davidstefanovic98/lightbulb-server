package com.lightbulb.core.logging;

import com.lightbulb.core.util.PropertiesResolver;

import java.util.Enumeration;
import java.util.List;
import java.util.Properties;
import java.util.logging.LogManager;

import static java.util.logging.LogManager.getLogManager;

public class LoggerFactory {
    private static final String[] FILE_PATHS = {"META-INF/logging", "logging"};
    private static final PropertiesResolver PROPERTIES_RESOLVER;
    private static final Properties properties = new Properties();

    static {
        PROPERTIES_RESOLVER = new PropertiesResolver(List.of());
        PROPERTIES_RESOLVER.resolve(FILE_PATHS, is -> {
            properties.load(is);
            is.reset();
            getLogManager().readConfiguration(is);
            is.close();
        });
    }

    private LoggerFactory() {
    }

    public static Logger getLogger(Class<?> clazz) {
        LogManager manager = LogManager.getLogManager();
        java.util.logging.Logger logger = java.util.logging.Logger.getLogger(clazz.getName());
        setLevel(logger);
        manager.addLogger(logger);
        return new LoggerDelegate(logger);
    }

    private static void setLevel(java.util.logging.Logger logger) {
        Enumeration<?> names = properties.propertyNames();
        while (names.hasMoreElements()) {
            String name = (String) names.nextElement();
            if (!name.endsWith(".level")) {
                continue;
            }
            int x = name.length() - 6;
            String loggerName = name.substring(0, x);
            if (loggerName.isBlank() || logger.getName().startsWith(loggerName)) {
                com.lightbulb.core.logging.Level.findLevel(properties.getProperty(name)).ifPresent(logger::setLevel);
            }
        }
    }

}
