package com.themetacity.beans;

import com.themetacity.typebeans.LinkBean;
import com.themetacity.utilities.DatabaseBean;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

import org.apache.log4j.Logger;

/**
 * This class process the Links in the side bar.
 */
public class LinkProcessBean {

    static Logger logger = Logger.getLogger(LinkProcessBean.class);

    private DatabaseBean dbaBean = new DatabaseBean();

    String linkID = "";
    String linkURL = "";
    String descText = "";

    public LinkProcessBean() {
    }

    // Perform the queries
    private LinkedList<LinkBean> processQuery() {
        LinkedList<LinkBean> noticeList = new LinkedList<LinkBean>();

        // The statment is already set in the calling method
        ResultSet result = dbaBean.executeQuery();

        try {
            while (result.next()) {
                LinkBean linkBean = new LinkBean();

                linkBean.setLinkID("" + result.getInt("id"));
                linkBean.setLinkURL(result.getString("URL"));
                linkBean.setDescText(result.getString("desc_text"));
                linkBean.setDatePosted(result.getString("date_posted"));

                noticeList.add(linkBean);
            }
        } catch (SQLException SQLEX) {
            logger.warn("There as an error in the LinkProcessBean");
            logger.warn(SQLEX);
        } finally {
            try {// Close the Result Set
                result.close();
                // Close the database connection
                dbaBean.close();
            } catch (SQLException SQLEx) {
                logger.warn("Could not close DB connection");
                logger.warn(SQLEx);
            }
        }

        return noticeList;
    }

    // Perforn the update commands
    private int processUpdate() {
        int result = dbaBean.executeUpdate();
        dbaBean.close();
        return result;
    }

    /**
     * Get the lists of front page Links
     * <b>Hardcoded to max 5 at this point in time</b>
     *
     * @return LinkedList with linktype beans in it
     */
    public LinkedList<LinkBean> getFrontPageLinks() {
        DatabaseBean dbaBean = new DatabaseBean();
        LinkedList<LinkBean> linksList = new LinkedList<LinkBean>();

        try {
            dbaBean.setPrepStmt(dbaBean.getConn().prepareStatement(
                    "SELECT URL, desc_text " +
                            "FROM links " +
                            "ORDER BY id desc LIMIT 5;"));
        } catch (SQLException SQLEx) {
            logger.fatal(SQLEx);
        }

        ResultSet result = dbaBean.executeQuery();
        try {
            while (result.next()) {
                LinkBean linkBean = new LinkBean();

                linkBean.setLinkURL(result.getString("URL"));
                linkBean.setDescText(result.getString("desc_text"));
                linksList.add(linkBean);
            }
        } catch (SQLException SQLEx) {
            logger.fatal(SQLEx);
        } finally {
            dbaBean.close();
        }
        
        return linksList;
    }

    /**
     * @return LinkedList with every link<linkbean> in it
     */
    public LinkedList<LinkBean> getAllLinks() {
        try {
            dbaBean.setPrepStmt(dbaBean.getConn().prepareStatement("SELECT * FROM links ORDER BY id desc;"));
        } catch (SQLException SQLEx) {
            logger.fatal(SQLEx);
        }

        return processQuery();
    }


    /**
     * Delete a link
     *
     * @return the number of rows affected
     */
    public int getDeleteLink() {
        try {
            dbaBean.setPrepStmt(dbaBean.getConn().prepareStatement("DELETE FROM links WHERE id = ?;"));
            dbaBean.getPrepStmt().setInt(1, Integer.parseInt(linkID));
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
    public int getAddLink() {

        try {
            dbaBean.setPrepStmt(dbaBean.getConn().prepareStatement("INSERT INTO links (URL, desc_text, date_posted) VALUES (?, ?, ?);"));
            dbaBean.getPrepStmt().setString(1, linkURL);
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
    public int getUpdateLink() {

        try {
            dbaBean.setPrepStmt(dbaBean.getConn().prepareStatement("UPDATE links SET URL = ?, desc_text = ? WHERE id = ?;"));
            dbaBean.getPrepStmt().setString(1, linkURL);
            dbaBean.getPrepStmt().setString(2, descText);
            dbaBean.getPrepStmt().setString(3, linkID);
        } catch (SQLException SQLEx) {
            logger.fatal(SQLEx);
        }

        return processUpdate();
    }

    public String getLinkID() {
        return linkID;
    }

    public void setLinkID(String linkID) {
        this.linkID = linkID;
    }

    public String getLinkURL() {
        return linkURL;
    }

    public void setLinkURL(String linkURL) {
        this.linkURL = linkURL;
    }

    public String getDescText() {
        return descText;
    }

    public void setDescText(String descText) {
        this.descText = descText;
    }
}
