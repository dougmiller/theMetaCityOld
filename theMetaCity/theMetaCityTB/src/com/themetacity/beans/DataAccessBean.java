package com.themetacity.beans;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DataAccessBean {
    
    public DataAccessBean() {

    }

    public List getNewsResults() throws NamingException {

        Context initContext = new InitialContext();
        Context envContext = (Context) initContext.lookup("java:/comp/env");
        DataSource ds = (DataSource) envContext.lookup("jdbc/theMetaCity");
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        List result = new ArrayList();

        try {
            String sql = "SELECT * FROM News, users WHERE News.user = users.username";

            conn = ds.getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);

            while (rs.next()) {
                /* New Article object that is populated and then added to a list */
                NewsArticleBean article = new NewsArticleBean();

                /* Populate the article with the corrisposding articls from the database */
                article.setTitle(rs.getString("title"));
                article.setAuthor(rs.getString("author"));
                article.setEmail(rs.getString("email"));
                article.setNews(rs.getString("News"));
                article.setPictureURL(rs.getString("pictureURL"));
                article.setDate(rs.getDate("date"));
                article.setTime(rs.getTime("time"));

                /* Add the completed article to the result List */
                result.add(article);
            }

        } catch (SQLException e) {
            System.out.println("Ther was an error with your SQL.");
            System.out.println(e);
        }

        finally {
            try {
                if (rs != null) rs.close();
            } catch (Exception e) {
                /* There was an exception */
                System.out.println("The ResultSet failed to close.");
                System.out.println(e);
            }
            try {
                if (stmt != null) stmt.close();
            } catch (Exception e) {
                /* There was an exception */
                System.out.println("The statement failed to close.");
                System.out.println(e);
            }
            /* Check if the connection is closed and if it isnt then close it */
            try {
                if (conn != null) conn.close();
            } catch (Exception e) {
                /* There was an exception */
                System.out.println("The connection failed to close.");
                System.out.println(e);
            }
        }
        /* Return the List containing the Articles */
        return result;
    }

    public List getSearchResults() throws NamingException {

        Context initContext = new InitialContext();
        Context envContext = (Context) initContext.lookup("java:/comp/env");
        DataSource ds = (DataSource) envContext.lookup("jdbc/theMetaCity");
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        List result = new ArrayList();

        String sql = "SELECT * FROM News, users WHERE News.user = users.username";

        try {

            conn = ds.getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);

            while (rs.next()) {
                /* Populate the search bean */
            }

        } catch (SQLException e) {
            System.out.println("Ther was an error with your SQL.");
            System.out.println(e);
        }

        return result;
    }

}


