package com.themetacity.beans;

import com.themetacity.utilities.DatabaseBean;
import com.themetacity.utilities.SecurityBean;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.apache.log4j.Logger;

/**
 * This bean handles logins. Takes a
 */
public class LoginBean {
    private String username;
    private String password;

    static Logger logger = Logger.getLogger(LoginBean.class);

    /**
     * This class handles user logins.
     * Makes the database lookup and the comparison of the passwords (encrypted).
     *
     * @return 1 if the user is valid. 0 otherwise.
     */
    public boolean getValidUser() {

        DatabaseBean dbBean = new DatabaseBean();

        try {
            int numRows = 0;

            ResultSet results = dbBean.executeQuery("SELECT * FROM users WHERE username='" + username + "' AND password = '"+ password +"';");

            while (results.next()) {
                numRows++;
            }

            results.close();

            // If the number of results was 0, then no username/password matched. So invalid username.
            if (numRows == 0) {
                return false;
            }
        }
        catch (SQLException SQLException) {
            logger.warn(SQLException);
        }

        return true;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        SecurityBean security= new SecurityBean();
        this.password = security.getSHA512OfString(password, username);
    }
}