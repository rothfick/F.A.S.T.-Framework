package utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ThreadLogger {

    // ThreadLocal logger for each thread
    private static final ThreadLocal<Logger> threadLocalLogger = ThreadLocal.withInitial(() -> {
        // Initialize logger for current thread
        String threadName = Thread.currentThread().getName();
        return LogManager.getLogger(threadName);
    });

    // Private constructor to prevent instantiation
    private ThreadLogger() {
    }

    // Method to get logger for current thread
    public static ThreadLogger getLogger(Class<?> clazz) {
        return new ThreadLogger();
    }

    // Log methods
    public void info(String message) {
        threadLocalLogger.get().info(message);
    }

    public void error(String message) {
        threadLocalLogger.get().error(message);
    }

    // Add more log methods as needed (e.g., debug, warn)

    // Example of logging an exception
    public void error(String message, Throwable throwable) {
        threadLocalLogger.get().error(message, throwable);
    }
}
