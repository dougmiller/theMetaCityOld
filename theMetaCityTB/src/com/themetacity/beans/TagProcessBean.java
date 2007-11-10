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


    // The user this list represents
    String user;

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

        ResultSet result = dbaBean.executeQuery(constructUserTagQuery());

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

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
}