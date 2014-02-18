package com.themetacity.beans;

import com.themetacity.typebeans.ImportantNoticeBean;
import com.themetacity.utilities.DatabaseBean;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.util.LinkedList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ImportantNoticeProcessBean {

    private static final Logger logger = LogManager.getLogger(ImportantNoticeProcessBean.class);

    DatabaseBean dbaBean = new DatabaseBean();

    private String messageID = "";
    private String message = "";
    private String dateTo = "";
    private String dateFrom = "";

    public ImportantNoticeProcessBean() {
    }

    // Perform the update commands
    private int processUpdate() {
        int result = dbaBean.executeUpdate();
        dbaBean.close();
        return result;
    }

    public LinkedList<ImportantNoticeBean> getImportantNotices() {

        DatabaseBean noticesDBBean = new DatabaseBean();
        LinkedList<ImportantNoticeBean> listOfNotices = new LinkedList<ImportantNoticeBean>();
        ResultSet result = null;
        try {
            noticesDBBean.setPrepStmt(noticesDBBean.getConn().prepareStatement("SELECT message, date_to, date_from " +
                    "FROM importantnotices " +
                    "WHERE NOW() >= date_from " +
                    "AND NOW() <= date_to;"));

            result = noticesDBBean.executeQuery();

            while (result.next()) {
                ImportantNoticeBean noticeBean = new ImportantNoticeBean();

                noticeBean.setMessage(result.getString("message"));
                noticeBean.setDateTo(result.getDate("date_to"));
                noticeBean.setDateFrom(result.getDate("date_from"));

                listOfNotices.add(noticeBean);
            }          
        } catch (SQLException SQLEx) {
            logger.error("There was an error in ImportantNoticesBean.getImportantNotices()");
            logger.error(SQLEx);
        } finally {
            if (result != null) {
                try {
                    result.close();
                } catch (SQLException SQLEx) {
                    logger.warn("You had an error closing the ResultSet in ImportantNoticeProcessBean().getImportantNotices()");
                    logger.warn(SQLEx);
                }
            }
            noticesDBBean.close();
        }
        return listOfNotices;
    }

    public LinkedList<ImportantNoticeBean> getAllImportantNotices() {

        DatabaseBean noticesDBBean = new DatabaseBean();
        LinkedList<ImportantNoticeBean> listOfNotices = new LinkedList<ImportantNoticeBean>();
        ResultSet result = null;

        try {
            PreparedStatement preStmt = noticesDBBean.getConn().prepareStatement(
                    "SELECT id, message, date_to, date_from " +
                            "FROM importantnotices;");

            noticesDBBean.setPrepStmt(preStmt);
            result = noticesDBBean.executeQuery();

            while (result.next()) {
                ImportantNoticeBean noticeBean = new ImportantNoticeBean();

                noticeBean.setMessageID(result.getString("id"));
                noticeBean.setMessage(result.getString("message"));
                noticeBean.setDateTo(result.getDate("date_to"));
                noticeBean.setDateFrom(result.getDate("date_from"));

                listOfNotices.add(noticeBean);
            }

            result.close();

        } catch (SQLException SQLEx) {
            logger.error("There was an error in ImportantNoticesBean.getImportantNotices()");
            logger.error(SQLEx);
        } finally {
            if (result != null) {
                try {
                    result.close();
                } catch (SQLException SQLEx) {
                    logger.warn("You had an error closing the ResultSet in ImportantNoticeProcessBean().getAllImportantNotices()");
                    logger.warn(SQLEx);
                }
            }
            noticesDBBean.close();
        }
        return listOfNotices;
    }

    public LinkedList<ImportantNoticeBean> getImportantNoticeToEdit() {
        DatabaseBean noticesDBBean = new DatabaseBean();
        LinkedList<ImportantNoticeBean> listOfNotices = new LinkedList<ImportantNoticeBean>();
        ResultSet result = null;

        try {
            noticesDBBean.setPrepStmt(noticesDBBean.getConn().prepareStatement(
                    "SELECT * FROM importantnotices WHERE id = ?;"));
            noticesDBBean.getPrepStmt().setString(1, messageID);

            result = noticesDBBean.executeQuery();

            while (result.next()) {
                ImportantNoticeBean noticeBean = new ImportantNoticeBean();

                noticeBean.setMessageID(result.getString("id"));
                noticeBean.setMessage(result.getString("message"));
                noticeBean.setDateTo(result.getDate("date_to"));
                noticeBean.setDateFrom(result.getDate("date_from"));

                listOfNotices.add(noticeBean);
            }

            result.close();

        } catch (SQLException SQLEx) {
            logger.error("There was an error in ImportantNoticesBean.getImportantNotices()");
            logger.error(SQLEx);
        } finally {
            if (result != null) {
                try {
                    result.close();
                } catch (SQLException SQLEx) {
                    logger.warn("You had an error closing the ResultSet in ImportantNoticeProcessBean().getImportantNoticesToEdit()");
                    logger.warn(SQLEx);
                }
            }
            noticesDBBean.close();
        }
        return listOfNotices;
    }

    /**
     * Add an article
     *
     * @return True from succesfully adding a new article or fasle for not adding it
     */
    public int getAddMessage() {
        try {
            dbaBean.setPrepStmt(dbaBean.getConn().prepareStatement(
                    "INSERT INTO importantnotices (message, date_to, date_from) " +
                    "VALUES (?, ?, ?);"));
            dbaBean.getPrepStmt().setString(1, message);
            dbaBean.getPrepStmt().setString(2, dateTo);
            dbaBean.getPrepStmt().setString(3, dateFrom);
        } catch (SQLException SQLEx) {
            logger.fatal(SQLEx);
        }
        return processUpdate();
    }

    public int getDeleteMessage() {

        try {
            dbaBean.setPrepStmt(dbaBean.getConn().prepareStatement("DELETE FROM importantnotices WHERE id = ?;"));
            dbaBean.getPrepStmt().setString(1, messageID);
        } catch (SQLException SQLEx) {
            logger.fatal(SQLEx);
        }
        return processUpdate();
    }

    public int getUpdateMessage() {

        try {
            dbaBean.setPrepStmt(dbaBean.getConn().prepareStatement(
                    "UPDATE importantnotices SET message = ?, date_to = ?, date_from = ? WHERE id = ?;"));
            dbaBean.getPrepStmt().setString(1, message);
            dbaBean.getPrepStmt().setString(2, dateTo);
            dbaBean.getPrepStmt().setString(3, dateFrom);
            dbaBean.getPrepStmt().setString(4, messageID);
        } catch (SQLException SQLEx) {
            logger.fatal(SQLEx);
        }
        return processUpdate();
    }

    public String getMessageID() {
        return messageID;
    }

    public void setMessageID(String messageID) {
        this.messageID = messageID;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDateTo() {
        return dateTo;
    }

    public void setDateTo(String dateTo) {
        this.dateTo = dateTo;
    }

    public String getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(String dateFrom) {
        this.dateFrom = dateFrom;
    }
}
