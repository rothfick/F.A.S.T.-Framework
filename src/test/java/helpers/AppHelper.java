package helpers;

import lombok.NonNull;
import org.reflections.Reflections;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public abstract class AppHelper {

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
    public static Map<Class, AppHelper> initAppHelpers(@NonNull SeleniumElementsHelper seleniumElementsHelper) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Map<Class, AppHelper> appHelperMap = new HashMap<>();
        Reflections reflections = new Reflections(AppHelper.class.getPackage().getName());
        Set<Class<? extends AppHelper>> appHelpers = reflections.getSubTypesOf(AppHelper.class);
        for (Class<? extends AppHelper> appHelper : appHelpers) {
            if (!Modifier.isAbstract(appHelper.getModifiers())) {
                Constructor<? extends AppHelper> constructor = appHelper.getConstructor(
                        SeleniumElementsHelper.class
                );
                appHelperMap.put(appHelper, constructor.newInstance(seleniumElementsHelper));
            }
        }
        return appHelperMap;
    }
}