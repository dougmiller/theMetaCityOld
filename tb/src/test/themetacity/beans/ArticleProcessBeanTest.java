package test.themetacity.beans;

import com.themetacity.beans.ArticleProcessBean;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class ArticleProcessBeanTest {

    ArticleProcessBean articleProcessBean = new ArticleProcessBean();

    /**
     * Test that the constructor of the feed title handles the dates appropriately
     */
    @Test
    public void rssArticleTitleConstructorTest() {
        articleProcessBean.setYear("");
        articleProcessBean.setMonth("");
        articleProcessBean.setDay("");
        assertEquals("Title with year etc not set", "The MetaCity", articleProcessBean.getRSSFeedTitle());

        articleProcessBean.setYear("2008");
        articleProcessBean.setMonth("");
        articleProcessBean.setDay("");
        assertEquals("Title with year set", "The MetaCity - 2008", articleProcessBean.getRSSFeedTitle());

        articleProcessBean.setYear("2008");
        articleProcessBean.setMonth("11");
        articleProcessBean.setDay("");
        assertEquals("Title with year and month", "The MetaCity - 2008/11", articleProcessBean.getRSSFeedTitle());

        articleProcessBean.setYear("2008");
        articleProcessBean.setMonth("11");
        articleProcessBean.setDay("16");
        assertEquals("Title with  year , month and day", "The MetaCity - 2008/11/16", articleProcessBean.getRSSFeedTitle());
    }

    /**
     * Test that the link constructor handles the dates appropriatly
     */
    @Test
    public void rssArticleLinkConstructorTest() {
        articleProcessBean.setYear("");
        articleProcessBean.setMonth("");
        articleProcessBean.setDay("");
        assertEquals("Link with year etc not set", "http://wwws.themetacity.com/", articleProcessBean.getRSSFeedLink());

        articleProcessBean.setYear("2008");
        articleProcessBean.setMonth("");
        articleProcessBean.setDay("");
        assertEquals("Link with year set", "http://wwws.themetacity.com/2008/", articleProcessBean.getRSSFeedLink());

        articleProcessBean.setYear("2008");
        articleProcessBean.setMonth("11");
        articleProcessBean.setDay("");
        assertEquals("Link with year and month", "https://www.themetacity.com/2008/11/", articleProcessBean.getRSSFeedLink());

        articleProcessBean.setYear("2008");
        articleProcessBean.setMonth("11");
        articleProcessBean.setDay("16");
        assertEquals("Link with  year , month and day", "https://www.themetacity.com/2008/11/16/", articleProcessBean.getRSSFeedLink());
    }

     /**
     * Test that the link constructor handles the dates appropriatly
     */
    @Test
    public void rssTagTitleConstructorTest() {
        articleProcessBean.setSearchTag("test");
        assertEquals("Title with tag set", "The MetaCity Tags - test", articleProcessBean.getRSSFeedTagTitle());
    }

    /**
     * Test that the link constructor handles the dates appropriatly
     */
    @Test
    public void rssTagLinkConstructorTest() {
        articleProcessBean.setSearchTag("test");
        assertEquals("Link with year etc not set", "https://www.themetacity.com/tags/test/", articleProcessBean.getRSSFeedTagLink());
    }
}
