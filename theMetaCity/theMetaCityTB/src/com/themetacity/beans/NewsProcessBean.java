package com.themetacity.beans;

//import com.themetacity.typebeans.NewsArticleBean;

import com.themetacity.typebeans.NewsArticleBean;

import javax.naming.NamingException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

/**
 *
 */
public class NewsProcessBean {
    public NewsProcessBean() {
    }

    ResultSet result; // The returned ResultSet from the executed SQL statement.
    LinkedList<NewsArticleBean> listOfBeans; // The list of populated beans.

    /**
     * Process the results of a database connection that retuns a ResultSet of a query into beans that are passed to
     * custom tag for output.
     *
     * @return A list of NewsArticelBeans
     */
    public LinkedList ProcessNews() {
        DatabaseAccessBean dbaBean = new DatabaseAccessBean();


        try {
            result = dbaBean.getNewsResults("Select * FROM NEWS;");
        } catch (NamingException nameEx) {
            System.out.println("You had a naming exception");
            System.out.println(nameEx);
        }

        try {
            while (result.next()) {  //For every row that is returned from the database query populate a bean and add
                //  it to a linked list so that the JSTL can iterate over it.
                NewsArticleBean newsBean = new NewsArticleBean(); //Make a new NewsArticleBean that represents one
                //  article

                //Set the properties of the bean
                newsBean.setAuthor(result.getString("Author"));
                newsBean.setEmail(result.getString("Email"));
                newsBean.setTitle(result.getString("Title"));
                newsBean.setPictureURL(result.getString("pictureURL"));
                newsBean.setNews(result.getString("News"));
                newsBean.setDate(result.getDate("Date"));
                newsBean.setTime(result.getTime("Time"));

                listOfBeans.add(newsBean);  //Add the now populated bean to the list
            }
        } catch (SQLException SQLEx) {
            System.out.println("There was a problem in your SQL statement");
            System.out.println(SQLEx);
        }
        return listOfBeans;
    }


}