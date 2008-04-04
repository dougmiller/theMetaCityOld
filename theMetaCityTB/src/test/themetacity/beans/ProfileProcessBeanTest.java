package test.themetacity.beans;

import com.themetacity.beans.ProfileProcessBean;
import org.junit.After;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

public class ProfileProcessBeanTest {
    ProfileProcessBean profileProcessBean;

    @Before public void setup() {
        profileProcessBean = new ProfileProcessBean();
    }

    @After public void destroy() {
        profileProcessBean = null;
    }

    @Test public void buildProfileQuery() {
    //    assertEquals("No user pramater", "SELECT * FROM users;", profileProcessBean.buildProfileQuery(""));
    //    assertEquals("No user pramater", "SELECT * FROM users WHERE username = 'doug';", profileProcessBean.buildProfileQuery("doug"));
    }
}
