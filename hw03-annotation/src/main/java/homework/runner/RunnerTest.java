package homework.runner;

import homework.annotation.After;
import homework.annotation.Before;
import homework.annotation.Test;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class RunnerTest {

    public static void run(Class<?> clazz) {
        int numOfFailedTests = 0;
        int numOfPassedTests = 0;
        List<Method> tests = getAllAnnotations(clazz, Test.class);
        List<Method> before = getAllAnnotations(clazz, Before.class);
        List<Method> after = getAllAnnotations(clazz, After.class);

        for (Method method : tests) {
            Object object = null;
            try {
                object = clazz.getConstructor().newInstance();
            } catch (NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException exc) {
                System.err.println("Creation of new object is failed with " + exc);
                          }
            try {
                for (Method beforeMethod : before) {
                    beforeMethod.invoke(object);
                }
                method.invoke(object);
                numOfPassedTests++;
            } catch (InvocationTargetException | IllegalAccessException exc) {
                numOfFailedTests++;
                System.err.println("Failed on method \"" + method + "\" with " + exc);
            } finally {
                try {
                    for (Method afterMethodLast : after) {
                        afterMethodLast.invoke(object);
                    }
                } catch (InvocationTargetException | IllegalAccessException exc) {
                    numOfFailedTests++;
                    System.err.println("Failed test for @After with " + exc);
                }
            }
        }
        System.out.printf("All tests number - %d.\nSucceed tests number - %d",numOfFailedTests+numOfPassedTests,numOfPassedTests);
    }


    private static List<Method> getAllAnnotations(Class<?> clazz, Class<? extends Annotation> annotationClass) {
        List<Method> annotations = new ArrayList<>();
        for (Method method : clazz.getDeclaredMethods()) {
            if (method.isAnnotationPresent(annotationClass)) {
                annotations.add(method);
            }
        }
        return annotations;
    }
}
