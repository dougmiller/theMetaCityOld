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
    LinkedList<ProfileBean> listOfBeans = new LinkedList<ProfileBean>();    // The list of beans

    private String author;    // The username that can be passed in as an optional argument

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
        DatabaseBean dbaBean = new DatabaseBean();
        try {
            // Use a statment that returns them all the users.
            if (author == null) {
                result = dbaBean.executeQuery("SELECT * FROM users;");
            } else {
                // Check if a pseudonym is passed as "author" and if it is amend the stament
                result = dbaBean.executeQuery("SELECT * FROM users WHERE pseudonym = " + getAuthor() + ";"); // Select a specific profile");                             // Select all the profiles as no specific profile was given
            }

            while (result.next()) {
                ProfileBean profileBean = new ProfileBean();            // Make a new bean to be populated

                // Now set all the properties
                profileBean.setPseudonym(result.getString("pseudonym"));
                //profileBean.setPicURL(result.getString("picURL"));     //todo not in test DB, next interation perhapse
                profileBean.setAbout(result.getString("about"));

                listOfBeans.add(profileBean);                           // Add the newly populated profile to the list, it could be one or many, it doesnt really matter
            }
        }
        catch (SQLException SQLEx) {
            System.out.println("Error in the SQL");
            System.out.println(SQLEx);
        } catch (NamingException nameEx) {
            System.out.println("You had a naming exception");
            System.out.println(nameEx);
        }
        return listOfBeans; // Return the list full of populated beans
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}