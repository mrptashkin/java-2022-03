package homework.runner;

import homework.annotation.After;
import homework.annotation.Before;
import homework.annotation.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class RunnerTest {

    public static void runTest(Class<?> clazz) {
        System.out.println(runMethodsForTestAnnotation(clazz));
    }


    private static AnnotatedMethods getAllAnnotatedMethods(Class<?> clazz) {
        AnnotatedMethods annotatedMethods = new AnnotatedMethods();
        for (Method method : clazz.getDeclaredMethods()) {
            if (method.isAnnotationPresent(Before.class)) {
                annotatedMethods.beforeMethods.add(method);
            } else if (method.isAnnotationPresent(Test.class)) {
                annotatedMethods.testMethods.add(method);
            } else if (method.isAnnotationPresent(After.class)) {
                annotatedMethods.afterMethods.add(method);
            }
        }
        return annotatedMethods;
    }

    private static Object createObject(Class<?> clazz) {
        try {
            return clazz.getConstructor().newInstance();
        } catch (NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException exc) {
            System.err.println("Creation of new object is failed with " + exc);
            return null;
        }
    }

    private static String runMethodsForTestAnnotation(Class<?> clazz) {
        int numOfPassedTests = 0;
        int numOfFailedTests = 0;
        AnnotatedMethods annotatedMethods = getAllAnnotatedMethods(clazz);
        for (Method method : annotatedMethods.testMethods) {
            Object testObject = createObject(clazz);
            try {
                assert testObject != null;
                runMethodsForBefore(testObject, annotatedMethods);
                method.invoke(testObject);
                numOfPassedTests++;
            } catch (InvocationTargetException | IllegalAccessException exc) {
                numOfFailedTests++;
                System.err.println("Failed on method \"" + method + "\" with " + exc);
            } finally {
                try {
                    assert testObject != null;
                    runMethodsForAfter(testObject, annotatedMethods);
                } catch (Throwable exc) {
                    System.err.println("Failed test for @After with " + exc);
                }
            }
        }
        return "All tests number - " + (numOfPassedTests + numOfFailedTests) + "\nSucceed tests number - " + numOfPassedTests;
    }

    private static void runMethodsForBefore(Object object, AnnotatedMethods annotatedMethods) throws InvocationTargetException, IllegalAccessException {
        for (Method method : annotatedMethods.beforeMethods) {
            method.invoke(object);
        }
    }

    private static void runMethodsForAfter(Object object, AnnotatedMethods annotatedMethods) throws InvocationTargetException, IllegalAccessException {
        for (Method method : annotatedMethods.afterMethods) {
            method.invoke(object);
        }
    }

}
