
public class Demo implements DemoInterface, TestLogging {
    private int param1;
    private int param2;
    private String param3;

    public Demo() {
    }


    public void setParam3(String param3) {
        this.param3 = param3;
    }

    public void setParam1(int param1) {
        this.param1 = param1;
    }

    public void setParam2(int param2) {
        this.param2 = param2;
    }

    @Override
    public void modifyValues(int param1) {
        setParam1(param1);
    }

    @Override
    public void modifyValues(int param1, int param2) {
        setParam1(param1);
        setParam2(param2);
    }

    @Override
    public void modifyValues(int param1, int param2, String param3) {
        setParam1(param1);
        setParam2(param2);
        setParam3(param3);
    }

    @Override
    public String toString() {
        return "Param3: " + param3;
    }

    @Override
    public void changeValues(int param1, int param2, String param3) {

    }
    @Override
    public void changeValues(int param1, int param2) {

    }
    @Override
    public void changeValues(int param1) {

    }
}
