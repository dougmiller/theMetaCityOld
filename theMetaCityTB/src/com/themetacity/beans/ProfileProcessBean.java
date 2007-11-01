package com.themetacity.beans;

import com.themetacity.typebeans.ProfileBean;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

/**
 * This is the bean that process a profile.
 * It makes a call to the database and populates profileBeans with the results.
 *
 * @see DatabaseBean
 */
public class ProfileProcessBean {

    ResultSet result = null;                       // The results fo the database call
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
        System.out.println("making new db bean");
        DatabaseBean dbaBean = new DatabaseBean();
        try {
            // Use a statment that returns them all the users.
            // Arguments have not been implimented yet
            result = dbaBean.executeQuery("SELECT * FROM users;");
            System.out.println("executed query");

            // Check to see if something was returned from the database
            if (result == null) {
                System.out.println("Nothing returned");
            } else

                while (result.next()) {
                    System.out.println("trying for new profilebean");
                    ProfileBean profileBean = new ProfileBean();            // Make a new bean to be populated

                    // Now set all the properties
                    profileBean.setPseudonym(result.getString("pseudonym"));
                    //profileBean.setPicURL(result.getString("picURL"));     
                    profileBean.setAbout(result.getString("about"));

                    listOfBeans.add(profileBean);                           // Add the newly populated profile to the list, it could be one or many, it doesnt really matter
                }
            // Close the open databse connections
            result.close();
            dbaBean.close();
        }
        catch (SQLException SQLEx) {
            System.out.println("There is an error with the database layer.");
            System.out.println(SQLEx);
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