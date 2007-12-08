package themetacity.tags;

import com.themetacity.tags.ArchiveEntry;
import org.junit.After;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

public class ArchiveEntryTest {
    ArchiveEntry archiveEntry;

    @Before public void setup() {
        archiveEntry = new ArchiveEntry();
    }

    @After public void destroy() {
        archiveEntry = null;
    }

    @Test public void buildTitleLink() {
        assertEquals("hyphens to spaces", "This-should-be-the-link", archiveEntry.buildTitleLink("This should be the link"));
    }

    @Test public void buildDateLink() {
        String goodexpected = "<a href=\"2007\" />2007</a>-<a href=\"2007/11\" />11</a>-<a href=\"2007/11/16\" />16</a>";
        assertEquals("Good regular date", goodexpected, archiveEntry.buildDateLink("2007-11-16"));

    }

    @Test public void buildTitle() {
        String goodExpected = "<a href=\"/This-is-the-third-article\">This is the third article</a>";
        assertEquals("Good formatted entry", goodExpected, archiveEntry.buildTtitle("This is the third article"));

    }


}
