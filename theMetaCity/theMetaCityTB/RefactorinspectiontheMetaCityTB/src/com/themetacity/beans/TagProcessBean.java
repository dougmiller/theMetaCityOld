package com.themetacity.beans;

import com.themetacity.typebeans.TagBean;

import java.util.LinkedList;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * This class processes tags
 * It makes requests to the database and returns a linkedlist of the results
 *
 * @see DatabaseBean
 */
class TagProcessBean {

    private final LinkedList<TagBean> listOfTags = new LinkedList<TagBean>();


    // Zero argment constructor
    public TagProcessBean() {

    }

    /**
     * Process the result of a call to the database (a results set) into a linked list containing
     * TagBean objects, which can be passed to a custom tag for output.
     *
     * @return a linked list containing TagBean objects
     */
    public LinkedList getTags() {

        DatabaseBean dbaBean = new DatabaseBean();
        
        ResultSet result=dbaBean.executeQuery("SELECT tag, count(tag) as timesused FROM usertags WHERE user = 'someting' GROUP BY tag;");

        try {
        while (result.next()){
            TagBean tagBean = new TagBean();

            tagBean.setTag(result.getString("tag"));
            tagBean.setNumTimesTagUsed(result.getInt("timesused"));

            listOfTags.add(tagBean);
        }
        } catch (SQLException SQLEx){
            System.out.println("some problem with the SQL");  
        }
        dbaBean.close();

        return listOfTags;
    }
}
