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
    private String id = "";

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
        ResultSet result = null;

        try {
            dbaBean.setPrepStmt(dbaBean.getConn().prepareStatement(
                    "SELECT tag, count(tag) as timesused " +
                            "FROM articles, articletags " +
                            "WHERE articles.author = ? " +
                            "AND articles.id = articletags.id " +
                            "GROUP BY tag;"));
            dbaBean.getPrepStmt().setString(1, user);

            result = dbaBean.executeQuery();

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
            if (result != null) {
                try {
                    result.close();
                } catch (SQLException SQLEx) {
                    logger.warn("You had an error closing the ResultSet in TagProcessBean().getAuthorTags()");
                    logger.warn(SQLEx);
                }
            }
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
        ResultSet result = null;

        try {
            dbaBean.setPrepStmt(dbaBean.getConn().prepareStatement(
                    "SELECT tag, count(tag) as timesused " +
                            "FROM articles, articletags " +
                            "WHERE TO_CHAR(articles.id, '99' ) = ? " +
                            "AND articles.id = articletags.id " +
                            "GROUP BY tag;"));
            dbaBean.getPrepStmt().setString(1, id);

            result = dbaBean.executeQuery();

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
            if (result != null) {
                try {
                    result.close();
                } catch (SQLException SQLEx) {
                    logger.warn("You had an error closing the ResultSet in TagProcessBean().getArticleTags()");
                    logger.warn(SQLEx);
                }
            }
            dbaBean.close();
        }
        return listOfTags;
    }

    /**
     * Get all the tags (useful for the admin)
     *
     * @return all the tags in the database
     */
    public LinkedList<TagBean> getAllTags() {
        LinkedList<TagBean> listOfTags = new LinkedList<TagBean>();
        DatabaseBean dbaBean = new DatabaseBean();
        ResultSet result = null;

        try {
            dbaBean.setPrepStmt(dbaBean.getConn().prepareStatement(
                    "SELECT tag, count(tag) as timesused " +
                            "FROM articletags " +
                            "GROUP BY tag;"));

            result = dbaBean.executeQuery();

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
            if (result != null) {
                try {
                    result.close();
                } catch (SQLException SQLEx) {
                    logger.warn("You had an error closing the ResultSet in TagProcessBean().getAllTags()");
                    logger.warn(SQLEx);
                }
            }
            dbaBean.close();
        }
        return listOfTags;
    }

    //todo check this out for names and purpose etc
    /**
     * Get all the tags (useful for the admin)
     *
     * @return all the tags in the database
     */
    public LinkedList<TagBean> getSitemapTags() {
        LinkedList<TagBean> listOfTags = new LinkedList<TagBean>();
        DatabaseBean dbaBean = new DatabaseBean();
        ResultSet result = null;

        try {
            dbaBean.setPrepStmt(dbaBean.getConn().prepareStatement(
                    "SELECT articletags.tag, MAX(articles.date_modified) as latest "
                            + "FROM articles, articletags "
                            + "WHERE articletags.id = articles.id "
                            + "GROUP BY articletags.tag"));

            result = dbaBean.executeQuery();

            while (result.next()) {
                TagBean tagBean = new TagBean();

                tagBean.setTag(result.getString("tag"));
                tagBean.setLastUpdatedDate(result.getDate("latest"));

                listOfTags.add(tagBean);
            }
        } catch (SQLException SQLEx) {
            logger.warn("You had an error in TagProcessBean().getSitemapTags()");
            logger.warn(SQLEx);
        } finally {
            if (result != null) {
                try {
                    result.close();
                } catch (SQLException SQLEx) {
                    logger.warn("You had an error closing the ResultSet in TagProcessBean().getSiteMapTags()");
                    logger.warn(SQLEx);
                }
            }
            dbaBean.close();
        }
        return listOfTags;
    }

    /**
     * Get all the tags posted for a workshop article
     *
     * @return a list of <TagBeans> belonging to an article
     */
    public LinkedList<TagBean> getWorkshopTags() {
        LinkedList<TagBean> listOfTags = new LinkedList<TagBean>();
        DatabaseBean dbaBean = new DatabaseBean();
        ResultSet result = null;

        try {
            dbaBean.setPrepStmt(dbaBean.getConn().prepareStatement(
                    "SELECT tag " +
                            "FROM workshop, workshoptags " +
                            "WHERE workshop.id = TO_NUMBER(?, '999') " +
                            "AND workshop.id = workshoptags.id " +
                            "GROUP BY tag;"));
            dbaBean.getPrepStmt().setString(1, id);

            result = dbaBean.executeQuery();

            while (result.next()) {
                TagBean tagBean = new TagBean();

                tagBean.setTag(result.getString("tag"));

                listOfTags.add(tagBean);
            }
        } catch (SQLException SQLEx) {
            logger.warn("You had an error in TagProcessBean().getWorkshopTags()");
            logger.warn(SQLEx);
        } finally {
            if (result != null) {
                try {
                    result.close();
                } catch (SQLException SQLEx) {
                    logger.warn("You had an error closing the ResultSet in TagProcessBean().getWorkshopTags()");
                    logger.warn(SQLEx);
                }
            }
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}