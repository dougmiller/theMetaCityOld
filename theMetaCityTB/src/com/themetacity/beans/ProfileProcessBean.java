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

    LinkedList<ProfileBean> listOfBeans = new LinkedList<ProfileBean>();    // The list of beans

    private String author;    // The username that can be passed in as an optional argument

    public ProfileProcessBean() {
        author = "";
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
    public LinkedList<ProfileBean> getProfiles() {
        DatabaseBean dbaBean = new DatabaseBean();
        try {
            // Use a statment that returns them all the users.
            // Arguments have not been implimented yet
            ResultSet result = dbaBean.executeQuery(buildProfileQuery());

            while (result.next()) {
                ProfileBean profileBean = new ProfileBean();              // Make a new bean to be populated

                // Now set all the properties
                profileBean.setPseudonym(result.getString("pseudonym"));
                profileBean.setContact(result.getString("contact"));
                profileBean.setAbout(result.getString("about"));

                // Process this users tags
                TagProcessBean tagProcessBean = new TagProcessBean();
                tagProcessBean.setUser(result.getString("username"));     // Set the user in the TagProcessBean

                profileBean.setTags(tagProcessBean.getTags());            // Assign the results to the bean

                listOfBeans.add(profileBean);                             // Add the newly populated profile to the list, it could be one or many, it doesnt really matter
            }
            // Close the open database connections, returning it to the pool
            result.close();
            dbaBean.close();
        }
        catch (SQLException SQLEx) {
            System.out.println("There is an error with the database layer.");
            System.out.println(SQLEx);
        }
        return listOfBeans; // Return the list full of populated beans
    }

    /**
     * Builds the database query for profiles
     *
     * @return String of the statment ready to execute
     */
    private String buildProfileQuery() {
        String query;
        if (author.equals("")) {
            query = "SELECT * FROM users;";
        } else {
            query = "SELECT * FROM users WHERE username = '" + author + "';";
        }

        return query;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}