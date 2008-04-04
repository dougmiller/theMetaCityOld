package com.themetacity.beans;

import com.themetacity.typebeans.ArticleBean;
import com.themetacity.utilities.DatabaseBean;
import org.apache.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.regex.Pattern;

/**
 * This is the bean that process the articles from the database into ArticleBeans and then presents them for the tag.
 */
public class ArticleProcessBean {

    private DatabaseBean dbaBean = new DatabaseBean();

    // Selectors for the the article
    private String year = "";
    private String month = "";
    private String day = "";
    private String title = "";

    private String tag = "";

    static Logger logger = Logger.getLogger(ArticleProcessBean.class);

    public ArticleProcessBean() {
    }

    /**
     * Process the results of an executed SQL statment into article beans that are placed into a LinkedList.
     * <br />
     * The statement selects news article(s) and returns them to this method as a ResultSet. The results set is then
     * iterated over and each record is used to populate a ArticleBean. Once populated the ArticleBean is added
     * to a LinkedList and once all records have been processed the list is returned.
     *
     * @return A linked list of populated NewsArticelBeans
     */
    private LinkedList<ArticleBean> processQuery() {
        LinkedList<ArticleBean> listOfBeans = new LinkedList<ArticleBean>();       // The list of populated beans.

        ResultSet result = dbaBean.executeQuery();

        try {
            // Build the SQL query string
            // Execute the actual query and put the resusult into a ResultSet
            // For every row that is returned from the database query populate a bean and add
            // it to a linked list so that the JSTL can iterate over it.
            while (result.next()) {
                // Make a new ArticleBean that represents one article
                ArticleBean articleBean = new ArticleBean();
                // Set the properties of the bean
                articleBean.setAuthor(result.getString("author"));
                articleBean.setTitle(result.getString("title"));
                articleBean.setArticleText(result.getString("articletext"));
                articleBean.setDateTime(result.getDate("datetime"));
                // Process this articles tags
                TagProcessBean tagProcessBean = new TagProcessBean();
                tagProcessBean.setArticleID("" + result.getInt("articleID"));     // Set the user in the TagProcessBean *cast to String*
                articleBean.setTags(tagProcessBean.getArticleTags());             // Assign the results to the bean

                listOfBeans.add(articleBean);                                     //Add the now populated bean to the list to be returned for display
            }
            // Close the Result Set
            result.close();
            // Close the database connection
            dbaBean.close();
        } catch (SQLException SQLEx) {
            logger.warn("You had an error with your SQL");
            logger.warn(SQLEx);
        }
        return listOfBeans;
    }

    public LinkedList<ArticleBean> getFrontpageArticles() {
        try {
            dbaBean.setPrepStmt(dbaBean.getConn().prepareStatement("SELECT * FROM articles ORDER BY articleid desc LIMIT 5;"));
        } catch (SQLException SQLEx) {

            logger.fatal(SQLEx);
        }
        logger.debug(dbaBean.getPrepStmt().toString());
        return processQuery();
    }

