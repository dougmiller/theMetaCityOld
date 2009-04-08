package com.themetacity.beans;

import org.apache.log4j.Logger;
import com.themetacity.utilities.DatabaseBean;
import com.themetacity.typebeans.WorkshopBean;

import java.util.LinkedList;
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
        try {
            workshopDBBean.setPrepStmt(workshopDBBean.getConn().prepareStatement(
                    "SELECT id, title, article_text, date_time, timestamp " +
                            "FROM workshop " +
                            "WHERE id = ? " +
                            "ORDER BY id desc;"));
            workshopDBBean.getPrepStmt().setString(1, id);

            ResultSet result = workshopDBBean.executeQuery();
            while (result.next()) {
                WorkshopBean workshopBean = new WorkshopBean();
                workshopBean.setId(result.getInt("id"));
                workshopBean.setTitle(result.getString("title"));
                workshopBean.setContent(result.getString("article_text"));
                workshopBean.setDateTime(result.getDate("date_time"));
                workshopBean.setTimestamp(result.getTimestamp("timestamp"));

                // Process this workshops tags
                TagProcessBean tagProcessBean = new TagProcessBean();
                tagProcessBean.setId("" + result.getInt("id"));     // Set the user in the TagProcessBean *cast to String*
                workshopBean.setTags(tagProcessBean.getWorkshopTags());      // Assign the results to the bean

                listOfBeans.add(workshopBean);
            }
        } catch (SQLException SQLEx) {
            logger.warn("You had an error in WorkshopProcessBean.getWorkshopEntries()");
            logger.warn(SQLEx);
        } finally {
            workshopDBBean.close();
        }
        return listOfBeans;
    }

    /**
     * Get the links to the workshop entries
     * @return a linked list of WorkshopBeans, mapped to the databased relations
     */
    public LinkedList<WorkshopBean> getWorkshopLinks() {

        DatabaseBean workshopDBBean = new DatabaseBean();
        LinkedList<WorkshopBean> listOfBeans = new LinkedList<WorkshopBean>();

        try {
            workshopDBBean.setPrepStmt(workshopDBBean.getConn().prepareStatement(
                    "SELECT id, title " +
                            "FROM workshop " +
                            "ORDER BY id desc;"));

            ResultSet result = workshopDBBean.executeQuery();

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
            workshopDBBean.close();
        }
        return listOfBeans;
    }

    /**
     * Get the blurb entries for the workshop entries
     * @return a linked list of WorkshopBeans, mapped to the databased relations
     */
    public LinkedList<WorkshopBean> getWorkshopBlurbs() {

        DatabaseBean workshopDBBean = new DatabaseBean();
        LinkedList<WorkshopBean> listOfBeans = new LinkedList<WorkshopBean>();
        try {
            workshopDBBean.setPrepStmt(workshopDBBean.getConn().prepareStatement(
                    "SELECT id, title, blurb, date_time, timestamp " +
                            "FROM workshop " +
                            "ORDER BY id desc;"));

            ResultSet result = workshopDBBean.executeQuery();
            while (result.next()) {
                WorkshopBean workshopBean = new WorkshopBean();
                workshopBean.setId(result.getInt("id"));
                workshopBean.setTitle(result.getString("title"));
                workshopBean.setBlurb(result.getString("blurb"));
                workshopBean.setDateTime(result.getDate("date_time"));
                workshopBean.setTimestamp(result.getTimestamp("timestamp"));

                // Process this workshops tags
                TagProcessBean tagProcessBean = new TagProcessBean();
                tagProcessBean.setId("" + result.getInt("id"));     // Set the user in the TagProcessBean *cast to String*
                workshopBean.setTags(tagProcessBean.getWorkshopTags());      // Assign the results to the bean

                listOfBeans.add(workshopBean);
            }
        } catch (SQLException SQLEx) {
            logger.warn("You had an error in WorkshopProcessBean.getWorkshopBlurbs()");
            logger.warn(SQLEx);
        } finally {
            workshopDBBean.close();
        }
        return listOfBeans;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
