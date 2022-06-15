public interface TestLogging extends DemoInterface {

    @Log
    void modifyValues(int param1);

    @Log
    void modifyValues(int param1, int param2);

    @Log
    void modifyValues(int param1, int param2, String param3);

    void changeValues(int param1);
    void changeValues(int param1, int param2);
    void changeValues(int param1, int param2, String param3);

}