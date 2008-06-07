package com.themetacity.beans;

import com.themetacity.typebeans.TagBean;
import com.themetacity.utilities.DatabaseBean;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

import org.apache.log4j.Logger;

/**
 * This class processes tags for articles and profiles. It is not the search.
 * It makes requests to the database and returns a linkedlist of the results.
 */
public class TagProcessBean {
    DatabaseBean dbaBean;

    private String user = "";
    private String articleID = "";

    static Logger logger = Logger.getLogger(TagProcessBean.class);

    // Zero argment constructor
    public TagProcessBean() {
        dbaBean = new DatabaseBean();
    }

    /**
     * Process the result of a call to the database (a results set) into a linked list containing
     * TagBean objects, which can be passed to a custom tag for output.
     *
     * @return a linked list containing TagBean objects
     */
    private LinkedList<TagBean> processQuery() {
        LinkedList<TagBean> listOfTags = new LinkedList<TagBean>();
        ResultSet result = dbaBean.executeQuery();
        try {
            while (result.next()) {
                TagBean tagBean = new TagBean();

                tagBean.setTag(result.getString("tag"));
                tagBean.setNumTimesTagUsed(result.getInt("timesused"));

                listOfTags.add(tagBean);
            }
            // Close the Result Set
            result.close();
            // Close the database connection
            dbaBean.close();

        } catch (SQLException SQLEx) {
            logger.warn("Some problem with the SQL");
            logger.warn(SQLEx);
        }

        return listOfTags;
    }

    // Perforn the update commands
    private int processUpdate() {
        int result = dbaBean.executeUpdate();
        dbaBean.close();
        return result;
    }

    public LinkedList<TagBean> getAuthorTags(String author) {
        try {
            dbaBean.setPrepStmt(dbaBean.getConn().prepareStatement("" +
                    "SELECT tag, count(tag) as timesused " +
                    "FROM articles, articletags " +
                    "WHERE articles.author = ? " +
                    "AND articles.id = articletags.id " +
                    "GROUP BY tag;"));
            dbaBean.getPrepStmt().setString(1, author);
        } catch (SQLException SQLEx) {
            logger.fatal(SQLEx);
        }
        return processQuery();
    }

    public LinkedList<TagBean> getArticleTags() {
        try {
            dbaBean.setPrepStmt(dbaBean.getConn().prepareStatement(
                    "SELECT tag, count(tag) as timesused " +
                    "FROM articles, articletags " +
                    "WHERE articles.id = ? " +
                    "AND articles.id = articletags.id " +
                    "GROUP BY tag;"));
            dbaBean.getPrepStmt().setString(1, articleID);
        } catch (SQLException SQLEx) {
            logger.fatal(SQLEx);
        }
        return processQuery();
    }

    public LinkedList<TagBean> getAllTags() {
        try {
            dbaBean.setPrepStmt(dbaBean.getConn().prepareStatement(
                    "SELECT tag, count(tag) as timesused " +
                    "FROM articletags " +
                    "GROUP BY tag;"));
        } catch (SQLException SQLEx) {
            logger.fatal(SQLEx);
        }
        return processQuery();
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getArticleID() {
        return articleID;
    }

    public void setArticleID(String articleID) {
        this.articleID = articleID;
    }
}