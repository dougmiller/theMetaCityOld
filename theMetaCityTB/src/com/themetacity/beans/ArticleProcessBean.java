package com.themetacity.beans;

import com.themetacity.typebeans.ArticleBean;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

/**
 * This is the bean that process the news.
 *
 * @see DatabaseBean
 */
public class ArticleProcessBean implements Serializable {

    String query;                           // The SQL query to be be run
    ResultSet result;                       // The returned ResultSet from the executed SQL statement.
    LinkedList<ArticleBean> listOfBeans = new LinkedList<ArticleBean>();    // The list of populated beans.

    public ArticleProcessBean() {
    }

    /**
     * Process the results of an executed SQL statment into article beans that are placed into a LinkedList.
     * <br />
     * The statement selects news article(s) and returns them to this method as a ResultSet. The results set is then
     * iterated over and each record is used to populate a ArticleBean. Once populated the ArticleBean is added
     * to a LinkedList and once all records have been processed the list is returned.
     *
     * @return A linked list of populated NewsArticelBeans
     */
    public LinkedList getArticles() {
        DatabaseBean dbBean = new DatabaseBean();

        try {
            // Build the SQL query string
            query = "Select * FROM articles;";

            // Execute the actual query and put the resusult into a ResultSet
            result = dbBean.executeQuery(query);

            // For every row that is returned from the database query populate a bean and add
            // it to a linked list so that the JSTL can iterate over it.
            while (result.next()) {

                // Make a new ArticleBean that represents one article
                ArticleBean articleBean = new ArticleBean();

                // Set the properties of the bean

                articleBean.setAuthor(result.getString("author"));
                //articleBean.setContact(result.getString("Email"));
                articleBean.setTitle(result.getString("title"));
                //articleBean.setPictureURL(result.getString("pictureURL"));
                articleBean.setNews(result.getString("articletext"));
                articleBean.setDate(result.getDate("datetime"));
                //articleBean.setTime(result.getTime("Time"));


                listOfBeans.add(articleBean);               //Add the now populated bean to the list to be returned for display
            }
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