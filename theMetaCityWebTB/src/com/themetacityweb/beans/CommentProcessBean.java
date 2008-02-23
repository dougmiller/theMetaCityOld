package com.themetacityweb.beans;

import com.themetacitycommon.beans.DatabaseBean;
import com.themetacityweb.typebeans.CommentBean;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

/**
 * This bean process the result of a database call to find beans into a
 * linked list that can be passed to the Comment custom tag for formatting.
 */
public class CommentProcessBean {

    ResultSet result;                       // The return of the call to the database
    LinkedList<CommentBean> listOfBeans = new LinkedList<CommentBean>();    // The list containing commentBeans

    private int article = 0;                    // The article to fetch comments for

    /**
     * @return listOfBeans A LinkedList of populated CommentBeans
     */
    public LinkedList getComments() {
        DatabaseBean dbBean = new DatabaseBean();


        try {
            result = dbBean.executeQuery("SELECT * FROM comments" + " " +
                    "WHERE articleID = " + getArticle() + " " +
                    "ORDER BY commentID ASCENDING;");


            while (result.next()) {
                CommentBean comment = new CommentBean();    // Make a new comment bean to populate

                // Now populate the bean with the reult from the row of the database call
                comment.setName(result.getString("author"));
                comment.setContact(result.getString("contact"));
                comment.setDateTime(result.getDate("date"));
                comment.setComment(result.getString("comment"));

                listOfBeans.add(comment);                   // Add the now populated bean to the list

            }


        } catch (SQLException SQLEx) {
            System.out.println("You had an SQL exception");
            System.out.println(SQLEx);
        }

        return listOfBeans;     // Reutrn the now populated list
    }

    public int getArticle() {
        return article;
    }

    public void setArticle(int article) {
        this.article = article;
    }
}
