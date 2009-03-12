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
    DatabaseBean dbaBean = new DatabaseBean();

    private String user = "";
    private String articleID = "";

    static Logger logger = Logger.getLogger(TagProcessBean.class);

    public TagProcessBean() {
    }

    /**
     * Get all the tags the the author has posted under
     *
     * @return a linked list of <TagBean> that belong to the given user
     */
    public LinkedList<TagBean> getAuthorTags() {
        LinkedList<TagBean> listOfTags = new LinkedList<TagBean>();
        DatabaseBean dbaBean = new DatabaseBean();

        try {
            dbaBean.setPrepStmt(dbaBean.getConn().prepareStatement(
                    "SELECT tag, count(tag) as timesused " +
                            "FROM articles, articletags " +
                            "WHERE articles.author = ? " +
                            "AND articles.id = articletags.id " +
                            "GROUP BY tag;"));
            dbaBean.getPrepStmt().setString(1, user);

            ResultSet result = dbaBean.executeQuery();

            while (result.next()) {
                TagBean tagBean = new TagBean();

                tagBean.setTag(result.getString("tag"));
                tagBean.setNumTimesTagUsed(result.getInt("timesused"));

                listOfTags.add(tagBean);
            }
        } catch (SQLException SQLEx) {
            logger.warn("You had an error in TagProcessBean()");
            logger.warn(SQLEx);
        } finally {
            dbaBean.close();
        }
        return listOfTags;
    }

    /**
     * Get all the tags posted for an article
     *
     * @return a list of <TagBeans> belonging to an article
     */
    public LinkedList<TagBean> getArticleTags() {
        LinkedList<TagBean> listOfTags = new LinkedList<TagBean>();
        DatabaseBean dbaBean = new DatabaseBean();

        try {
            dbaBean.setPrepStmt(dbaBean.getConn().prepareStatement(
                    "SELECT tag, count(tag) as timesused " +
                            "FROM articles, articletags " +
                            "WHERE articles.id = ? " +
                            "AND articles.id = articletags.id " +
                            "GROUP BY tag;"));
            dbaBean.getPrepStmt().setString(1, articleID);

            ResultSet result = dbaBean.executeQuery();

            while (result.next()) {
                TagBean tagBean = new TagBean();

                tagBean.setTag(result.getString("tag"));
                tagBean.setNumTimesTagUsed(result.getInt("timesused"));

                listOfTags.add(tagBean);
            }
        } catch (SQLException SQLEx) {
            logger.warn("You had an error in TagProcessBean()");
            logger.warn(SQLEx);
        } finally {
            dbaBean.close();
        }
        return listOfTags;
    }

    /**
     * Get all lthe tags (useful for the admin)
     *
     * @return all the tags in the database
     */
    public LinkedList<TagBean> getAllTags() {
        LinkedList<TagBean> listOfTags = new LinkedList<TagBean>();
        DatabaseBean dbaBean = new DatabaseBean();

        try {
            dbaBean.setPrepStmt(dbaBean.getConn().prepareStatement(
                    "SELECT tag, count(tag) as timesused " +
                            "FROM articletags " +
                            "GROUP BY tag;"));

            ResultSet result = dbaBean.executeQuery();

            while (result.next()) {
                TagBean tagBean = new TagBean();

                tagBean.setTag(result.getString("tag"));
                tagBean.setNumTimesTagUsed(result.getInt("timesused"));

                listOfTags.add(tagBean);
            }
        } catch (SQLException SQLEx) {
            logger.warn("You had an error in TagProcessBean()");
            logger.warn(SQLEx);
        } finally {
            dbaBean.close();
        }
        return listOfTags;
    }

    /**
     * Get all lthe tags (useful for the admin)
     *
     * @return all the tags in the database
     */
    public LinkedList<TagBean> getSiteMapTags() {
        LinkedList<TagBean> listOfTags = new LinkedList<TagBean>();
        DatabaseBean dbaBean = new DatabaseBean();

        try {
            dbaBean.setPrepStmt(dbaBean.getConn().prepareStatement(
                    "SELECT articletags.tag, MAX(articles.timestamp) as latest "
                            + "FROM articles, articletags "
                            + "WHERE articletags.id = articles.id "
                            + "GROUP BY articletags.tag"));

            ResultSet result = dbaBean.executeQuery();

            while (result.next()) {
                TagBean tagBean = new TagBean();

                tagBean.setTag(result.getString("tag"));
                tagBean.setLastUpdatedDate(result.getDate("latest"));

                listOfTags.add(tagBean);
            }
        } catch (SQLException SQLEx) {
            logger.warn("You had an error in TagProcessBean().getSiteMapTags()");
            logger.warn(SQLEx);
        } finally {
            dbaBean.close();
        }
        return listOfTags;
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