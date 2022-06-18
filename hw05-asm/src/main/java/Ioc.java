import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;

public class Ioc {

    private Ioc() {
    }

    public static TestLogging createDemo() {
        var demo = new Demo();
        return createProxyDemoLogging(demo);
    }

    private static TestLogging createProxyDemoLogging(Demo demo) {
        var handler = new DemoInvocationHandler(demo);
        Class<?>[] interfaces = demo.getClass().getInterfaces();
        var demoClassLoader = demo.getClass().getClassLoader();
        return (TestLogging) Proxy.newProxyInstance(demoClassLoader, interfaces, handler);
    }

    private static class DemoInvocationHandler implements InvocationHandler {
        private final TestLogging demo;
        ArrayList<String> methods = new ArrayList<>();

        DemoInvocationHandler(TestLogging demo) {
            this.demo = demo;
            for (Method method : demo.getClass().getDeclaredMethods()) {
                if (method.isAnnotationPresent(Log.class))
                {
                    methods.add(method.getName());
                }
            }
        }


        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            if (methods.contains(method.getName())){
            System.out.println(buildLogMessage(method, args));}
            return method.invoke(demo, args);
        }

        private String buildLogMessage(Method method, Object[] args) {
            var stringBuilder = new StringBuilder(0);
            stringBuilder.append("executed method: ").append(method.getName());
            if (args != null) {
                if (args.length == 1) {
                    stringBuilder.append(", param1: ").append(args[0]);
                } else if (args.length > 1) {
                    for (int i = 0; i < args.length; i++) {
                        stringBuilder.append(", param").append(i + 1).append(": ").append(args[i]);
                    }
                }
            }
            return stringBuilder.toString();
        }
    }
}