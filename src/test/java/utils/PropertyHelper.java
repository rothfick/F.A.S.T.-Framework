package utils;

import lombok.NonNull;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.reflections.Reflections;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Modifier;
import java.text.MessageFormat;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public final class PropertyHelper {
    private static final Logger LOG = LogManager.getLogger(PropertyHelper.class);
    private static final Map<String, Properties> properties = new ConcurrentHashMap<>();

    private PropertyHelper() { }

    private static void initProperties(@NonNull Class<?> clazz, @NonNull String filename) {
        try (InputStream inputStream = clazz.getClassLoader().getResourceAsStream(filename)) {
            if (inputStream != null) {
                Properties props = new Properties();
                props.load(inputStream);
                properties.put(clazz.getCanonicalName(), props);
            } else {
                LOG.error("Property file '" + filename + "' not found for class " + clazz.getCanonicalName());
            }
        } catch (IOException e) {
            LOG.error("Error loading property file: " + filename, e);
        }
    }

    public static String get(@NonNull Class<?> clazz, @NonNull String key, String... params) {
        String property = getProperty(clazz.getCanonicalName(), key);
        return property != null ? format(property, params) : null;
    }

    private static String getProperty(@NonNull String className, @NonNull String key) {
        Properties props = properties.get(className);
        return props != null ? props.getProperty(key) : null;
    }

    public static void loadProperties() {
        Reflections reflections = new Reflections("your.package.name.for.enums"); // Adjust the package name
        Set<Class<?>> propertyEnums = reflections.getTypesAnnotatedWith(PropertyFile.class);
        propertyEnums.forEach(enumClass -> {
            if (!Modifier.isAbstract(enumClass.getModifiers())) {
                String filename = enumClass.getAnnotation(PropertyFile.class).value();
                initProperties(enumClass, filename);
            }
        });
    }

    private static String format(String property, String[] params) {
        return params.length > 0 ? MessageFormat.format(property.replace("'", "''"), (Object[]) params) : property;
    }
}