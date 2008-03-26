package com.themetacity.beans;

import com.themetacity.typebeans.ImportantNoticeBean;
import com.themetacity.utilities.DatabaseBean;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

import org.apache.log4j.Logger;

public class ImportantNoticeProcessBean {

    LinkedList<ImportantNoticeBean> noticeList = new LinkedList<ImportantNoticeBean>();

    static Logger logger = Logger.getLogger(ImportantNoticeProcessBean.class);

    public ImportantNoticeProcessBean() {
    }

    public LinkedList<ImportantNoticeBean> getNotices() {
        DatabaseBean dbaBean = new DatabaseBean();

        ResultSet result = dbaBean.executeQuery(constructImportantNoticeQuery());

        try {
            while (result.next()) {
                ImportantNoticeBean noticeBean = new ImportantNoticeBean();

                noticeBean.setAuthor(result.getString("pseudonym"));
                noticeBean.setMessage(result.getString("message"));

                noticeList.add(noticeBean);
            }
            result.close();
            dbaBean.close();
        } catch (SQLException SQLEX) {
            logger.warn("There as an error in the NoticeProcessBean");
            logger.warn(SQLEX);
        }

        return noticeList;
    }

    private String constructImportantNoticeQuery() {
        return "SELECT pseudonym, message FROM importantNotices, users WHERE importantNotices.username = users.username AND NOW() >= dateFrom AND NOW() <= dateTo;";
    }

    public LinkedList<ImportantNoticeBean> getNoticeList() {
        return noticeList;
    }

    public void setNoticeList(LinkedList<ImportantNoticeBean> noticeList) {
        this.noticeList = noticeList;
    }
}
