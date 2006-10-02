package com.themetacity.beans;

import com.themetacity.typebeans.ArticleBean;

import javax.naming.NamingException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.io.Serializable;

/**
 * This is the bean that process the news.
 * It sends requests to DatabaseBean and populates NewsBeans with the results.
 *
 * @see DatabaseBean
 */
public class ArticleProcessBean implements Serializable {

    ResultSet result; // The returned ResultSet from the executed SQL statement.
    LinkedList<ArticleBean> listOfBeans; // The list of populated beans.
    String condition = "";

    public ArticleProcessBean() {
    }

    /**
     * Process the results of an executed SQL statment into news beans that are placed into a LinkedList.
     * <p/>
     * The statment selects news article(s) and returns them to this method as a ResultSet. The results set is then
     * iterated over and each record is used to populate a ArticleBean. Once populated the ArticleBean is added
     * to a LinkedList and once all records have been processed the list is returned.
     *
     * @return A linked list of populated NewsArticelBeans
     */
    public LinkedList getArticles() {
        DatabaseBean dbBean = new DatabaseBean();

        try {
            // Execute the actual query and put the resusult into a ResultSet
            result = dbBean.executeQuery("Select * FROM NEWS;");

            // For every row that is returned from the database query populate a bean and add
            // it to a linked list so that the JSTL can iterate over it.
            while (result.next()) {

                // Make a new ArticleBean that represents one article
                ArticleBean newsBean = new ArticleBean();

                // Set the properties of the bean
                newsBean.setAuthor(result.getString("Author"));
                newsBean.setEmail(result.getString("Email"));
                newsBean.setTitle(result.getString("Title"));
                newsBean.setPictureURL(result.getString("pictureURL"));
                newsBean.setNews(result.getString("Article"));
                newsBean.setDate(result.getDate("Date"));
                newsBean.setTime(result.getTime("Time"));

                //Add the now populated bean to the list
                listOfBeans.add(newsBean);
            }
        } catch (NamingException nameEx) {
            System.out.println("You had a naming exception");
            System.out.println(nameEx);
        } catch (SQLException SQLEx) {
            System.out.println("You had an error with your SQL");
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