package test.themetacity.utilities;

import com.themetacity.utilities.SecurityBean;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

/**
 * Test case for security
 */
public class SecurityBeanTest {

    SecurityBean securityBean;

    @Before public void setup() {
        securityBean = new SecurityBean();
    }

    @Test public void hashPasswordWithSaltTest() {
        assertEquals("Test an empty string", "8fb29448faee18b656030e8f5a8b9e9a695900f36a3b7d7ebb0d9d51e06c8569d81a55e39b481cf50546d697e7bde1715aa6badede8ddc801c739777be77f166",securityBean.hashPasswordWithSalt("", ""));
        assertEquals("Known sentence", "435baf520ff4ea776c5b47486d002e9e6c47476194843063898ab20c79467e6e9214739ece9d34acb475b8b668a5cb02c7cb0a63c8d5a9cd1ba05fafb44a2d1b", securityBean.hashPasswordWithSalt("The quick brown fox jumps over the lazy dog", ""));
    }
}
