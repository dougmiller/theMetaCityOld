package com.themetacity.beans;

import com.themetacity.typebeans.CommentBean;
import com.themetacity.utilities.DatabaseBean;
import org.apache.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

/**
 * This bean process the result of a database call to find beans into a
 * linked list that can be passed to the Comment custom tag for formatting.
 */
public class CommentProcessBean {

    DatabaseBean dbaBean = new DatabaseBean();

    private int article = 0;
    private int comment = 0;

    static Logger logger = Logger.getLogger(CommentProcessBean.class);

    /**
     * @return listOfBeans A LinkedList of populated CommentBeans
     */
    private LinkedList<CommentBean> processQuery() {

        LinkedList<CommentBean> listOfBeans = new LinkedList<CommentBean>();
        ResultSet result = dbaBean.executeQuery();
        try {

            while (result.next()) {
                CommentBean comment = new CommentBean();

                // Now populate the bean with the result from the row of the database call
                comment.setName(result.getString("author"));
                comment.setContact(result.getString("contact"));
                comment.setDateTime(result.getDate("date"));
                comment.setComment(result.getString("comment"));

                listOfBeans.add(comment);

            }
            // Close the Result Set
            result.close();
            // Close the database connection
            dbaBean.close();

        } catch (SQLException SQLEx) {
            logger.warn("You had an SQL exception");
            logger.warn(SQLEx);
        }

        return listOfBeans;
    }

    public LinkedList<CommentBean> getCommentsForArticle() {
        try {
            dbaBean.setPrepStmt(dbaBean.getConn().prepareStatement("SELECT * FROM comments WHERE id = ?;"));
            dbaBean.getPrepStmt().setInt(1, article);
        } catch (SQLException SQLEx) {
            logger.fatal(SQLEx);
        }

        return processQuery();
    }

    public int getArticle() {
        return article;
    }

    public void setArticle(int article) {
        this.article = article;
    }

    public int getComment() {
        return comment;
    }

    public void setComment(int comment) {
        this.comment = comment;
    }
}
