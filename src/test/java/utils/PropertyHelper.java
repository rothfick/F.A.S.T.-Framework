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
    /**
     * Caches for property files/bundles. Can be accessed from multiple test threads simultaneously.
     */
    private static Map<String, Properties> properties = new ConcurrentHashMap<>();
    private static Map<String, Map<Locale, ResourceBundle>> resourceBundles = new ConcurrentHashMap<>();

    /**
     * Constructor
     */
    private PropertyHelper() {
    }

    /**
     * Method that initializes property file cache for given class with property file with given filename.
     * @param clazz PropertyEnum class for which cache needs to be initialized; required not null
     * @param filename property file filename; required not null
     * @throws NullPointerException in case filename is incorrect
     */
    private static void initProperties(@NonNull Class clazz, @NonNull String filename) {
        try (InputStream inputStream = clazz.getClassLoader().getResourceAsStream(filename)) {
            Properties props = new Properties();
            props.load(inputStream);
            properties.put(clazz.getCanonicalName(), props);
        } catch (IOException e) {
            LOG.error("Missing property file: " + filename);
        }
    }

    /**
     * Method that fetches the value from caches and formats it with given parameters as specified by {@link MessageFormat},
     * except the single-quote character, which is escaped automatically.
     * @param clazz PropertyEnum class for which value needs to be fetched from caches; required not null
     * @param key value key in the .Properties.properties file/bundle; required not null
     * @param params parameters to be used in formatting
     * @return property value for given class and key, formatted as needed; if value for the given class or key
     * could not be found, null is returned
     */
    public static String get(@NonNull Class clazz, @NonNull String key, String... params) {
        String property = getProperty(clazz.getCanonicalName(), key);
        if (property != null) {
            if (params == null || params.length == 0) {
                return property;
            } else {
                return MessageFormat.format(property.replace("'", "''"), (Object[]) params);
            }
        }
        return null;
    }

    /**
     * Method that fetched the property value from correct cache. If the value needs to be fetched from resource bundle,
     * current user locale will be used or EN_US locale in case current user locale is invalid.
     * @param className PropertyEnum class name for which value needs to be fetched from caches; required not null
     * @param key value key in the .Properties.properties file/bundle; required not null
     * @return property value for given class and key; if value for the given class or key could not be found, null is
     * returned
     */
    private static String getProperty(@NonNull String className, @NonNull String key) {
        Properties props = properties.getOrDefault(className, null);
        if (props != null) {
            return props.getProperty(key);
        }

        return null;
    }

    /**
     * Method that initialized all the property caches for all PropertyEnum classes by utilizing reflection
     * to find all {@link PropertyFile}
     * Needs to be called once before any other public method from this class is used.
     */
    public static void loadProperties() {
        Reflections reflections = new Reflections(PropertyHelper.class.getPackage().getName());

        Set<Class<? extends Enum>> propertyEnums = reflections.getSubTypesOf(Enum.class)
                .stream().filter(helper -> helper.isAnnotationPresent(PropertyFile.class)).collect(Collectors.toSet());
        for (Class<? extends Enum> propertyEnum : propertyEnums) {
            PropertyHelper.initProperties(propertyEnum, propertyEnum.getAnnotation(PropertyFile.class).value());
        }


    }
}