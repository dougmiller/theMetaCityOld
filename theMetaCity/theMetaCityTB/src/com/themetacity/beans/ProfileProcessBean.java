package com.themetacity.beans;

import com.themetacity.typebeans.ProfileBean;

import javax.naming.NamingException;
import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

/**
 * This is the bean that process a profile.
 * It makes a call to the database and populates profileBeans with the results.
 *
 * @see DatabaseBean
 */
public class ProfileProcessBean implements Serializable {

    ResultSet result;                       // The results fo the database call
    LinkedList<ProfileBean> listOfBeans;    // The list of beans

    String user;    // The username that can be passed in as an optional argument

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
    public LinkedList getProfiles() {
        System.out.println("in profileban");
        DatabaseBean dbaBean = new DatabaseBean();
        System.out.println("In profile bean.");
        try {
            // Check if a username is passed as "user" and if it is amend the stament
            if (user != null) {
                result = dbaBean.executeQuery("SELECT * FROM \'users\' WHERE username = " + user + ";"); // Select a specific profile
            } else {
                // Otherwise use the statment that returns them all.
                result = dbaBean.executeQuery("SELECT * FROM \'users\';");                             // Select all the profiles as no specific profile was given
                System.out.println("Executed query.");
            }

            while (result.next()) {
                System.out.println("filling out beans.");
                ProfileBean profileBean = new ProfileBean();            // Make a new bean to be populated

                profileBean.setPseudonym(result.getString("user"));
                profileBean.setEmail(result.getString("email"));
                //profileBean.setPicURL(result.getString("picURL"));     // not in test DB, next interation perhapse
                profileBean.setAbout(result.getString("about"));
                System.out.println("adding bean to list");


                listOfBeans.add(profileBean);                           // Add the newly populated profile to the list, it could be one or nmany, it doesnt really matter
            }
        }
        catch (SQLException SQLEx) {
            System.out.println("Error in the SQL");
            System.out.println(SQLEx);
        } catch (NamingException nameEx) {
            System.out.println("You had a naming exception");
            System.out.println(nameEx);

        }
        System.out.println("returning bean");
        System.out.println("leaving profilebean");
        return listOfBeans; // Return the list full of populated beans
    }
}