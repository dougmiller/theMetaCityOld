package com.themetacity.beans;

import com.themetacity.typebeans.ArchiveEntryBean;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

/**
 * This is the bean that process the tag archive and search. It is not for articles and profiles.
 *
 * @see TagProcessBean
 */

public class TagSearchProcessBean {

    private String tag;

    public TagSearchProcessBean() {
        tag = "";
    }

    public LinkedList<ArchiveEntryBean> getArticles() {
        LinkedList<ArchiveEntryBean> articlesWithTagList = new LinkedList<ArchiveEntryBean>();


        DatabaseBean dbaBean = new DatabaseBean();

        ResultSet result = dbaBean.executeQuery(constructQuery());
        try {
            while (result.next()) {
                ArchiveEntryBean bean = new ArchiveEntryBean();

                bean.setAuthor(result.getString("author"));
                bean.setTitle(result.getString("title"));
                bean.setDateTime(result.getDate("datetime"));

               articlesWithTagList.add(bean);
            }
            result.close();
            dbaBean.close();
        } catch (SQLException SQLEx) {
            System.out.println("There was a problem in the TagSearchProcessBean");
            System.out.println(SQLEx);
        }

        return articlesWithTagList;
    }

    private String constructQuery() {
        return "SELECT title, datetime, author, tag from articles, articletags\n" +
                "WHERE articles.articleid = articletags.articleid\n" +
                "AND tag = '" + tag + "';";
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}
