package com.themetacity.beans;

import com.themetacity.typebeans.ArticleBean;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

/**
 * This is the bean that process the articles from the
 * database into ArticleBeans and then presents them for the tag.
 *
 * @see DatabaseBean
 */
public class ArticleProcessBean {

    private LinkedList<ArticleBean> listOfBeans = new LinkedList<ArticleBean>();    // The list of populated beans.

    // Selectors for the the article
    private String year;
    private String month;
    private String day;
    private String title;

    public ArticleProcessBean() {
        year = "";
        month = "";
        day = "";
        title = "";
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
    public LinkedList getArticles() {
        DatabaseBean dbBean = new DatabaseBean();

        try {
            // Build the SQL query string
            // Execute the actual query and put the resusult into a ResultSet
            ResultSet result = dbBean.executeQuery(validateAndBuildArticleQuery(year, month, day));

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
                tagProcessBean.setArticleID(result.getInt("articleID"));     // Set the user in the TagProcessBean
                articleBean.setTags(tagProcessBean.getTags());               // Assign the results to the bean

                listOfBeans.add(articleBean);      //Add the now populated bean to the list to be returned for display
            }
        } catch (SQLException SQLEx) {
            System.out.println("You had an error with your SQL");
            System.out.println(SQLEx);
        }
        // Return the list of populated articleBeans
        return listOfBeans;
    }

    /**
     * This is the article validator and query builder. It takes input for selecting
     * a particular date that an article might appear on and build a string to retrieve
     * that information. If year is an empty string then the string is returned to
     * select every entry from teh front page. ir the welcome page.
     * If year is not empty but month is then the string is constructed for selecting all the
     * articles from that year
     * Simililarly if the month is not empty then the string will be for the month of that year
     * Same for the day -> month -> year combination.
     * <p/>
     * If the input is not a number then the input is reflectd to the next highest value.
     * For example if the date input is not a number (eg: "/2007/11/Cow") then the input is disregarded
     * and the query is constructed around the month (eg: "/2007/22/")
     *
     * @param year  that people are searching for.
     * @param month that people are searching for, provided that year is not empty
     * @param day   that people are searching for, providing month is not empty.
     * @return the search query, constructed with the validated input to give the most chronologically
     *         focused of entry results.
     */
    private String validateAndBuildArticleQuery(String year, String month, String day) {
        // Sanitize inputs;
        // First is the year
        try {
            Integer.parseInt(year);
        } catch (NumberFormatException numFormatEx) {
            year = "";
        }

        // Now check the month
        try {
            Integer.parseInt(month);
        } catch (NumberFormatException numFormatEx) {
            month = "";
        }

        // Now check the day
        try {
            Integer.parseInt(day);
        } catch (NumberFormatException numFormatEx) {
            day = "";
        }

        // If the year has been supplied but not the month or the day then retuen the articles of that year.
        if (!year.equals("") && month.equals("")) {             // If year has been set, but not the month
            return "SELECT * FROM articles WHERE YEAR(datetime) = " + year + " ORDER BY articleid DESC;";
        }
        // If the year and month have been supplied then return articles for only that year/month
        else if (!year.equals("") && !month.equals("") && day.equals("")) {       // If year and month have been set
            return "SELECT * FROM articles WHERE YEAR(datetime) = " + year + " AND MONTH(datetime) = " + month + " ORDER BY articleid DESC;";
        }
        // If all the arguments must have been set and so return the articles for a year/month/day
        else
        if (!year.equals("") && !month.equals("") && !day.equals("")) {      // If all three argument have been supplied
            return "SELECT * FROM articles WHERE YEAR(datetime) = " + year + " AND MONTH(datetime) = " + month + " AND DAY(datetime) = " + day + " ORDER BY articleid DESC;";
        }
        // Otherwise no date argument have been supplied return the latest articles
        return "SELECT * FROM articles ORDER BY articleid DESC;";

    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}