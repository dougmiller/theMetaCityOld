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

    public ImportantNoticeProcessBean() {
    }

    private LinkedList<ImportantNoticeBean> processQuery() {

        ResultSet result = dbaBean.executeQuery();

        try {
            while (result.next()) {
                ImportantNoticeBean noticeBean = new ImportantNoticeBean();

                noticeBean.setAuthor(result.getString("pseudonym"));
                noticeBean.setMessage(result.getString("message"));
                noticeBean.setDateTo(result.getDate("dateto"));
                noticeBean.setDateFrom(result.getDate("datefrom"));

                noticeList.add(noticeBean);
            }
            // Close the Result Set
            result.close();
            // Close the database connection
            dbaBean.close();
        } catch (SQLException SQLEX) {
            logger.warn("There as an error in the NoticeProcessBean");
            logger.warn(SQLEX);
        }

        return noticeList;
    }

    public LinkedList<ImportantNoticeBean> getImportantNotices(){
        try {
             dbaBean.setPrepStmt(dbaBean.getConn().prepareStatement("SELECT pseudonym, message FROM importantNotices, users WHERE importantNotices.username = users.username AND NOW() >= dateFrom AND NOW() <= dateTo;"));
        } catch (SQLException SQLEx) {
            logger.fatal(SQLEx);
        }

        return processQuery();
    }
}
