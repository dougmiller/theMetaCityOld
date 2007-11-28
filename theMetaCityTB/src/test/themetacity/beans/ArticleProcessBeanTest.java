package themetacity.beans;

import com.themetacity.beans.ArticleProcessBean;
import org.junit.After;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;


public class ArticleProcessBeanTest {
    ArticleProcessBean articleProcessBean;

    @Before public void setup() {
        articleProcessBean = new ArticleProcessBean();
    }

    @After public void destroy() {
        articleProcessBean = null;
    }

    @Test public void queryConstructor() {
        // Best case scenario tests
        // Test no argument
        assertEquals("No argument", "SELECT * FROM articles ORDER BY articleid DESC;", articleProcessBean.constructQuery("", "", "", ""));

        // Test year is set but nothing else
        assertEquals("Year set", "SELECT * FROM articles WHERE YEAR(datetime) = 2007 ORDER BY articleid DESC;", articleProcessBean.constructQuery("2007", "", "", ""));

        // Test year and month are set but nothing else
        assertEquals("Year and month set", "SELECT * FROM articles WHERE YEAR(datetime) = 2007 AND MONTH(datetime) = 11 ORDER BY articleid DESC;", articleProcessBean.constructQuery("2007", "11", "", ""));

        // Test year, month and day are set but nothing else
        assertEquals("Year month and day set", "SELECT * FROM articles WHERE YEAR(datetime) = 2007 AND MONTH(datetime) = 11 AND DAY(datetime) = 16 ORDER BY articleid DESC;", articleProcessBean.constructQuery("2007", "11", "16", ""));

        // Test funny inputs
        assertEquals("No year but month", "SELECT * FROM articles ORDER BY articleid DESC;", articleProcessBean.constructQuery("", "11", "", ""));
        assertEquals("No year but day", "SELECT * FROM articles ORDER BY articleid DESC;", articleProcessBean.constructQuery("", "", "16", ""));
        assertEquals("No year but month and day", "SELECT * FROM articles ORDER BY articleid DESC;", articleProcessBean.constructQuery("", "11", "16", ""));
        assertEquals("Year,day but no month", "SELECT * FROM articles WHERE YEAR(datetime) = 2007 ORDER BY articleid DESC;", articleProcessBean.constructQuery("2007", "", "16", ""));

        // Now testing with titles
        assertEquals("Title only", "SELECT * FROM articles WHERE title = \"First Post\" ORDER BY articleid DESC;", articleProcessBean.constructQuery("", "", "", "First Post"));
        assertEquals("Year and title", "SELECT * FROM articles WHERE YEAR(datetime) = 2007 AND title = \"First Post\" ORDER BY articleid DESC;", articleProcessBean.constructQuery("2007", "", "", "First Post"));
        assertEquals("Year, month and title", "SELECT * FROM articles WHERE YEAR(datetime) = 2007 AND MONTH(datetime) = 11 AND title = \"First Post\" ORDER BY articleid DESC;", articleProcessBean.constructQuery("2007", "11", "", "First Post"));
        assertEquals("Year, month, day and title", "SELECT * FROM articles WHERE YEAR(datetime) = 2007 AND MONTH(datetime) = 11 AND DAY(datetime) = 16 AND title = \"First Post\" ORDER BY articleid DESC;", articleProcessBean.constructQuery("2007", "11", "16", "First Post"));

        // Funny input with titles
        assertEquals("Year, day and title", "SELECT * FROM articles WHERE YEAR(datetime) = 2007 AND title = \"First Post\" ORDER BY articleid DESC;", articleProcessBean.constructQuery("2007", "", "16", "First Post"));
        assertEquals("Month, day and title", "SELECT * FROM articles WHERE title = \"First Post\" ORDER BY articleid DESC;", articleProcessBean.constructQuery("", "11", "16", "First Post"));
        assertEquals("Day and title", "SELECT * FROM articles WHERE title = \"First Post\" ORDER BY articleid DESC;", articleProcessBean.constructQuery("", "", "16", "First Post"));
        assertEquals("Month and title", "SELECT * FROM articles WHERE title = \"First Post\" ORDER BY articleid DESC;", articleProcessBean.constructQuery("", "11", "", "First Post"));

    }

    @Test public void titleExtractor() {
        assertEquals("Single word, no hyphen", "Entry", articleProcessBean.extactTitle("Entry"));
        assertEquals("Two hyphen separeted words", "Entry Hyphen", articleProcessBean.extactTitle("Entry-Hyphen"));
        assertEquals("Multiple hyphen separated words", "Entry With Multiple Hyphen", articleProcessBean.extactTitle("Entry-With-Multiple-Hyphen"));
    }

    @Test public void titleRegex() {
        assertTrue("Check single word", articleProcessBean.titleRegex("Entry"));
        assertTrue("Check two word", articleProcessBean.titleRegex("Entry-Next"));
        assertTrue("Check multiple word", articleProcessBean.titleRegex("Entry-Next-Wednesday"));
        assertTrue("Check multiple word wiht numbers", articleProcessBean.titleRegex("Entry-6-Wednesday-18"));

        // These are broken entries that should fail
        assertFalse("Too many hyphens", articleProcessBean.titleRegex("Entry--Next-----Wednesday"));
        assertFalse("Spaces!!", articleProcessBean.titleRegex("Entry Next Wednesday"));
    }
}
