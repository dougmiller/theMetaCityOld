package com.themetacity.beans;

import com.themetacity.typebeans.NewsArticleBean;

import javax.naming.NamingException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

/**
 *
 */
public class  NewsProcessBean {

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
     * @return A linked list of NewsArticelBeans
     */
    public LinkedList getProcessNews() {
        DatabaseAccessBean dbaBean = new DatabaseAccessBean();

        try {
            result = dbaBean.executeQuery("Select * FROM NEWS;");
        } catch (NamingException nameEx) {
            System.out.println("You had a naming exception");
            System.out.println(nameEx);
        }

        try {
            while (result.next())
            {  //For every row that is returned from the database query populate a bean and add
                // it to a linked list so that the JSTL can iterate over it.

                //Make a new NewsArticleBean that represents one article
                NewsArticleBean newsBean = new NewsArticleBean();

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
        } catch (NullPointerException nullPoint) {
            // this error is thown if there are no rows in the database at all
            // So you must return a message telling people this fact.
            //for the mean tiem Just populate a bean with this as a dirty hack.
            //todo fix the no results returned error

            //constructor only, good old flux
            NewsArticleBean newsBean = new NewsArticleBean(); //Make a new NewsArticleBean that represents one
            listOfBeans.add(newsBean);
        }

        return listOfBeans;
    }

    public ResultSet getResult() {
        return result;
    }

    public void setResult(ResultSet result) {
        this.result = result;
    }
}