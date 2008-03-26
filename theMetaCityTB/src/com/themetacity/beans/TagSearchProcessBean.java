package com.themetacity.beans;

import com.themetacity.typebeans.ArticleBean;
import com.themetacity.utilities.DatabaseBean;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

import org.apache.log4j.Logger;

/**
 * This is the bean that process the tag archive and search. It is not for articles and profiles.
 *
 * @see TagProcessBean
 */

public class TagSearchProcessBean {

    private String tag;

    static Logger logger = Logger.getLogger(TagSearchProcessBean.class);    

    public TagSearchProcessBean() {
        tag = "";
    }

    public LinkedList<ArticleBean> getArticles() {
        LinkedList<ArticleBean> articlesWithTagList = new LinkedList<ArticleBean>();


        DatabaseBean dbaBean = new DatabaseBean();

        ResultSet result = dbaBean.executeQuery(constructQuery());
        try {
            while (result.next()) {
                ArticleBean bean = new ArticleBean();

                bean.setTitle(result.getString("title"));
                bean.setDateTime(result.getDate("datetime"));

                articlesWithTagList.add(bean);
            }
            result.close();
            dbaBean.close();
        } catch (SQLException SQLEx) {
            logger.warn("There was a problem in the TagSearchProcessBean");
            logger.warn(SQLEx);
        }

        return articlesWithTagList;
    }

    private String constructQuery() {
        return "SELECT title, datetime, author, tag from articles, articletags " +
                "WHERE articles.articleid = articletags.articleid " +
                "AND tag = '" + tag + "';";
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}
