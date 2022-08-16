package com.loudsight.vwap.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A logging utility class that acts as a wrapper for an slf4j logger
 */
public class LoggingHelper {

    private final Logger logger;

    private LoggingHelper(Logger logger) {
        this.logger = logger;
    }

    public static LoggingHelper wrap(Class<?> clazz) {
        var logger = LoggerFactory.getLogger(clazz);
        return new LoggingHelper(logger);
    }

    public void trace(String log, Object... params) {
        if (logger.isTraceEnabled()) {
            logger.trace(log, params);
        }
    }

    public void logDebug(String log, Object... params) {
        if (logger.isDebugEnabled()) {
            logger.debug(log, params);
        }
    }

    public void logInfo(String log, Object... params) {
        if (logger.isInfoEnabled()) {
            logger.info(log, params);
        }
    }

    public void logWarn(String log, Object... params) {
        if (logger.isWarnEnabled()) {
            logger.warn(log, params);
        }
    }

    public void logError(String log, Object... params) {
        if (logger.isErrorEnabled()) {
            logger.error(log, params);
        }
    }
}
