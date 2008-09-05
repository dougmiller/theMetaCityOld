package com.themetacity.beans;

import com.themetacity.typebeans.ProfileBean;
import com.themetacity.utilities.DatabaseBean;
import com.themetacity.utilities.SecurityBean;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;
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

        DatabaseBean dbaBean = new DatabaseBean();
        LinkedList<ProfileBean> profileList = new LinkedList<ProfileBean>();

        try {
            PreparedStatement prepStmt;

            if (author.equals("")) {
                prepStmt = dbaBean.getConn().prepareStatement("SELECT username, pseudonym, contact, about FROM users;");
            } else {
                prepStmt = dbaBean.getConn().prepareStatement("SELECT username, pseudonym, contact, about FROM users WHERE username = ?;");
                prepStmt.setString(1, author);
            }

            dbaBean.setPrepStmt(prepStmt);

            ResultSet result = dbaBean.executeQuery();

            while (result.next()) {
                ProfileBean profileBean = new ProfileBean();

                profileBean.setPseudonym(result.getString("pseudonym"));
                profileBean.setContact(result.getString("contact"));
                profileBean.setAbout(result.getString("about"));

                profileList.add(profileBean);
            }
        } catch (SQLException SQLEx) {
            logger.fatal(SQLEx);
        } finally {
            dbaBean.close();
        }

        return profileList;
    }

    /**
     * Queries the database as to weather the uername/password combination is valid.
     *
     * @return 0 if the username/password combination does not exist. Return 1 if it does. Becasue usernames are unique there can not be any other value
     */
    public Boolean getValidUser() {

        DatabaseBean dbaBean = new DatabaseBean();
        LinkedList<ProfileBean> profileList = new LinkedList<ProfileBean>();

        try {
            PreparedStatement prepStmt;

            prepStmt = dbaBean.getConn().prepareStatement("SELECT username FROM users WHERE username = ? AND password = ?;");
            prepStmt.setString(1, author);
            prepStmt.setString(2, password);

            dbaBean.setPrepStmt(prepStmt);

            ResultSet result = dbaBean.executeQuery();

            while (result.next()) {
                ProfileBean profileBean = new ProfileBean();

                profileBean.setUsername(result.getString("username"));

                profileList.add(profileBean);
            }
        } catch (SQLException SQLEx) {
            logger.fatal(SQLEx);
        } finally {
            dbaBean.close();
        }

        return profileList.size() > 0;
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

    public String getPseudonym () {
        return pseudonym;
    }

    public void setPseudonym(String pseudonym) {
        this.pseudonym = pseudonym;
    }
}