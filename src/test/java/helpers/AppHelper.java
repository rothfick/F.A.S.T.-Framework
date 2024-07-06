package helpers;

import lombok.NonNull;
import org.reflections.Reflections;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public abstract class AppHelper {

    private static final Logger LOG = LogManager.getLogger(AppHelper.class);
    protected SeleniumElementsHelper seleniumElementsHelper;

    public AppHelper(SeleniumElementsHelper seleniumElementsHelper) {
        this.seleniumElementsHelper = seleniumElementsHelper;
    }

    /**
     * Initializes all subclasses of AppHelper found in the 'helpers' package using reflection.
     * Each helper is expected to have a constructor that accepts a SeleniumElementsHelper instance.
     *
     * @param seleniumElementsHelper an instance of SeleniumElementsHelper to be passed to helpers' constructors.
     * @return a map of helper classes to their instantiated objects.
     */
    public static Map<Class<? extends AppHelper>, AppHelper> initAppHelpers(@NonNull SeleniumElementsHelper seleniumElementsHelper) {
        Map<Class<? extends AppHelper>, AppHelper> appHelperMap = new HashMap<>();
        Reflections reflections = new Reflections("helpers");

        Set<Class<? extends AppHelper>> appHelpers = reflections.getSubTypesOf(AppHelper.class);
        for (Class<? extends AppHelper> appHelperClass : appHelpers) {
            if (!java.lang.reflect.Modifier.isAbstract(appHelperClass.getModifiers())) {
                try {
                    Constructor<? extends AppHelper> constructor = appHelperClass.getConstructor(SeleniumElementsHelper.class);
                    AppHelper helperInstance = constructor.newInstance(seleniumElementsHelper);
                    appHelperMap.put(appHelperClass, helperInstance);
                } catch (Exception e) {
                    LOG.error("Error initializing helper: " + appHelperClass.getSimpleName(), e);
                }
            }
        }
        return appHelperMap;
    }
}