    public LinkedList<ArticleBean> getFilteredArticles() {
        try {
            if (!title.equals("")) { // Title is set
                if (!day.equals("")) {
                    dbaBean.setPrepStmt(dbaBean.getConn().prepareStatement("SELECT * FROM articles WHERE YEAR(dateTime) = ? AND MONTH(dateTime) = ? AND DAY(dateTime) = ? AND title = ? ORDER BY articleid desc;"));
                    dbaBean.getPrepStmt().setString(1, year);
                    dbaBean.getPrepStmt().setString(2, month);
                    dbaBean.getPrepStmt().setString(3, day);
                    dbaBean.getPrepStmt().setString(4, extactTitle(title));
                } else if (!month.equals("")) {
                    dbaBean.setPrepStmt(dbaBean.getConn().prepareStatement("SELECT * FROM articles WHERE YEAR(dateTime) = ? AND MONTH(dateTime) = ? AND title = ? ORDER BY articleid desc;"));
                    dbaBean.getPrepStmt().setString(1, year);
                    dbaBean.getPrepStmt().setString(2, month);
                    dbaBean.getPrepStmt().setString(3, extactTitle(title));
                } else if (!year.equals("")) {
                    dbaBean.setPrepStmt(dbaBean.getConn().prepareStatement("SELECT * FROM articles WHERE YEAR(dateTime) = ? AND title = ? ORDER BY articleid desc;"));
                    dbaBean.getPrepStmt().setString(1, year);
                    dbaBean.getPrepStmt().setString(2, extactTitle(title));
                } else {
                    dbaBean.setPrepStmt(dbaBean.getConn().prepareStatement("SELECT * FROM articles WHERE title = ? ORDER BY articleid desc;"));
                    dbaBean.getPrepStmt().setString(1, extactTitle(title));
                }
            } else { // No title
                if (!day.equals("")) {
                    dbaBean.setPrepStmt(dbaBean.getConn().prepareStatement("SELECT * FROM articles WHERE YEAR(dateTime) = ? AND MONTH(dateTime) = ? AND DAY(dateTime) = ? ORDER BY articleid desc;"));
                    dbaBean.getPrepStmt().setString(1, year);
                    dbaBean.getPrepStmt().setString(2, month);
                    dbaBean.getPrepStmt().setString(3, day);
                } else if (!month.equals("")) {
                    dbaBean.setPrepStmt(dbaBean.getConn().prepareStatement("SELECT * FROM articles WHERE YEAR(dateTime) = ? AND MONTH(dateTime) = ?  ORDER BY articleid desc;"));
                    dbaBean.getPrepStmt().setString(1, year);
                    dbaBean.getPrepStmt().setString(2, month);
                } else if (!year.equals("")) {
                    dbaBean.setPrepStmt(dbaBean.getConn().prepareStatement("SELECT * FROM articles WHERE YEAR(dateTime) = ? ORDER BY articleid desc;"));
                    dbaBean.getPrepStmt().setString(1, year);
                } else { // Deault case of nothing being passed as an argument
                    dbaBean.setPrepStmt(dbaBean.getConn().prepareStatement("SELECT * FROM articles ORDER BY articleid desc;"));
                }
            }
        } catch (SQLException SQLEx) {
            logger.fatal(SQLEx);
        }
        logger.debug(dbaBean.getPrepStmt().toString());
        return processQuery();
    }

    public LinkedList<ArticleBean> getArticlesWithTag() {
        try {
            dbaBean.setPrepStmt(dbaBean.getConn().prepareStatement("SELECT * FROM articles, articletags " +
                    "WHERE articles.articleid = articletags.articleid " +
                    "AND articletags.tag = ? " +
                    "ORDER BY articletags.articleid desc"));
            dbaBean.getPrepStmt().setString(1, tag);

        } catch (SQLException SQLEx) {
            logger.fatal(SQLEx);
        }

        logger.debug(dbaBean.getPrepStmt().toString());
        return processQuery();
    }

    /**
     * Checks wether a string input is a number
     *
     * @param numToCheck is the number to check
     * @return true if the input is indeed a number
     */
    public boolean isNumber
            (String
                    numToCheck) {
        try {
            Integer.parseInt(numToCheck);
        } catch (NumberFormatException numformatEx) {
            return false;
        }
        return true;
    }

    /**
     * @param inputString is the title from the parameters.
     * @return a string that has the "-" removed and ready for searching.
     */
    public String extactTitle
            (String
                    inputString) {
        return inputString.replace("-", " ");
    }

    /**
     * * NEVER USED IN CODE * this is the regex used in the URL filter.
     *
     * @param inputString is the input string to check against the regex
     * @return true if the regex matches the inputString
     */
    public Boolean titleRegex
            (String
                    inputString) {
        return Pattern.matches("([\\w*](-?[\\w*])*)", inputString);
    }

    public String getYear
            () {
        return year;
    }

    public void setYear
            (String
                    year) {
        this.year = year;
    }

    public String getMonth
            () {
        return month;
    }

    public void setMonth
            (String
                    month) {
        this.month = month;
    }

    public String getDay
            () {
        return day;
    }

    public void setDay
            (String
                    day) {
        this.day = day;
    }

    public String getTitle
            () {
        return title;
    }

    public void setTitle
            (String
                    title) {
        this.title = title;
    }

    public String getTag
            () {
        return tag;
    }

    public void setTag
            (String
                    tag) {
        this.tag = tag;
    }
}