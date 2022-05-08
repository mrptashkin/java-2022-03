package homework.runner;

import homework.annotation.After;
import homework.annotation.Before;
import homework.annotation.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class RunnerTest {

    public static void run(Class<?> clazz) {
        System.out.println(runTestAnnotation(clazz));
    }


    private static List<Method> getBeforeAnnotations(Class<?> clazz) {
        List<Method> annotationsBefore = new ArrayList<>();
        for (Method method : clazz.getDeclaredMethods()) {
            if (method.isAnnotationPresent(Before.class)) {
                annotationsBefore.add(method);
            }
        }
        return annotationsBefore;
    }

    private static List<Method> getTestAnnotations(Class<?> clazz) {
        List<Method> annotationsTest = new ArrayList<>();
        for (Method method : clazz.getDeclaredMethods()) {
            if (method.isAnnotationPresent(Test.class)) {
                annotationsTest.add(method);
            }
        }
        return annotationsTest;
    }

    private static List<Method> getAfterAnnotations(Class<?> clazz) {
        List<Method> annotationsAfter = new ArrayList<>();
        for (Method method : clazz.getDeclaredMethods()) {
            if (method.isAnnotationPresent(After.class)) {
                annotationsAfter.add(method);
            }
        }
        return annotationsAfter;
    }

    private static Object createObject(Class<?> clazz) {
        try {
            return clazz.getConstructor().newInstance();
        } catch (NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException exc) {
            System.err.println("Creation of new object is failed with " + exc);
            return null;
        }
    }

    private static String runTestAnnotation(Class<?> clazz) {
        int numOfPassedTests = 0;
        int numOfFailedTests = 0;
        for (Method method : getTestAnnotations(clazz)) {
            Object testObject = createObject(clazz);
            try {
                assert testObject != null;
                runBeforeAnnotation(testObject);
                method.invoke(testObject);
                numOfPassedTests++;
            } catch (InvocationTargetException | IllegalAccessException exc) {
                numOfFailedTests++;
                System.err.println("Failed on method \"" + method + "\" with " + exc);
            } finally {
                try {
                    assert testObject != null;
                    runAfterAnnotation(testObject);
                } catch (Throwable exc) {
                    System.err.println("Failed test for @After with " + exc);
                }
            }
        }
        return "All tests number - " + (numOfPassedTests + numOfFailedTests) + "\nSucceed tests number - " + numOfPassedTests;
    }

    private static void runBeforeAnnotation(Object object) throws InvocationTargetException, IllegalAccessException {

        for (Method beforeMethod : getBeforeAnnotations(object.getClass())) {
            beforeMethod.invoke(object);
        }
    }

    private static void runAfterAnnotation(Object object) throws InvocationTargetException, IllegalAccessException {
        for (Method afterMethod : getAfterAnnotations(object.getClass())) {
            afterMethod.invoke(object);
        }
    }

}
