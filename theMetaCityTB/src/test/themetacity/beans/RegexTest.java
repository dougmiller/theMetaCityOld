package test.themetacity.beans;

import com.themetacity.beans.ArticleProcessBean;
import org.junit.After;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import java.util.regex.Pattern;

public class RegexTest {
    ArticleProcessBean articleProcessBean;

    @Before public void setup() {
        articleProcessBean = new ArticleProcessBean();
    }

    @After public void destroy() {
        articleProcessBean = null;
    }

    /**
     * Test that the regex used in the URL filter is working correctly
     */
    @Test public void titleRegex() {
        assertTrue("Check single word", titleRegex("Entry"));
        assertTrue("Check two word", titleRegex("Entry-Next"));
        assertTrue("Check multiple word", titleRegex("Entry-Next-Wednesday"));

        // These are broken entries that should fail as bad input and wont match the regex ending in a 404
        assertFalse("Too many hyphens", titleRegex("Entry--Next-----Wednesday"));
        assertFalse("Hyphen on the end", titleRegex("Entry-Next-Wednesday-"));
        assertFalse("Spaces!!", titleRegex("Entry Next Wednesday"));
        assertFalse("First hyphen", titleRegex("-Entry-Next-Wednesday"));
        assertFalse("Only hyphen", titleRegex("-------"));
        assertFalse("Only space", titleRegex("  "));           

        // Funny character and puctutation are not allowed
        assertFalse("Funny Character", titleRegex("Entry?"));
        assertFalse("Funny Character and hyphen", titleRegex("Entry-Next-Wednesday?"));
        assertFalse("Only Swearing", titleRegex("?!@*"));
        assertFalse("Swearing multiple times", titleRegex("?!@*-?!@*-~!@#$%^&*()_+|}{[]\\"));
    }


    /**
     * * NEVER USED IN CODE * this is the regex used in the URL filter.
     *
     * @param inputString is the input string to check against the regex
     * @return true if the regex matches the inputString
     */
    private Boolean titleRegex(String inputString) {
        return Pattern.matches("\\w+(-?\\w+)*", inputString);   // Appears to be the best form untill browser handeling of punctutation is solved.
        //return Pattern.matches("[^- .]+(-?[^- .]+)*", inputString);    //  A little to eager to match things
        //return Pattern.matches("([^- ].)+(-?([^- ].)+)*", inputString);   //  Fails single word match and I am not sure why. [^] takes the place of . they were conflicing
        //return Pattern.matches("(\\w)+(-?(\\w+))*", inputString);  // This will match accurately but no specialish characters
    }
}
