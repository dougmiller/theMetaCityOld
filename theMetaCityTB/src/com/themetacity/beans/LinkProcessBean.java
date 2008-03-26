package com.themetacity.beans;

import com.themetacity.typebeans.LinkBean;
import com.themetacity.utilities.DatabaseBean;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

import org.apache.log4j.Logger;

/**
 * This class process the links in the side bar.
 */
public class LinkProcessBean {

    static Logger logger = Logger.getLogger(LinkProcessBean.class);

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
                linkBean.setDatePosted(result.getString("datePosted"));

                noticeList.add(linkBean);
            }
            result.close();
            dbaBean.close();
        } catch (SQLException SQLEX) {
            logger.warn("There as an error in the NoticeProcessBean");
            logger.warn(SQLEX);
        }

        return noticeList;
    }

    private String constructLinkQuery() {
        return "SELECT * FROM links ORDER BY linkid desc LIMIT 5;";
    }
}
