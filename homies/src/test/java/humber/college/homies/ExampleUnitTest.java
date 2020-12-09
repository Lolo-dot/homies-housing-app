package humber.college.homies;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void validation_isCorrect() {
        UnitTest test1 = new UnitTest();
        assertEquals(true,test1.validation_isCorrect("Shermal"));
    }

    @Test
    public void validation_isCorrect2() {
        UnitTest test1 = new UnitTest();
        assertNotEquals(true,test1.validation_isCorrect(""));
    }

    @Test
    public void validation_isCorrect3() {
        UnitTest test1 = new UnitTest();
        assertEquals(true,test1.validation_isCorrect2("qwerty"));
    }

    @Test
    public void validation_isCorrect4() {
        UnitTest test1 = new UnitTest();
        assertNotEquals(true,test1.validation_isCorrect2("qwe"));
    }

    @Test
    public void validation_isCorrect5() {
        UnitTest test1 = new UnitTest();
        assertNotEquals(true,test1.validation_isCorrect2(""));
    }

    @Test
    public void validation_isCorrect6() {
        UnitTest test1 = new UnitTest();
        assertEquals(true,test1.validation_isCorrect3("Shermal123","Shermal123"));
    }

    @Test
    public void validation_isCorrect7() {
        UnitTest test1 = new UnitTest();
        assertNotEquals(true,test1.validation_isCorrect3("ELO5768","Shermal123"));
    }
}