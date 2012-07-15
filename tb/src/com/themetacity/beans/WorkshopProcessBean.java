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
     * Get a specific workshop entry
     *
     * @return a list of workshop entries
     */
    public LinkedList<WorkshopBean> getWorkshopEntry() {
        DatabaseBean workshopDBBean = new DatabaseBean();
        LinkedList<WorkshopBean> listOfBeans = new LinkedList<WorkshopBean>();
        ResultSet result = null;

        try {
            workshopDBBean.setPrepStmt(workshopDBBean.getConn().prepareStatement(
                    "SELECT id, title, article_text, date_created, date_modified " +
                            "FROM workshop " +
                            "WHERE id = TO_NUMBER(?, '999') " +
                            "ORDER BY id desc;"));
            workshopDBBean.getPrepStmt().setString(1, id);

            result = workshopDBBean.executeQuery();

            while (result.next()) {
                WorkshopBean workshopBean = new WorkshopBean();

                workshopBean.setId(result.getInt("id"));
                workshopBean.setTitle(result.getString("title"));
                workshopBean.setContent(result.getString("article_text"));
                workshopBean.setCreatedDate(result.getDate("date_created"));
                workshopBean.setModifiedDate(result.getDate("date_modified"));

                // Process this workshops tags
                TagProcessBean tagProcessBean = new TagProcessBean();
                tagProcessBean.setId("" + result.getInt("id"));
                workshopBean.setTags(tagProcessBean.getWorkshopTags());

                listOfBeans.add(workshopBean);
            }
        } catch (SQLException SQLEx) {
            logger.warn("You had an error in WorkshopProcessBean.getWorkshopEntries()");
            logger.warn(SQLEx);
            logger.info(SQLEx.getCause());
        } finally {
            if (result != null) {
                try {
                    result.close();
                } catch (SQLException SQLEx) {
                    logger.warn("You had an error closing the ResultSet in WorkshopProcessBean().getWorkshopEntry()");
                    logger.warn(SQLEx);
                    logger.info(SQLEx.getCause());
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
                    logger.info(SQLEx.getCause());
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
                    "SELECT id, title, blurb, date_created, date_modified " +
                            "FROM workshop " +
                            "ORDER BY id desc;"));

            result = workshopDBBean.executeQuery();

            while (result.next()) {
                WorkshopBean workshopBean = new WorkshopBean();
                workshopBean.setId(result.getInt("id"));
                workshopBean.setTitle(result.getString("title"));
                workshopBean.setBlurb(result.getString("blurb"));
                workshopBean.setCreatedDate(result.getDate("date_created"));
                workshopBean.setModifiedDate(result.getDate("date_modified"));

                // Process this workshops tags
                TagProcessBean tagProcessBean = new TagProcessBean();
                tagProcessBean.setId("" + result.getInt("id"));
                workshopBean.setTags(tagProcessBean.getWorkshopTags());

                listOfBeans.add(workshopBean);
            }
        } catch (SQLException SQLEx) {
            logger.warn("You had an error in WorkshopProcessBean.getWorkshopBlurbs()");
            logger.info(SQLEx);
        } finally {
            if (result != null) {
                try {
                    result.close();
                } catch (SQLException SQLEx) {
                    logger.warn("You had an error closing the ResultSet in WorkshopProcessBean().getWorkshopBlurbs()");
                    logger.info(SQLEx);
                    logger.info(SQLEx.getCause());
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
                    "SELECT MAX(date_modified) as timestamp " +
                            "FROM workshop;");

            workshopDBBean.setPrepStmt(prepStmt);

            result = workshopDBBean.executeQuery();

            while (result.next()) {
                lastModified = result.getTimestamp("timestamp");    // Query only returns 1 result (due to MAX);
            }

        } catch (SQLException SQLEx) {
            logger.warn("You had an error in WorkshopProcessBean.getLastUpdateDate()");
            logger.info(SQLEx);
        } finally {
            if (result != null) {
                try {
                    result.close();
                } catch (SQLException SQLEx) {
                    logger.warn("You had an error closing the ResultSet in WorkshopProcessBean.getLastUpdateDate()");
                    logger.info(SQLEx);
                    logger.info(SQLEx.getCause());
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
