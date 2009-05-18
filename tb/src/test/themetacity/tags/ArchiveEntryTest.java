package test.themetacity.tags;

import org.junit.After;
import org.junit.Before;

/**
 * This is the class for testing an archive entry.
 */
public class ArchiveEntryTest {
    com.themetacity.tags.blog.ArchiveEntry archiveEntry;

    @Before public void setup() {
        archiveEntry = new com.themetacity.tags.blog.ArchiveEntry();
    }

    @After public void destroy() {
        archiveEntry = null;
    }

}
