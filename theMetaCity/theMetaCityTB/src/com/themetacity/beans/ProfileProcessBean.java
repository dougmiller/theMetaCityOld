package com.themetacity.beans;

import com.themetacity.typebeans.ProfileBean;

import javax.naming.NamingException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

/**
 * User: doug
 * com.themetacity.beans
 */
public class ProfileProcessBean {

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
    public LinkedList ProfileProcess() {
        DatabaseAccessBean dbaBean = new DatabaseAccessBean();

        try {
            result = dbaBean.getNewsResults("Select * FROM users;");
        } catch (NamingException nameEx) {
            System.out.println("You had a naming exception");
            System.out.println(nameEx);
        }

        try {
            while (result.next()) {
                ProfileBean profileBean = new ProfileBean();

                profileBean.setUsername(result.getString("username"));
                profileBean.setEmail(result.getString("email"));
                profileBean.setPicURL(result.getString("picurl"));
                profileBean.setAbout(result.getString("about"));
                listOfBeans.add(profileBean);
            }
        }
        catch (SQLException SQLEx) {
            System.out.println("Error in the SQL");
            System.out.println(SQLEx);
        }
        return listOfBeans;
    }
}
