package com.themetacity.beans;

import com.themetacity.typebeans.NewsArticleBean;

import javax.naming.NamingException;
import java.sql.ResultSet;
import java.util.LinkedList;

/**
 * User: doug
 * com.themetacity.beans
 */
public class ProfileProcessBean {

    ResultSet result; // The returned ResultSet from the executed SQL statement.
    LinkedList<NewsArticleBean> listOfBeans; // The list of populated beans.

    public ProfileProcessBean(){}

    public LinkedList ProfileProcessBean() {
        DatabaseAccessBean dbaBean = new DatabaseAccessBean();

        try {
            result = dbaBean.getNewsResults("Select * FROM user;");
        } catch (NamingException nameEx) {
            System.out.println("You had a naming exception");
            System.out.println(nameEx);
        }
    }
}
