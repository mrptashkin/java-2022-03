package homework.runner;

import homework.runner.exceptions.CheckSumException;
import homework.runner.exceptions.TestClassException;
import homework.annotation.After;
import homework.annotation.Before;
import homework.annotation.Test;

public class TestedClass {
    private int a, b;

    @Before
    public void initializeA() {
        a = 27;
        System.out.println("@Before initializeA()");
    }

    @Before
    public void initializeB() {
        b = 3;
        System.out.println("@Before initializeB()");
    }

    @Test
    public void checkSumAB() throws CheckSumException {
        System.out.println("@Test checkSum()");
        if (a + b != 30) {
            throw new CheckSumException("Uncorrected sum");
        }
    }

    @Test
    public void unstopTest() throws TestClassException {
        System.out.println("@Test unstopTest()");
        throw new TestClassException("Testing continue...");
    }

    @Test
    public void workTest() {
        System.out.println("Test of function workTest() is success");
    }

    @After
    public void clearA() {
        a = 0;System.out.println("@After clearA()");
    }

    @After
    public void clearB() {
        b = 0;
        System.out.println("@After clearB()");
    }
}
