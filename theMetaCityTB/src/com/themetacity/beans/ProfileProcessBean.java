package com.themetacity.beans;

import com.themetacity.typebeans.ProfileBean;
import com.themetacity.utilities.DatabaseBean;
import com.themetacity.utilities.SecurityBean;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

import org.apache.log4j.Logger;

/**
 * This is the bean that process a profile.
 * It makes a call to the database and populates profileBeans with the results.
 */
public class ProfileProcessBean {

    LinkedList<ProfileBean> listOfBeans = new LinkedList<ProfileBean>();    // The list of beans

    private String author = "";
    private String password = "";
    private String contact = "";
    private String about = "";
    private String pseudonym = "";

    static Logger logger = Logger.getLogger(ProfileProcessBean.class);

    DatabaseBean dbaBean = new DatabaseBean();


    public ProfileProcessBean() {
    }

    private LinkedList<ProfileBean> processQuery() {
        try {
            // Use a statment that returns them all the users.
            // Arguments have not been implimented yet
            ResultSet result = dbaBean.executeQuery();

            while (result.next()) {
                ProfileBean profileBean = new ProfileBean();              // Make a new bean to be populated

                // Now set all the properties
                profileBean.setPseudonym(result.getString("pseudonym"));
                profileBean.setContact(result.getString("contact"));
                profileBean.setAbout(result.getString("about"));

                // Process this users tags
                TagProcessBean tagProcessBean = new TagProcessBean();
                tagProcessBean.setUser(result.getString("username"));     // Set the user in the TagProcessBean

                profileBean.setTags(tagProcessBean.getAuthorTags(tagProcessBean.getUser()));            // Assign the results to the bean

                listOfBeans.add(profileBean);                             // Add the newly populated profile to the list, it could be one or many, it doesnt really matter
            }
            // Close the Result Set
            result.close();
            // Close the database connection
            dbaBean.close();
        }
        catch (SQLException SQLEx) {
            logger.warn("There is an error with the database layer.");
            logger.warn(SQLEx);
        }

        return listOfBeans; // Return the list full of populated beans
    }

    // Perforn the update commands
    private int processUpdate() {
        int result = dbaBean.executeUpdate();
        dbaBean.close();
        return result;
    }

    /**
     * Finds all the authors matching the parameters
     *
     * @return LinkedList of the authors typebean
     */
    public LinkedList<ProfileBean> getProfiles() {
        try {
            if (author.equals("")) {
                dbaBean.setPrepStmt(dbaBean.getConn().prepareStatement("SELECT * FROM users;"));
            } else {
                dbaBean.setPrepStmt(dbaBean.getConn().prepareStatement("SELECT * FROM users WHERE username = ?;"));
                dbaBean.getPrepStmt().setString(1, author);
            }

        } catch (SQLException SQLEx) {
            logger.fatal(SQLEx);
        }
        return processQuery();
    }

    /**
     * Queries the database as to weather the uername/password combination is valid.
     *
     * @return 0 if the username/password combination does not exist. Return 1 if it does. Becasue usernames are unique there can not be any other value
     */
    public Boolean getValidUser() {
        try {
            dbaBean.setPrepStmt(dbaBean.getConn().prepareStatement("SELECT * FROM users WHERE username = ? AND password = ?;"));
            dbaBean.getPrepStmt().setString(1, author);
            dbaBean.getPrepStmt().setString(2, password);
        } catch (SQLException SQLEx) {
            logger.fatal(SQLEx);
        }

        LinkedList<ProfileBean> profile = processQuery();

        return profile.size() > 0;
    }

    /**
     * Change a users password.
     *
     * @return 1 if the password has been changed successfuly. 0 if it has not been changed.
     */
    public Boolean getChangePassword() {

        try {
            dbaBean.setPrepStmt(dbaBean.getConn().prepareStatement("UPDATE users SET password = ? WHERE username = ?;"));
            dbaBean.getPrepStmt().setString(1, password);
            dbaBean.getPrepStmt().setString(2, author);
        } catch (SQLException SQLEx) {
            logger.fatal(SQLEx);
        }

        return processUpdate() > 0;
    }

    /**
     * Updates the users profile
     *
     * @return return 1 if the user's profile has been updated successfully. 0 Otherwise.
     */
    public Boolean getUpdateProfile() {
        try {
            dbaBean.setPrepStmt(dbaBean.getConn().prepareStatement("UPDATE users SET contact = ?, pseudonym = ?, about = ? WHERE username = ?;"));
            dbaBean.getPrepStmt().setString(1, contact);
            dbaBean.getPrepStmt().setString(2, pseudonym);
            dbaBean.getPrepStmt().setString(3, about);
            dbaBean.getPrepStmt().setString(4, author);
        } catch (SQLException SQLEx) {
            logger.fatal(SQLEx);
        }

        return processUpdate() > 0;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        SecurityBean security = new SecurityBean();
        this.password = security.hashPasswordWithSalt(password, author);
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getPseudonym() {
        return pseudonym;
    }

    public void setPseudonym(String pseudonym) {
        this.pseudonym = pseudonym;
    }
}