package com.themetacity.beans;

import com.themetacity.typebeans.NewsArticleBean;

import javax.naming.NamingException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.io.Serializable;

/**
 * This is the bean that process the news.
 * It sends requests to DatabaseAccessBean and populates NewsBeans with the results.
 * @see DatabaseAccessBean
 */
public class NewsProcessBean implements Serializable {

    ResultSet result; // The returned ResultSet from the executed SQL statement.
    LinkedList<NewsArticleBean> listOfBeans; // The list of populated beans.
    String condition = "";

    public NewsProcessBean() {
    }

    /**
     * Process the results of an executed SQL statment into news beans that are placed into a LinkedList.
     * <p/>
     * The statment selects news article(s) and returns them to this method as a ResultSet. The results set is then
     * iterated over and each record is used to populate a NewsArticleBean. Once populated the NewsArticleBean is added
     * to a LinkedList and once all records have been processed the list is returned.
     *
     * @return A linked list of populated NewsArticelBeans
     */
    public LinkedList getProcessNews() {
        DatabaseAccessBean dbaBean = new DatabaseAccessBean();

        try {
            // Execute the actual query and put the resusult into a ResultSet
            result = dbaBean.executeQuery("Select * FROM NEWS;");
        } catch (NamingException nameEx) {
            System.out.println("You had a naming exception");
            System.out.println(nameEx);
        } catch (SQLException SQLEx) {
            System.out.println("You had an error with your SQL");
            System.out.println(SQLEx);
        }

        try {
            // For every row that is returned from the database query populate a bean and add
            // it to a linked list so that the JSTL can iterate over it.
            while (result.next()) {

                // Make a new NewsArticleBean that represents one article
                NewsArticleBean newsBean = new NewsArticleBean();

                // Set the properties of the bean
                newsBean.setAuthor(result.getString("Author"));
                newsBean.setEmail(result.getString("Email"));
                newsBean.setTitle(result.getString("Title"));
                newsBean.setPictureURL(result.getString("pictureURL"));
                newsBean.setNews(result.getString("News"));
                newsBean.setDate(result.getDate("Date"));
                newsBean.setTime(result.getTime("Time"));

                //Add the now populated bean to the list
                listOfBeans.add(newsBean);
            }
        } catch (SQLException SQLEx) {
            System.out.println("There was a problem accessing the database");
            System.out.println(SQLEx);
        } 
        // Return the list of populated newsBeans
        return listOfBeans;
    }

    public ResultSet getResult() {
        return result;
    }

    public void setResult(ResultSet result) {
        this.result = result;
    }
}