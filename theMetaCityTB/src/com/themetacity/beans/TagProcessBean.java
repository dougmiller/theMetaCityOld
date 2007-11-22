package com.themetacity.beans;

import com.themetacity.typebeans.TagBean;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

/**
 * This class processes tags
 * It makes requests to the database and returns a linkedlist of the results
 *
 * @see DatabaseBean
 */
public class TagProcessBean {


    // The possible inputs used to construct the database call
    String user;
    int articleID;

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

        DatabaseBean dbaBean = new DatabaseBean();

        String query = null;

        /* Check the input to determine how to construct the query tag */
        if (user != null) {                              // If the input is a username
            query = constructUserTagQuery();
        } else if (articleID != 0) {                    // If the input is an article id num
            query = constructArticleTagQuery();
        }

        ResultSet result = dbaBean.executeQuery(query);

        try {
            while (result.next()) {
                TagBean tagBean = new TagBean();

                tagBean.setTag(result.getString("tag"));
                tagBean.setNumTimesTagUsed(result.getInt("timesused"));

                listOfTags.add(tagBean);
            }
        } catch (SQLException SQLEx) {
            System.out.println("some problem with the SQL");
        }
        dbaBean.close();

        return listOfTags;
    }

    /**
     * Constructs the query used to find the tags used by a user for their profile page
     *
     * @return SQL query string, the fully built string.
     */
    public String constructUserTagQuery() {
        return "SELECT tag, count(tag) as timesused " +
                "FROM articles, articletags " +
                "WHERE articles.author = '" + user + "' " +
                "AND articles.articleid = articletags.articleid " +
                "GROUP BY tag;";
    }

    public String constructArticleTagQuery() {
        return "SELECT tag, count(tag) as timesused " +
                "FROM articles, articletags " +
                "WHERE articles.articleid = " + articleID + " " +
                "AND articles.articleid = articletags.articleid " +
                "GROUP BY tag;";
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public int getArticleID() {
        return articleID;
    }

    public void setArticleID(int articleID) {
        this.articleID = articleID;
    }
}