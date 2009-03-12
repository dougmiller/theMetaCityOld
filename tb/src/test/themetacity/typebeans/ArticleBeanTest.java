package test.themetacity.typebeans;

import com.themetacity.typebeans.ArticleBean;
import org.junit.After;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class ArticleBeanTest {
    ArticleBean articleBean;

    @Before
    public void setup() {
        articleBean = new ArticleBean();
    }

    @After
    public void destroy() {
        articleBean = null;
    }

    /**
     * Tests that article titles are build into hyphen delimeted strings
     */
    @Test
    public void buildURL() {
        assertEquals("Single", "Alone", articleBean.buildURL("Alone"));
        assertEquals("Two words", "Alone-Together", articleBean.buildURL("Alone Together"));
        assertEquals("Multiple words", "Finally-Alone-Togeather", articleBean.buildURL("Finally Alone Togeather"));

        // Potential grammar and style issues
        assertEquals("Too many spaces", "Alone-Together", articleBean.buildURL("Alone  Together"));
        assertEquals("Really too many spaces", "Alone-Together", articleBean.buildURL("Alone          Together"));
        assertEquals("Spaces in inappropriate places", "Alone-Together", articleBean.buildURL("   Alone Together   "));

        // Invalid characters
        assertEquals("Non URL characters", "Alone-Together-at-9", articleBean.buildURL("Alone% % Toge(&^%.$ther at 9"));
    }

    /**
     * Tests that the title setter behaves as expected as it has a trim function run on it
     */
    @Test
    public void titleSetter() {
        articleBean.setTitle("Alone");
        assertEquals("Single word no spaces", "Alone", articleBean.getTitle());

        articleBean.setTitle(" Alone ");
        assertEquals("Spaces around single word", "Alone", articleBean.getTitle());

        articleBean.setTitle("Alone Together");
        assertEquals("Multiple words no spaces before or after", "Alone Together", articleBean.getTitle());

        articleBean.setTitle(" Alone Together ");
        assertEquals("Single word no spaces", "Alone Together", articleBean.getTitle());
    }
}
