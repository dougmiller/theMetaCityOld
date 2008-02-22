package com.themetacity.beans;

import com.themetacity.typebeans.ArchiveEntryBean;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

public class ArchiveProcessBean {
    String year;
    String month;
    String day;
    String title;

    public ArchiveProcessBean() {
        // Set these explicitly to "" so that the string comparisons below used to construct
        // SQL queries will work
        year = "";
        month = "";
        day = "";
        title = "";
    }

    public LinkedList getArchive() {
        DatabaseBean dbBean = new DatabaseBean();
        LinkedList<ArchiveEntryBean> listOfBeans = new LinkedList<ArchiveEntryBean>();    // The list of populated beans.

        try {
            ResultSet result = dbBean.executeQuery(constructQuery(year, month, day, title));

            while (result.next()) {
                ArchiveEntryBean achiveEntry = new ArchiveEntryBean();

                achiveEntry.setAuthor(result.getString("author"));
                achiveEntry.setTitle(result.getString("title"));
                achiveEntry.setDateTime(result.getDate("datetime"));

                listOfBeans.add(achiveEntry);
            }
            // Close the Result Set
            result.close();
            // Close the database connection
            dbBean.close();
        } catch (SQLException SQLEx) {
            System.out.println("You had an error with your SQL");
            System.out.println(SQLEx);
        }

        return listOfBeans;
    }

    /**
     * Construct a query to select articles for the archive, given the inputs for year, month, day and/or title.
     * <p/>
     * Valid input combinations:
     * /archive/(No values given)
     * /archive/Year
     * /archive/Year/Title
     * /archive/Year/Month
     * /archive/Year/Month/Title
     * /archive/Year/Month/Day
     * /archive/Year/Month/Day/Title
     * <p/>
     * Any combination not in the list above will revert to the highest valid entry and disregard the remaining dates
     * but keeping the title
     * <p/>
     * eg / will retuen a query to retuen every article
     * eg /year/day will return a query constructed for /year
     * eg /month/title will retuen a query for /title
     *
     * @param year  is the year that the article was posted as a string
     * @param month is the month that the article was posted as a string
     * @param day   is the day that the article was posted as a string
     * @param title is the title of the article
     * @return a constructed query string for the particular article(s) given the inputs
     */
    public String constructQuery(String year, String month, String day, String title) {
        // Sanitize inputs; Probably a better way to do this and ot doesnt offerer complete coverage
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

        // Sanitize the parameter input
        try {
            Integer.parseInt(day);
        } catch (NumberFormatException numFormatEx) {
            day = "";
        }

        // Check for year
        if (!year.equals("")) {
            // Check for month
            if (!month.equals("")) {
                // Cehck for day
                if (!day.equals("")) {
                    // Check if the title is set or not
                    if (!title.equals("")) {
                        return "SELECT title, author, datetime FROM articles WHERE YEAR(datetime) = " + year + " AND MONTH(datetime) = " + month + " AND DAY(datetime) = " + day + " AND title = \"" + extactTitle(title) + "\" ORDER BY articleid DESC;";
                    }
                    // Year, month and day set
                    return "SELECT title, author, datetime FROM articles WHERE YEAR(datetime) = " + year + " AND MONTH(datetime) = " + month + " AND DAY(datetime) = " + day + " ORDER BY articleid DESC;";
                }
                // Check for title
                if (!title.equals("")) {
                    return "SELECT title, author, datetime FROM articles WHERE YEAR(datetime) = " + year + " AND MONTH(datetime) = " + month + " AND title = \"" + extactTitle(title) + "\" ORDER BY articleid DESC;";
                }

                // Year and month was set
                return "SELECT title, author, datetime FROM articles WHERE YEAR(datetime) = " + year + " AND MONTH(datetime) = " + month + " ORDER BY articleid DESC;";
            }
            // Check for title
            if (!title.equals("")) {
                return "SELECT title, author, datetime FROM articles WHERE YEAR(datetime) = " + year + " AND title = \"" + extactTitle(title) + "\" ORDER BY articleid DESC;";
            }
            // Only year was set
            return "SELECT title, author, datetime FROM articles WHERE YEAR(datetime) = " + year + " ORDER BY articleid DESC;";
        }
        // Check for title
        if (!title.equals("")) {
            return "SELECT title, author, datetime FROM articles WHERE title = \"" + extactTitle(title) + "\" ORDER BY articleid DESC;";
        }
        // Nothing set so return everything
        return "SELECT title, author, datetime FROM articles ORDER BY articleid DESC;";
    }

    /**
     * @param inputString is the title from the parameters.
     * @return a string that has the "-" removed and ready for searching.
     */
    public String extactTitle(String inputString) {
        return inputString.replace("-", " ");
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
