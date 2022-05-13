package homework.runner;

import homework.annotation.After;
import homework.annotation.Before;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class RunnerTest {

    public static void runTest(Class<?> clazz) {
        System.out.println(runMethodsForTestAnnotation(clazz));
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
        AnnotatedMethods annotatedMethods = new AnnotatedMethods(clazz);
        for (Method method : annotatedMethods.getTestMethods()) {
            Object testObject = createObject(clazz);
            try {
                assert testObject != null;
                runMethodsForBeforeAndAfter(testObject, annotatedMethods, Before.class);
                method.invoke(testObject);
                numOfPassedTests++;
            } catch (InvocationTargetException | IllegalAccessException exc) {
                numOfFailedTests++;
                System.err.println("Failed on method \"" + method + "\" with " + exc);
            } finally {
                try {
                    assert testObject != null;
                    runMethodsForBeforeAndAfter(testObject, annotatedMethods, After.class);
                } catch (Throwable exc) {
                    System.err.println("Failed test for @After with " + exc);
                }
            }
        }
        return "All tests number - " + (numOfPassedTests + numOfFailedTests) + "\nSucceed tests number - " + numOfPassedTests;
    }

    private static void runMethodsForBeforeAndAfter(Object object, AnnotatedMethods annotatedMethods, Class<? extends Annotation> annotation) throws InvocationTargetException, IllegalAccessException {
        if (annotatedMethods.getBeforeAfter(annotation).equals("Before")) {
            for (Method method : annotatedMethods.getBeforeMethods()) {
                method.invoke(object);
            }
        } else if (annotatedMethods.getBeforeAfter(annotation).equals("After")) {
            for (Method method : annotatedMethods.getAfterMethods()) {
                method.invoke(object);
            }
        } else {
            System.out.println("Incorrect annotation");
        }
    }


}
