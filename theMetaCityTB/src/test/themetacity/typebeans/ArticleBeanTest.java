package test.themetacity.typebeans;

import com.themetacity.typebeans.ArticleBean;
import org.junit.After;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class ArticleBeanTest {
    ArticleBean articleBean;

    @Before public void setup() {
        articleBean = new ArticleBean();
    }

    @After public void destroy() {
        articleBean = null;
    }

    /**
     * Tests that article titles are extracted correctly from the hyphen delimited input
     */
    @Test public void titleExtractor() {
        assertEquals("Single word, no hyphen", "Entry", articleBean.extractTitle("Entry"));
        assertEquals("Two hyphen separeted words", "Entry Hyphen", articleBean.extractTitle("Entry-Hyphen"));
        assertEquals("Multiple hyphen separated words", "Entry With Multiple Hyphen", articleBean.extractTitle("Entry-With-Multiple-Hyphen"));

        // These cases should carefully looked at
        assertEquals("Single hyphen alone", " ", articleBean.extractTitle("-"));
    }

    /**
     * Tests that article titles are build into hyphen delimeted strings
     */
    @Test public void titleBuilder() {
         assertEquals("Single", "Alone", articleBean.buildTitle("Alone"));
         assertEquals("Two words", "Alone-Together", articleBean.buildTitle("Alone Together"));
         assertEquals("Multiple words", "Finally-Alone-Togeather", articleBean.buildTitle("Finally Alone Togeather"));
    }

    /**
     * Tests that the title setter behaves as expected as it has a trim function run on it
     */
    @Test public void titleSetter() {
        articleBean.setTitle("Alone");
        assertEquals("Single word no spaces","Alone",articleBean.getTitle());

        articleBean.setTitle(" Alone ");
        assertEquals("Spaces around single word","Alone",articleBean.getTitle());

        articleBean.setTitle("Alone Together");
        assertEquals("Multiple words no spaces before or after","Alone Together",articleBean.getTitle());

        articleBean.setTitle(" Alone Together ");
        assertEquals("Single word no spaces","Alone Together",articleBean.getTitle());
    }
}
