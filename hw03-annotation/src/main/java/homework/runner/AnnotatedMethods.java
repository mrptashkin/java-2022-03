package homework.runner;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class AnnotatedMethods {
    public List<Method> beforeMethods = new ArrayList<>();
    public List<Method> afterMethods = new ArrayList<>();
    public List<Method> testMethods = new ArrayList<>();
}
