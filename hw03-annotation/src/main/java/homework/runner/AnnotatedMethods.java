package homework.runner;

import homework.annotation.After;
import homework.annotation.Before;
import homework.annotation.Test;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AnnotatedMethods {
    private final List<Method> beforeMethods = new ArrayList<>();
    private final List<Method> afterMethods = new ArrayList<>();
    private final List<Method> testMethods = new ArrayList<>();
private final Map<Class<? extends Annotation>, String> beforeAfter = new HashMap<>();

    public AnnotatedMethods(Class<?> clazz) {
        for (Method method : clazz.getDeclaredMethods()) {
            if (method.isAnnotationPresent(Before.class)) {
                beforeMethods.add(method);
            } else if (method.isAnnotationPresent(Test.class)) {
                testMethods.add(method);
            } else if (method.isAnnotationPresent(After.class)) {
                afterMethods.add(method);
            }
        }
        beforeAfter.put(Before.class,"Before");
        beforeAfter.put(After.class,"After");
    }


    public String getBeforeAfter(Class<? extends Annotation> annotation) {
        return beforeAfter.get(annotation);
    }

    public List<Method> getBeforeMethods() {
        return beforeMethods;
    }

    public List<Method> getAfterMethods() {
        return afterMethods;
    }

    public List<Method> getTestMethods() {
        return testMethods;
    }
}
