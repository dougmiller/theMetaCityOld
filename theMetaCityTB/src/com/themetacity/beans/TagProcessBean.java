package com.themetacity.beans;

import com.themetacity.typebeans.TagBean;
import com.themetacity.utilities.DatabaseBean;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

import org.apache.log4j.Logger;

/**
 * This class processes tags for articles and profiles. It is not the search.
 * It makes requests to the database and returns a linkedlist of the results.
 *
 * @see TagSearchProcessBean for more infor on search.
 */
public class TagProcessBean {

    // The possible inputs used to construct the database call haveto be initialised
    String user = "";
    String articleID = "";  // Is a string becasue its a request parameter

    static Logger logger = Logger.getLogger(TagProcessBean.class);    

    // Zero argment constructor
    public TagProcessBean() {
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
            logger.warn("Some problem with the SQL");
            logger.warn(SQLEx);
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
        String query = "SELECT tag, count(tag) as timesused " +
                "FROM articles, articletags ";

        String conditions = "";

        if (!article.equals("")) {
            conditions += "WHERE articles.articleid = '" + article + "' ";
        } else if (!user.equals("")) {
            conditions += "WHERE articles.author = '" + user + "' ";
        }
        // Append the final part of the query string
        query += conditions + ((conditions.length() == 0) ? "WHERE" : "AND") + " articles.articleid = articletags.articleid " +
                 "GROUP BY tag;";
        return query;
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