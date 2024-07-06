package utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ThreadLogger {

    // Metoda, która zwraca logger Log4j2 bez żadnych dodatkowych opakowań.
    public static Logger getLogger(Class<?> clazz) {
        return LogManager.getLogger(clazz);
    }
}