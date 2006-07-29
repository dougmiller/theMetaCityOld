package com.themetacity.beans;

//import com.themetacity.typebeans.NewsArticleBean;

import com.themetacity.typebeans.NewsArticleBean;

import java.util.LinkedList;
import java.sql.ResultSet;

/**
 *
 */
public class NewsProcessBean {


    public ResultSet result; // The returned ResultSet from the executed SQL statement.
    public LinkedList<NewsArticleBean> listOfBeans; // The list of populated beans.
    DatabaseAccessBean dbaBean = new DatabaseAccessBean();


    /**
     * Process the results of a database connection that retuns a ResultSet of a query into beans that are passed to
     * custom tag for output.
     *
     * @return A list of NewsArticelBeans
     */
    public LinkedList ProcessNews() {



        result = dbaBean.getNewsResults("Select * FROM NEWS;");

        while (result.next()) {
            NewsArticleBean newsBean = new NewsArticleBean();


            listOfBeans.add(newsBean);
        }
        return listOfBeans;
    }


}