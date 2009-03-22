package com.themetacity.beans;

import com.themetacity.typebeans.ImportantNoticeBean;
import com.themetacity.utilities.DatabaseBean;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.util.LinkedList;

import org.apache.log4j.Logger;

public class ImportantNoticeProcessBean {

    private LinkedList<ImportantNoticeBean> noticeList = new LinkedList<ImportantNoticeBean>();

    private static final Logger logger = Logger.getLogger(ImportantNoticeProcessBean.class);

    DatabaseBean dbaBean = new DatabaseBean();

    private String messageID = "";
    private String author = "";
    private String message = "";
    private String dateTo = "";
    private String dateFrom = "";

    public ImportantNoticeProcessBean() {
    }

    // Perforn the update commands
    private int processUpdate() {
        int result = dbaBean.executeUpdate();
        dbaBean.close();
        return result;
    }

    public LinkedList<ImportantNoticeBean> getImportantNotices() {

        DatabaseBean noticesDBBean = new DatabaseBean();
        LinkedList<ImportantNoticeBean> listOfNotices = new LinkedList<ImportantNoticeBean>();

        try {
            PreparedStatement preStmt = noticesDBBean.getConn().prepareStatement("SELECT pseudonym, message, date_to, date_from " +
                    "FROM importantnotices, users " +
                    "WHERE importantnotices.username = users.username " +
                    "AND NOW() >= date_from AND NOW() <= date_to;");

            noticesDBBean.setPrepStmt(preStmt);
            ResultSet result = noticesDBBean.executeQuery();

            while (result.next()) {
                ImportantNoticeBean noticeBean = new ImportantNoticeBean();

                noticeBean.setAuthor(result.getString("pseudonym"));
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
            noticesDBBean.close();
        }
        return listOfNotices;
    }

    public LinkedList<ImportantNoticeBean> getAllImportantNotices() {

        DatabaseBean noticesDBBean = new DatabaseBean();
        LinkedList<ImportantNoticeBean> listOfNotices = new LinkedList<ImportantNoticeBean>();

        try {
            PreparedStatement preStmt = noticesDBBean.getConn().prepareStatement(
                    "SELECT id, username, message, date_to, date_from " +
                            "FROM importantnotices;");

            noticesDBBean.setPrepStmt(preStmt);
            ResultSet result = noticesDBBean.executeQuery();

            while (result.next()) {
                ImportantNoticeBean noticeBean = new ImportantNoticeBean();

                noticeBean.setMessageID(result.getString("id"));
                noticeBean.setUsername(result.getString("username"));
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
            noticesDBBean.close();
        }
        return listOfNotices;
    }

    public LinkedList<ImportantNoticeBean> getImportantNoticeToEdit() {

        DatabaseBean noticesDBBean = new DatabaseBean();
        LinkedList<ImportantNoticeBean> listOfNotices = new LinkedList<ImportantNoticeBean>();

        try {
            PreparedStatement preStmt = noticesDBBean.getConn().prepareStatement(
                    "SELECT * FROM importantnotices WHERE id = ?;");
            preStmt.setString(1, messageID);

            noticesDBBean.setPrepStmt(preStmt);
            ResultSet result = noticesDBBean.executeQuery();

            while (result.next()) {
                ImportantNoticeBean noticeBean = new ImportantNoticeBean();

                noticeBean.setMessageID(result.getString("id"));
                noticeBean.setUsername(result.getString("username"));
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
            dbaBean.setPrepStmt(dbaBean.getConn().prepareStatement("INSERT INTO importantnotices (username, message, date_to, date_from) VALUES (?, ?, ?, ?);"));
            dbaBean.getPrepStmt().setString(1, author);
            dbaBean.getPrepStmt().setString(2, message);
            dbaBean.getPrepStmt().setString(3, dateTo);
            dbaBean.getPrepStmt().setString(4, dateFrom);
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
            dbaBean.setPrepStmt(dbaBean.getConn().prepareStatement("UPDATE importantnotices SET username = ?, message = ?, date_to = ?, date_from = ? WHERE id = ?;"));
            dbaBean.getPrepStmt().setString(1, author);
            dbaBean.getPrepStmt().setString(2, message);
            dbaBean.getPrepStmt().setString(3, dateTo);
            dbaBean.getPrepStmt().setString(4, dateFrom);
            dbaBean.getPrepStmt().setString(5, messageID);
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

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
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