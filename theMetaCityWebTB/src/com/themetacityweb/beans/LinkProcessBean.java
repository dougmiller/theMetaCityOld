package com.themetacityweb.beans;

import com.themetacitycommon.beans.DatabaseBean;
import com.themetacityweb.typebeans.LinkBean;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

/**
 * This class process the links in the side bar.
 */
public class LinkProcessBean {

    public LinkProcessBean() {
    }

    public LinkedList<LinkBean> getLinks() {
        DatabaseBean dbaBean = new DatabaseBean();
        LinkedList<LinkBean> noticeList = new LinkedList<LinkBean>();

        ResultSet result = dbaBean.executeQuery(constructLinkQuery());

        try {
            while (result.next()) {
                LinkBean linkBean = new LinkBean();

                linkBean.setLinkURL(result.getString("linkURL"));
                linkBean.setDescText(result.getString("descText"));
                linkBean.setDatePosted(result.getDate("datePosted").toString());

                noticeList.add(linkBean);
            }
            result.close();
            dbaBean.close();
        } catch (SQLException SQLEX) {
            System.out.println("There as an error in the NoticeProcessBean");
            System.out.println(SQLEX);
        }

        return noticeList;
    }

    private String constructLinkQuery() {
        return "SELECT * FROM links ORDER BY linkid desc LIMIT 5;";
    }
}
