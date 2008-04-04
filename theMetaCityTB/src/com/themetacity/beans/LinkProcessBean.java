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

    private DatabaseBean dbaBean;

    String linkID;
    String linkURL;
    String descText;

    public LinkProcessBean() {
        dbaBean = new DatabaseBean();

        linkID = "";
        linkURL = "";
        descText = "";
    }

    // Perform the queries
    private LinkedList<LinkBean> processQuery() {
        LinkedList<LinkBean> noticeList = new LinkedList<LinkBean>();

        // The statment is already set in the calling method
        ResultSet result = dbaBean.executeQuery();

        try {
            while (result.next()) {
                LinkBean linkBean = new LinkBean();

                linkBean.setLinkID("" + result.getInt("linkID"));
                linkBean.setLinkURL(result.getString("linkURL"));
                linkBean.setDescText(result.getString("descText"));
                linkBean.setDatePosted(result.getString("datePosted"));

                noticeList.add(linkBean);
            }
            // Close the Result Set
            result.close();
            // Close the database connection
            dbaBean.close();
        } catch (SQLException SQLEX) {
            logger.warn("There as an error in the LinkProcessBean");
            logger.warn(SQLEX);
        }

        return noticeList;
    }

    // Perforn the update commands
    private int processUpdate() {
        return dbaBean.executeUpdate();
    }

    /**
     * Get the lists of front page Links
     * <b>Hardcoded to max 5 at this point in time</b>
     *
     * @return LinkedList with linktype benas in it
     */
    public LinkedList<LinkBean> getFrontPageLinks() {

        try {
            dbaBean.setPrepStmt(dbaBean.getConn().prepareStatement("SELECT * FROM links ORDER BY linkid desc LIMIT 5;"));
        } catch (SQLException SQLEx) {
            logger.fatal(SQLEx);
        }

        return processQuery();
    }

    /**
     * @return LinkedList with every link<linkbean> in it
     */
    public LinkedList<LinkBean> getAllLinks() {
        try {
            dbaBean.setPrepStmt(dbaBean.getConn().prepareStatement("SELECT * FROM links ORDER BY linkid desc;"));
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
            dbaBean.setPrepStmt(dbaBean.getConn().prepareStatement("DELETE FROM links WHERE linkid = ?;"));
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
            dbaBean.setPrepStmt(dbaBean.getConn().prepareStatement("INSERT INTO links (linkURL, descText, datePosted) VALUES (?, ?, ?);"));
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
            dbaBean.setPrepStmt(dbaBean.getConn().prepareStatement("UPDATE links SET linkURL = ?, descText = ? WHERE linkID = ?;"));
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
