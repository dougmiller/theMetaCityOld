package test.themetacity.tags;

import com.themetacity.tags.ArchiveEntry;
import org.junit.After;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

/**
 * This is the class for testing an archive entry.
 */
public class ArchiveEntryTest {
    ArchiveEntry archiveEntry;

    @Before public void setup() {
        archiveEntry = new ArchiveEntry();
    }

    @After public void destroy() {
        archiveEntry = null;
    }

    /**
     * Tests that the title is build to link to the appropriate article correctly
     */
    @Test public void buildTitleLink() {
        String goodExpected = "<a href=\"/This-is-the-third-article\">This is the third article</a>";
        assertEquals("Good formatted entry", goodExpected, archiveEntry.buildTitleLink("This is the third article"));
    }

    /**
     * Test that article title links are converted correctly form space delimeted links to hyphen delimted links so the a tags will work
     */
    @Test public void convertTitleToWeb() {
        assertEquals("hyphens to spaces", "This-should-be-the-link", archiveEntry.convertTitleToWeb("This should be the link"));
    }

    /**
     * Tests that the date links are built correctly
     */
    @Test public void buildDateLink() {
        String goodexpected = "<a href=\"/2007\" />2007</a>-<a href=\"/2007/11\" />11</a>-<a href=\"/2007/11/16\" />16</a>";
        assertEquals("Good regular date", goodexpected, archiveEntry.buildDateLink("2007-11-16"));
    }
}
