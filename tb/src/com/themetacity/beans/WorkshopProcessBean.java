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

    /**
     * Get the full list of workshop entries
     *
     * @return a list of workshop entries
     */
    public LinkedList<WorkshopBean> getWorkshopEntries() {

        DatabaseBean workshopDBBean = new DatabaseBean();
        LinkedList<WorkshopBean> listOfBeans = new LinkedList<WorkshopBean>();

        try {    //todo get the database done
            PreparedStatement prepStmt = workshopDBBean.getConn().prepareStatement(
                    "SELECT id, title, content " +
                            "FROM wokshop " +
                            "ORDER BY id desc;");

            workshopDBBean.setPrepStmt(prepStmt);

            ResultSet result = workshopDBBean.executeQuery();

            while (result.next()) {
                WorkshopBean workshopBean = new WorkshopBean();

                workshopBean.setTitle(result.getString("title"));
                workshopBean.setContent(result.getString("content"));

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

}
