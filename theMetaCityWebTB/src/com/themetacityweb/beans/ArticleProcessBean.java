package com.themetacityweb.beans;

import com.themetacitycommon.beans.DatabaseBean;
import com.themetacityweb.typebeans.ArticleBean;
import org.apache.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.regex.Pattern;

/**
 * This is the bean that process the articles from the database into ArticleBeans and then presents them for the tag.
 */
public class ArticleProcessBean {

    // Selectors for the the article
    private String year;
    private String month;
    private String day;
    private String title;

    static Logger logger = Logger.getLogger(ArticleProcessBean.class);

    public ArticleProcessBean() {
        // Set these explicitly to "" so that the string comparisons below used to construct
        // the SQL queries will work
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
    public LinkedList<ArticleBean> getArticles() {
        DatabaseBean dbBean = new DatabaseBean();
        LinkedList<ArticleBean> listOfBeans = new LinkedList<ArticleBean>();       // The list of populated beans.

        try {
            // Build the SQL query string
            // Execute the actual query and put the resusult into a ResultSet
            ResultSet result = dbBean.executeQuery(constructQuery(year, month, day, title));
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
                articleBean.setTags(tagProcessBean.getTags());                    // Assign the results to the bean
                listOfBeans.add(articleBean);      //Add the now populated bean to the list to be returned for display
            }
            // Close the Result Set
            result.close();
            // Close the database connection
            dbBean.close();
        } catch (SQLException SQLEx) {
            System.out.println("You had an error with your SQL");
            System.out.println(SQLEx);
        }
        // Return the list of populated articleBeans
        return listOfBeans;
    }

    /**
     * Construct a query to select articles, given the inputs for year, month, day and/or title.
     * <p/>
     * Valid input combinations:
     * /(No values given)
     * /Title
     * /Year
     * /Year/Title
     * /Year/Month
     * /Year/Month/Title
     * /Year/Month/Day
     * /Year/Month/Day/Title
     * <p/>
     * Any combination not in the list above will revert to the highest valid entry and disregard the remaining dates
     * but keeping the title
     * <p/>
     * eg / will retuen a query to return every article
     * eg /year/day will return a query constructed for /year
     * eg /month/title will retuen a query for /title
     * <p/>
     * N.B. Date range is not taken into account.
     * If the user wants to select the 54 month of the year 900, then so be it.
     *
     * @param year  is the year that the article was posted as a string
     * @param month is the month that the article was posted as a string
     * @param day   is the day that the article was posted as a string
     * @param title is the title of the article
     * @return a constructed query string for the particular article(s) given the inputs
     */
    public String constructQuery(String year, String month, String day, String title) {
        String conditions = "";

        // Date conditions
        if (isNumber(year)) {
            conditions += "WHERE YEAR(datetime) = " + year + " ";
            if (isNumber(month)) {
                conditions += "AND MONTH(datetime) = " + month + " ";
                if (isNumber(day)) {
                    conditions += "AND DAY(datetime) = " + day + " ";
                }
            }
        }

        // Year conditions
        if (title.length() > 0) {
            conditions += ((conditions.length() == 0) ? "WHERE " : "AND ") + "title = \"" + extactTitle(title) + "\" ";
        }


        return "SELECT * FROM articles " + conditions + "ORDER BY articleid DESC;";
    }

    /**
     * Checks wether a string input is a number
     *
     * @param numToCheck is the number to check
     * @return true if the input is indeed a number
     */
    public boolean isNumber(String numToCheck) {
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
    public String extactTitle(String inputString) {
        return inputString.replace("-", " ");
    }

    /**
     * * NEVER USED IN CODE * this is the regex used in the URL filter.
     *
     * @param inputString is the input string to check against the regex
     * @return true if the regex matches the inputString
     */
    public Boolean titleRegex(String inputString) {
        return Pattern.matches("([\\w*](-?[\\w*])*)", inputString);
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