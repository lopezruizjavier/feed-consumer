package com.sparta.feedconsumer.util;

import org.junit.jupiter.api.Test;

import java.lang.reflect.*;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;


/**
 * This class can be used as a base for unit test classes that test the typical utility classes in Java.
 * <p>
 * It includes a test that checks these fundamental conditions in a Utility class: 1. Constructor is private. 2. Constructor throws an
 * UnsupportedOperationException when is Instantiated. 3. Class is final. 4. Methods are static. 5. Fields are static.
 * <p>
 * To use it, simply create a unit test class and extend this, indicating in the generic parameter the class under test. For example, to test the
 * utility class called TypicalUtilityClass, the unit test should be:
 *
 * <pre>
 * {@code
 * public class TypicalUtilityClassTest extends
 * AbstractUtilityPrivateConstructorTester<TypicalUtilityClass>{...}
 * }
 * </pre>
 * <p>
 * With this, you obtain automatically test the 5 above conditions.
 * <p>
 * You can add your own tests too inside your test class. *
 *
 * @param <T> The utility class that you want to test.
 */
abstract class AbstractUtilityBaseTester<T> {
    @SuppressWarnings("unchecked")
    @Test
    void shouldTestPrivateConstructor() {
        Constructor<T> constructor;
        Class<T> persistentClass = null;
        try {
            persistentClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];

            checkFinalClass(persistentClass);

            constructor = checkConstructor(persistentClass);

            checkMethodsAreStatic(persistentClass);
            checkFieldsAreStatic(persistentClass);
            checkInstantiation(constructor, persistentClass);
        } catch (SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            assertThat("The cause of the inner exception must be UnsupportedOperationException when trying to instantiate the class " + persistentClass.getName() + e.getClass().getName(),
                    UnsupportedOperationException.class.getName(), is(equalTo(e.getCause().getClass().getName())));
        }
    }

    private void checkFinalClass(final Class<T> persistentClass) {
        assertThat("The class " + persistentClass.getName() + " must be final", Modifier.isFinal(persistentClass.getModifiers()), is(true));
    }

    @SuppressWarnings("unchecked")
    private Constructor<T> checkConstructor(final Class<T> persistentClass) {
        Constructor<T> constructor;
        constructor = (Constructor<T>) persistentClass.getDeclaredConstructors()[0];
        assertThat("The constructor of the class " + persistentClass.getName() + ", must be private", Modifier.isPrivate(constructor.getModifiers()), is(true));
        constructor.setAccessible(true);
        return constructor;
    }

    private void checkInstantiation(final Constructor<T> constructor, final Class<T> persistentClass) throws InstantiationException, IllegalAccessException, InvocationTargetException {
        constructor.newInstance();
        assertThat("No exception is thrown when instantiating " + persistentClass.getName() + " a UnsupportedOperationException should be thrown", false);
    }

    private void checkMethodsAreStatic(final Class<T> persistentClass) {
        final Method[] methods = persistentClass.getDeclaredMethods();
        for (Method method : methods) {
            assertThat("Method " + method.getName() + " of the class " + persistentClass.getName() + " is NOT STATIC. All methods must be static",
                    Modifier.isStatic(method.getModifiers()), is(true));
        }
    }

    private void checkFieldsAreStatic(final Class<T> persistentClass) {
        final Field[] fields = persistentClass.getDeclaredFields();
        for (Field field : fields) {
            assertThat("Field " + field.getName() + " of the class " + persistentClass.getName() + " is NOT STATIC. All methods must be static",
                    Modifier.isStatic(field.getModifiers()), is(true));
        }
    }
}
