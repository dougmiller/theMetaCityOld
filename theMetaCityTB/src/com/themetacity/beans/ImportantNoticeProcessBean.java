package com.themetacity.beans;

import com.themetacity.typebeans.ImportantNoticeBean;
import com.themetacity.utilities.DatabaseBean;

import java.sql.ResultSet;
import java.sql.SQLException;
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

    private LinkedList<ImportantNoticeBean> processQuery() {

        ResultSet result = dbaBean.executeQuery();

        try {
            while (result.next()) {
                ImportantNoticeBean noticeBean = new ImportantNoticeBean();

                try {
                    noticeBean.setMessageID(result.getString("id"));
                } catch (SQLException SQLEx) {
                    // munch this, kinda bad
                }
                try {
                    noticeBean.setAuthor(result.getString("pseudonym"));
                } catch (SQLException SQLEx) {
                    // munch this, kinda bad
                }
                noticeBean.setMessage(result.getString("message"));
                noticeBean.setDateTo(result.getDate("date_to"));
                noticeBean.setDateFrom(result.getDate("date_from"));

                noticeList.add(noticeBean);
            }
            // Close the Result Set
            result.close();
            // Close the database connection
            dbaBean.close();
        } catch (SQLException SQLEX) {
            logger.warn("There as an error in the ImportantNoticeProcessBean");
            logger.warn(SQLEX);
        }

        return noticeList;
    }

    // Perforn the update commands
    private int processUpdate() {
        int result = dbaBean.executeUpdate();
        dbaBean.close();
        return result;
    }

    public LinkedList<ImportantNoticeBean> getImportantNotices() {
        try {
            dbaBean.setPrepStmt(dbaBean.getConn().prepareStatement("SELECT pseudonym, message, date_to, date_from " +
                    "FROM importantnotices, users " +
                    "WHERE importantnotices.username = users.username " +
                    "AND NOW() >= date_from AND NOW() <= date_to;"));
        } catch (SQLException SQLEx) {
            logger.fatal(SQLEx);
        }

        return processQuery();
    }

    public LinkedList<ImportantNoticeBean> getAllImportantNotices() {
        try {
            dbaBean.setPrepStmt(dbaBean.getConn().prepareStatement("SELECT * FROM importantnotices;"));
        } catch (SQLException SQLEx) {
            logger.fatal(SQLEx);
        }

        return processQuery();
    }

    public LinkedList<ImportantNoticeBean> getImportantNoticeToEdit() {
        try {
            dbaBean.setPrepStmt(dbaBean.getConn().prepareStatement("SELECT * FROM importantnotices WHERE id = ?;"));
            dbaBean.getPrepStmt().setString(1, messageID);
        } catch (SQLException SQLEx) {
            logger.fatal(SQLEx);
        }

        return processQuery();
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
