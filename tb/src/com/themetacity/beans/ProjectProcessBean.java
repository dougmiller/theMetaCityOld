package com.themetacity.beans;

import com.themetacity.typebeans.ProjectBean;
import com.themetacity.utilities.DatabaseBean;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

import org.apache.log4j.Logger;

/**
 * This class process the Projects in the side bar.
 */
public class ProjectProcessBean {

    static Logger logger = Logger.getLogger(ProjectProcessBean.class);

    private DatabaseBean dbaBean = new DatabaseBean();

    String projectID = "";
    String projectURL = "";
    String descText = "";
    String isValid = "";

    public ProjectProcessBean() {
    }

    // Perforn the update commands
    private int processUpdate() {
        int result = dbaBean.executeUpdate();
        dbaBean.close();
        return result;
    }

    /**
     * Get the lists of front page Projects
     * <b>Hardcoded to max 5 at this point in time</b>
     *
     * @return LinkedList with ProjectBean in it
     */
    public LinkedList<ProjectBean> getFrontPageProjects() {
        DatabaseBean dbaBean = new DatabaseBean();
        LinkedList<ProjectBean> projectsList = new LinkedList<ProjectBean>();

        try {
            dbaBean.setPrepStmt(dbaBean.getConn().prepareStatement(
                    "SELECT URL, desc_text " +
                            "FROM projects " +
                            "WHERE is_valid = true " +
                            "ORDER BY id desc LIMIT 5;"));
        } catch (SQLException SQLEx) {
            logger.fatal(SQLEx);
        }

        ResultSet result = dbaBean.executeQuery();
        try {
            while (result.next()) {
                ProjectBean projectBean = new ProjectBean();

                projectBean.setProjectURL(result.getString("URL"));
                projectBean.setDescText(result.getString("desc_text"));
                
                projectsList.add(projectBean);
            }
        } catch (SQLException SQLEx) {
            logger.fatal(SQLEx);
        } finally {
            dbaBean.close();
        }

        return projectsList;
    }

    /**
     * @return LinkedList with every project <ProjectBean> in it
     */
    public LinkedList<ProjectBean> getAllProjects() {
        DatabaseBean dbaBean = new DatabaseBean();
        LinkedList<ProjectBean> projectsList = new LinkedList<ProjectBean>();

        try {
            dbaBean.setPrepStmt(dbaBean.getConn().prepareStatement(
                    "SELECT id, URL, desc_text, is_valid " +
                            "FROM projects " +
                            "ORDER BY id desc"));
        } catch (SQLException SQLEx) {
            logger.fatal(SQLEx);
        }

        ResultSet result = dbaBean.executeQuery();
        try {
            while (result.next()) {
                ProjectBean projectBean = new ProjectBean();

                projectBean.setProjectURL(result.getString("URL"));
                projectBean.setDescText(result.getString("desc_text"));
                projectBean.setValid(result.getString("is_valid"));

                projectsList.add(projectBean);
            }
        } catch (SQLException SQLEx) {
            logger.fatal(SQLEx);
        } finally {
            dbaBean.close();
        }

        return projectsList;
    }


    /**
     * Delete a link
     *
     * @return the number of rows affected
     */
    public int getDeleteProjct() {
        try {
            dbaBean.setPrepStmt(dbaBean.getConn().prepareStatement(
                    "DELETE FROM projects " +
                    "WHERE id = ?;"));
            dbaBean.getPrepStmt().setInt(1, Integer.parseInt(projectID));
        } catch (SQLException SQLEx) {
            logger.fatal(SQLEx);
        }

        return processUpdate();
    }

    /**
     * Add a link
     *
     * @return 1 if successful, 0 if unseccessful (rows added to db)
     */
    public int getAddProject() {
        try {
            dbaBean.setPrepStmt(dbaBean.getConn().prepareStatement(
                    "INSERT INTO projects (URL, desc_text, is_valid) " +
                    "VALUES (?, ?, ?);"));
            dbaBean.getPrepStmt().setString(1, projectURL);
            dbaBean.getPrepStmt().setString(2, descText);
            dbaBean.getPrepStmt().setDate(3, new java.sql.Date(System.currentTimeMillis()));
        } catch (SQLException SQLEx) {
            logger.fatal(SQLEx);
        }

        return processUpdate();
    }

    /**
     * Add a link
     *
     * @return 1 if successful, 0 if unseccessful (rows added to db)
     */
    public int getUpdateProject() {

        try {
            dbaBean.setPrepStmt(dbaBean.getConn().prepareStatement(
                    "UPDATE projects " +
                    "SET URL = ?, desc_text = ?, is_valid = ? " +
                    "WHERE id = ?;"));
            dbaBean.getPrepStmt().setString(1, projectURL);
            dbaBean.getPrepStmt().setString(2, descText);
            dbaBean.getPrepStmt().setString(3, isValid);
            dbaBean.getPrepStmt().setString(4, projectID);
        } catch (SQLException SQLEx) {
            logger.fatal(SQLEx);
        }

        return processUpdate();
    }

    public String getProjectID() {
        return projectID;
    }

    public void setProjectID(String projectID) {
        this.projectID = projectID;
    }

    public String getProjectURL() {
        return projectURL;
    }

    public void setProjectURL(String projectURL) {
        this.projectURL = projectURL;
    }

    public String getDescText() {
        return descText;
    }

    public void setDescText(String descText) {
        this.descText = descText;
    }

    public String getValid() {
        return isValid;
    }

    public void setValid(String valid) {
        isValid = valid;
    }
}
