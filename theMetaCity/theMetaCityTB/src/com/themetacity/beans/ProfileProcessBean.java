package com.themetacity.beans;

import com.themetacity.typebeans.ProfileBean;

import javax.naming.NamingException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.io.Serializable;

/**
 * This is the bean that process a profile
 */
public class ProfileProcessBean implements Serializable {

    ResultSet result;
    LinkedList<ProfileBean> listOfBeans;

    public ProfileProcessBean() {
    }

    /**
     * Process the results of a database connection that retuns a ResultSet of a statement into profile beans that are
     * passed to a custom tag for output.
     * <p/>
     * The statment selects a profile(s) and returns them to this method as a ResultSet. The results set is then
     * iterated over and each record is used to populate a ProfileBean. Once populated the ProfileBean is added to a
     * LinkedList and once all records have been processed the list is returned.
     *
     * @return A linked list of ProfileBeans
     */
    public LinkedList getProfileProcess(String user) {
        DatabaseBean dbaBean = new DatabaseBean();

        try {
            // Check if a username is passed as "user" and if it is amend the stament
            if (user != null) {
                result = dbaBean.executeQuery("Select * FROM profile WHERE username =" + user + ";"); // Select a specific profile
            } else {
                // Otherwise use the statment that returns them all.
                result = dbaBean.executeQuery("Select * FROM profile;");                              // Select all the profiles as no specific profile was given
            }

        } catch (NamingException nameEx) {
            System.out.println("You had a naming exception");
            System.out.println(nameEx);
        } catch (SQLException SQLEx) {
            System.out.println("You had an error with your SQL");
            System.out.println(SQLEx);
        }

        try {
            while (result.next()) {
                ProfileBean profileBean = new ProfileBean();            // Make a new bean to be populated

                profileBean.setUsername(result.getString("username"));
                profileBean.setEmail(result.getString("email"));
                profileBean.setPicURL(result.getString("profilepic"));
                profileBean.setPicURL(result.getString("profilealt"));
                profileBean.setAbout(result.getString("about"));

                listOfBeans.add(profileBean);                           // Add the newly populated profile to the list, it could be one or nmany, it doesnt really matter
            }
        }
        catch (SQLException SQLEx) {
            System.out.println("Error in the SQL");
            System.out.println(SQLEx);
        }
        return listOfBeans;                            // Return the list full of populated beans
    }
}
