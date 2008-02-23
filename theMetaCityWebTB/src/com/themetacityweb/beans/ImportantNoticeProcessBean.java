package com.themetacityweb.beans;

import com.themetacitycommon.beans.DatabaseBean;
import com.themetacityweb.typebeans.ImportantNoticeBean;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

public class ImportantNoticeProcessBean {

    LinkedList<ImportantNoticeBean> noticeList = new LinkedList<ImportantNoticeBean>();

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
            System.out.println("There as an error in the NoticeProcessBean");
            System.out.println(SQLEX);
        }

        return noticeList;
    }

    private String constructImportantNoticeQuery() {
        return "SELECT pseudonym, message FROM importantNotice, users WHERE importantNotice.username = users.username AND NOW() >= dateFrom AND NOW() <= dateTo;";
    }

    public LinkedList<ImportantNoticeBean> getNoticeList() {
        return noticeList;
    }

    public void setNoticeList(LinkedList<ImportantNoticeBean> noticeList) {
        this.noticeList = noticeList;
    }
}
