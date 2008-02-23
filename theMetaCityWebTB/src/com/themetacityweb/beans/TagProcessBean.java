package com.themetacityweb.beans;

import com.themetacitycommon.beans.DatabaseBean;
import com.themetacityweb.typebeans.TagBean;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

/**
 * This class processes tags for articles and profiles. It is not the search.
 * It makes requests to the database and returns a linkedlist of the results.
 *
 * @see TagSearchProcessBean for more infor on search.
 */
public class TagProcessBean {


    // The possible inputs used to construct the database call
    String user;
    String articleID;

    // Zero argment constructor
    public TagProcessBean() {
        // Set these explicitly to "" so that the string comparisons below used to construct
        // SQL queries will work
        user = "";
        articleID = "";
    }

    /**
     * Process the result of a call to the database (a results set) into a linked list containing
     * TagBean objects, which can be passed to a custom tag for output.
     *
     * @return a linked list containing TagBean objects
     */
    public LinkedList<TagBean> getTags() {
        LinkedList<TagBean> listOfTags = new LinkedList<TagBean>();
        DatabaseBean dbBean = new DatabaseBean();
        ResultSet result = dbBean.executeQuery(constructTagQuery(user, articleID));
        try {
            while (result.next()) {
                TagBean tagBean = new TagBean();

                tagBean.setTag(result.getString("tag"));
                tagBean.setNumTimesTagUsed(result.getInt("timesused"));

                listOfTags.add(tagBean);
            }
            // Close the result
            result.close();
            // Close the database connection and return it to the pool
            dbBean.close();

        } catch (SQLException SQLEx) {
            System.out.println("some problem with the SQL");
        }

        return listOfTags;
    }

    /**
     * Constructs the query used to find the tags used by a user for their profile page
     *
     * @param user    is the username to sort tag for.
     * @param article is the article id to sort tags for
     * @return SQL query string, the fully built string. Default is for every tag if no paramater is set
     */

    public String constructTagQuery(String user, String article) {
        // If the article is set then build and retun a query for them
        if (!article.equals("")) {
            return "SELECT tag, count(tag) as timesused " +
                    "FROM articles, articletags " +
                    "WHERE articles.articleid = '" + article + "' " +
                    "AND articles.articleid = articletags.articleid " +
                    "GROUP BY tag;";
        }
        if (!user.equals("")) {
            return "SELECT tag, count(tag) as timesused " +
                    "FROM articles, articletags " +
                    "WHERE articles.author = '" + user + "' " +
                    "AND articles.articleid = articletags.articleid " +
                    "GROUP BY tag;";
        }
        // Otherwise retuen every tag if not paramter has been set
        return "SELECT tag, count(tag) as timesused " +
                "FROM articles, articletags " +
                "WHERE articles.articleid = articletags.articleid " +
                "GROUP BY tag;";
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getArticleID() {
        return articleID;
    }

    public void setArticleID(String articleID) {
        this.articleID = articleID;
    }
}