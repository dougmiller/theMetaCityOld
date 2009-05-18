package com.themetacity.beans;

import org.apache.log4j.Logger;
import com.themetacity.utilities.DatabaseBean;
import com.themetacity.typebeans.WorkshopBean;

import java.util.LinkedList;
import java.util.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * This bean handles the workshop
 */
public class WorkshopProcessBean {

    static Logger logger = Logger.getLogger(WorkshopProcessBean.class);

    private String id = "";

    /**
     * Get the full list of workshop entries
     *
     * @return a list of workshop entries
     */
    public LinkedList<WorkshopBean> getWorkshopEntry() {
        DatabaseBean workshopDBBean = new DatabaseBean();
        LinkedList<WorkshopBean> listOfBeans = new LinkedList<WorkshopBean>();
        ResultSet result = null;

        try {
            workshopDBBean.setPrepStmt(workshopDBBean.getConn().prepareStatement(
                    "SELECT id, title, article_text, date_time, timestamp " +
                            "FROM workshop " +
                            "WHERE id = ? " +
                            "ORDER BY id desc;"));
            workshopDBBean.getPrepStmt().setString(1, id);

            result = workshopDBBean.executeQuery();

            while (result.next()) {
                WorkshopBean workshopBean = new WorkshopBean();

                workshopBean.setId(result.getInt("id"));
                workshopBean.setTitle(result.getString("title"));
                workshopBean.setContent(result.getString("article_text"));
                workshopBean.setDateTime(result.getDate("date_time"));
                workshopBean.setTimestamp(result.getDate("timestamp"));

                // Process this workshops tags
                TagProcessBean tagProcessBean = new TagProcessBean();
                tagProcessBean.setId("" + result.getInt("id"));
                workshopBean.setTags(tagProcessBean.getWorkshopTags());

                listOfBeans.add(workshopBean);
            }
        } catch (SQLException SQLEx) {
            logger.warn("You had an error in WorkshopProcessBean.getWorkshopEntries()");
            logger.warn(SQLEx);
        } finally {
            if (result != null) {
                try {
                    result.close();
                } catch (SQLException SQLEx) {
                    logger.warn("You had an error closing the ResultSet in WorkshopProcessBean().getWorkshopEntry()");
                    logger.warn(SQLEx);
                }
            }
            workshopDBBean.close();
        }
        return listOfBeans;
    }

    /**
     * Get the links to the workshop entries
     *
     * @return a linked list of WorkshopBeans, mapped to the databased relations
     */
    public LinkedList<WorkshopBean> getWorkshopLinks() {

        DatabaseBean workshopDBBean = new DatabaseBean();
        LinkedList<WorkshopBean> listOfBeans = new LinkedList<WorkshopBean>();
        ResultSet result = null;

        try {
            workshopDBBean.setPrepStmt(workshopDBBean.getConn().prepareStatement(
                    "SELECT id, title " +
                            "FROM workshop " +
                            "ORDER BY id desc;"));

            result = workshopDBBean.executeQuery();

            while (result.next()) {
                WorkshopBean workshopBean = new WorkshopBean();

                workshopBean.setTitle(result.getString("title"));
                workshopBean.setId(result.getInt("id"));

                listOfBeans.add(workshopBean);
            }
        } catch (SQLException SQLEx) {
            logger.warn("You had an error in WorkshopProcessBean.getWorkshopLinks()");
            logger.warn(SQLEx);
        } finally {
            if (result != null) {
                try {
                    result.close();
                } catch (SQLException SQLEx) {
                    logger.warn("You had an error closing the ResultSet in WorkshopProcessBean().getWorkshopLinks()");
                    logger.warn(SQLEx);
                }
            }
            workshopDBBean.close();
        }
        return listOfBeans;
    }

    /**
     * Get the blurb entries for the workshop entries, used in the index
     *
     * @return a linked list of WorkshopBeans, mapped to the databased relations
     */
    public LinkedList<WorkshopBean> getWorkshopBlurbs() {
        DatabaseBean workshopDBBean = new DatabaseBean();
        LinkedList<WorkshopBean> listOfBeans = new LinkedList<WorkshopBean>();
        ResultSet result = null;

        try {
            workshopDBBean.setPrepStmt(workshopDBBean.getConn().prepareStatement(
                    "SELECT id, title, blurb, date_time, timestamp " +
                            "FROM workshop " +
                            "ORDER BY id desc;"));

            result = workshopDBBean.executeQuery();

            while (result.next()) {
                WorkshopBean workshopBean = new WorkshopBean();
                workshopBean.setId(result.getInt("id"));
                workshopBean.setTitle(result.getString("title"));
                workshopBean.setBlurb(result.getString("blurb"));
                workshopBean.setDateTime(result.getDate("date_time"));
                workshopBean.setTimestamp(result.getDate("timestamp"));

                // Process this workshops tags
                TagProcessBean tagProcessBean = new TagProcessBean();
                tagProcessBean.setId("" + result.getInt("id"));
                workshopBean.setTags(tagProcessBean.getWorkshopTags());

                listOfBeans.add(workshopBean);
            }
        } catch (SQLException SQLEx) {
            logger.warn("You had an error in WorkshopProcessBean.getWorkshopBlurbs()");
            logger.warn(SQLEx);
        } finally {
            if (result != null) {
                try {
                    result.close();
                } catch (SQLException SQLEx) {
                    logger.warn("You had an error closing the ResultSet in WorkshopProcessBean().getWorkshopBlurbs()");
                    logger.warn(SQLEx);
                }
            }
            workshopDBBean.close();
        }
        return listOfBeans;
    }

    /**
     * Gets the date of the last updated article. Used in the sitemap to show when the articles section has been updated.
     *
     * @return a Date object.
     */
    public Date getLastUpdateDate() {
        DatabaseBean workshopDBBean = new DatabaseBean();
        ResultSet result = null;
        Date lastModified = new Date(0);

        try {
            PreparedStatement prepStmt = workshopDBBean.getConn().prepareStatement(
                    "SELECT max(timestamp) as timestamp " +
                            "FROM workshop;");

            workshopDBBean.setPrepStmt(prepStmt);

            result = workshopDBBean.executeQuery();

            while (result.next()) {
                lastModified = result.getTimestamp("timestamp");    // Query only returns 1 result;
            }

        } catch (SQLException SQLEx) {
            logger.warn("You had an error in WorkshopProcessBean.getLastUpdateDate()");
            logger.warn(SQLEx);
        } finally {
            if (result != null) {
                try {
                    result.close();
                } catch (SQLException SQLEx) {
                    logger.warn("You had an error closing the ResutlSet in WorkshopProcessBean.getLastUpdateDate()");
                    logger.warn(SQLEx);
                }
            }
            workshopDBBean.close();
        }

        return lastModified;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
