package test.themetacityweb.beans;

import com.themetacity.beans.TagProcessBean;
import org.junit.After;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

public class TagProcessBeanTest {
    TagProcessBean tagProcessBean;

    @Before public void setup() {
        tagProcessBean = new TagProcessBean();
    }

    @After public void destroy() {
        tagProcessBean = null;
    }

    @Test public void buildProfileQuery() {
        assertEquals("User argument set, articleid empty", "SELECT tag, count(tag) as timesused FROM articles, articletags WHERE articles.author = 'dmg' AND articles.articleid = articletags.articleid GROUP BY tag;", tagProcessBean.constructTagQuery("dmg",""));
        assertEquals("User argument empty, articleid set", "SELECT tag, count(tag) as timesused FROM articles, articletags WHERE articles.articleid = '1' AND articles.articleid = articletags.articleid GROUP BY tag;", tagProcessBean.constructTagQuery("", "1"));
        assertEquals("No argument set","SELECT tag, count(tag) as timesused FROM articles, articletags WHERE articles.articleid = articletags.articleid GROUP BY tag;",tagProcessBean.constructTagQuery("",""));

    }
}



