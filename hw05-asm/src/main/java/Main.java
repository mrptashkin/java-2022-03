public class Main {

    public static void main(String[] args) {
        TestLogging proxyDemo = Ioc.createDemo();
        proxyDemo.modifyValues(100, 200, "Param300");
        proxyDemo.changeValues(101, 201, "Param301"); //не аннотированный метод, не логируется
        proxyDemo.modifyValues(400, 500);
        proxyDemo.changeValues(401, 501); //не аннотированный метод, не логируется
        proxyDemo.modifyValues(600);
        proxyDemo.changeValues(601); //не аннотированный метод, не логируется
    }
}