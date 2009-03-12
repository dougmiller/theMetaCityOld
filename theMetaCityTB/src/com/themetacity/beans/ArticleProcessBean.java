package com.themetacity.beans;

import com.themetacity.typebeans.ArticleBean;
import com.themetacity.utilities.DatabaseBean;
import org.apache.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.util.LinkedList;

/**
 * This is the bean that process the articles from the database into ArticleBeans and then presents them for the tag.
 */
public class ArticleProcessBean {

    private DatabaseBean dbaBean = new DatabaseBean();

    // Selectors for the the article

    private String articleID = "";

    private String title;
    private String URL;
    private String year;
    private String month;
    private String day;

    private String author = "";
    private String articleText = "";
    private String[] articleTags;           // The tag checkboxes
    private String articleOtherTags;        // The text box for other tags

    private String searchTag = "";          // Select articles with this tag

    private String searchString = "";

    private static final Logger logger = Logger.getLogger(ArticleProcessBean.class);

    public ArticleProcessBean() {
    }

    // Perforn the update commands where you just want things to be closed and finished.
    // Otherwise call the database manually and do it that way
    private int processUpdate() {
        int result = dbaBean.executeUpdate();
        dbaBean.close();
        return result;
    }

    /**
     * Get the article destined for the front page
     *
     * @return a linked list of the articles
     */
    public LinkedList<ArticleBean> getFrontpageArticles() {
        DatabaseBean articlesDBBean = new DatabaseBean();
        LinkedList<ArticleBean> listOfBeans = new LinkedList<ArticleBean>();

        try {
            PreparedStatement prepStmt = articlesDBBean.getConn().prepareStatement(
                    "SELECT id, title, url, author, date_time, article_text " +
                            "FROM articles " +
                            "ORDER BY id desc " +
                            "LIMIT 5;");

            articlesDBBean.setPrepStmt(prepStmt);

            ResultSet result = articlesDBBean.executeQuery();

            while (result.next()) {
                ArticleBean articleBean = new ArticleBean();

                articleBean.setArticleID(result.getString("id"));
                articleBean.setAuthor(result.getString("author"));
                articleBean.setTitle(result.getString("title"));
                articleBean.setURL(result.getString("url"));
                articleBean.setArticleText(result.getString("article_text"));
                articleBean.setDateTime(result.getTimestamp("date_time"));

                // Process this articles tags
                TagProcessBean tagProcessBean = new TagProcessBean();
                tagProcessBean.setArticleID("" + result.getInt("id"));     // Set the user in the TagProcessBean *cast to String*
                articleBean.setTags(tagProcessBean.getArticleTags());      // Assign the results to the bean

                listOfBeans.add(articleBean);                              //Add the now populated bean to the list to be returned for display
            }
        } catch (SQLException SQLEx) {
            logger.warn("You had an error in ArticleProcessBean.getFrontpageArticles()");
            logger.warn(SQLEx);
        } finally {
            articlesDBBean.close();
        }
        return listOfBeans;
    }

    /**
     * Filter the articles according to date or title or both
     *
     * @return a linked list of filtered articles
     */
    public LinkedList<ArticleBean> getFilteredArticles() {

        DatabaseBean dbaBean = new DatabaseBean();
        LinkedList<ArticleBean> listOfBeans = new LinkedList<ArticleBean>();

        try {
            dbaBean.setPrepStmt(dbaBean.getConn().prepareStatement(
                    "SELECT id, author, title, url, article_text, date_time " +
                            "FROM articles WHERE " +
                            "(YEAR(date_time) = ? OR ? is null) " +
                            "AND (MONTH(date_time) = ? OR ? is null) AND " +
                            "(DAY(date_time) = ? OR ? is null) AND " +
                            "(url = ? OR ? is null) " +
                            "ORDER BY date_time desc;"));
            dbaBean.getPrepStmt().setString(1, year);
            dbaBean.getPrepStmt().setString(2, year);
            dbaBean.getPrepStmt().setString(3, month);
            dbaBean.getPrepStmt().setString(4, month);
            dbaBean.getPrepStmt().setString(5, day);
            dbaBean.getPrepStmt().setString(6, day);
            dbaBean.getPrepStmt().setString(7, URL);
            dbaBean.getPrepStmt().setString(8, URL);

            ResultSet result = dbaBean.executeQuery();

            while (result.next()) {
                // Make a new ArticleBeanTest that represents one article
                ArticleBean articleBean = new ArticleBean();
                // Set the properties of the bean
                articleBean.setArticleID(result.getString("id"));
                articleBean.setAuthor(result.getString("author"));
                articleBean.setTitle(result.getString("title"));
                articleBean.setURL(result.getString("url"));
                articleBean.setArticleText(result.getString("article_text"));
                articleBean.setDateTime(result.getTimestamp("date_time"));

                // Process this articles tags
                try {
                    TagProcessBean tagProcessBean = new TagProcessBean();
                    tagProcessBean.setArticleID("" + result.getInt("id"));     // Set the user in the TagProcessBean *cast to String*
                    articleBean.setTags(tagProcessBean.getArticleTags());      // Assign the results to the bean
                } catch (SQLException SQLEx) {
                    logger.warn(SQLEx);
                }

                listOfBeans.add(articleBean);                                  //Add the now populated bean to the list to be returned for display
            }
        } catch (SQLException SQLEx) {
            logger.warn("You had an error mapping objects in ArticleProcessBean.getFilteredArticles()");
            logger.warn(SQLEx);
        }
        finally {
            dbaBean.close();
        }
        return listOfBeans;
    }

    /**
     * Get an article by the ID
     *
     * @return a linked list of the articles with the matching id
     */
    public LinkedList<ArticleBean> getArticlesByID() {
        DatabaseBean dbaBean = new DatabaseBean();
        LinkedList<ArticleBean> listOfBeans = new LinkedList<ArticleBean>();

        try {
            dbaBean.setPrepStmt(dbaBean.getConn().prepareStatement(
                    "SELECT id, title, url, article_text " +
                            "FROM articles " +
                            "WHERE id = ?;"));
            dbaBean.getPrepStmt().setString(1, articleID);

            ResultSet result = dbaBean.executeQuery();

            while (result.next()) {
                ArticleBean articleBean = new ArticleBean();

                articleBean.setArticleID(result.getString("id"));
                articleBean.setTitle(result.getString("title"));
                articleBean.setURL(result.getString("url"));
                articleBean.setArticleText(result.getString("article_text"));

                listOfBeans.add(articleBean);                                  //Add the now populated bean to the list to be returned for display
            }
        } catch (SQLException SQLEx) {
            logger.warn("You had an error mapping objects in ArticleProcessBean.getArticlesByID()");
            logger.warn(SQLEx);
        }
        finally {
            dbaBean.close();
        }

        return listOfBeans;
    }

    /**
     * Return all the articles posted under the given tag
     *
     * @return A linked list of articles
     */
    public LinkedList<ArticleBean> getArticlesWithTag() {
        DatabaseBean dbaBean = new DatabaseBean();
        LinkedList<ArticleBean> listOfBeans = new LinkedList<ArticleBean>();

        try {
            dbaBean.setPrepStmt(dbaBean.getConn().prepareStatement(
                    "SELECT title, url, date_time " +
                            "FROM articles, articletags " +
                            "WHERE articles.id = articletags.id " +
                            "AND articletags.tag = ? " +
                            "ORDER BY articletags.id desc;"));
            dbaBean.getPrepStmt().setString(1, searchTag);

            ResultSet result = dbaBean.executeQuery();

            while (result.next()) {
                ArticleBean articleBean = new ArticleBean();

                articleBean.setTitle(result.getString("title"));
                articleBean.setURL(result.getString("url"));
                articleBean.setDateTime(result.getTimestamp("date_time"));

                listOfBeans.add(articleBean);
            }
        } catch (SQLException SQLEx) {
            logger.warn("You had an error in ArticleProcessBean.getArticlesWithTag()");
            logger.warn(SQLEx);
        }
        finally {
            dbaBean.close();
        }

        return listOfBeans;
    }

        /**
     * Return all the articles posted under the given tag.
     * Written for the RSS Feed
     *
     * @return A linked list of articles
     */
    public LinkedList<ArticleBean> getArticlesWithTagForRSSFeed() {
        DatabaseBean dbaBean = new DatabaseBean();
        LinkedList<ArticleBean> listOfBeans = new LinkedList<ArticleBean>();

        try {
            dbaBean.setPrepStmt(dbaBean.getConn().prepareStatement(
                    "SELECT  title, url, article_text, date_time  " +
                            "FROM articles, articletags " +
                            "WHERE articles.id = articletags.id " +
                            "AND articletags.tag = ? " +
                            "ORDER BY articletags.id desc;"));
            dbaBean.getPrepStmt().setString(1, searchTag);

            ResultSet result = dbaBean.executeQuery();

            while (result.next()) {
                ArticleBean articleBean = new ArticleBean();

                articleBean.setTitle(result.getString("title"));
                articleBean.setArticleText(result.getString("article_text"));
                articleBean.setURL(result.getString("url"));
                articleBean.setDateTime(result.getTimestamp("date_time"));

                listOfBeans.add(articleBean);
            }
        } catch (SQLException SQLEx) {
            logger.warn("You had an error in ArticleProcessBean.getArticlesWithTag()");
            logger.warn(SQLEx);
        }
        finally {
            dbaBean.close();
        }

        return listOfBeans;
    }

    /**
     * Search for articles with the given string in the title
     *
     * @return A linked list of articles
     */
    public LinkedList<ArticleBean> getSearchArticles() {
        DatabaseBean dbaBean = new DatabaseBean();
        LinkedList<ArticleBean> listOfBeans = new LinkedList<ArticleBean>();

        try {
            dbaBean.setPrepStmt(dbaBean.getConn().prepareStatement(
                    "SELECT title, url, date_time " +
                            "FROM articles " +
                            "WHERE title LIKE ? " +
                            "ORDER BY date_time desc;"));
            dbaBean.getPrepStmt().setString(1, "%" + searchString + "%");

            ResultSet result = dbaBean.executeQuery();

            while (result.next()) {
                ArticleBean articleBean = new ArticleBean();

                articleBean.setTitle(result.getString("title"));
                articleBean.setURL(result.getString("url"));
                articleBean.setDateTime(result.getTimestamp("date_time"));

                listOfBeans.add(articleBean);
            }
        } catch (SQLException SQLEx) {
            logger.warn("You had an error in ArticleProcessBean.getSearchArticles()");
            logger.warn(SQLEx);
        }
        finally {
            dbaBean.close();
        }
        return listOfBeans;
    }

    /**
     * Add an article
     *
     * @return True from succesfully adding a new article or fasle for not adding it
     */
    public int getAddArticle() {
        DatabaseBean dbaBean = new DatabaseBean();
        ArticleBean articleBean = new ArticleBean();
        try {
            dbaBean.setPrepStmt(dbaBean.getConn().prepareStatement("INSERT INTO articles (author, title, url, article_text, date_time) VALUES (?, ?, ?, ?, now());"));
            dbaBean.getPrepStmt().setString(1, author);
            dbaBean.getPrepStmt().setString(2, title);
            dbaBean.getPrepStmt().setString(3, articleBean.buildURL(title));
            dbaBean.getPrepStmt().setString(4, articleText);
        } catch (SQLException SQLEx) {
            logger.fatal(SQLEx);
        }

        int articleResult = dbaBean.executeUpdate();
        dbaBean.close();

        // now add the tags
        // get the id of the article just added
        articleID = getIdOfArticleFromTitle() + "";

        int tagsResult = updateArticleTags();

        if (articleResult > 0 && tagsResult > 0) {
            return 1;
        }

        return 0;
    }

    /**
     * Add an article
     *
     * @return True from succesfully adding a new article or fasle for not adding it
     */
    public int getUpdateArticle() {
        try {
            dbaBean.setPrepStmt(dbaBean.getConn().prepareStatement("UPDATE articles SET title = ?, article_text = ? WHERE id = ?;"));
            dbaBean.getPrepStmt().setString(1, title);
            dbaBean.getPrepStmt().setString(2, articleText);
            dbaBean.getPrepStmt().setString(3, articleID);
        } catch (SQLException SQLEx) {
            logger.fatal(SQLEx);
        }

        int articleResult = dbaBean.executeUpdate();
        int tagsResult = updateArticleTags();

        dbaBean.close();

        if (articleResult > 0 && tagsResult > 0) {
            return 1;
        }
        return 0;
    }

    // Update an article's tags. Used by other functions.
    private int updateArticleTags() {
        DatabaseBean tagDBBean = new DatabaseBean();

        try {
            tagDBBean.getConn().setAutoCommit(false);  // Start the transaction

            PreparedStatement deleteTags = tagDBBean.getConn().prepareStatement("DELETE FROM articletags WHERE id = ?;");
            PreparedStatement insertTags = tagDBBean.getConn().prepareStatement("INSERT INTO articletags VALUES (?, ?);");

            // clear out the old tags
            tagDBBean.setPrepStmt(deleteTags);
            deleteTags.setString(1, articleID);
            deleteTags.executeUpdate();              // Article may have 0 tags to cant return on 0

            deleteTags.close();

            // work out the new tags to insert
            if (articleTags.length == 0) {
                articleTags = new String[0];
            }
            // Handle textbox input
            String[] textBoxToArray = articleOtherTags.trim().split(",");

            // Stop empty tags being inserted
            try {
                if (textBoxToArray[0].equals("")) {          // nothing entered into the box
                    textBoxToArray = new String[0];
                }
            } catch (ArrayIndexOutOfBoundsException outOfBoundEx) {    // catch if the size is 0 (only happens when string to split is a ',' or more only)
                textBoxToArray = new String[0];
            }

            // Now combine the textbox newly created tags with the already existing ones.
            String[] tempTotalTags = new String[textBoxToArray.length + articleTags.length];
            System.arraycopy(textBoxToArray, 0, tempTotalTags, 0, textBoxToArray.length);
            System.arraycopy(articleTags, 0, tempTotalTags, textBoxToArray.length, articleTags.length);

            // Put in the tags

            try {
                for (String tag : tempTotalTags) {
                    tagDBBean.setPrepStmt(insertTags);
                    insertTags.setString(1, articleID);
                    insertTags.setString(2, tag.trim());
                    insertTags.executeUpdate();
                }
            } catch (SQLException SQLEx) {
                logger.fatal(SQLEx);
                try {
                    tagDBBean.getConn().rollback();
                } catch (SQLException rollbackSQLEx) {
                    logger.error("There was an error in ArticleProcessBean.updateArticleTags() when rolling back the transaction.");
                    logger.error(rollbackSQLEx);
                }
                return 0;
            }

            insertTags.close();

        } catch (SQLException SQLEx) {
            logger.fatal(SQLEx);
            try {
                tagDBBean.getConn().rollback();
            } catch (SQLException rollbackSQLEx) {
                logger.error("There was an error in ArticleProcessBean.updateArticleTags() when rolling back the transaction.");
                logger.error(rollbackSQLEx);
            }
            return 0;
        } finally {
            try {
                tagDBBean.getConn().commit();
                tagDBBean.getConn().setAutoCommit(true);  // Finish the transaction
                tagDBBean.close();
            } catch (SQLException finallyTryCatch) {
                logger.error("There was an error ArticleProcessBean.updateArticleTags() cleaning up");
                logger.error(finallyTryCatch);
            }

        }
        return 1;
    }

    /**
     * Delete an article by it's id number. Including associated tags.
     *
     * @return and integer shoing the number of rows affected
     */
    public int getDeleteArticle() {
        try {
            dbaBean.setPrepStmt(dbaBean.getConn().prepareStatement("DELETE FROM articles WHERE id = ?;"));
            dbaBean.getPrepStmt().setString(1, articleID);
        } catch (SQLException SQLEx) {
            logger.fatal(SQLEx);
        }
        /*
        Tags are deleted automatically via cascade delete and referential integrity on the DB
        */

        return processUpdate();
    }

    /**
     * Get all the articles for the edit/delete list in the admin app
     *
     * @return a linked list containg all the articles in ArticleBean format
     */
    public LinkedList<ArticleBean> getAllAdminArticles() {
        DatabaseBean dbaBean = new DatabaseBean();
        LinkedList<ArticleBean> listOfBeans = new LinkedList<ArticleBean>();

        try {
            dbaBean.setPrepStmt(dbaBean.getConn().prepareStatement(
                    "SELECT id, title, url, date_time " +
                            "FROM articles " +
                            "GROUP BY id ASC;"));

            ResultSet result = dbaBean.executeQuery();

            while (result.next()) {
                ArticleBean articleBean = new ArticleBean();

                articleBean.setArticleID(result.getString("id"));
                articleBean.setTitle(result.getString("title"));
                articleBean.setURL(result.getString("url"));
                articleBean.setDateTime(result.getTimestamp("date_time"));

                listOfBeans.add(articleBean);
            }
        } catch (SQLException SQLEx) {
            logger.warn("You had an error mapping objects in ArticleProcessBean.getAllAdminArticles()");
            logger.warn(SQLEx);
        }
        finally {
            dbaBean.close();
        }

        return listOfBeans;

    }

    /**
     * Get the potential article that will have the same url as the submitted title. The can be only one.
     * Used in JSON AJAX call
     *
     * @return a linked list of ArticleBeans
     */

    public LinkedList<ArticleBean> getMatchURLByTitle() {
        DatabaseBean dbaBean = new DatabaseBean();
        ArticleBean articleUtilBean = new ArticleBean();
        LinkedList<ArticleBean> listOfBeans = new LinkedList<ArticleBean>();

        try {
            dbaBean.setPrepStmt(dbaBean.getConn().prepareStatement(
                    "SELECT title, url " +
                            "FROM articles " +
                            "WHERE url = ?;"));
            dbaBean.getPrepStmt().setString(1, articleUtilBean.buildURL(title));

            ResultSet result = dbaBean.executeQuery();

            while (result.next()) {
                ArticleBean articleBean = new ArticleBean();

                articleBean.setTitle(result.getString("title"));
                articleBean.setURL(result.getString("url"));

                listOfBeans.add(articleBean);
            }
        } catch (SQLException SQLEx) {
            logger.warn("You had an error mapping objects in ArticleProcessBean.getMatchURLByTitle()");
            logger.warn(SQLEx);
        }
        finally {
            dbaBean.close();
        }

        return listOfBeans;
    }

    // Retrive the ID of articles based on the title. Used primarily to get the id of just entered articles
    private int getIdOfArticleFromTitle() {
        DatabaseBean dbaBean = new DatabaseBean();
        int idOfArticle = -1;
        try {
            dbaBean.setPrepStmt(dbaBean.getConn().prepareStatement(
                    "SELECT id " +
                            "FROM articles " +
                            "WHERE title = ?;"));
            dbaBean.getPrepStmt().setString(1, title);

            ResultSet result = dbaBean.executeQuery();

            while (result.next()) {
                idOfArticle = Integer.parseInt(result.getString("id"));
            }
        } catch (SQLException SQLEx) {
            logger.warn("You had an error mapping objects in ArticleProcessBean.getIdOfArticleFromTitle()");
            logger.warn(SQLEx);
        }
        finally {
            dbaBean.close();
        }

        return idOfArticle;
    }

    public LinkedList<ArticleBean> getSiteMapArticles() {
        DatabaseBean articlesDBBean = new DatabaseBean();
        LinkedList<ArticleBean> listOfBeans = new LinkedList<ArticleBean>();

        try {
            PreparedStatement prepStmt = articlesDBBean.getConn().prepareStatement(
                    "SELECT url, timestamp " +
                            "FROM articles;");

            articlesDBBean.setPrepStmt(prepStmt);

            ResultSet result = articlesDBBean.executeQuery();

            while (result.next()) {
                ArticleBean articleBean = new ArticleBean();

                articleBean.setURL(result.getString("url"));
                articleBean.setTimestamp(result.getTimestamp("timestamp"));

                listOfBeans.add(articleBean);
            }
        } catch (SQLException SQLEx) {
            logger.warn("You had an error in ArticleProcessBean.getSiteMapArticles()");
            logger.warn(SQLEx);
        } finally {
            articlesDBBean.close();
        }
        return listOfBeans;
    }

    /**
     * Get the title of the RSS Feed based on the year month and day params
     *
     * @return the constructed string
     */
    public String getRSSFeedTitle() {
        String feedTitle = "The MetaCity";
        if ((year != null) && (!year.equals(""))) {
            feedTitle += (" - " + year);
        }

        if ((month != null) && (!month.equals(""))) {
            feedTitle += ("/" + month);

        }

        if ((day != null) && (!day.equals(""))) {
            feedTitle += ("/" + day);

        }

        return feedTitle;
    }

    /**
     * Get the link of the RSS Feed based on the year month and day params
     *
     * @return the constructed string
     */
    public String getRSSFeedLink() {
        String feedLink = "http://www.themetacity.com/";

        if ((year != null) && (!year.equals(""))) {
            feedLink += (year + "/");
        }

        if ((month != null) && (!month.equals(""))) {
            feedLink += (month + "/");
        }

        if ((day != null) && (!day.equals(""))) {
            feedLink += (day + "/");

        }

        return feedLink;
    }

    /**
     * Get the link of the RSS Feed based on the tag
     *
     * @return the constructed string
     */
    public String getRSSFeedTagTitle() {
        String feedLink = "The MetaCity Tags - ";

        if ((searchTag != null) && (!searchTag.equals(""))) {
            feedLink += (searchTag);
        }

        return feedLink;
    }

    /**
     * Get the link of the RSS Feed based on the tag
     *
     * @return the constructed string
     */
    public String getRSSFeedTagLink() {
        String feedLink = "http://www.themetacity.com/tags/";

        if ((searchTag != null) && (!searchTag.equals(""))) {
            feedLink += (searchTag + "/");
        }

        return feedLink;
    }

    public String getArticleID() {
        return articleID;
    }

    public void setArticleID(String articleID) {
        this.articleID = articleID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        if (!URL.equals("")) {
            this.URL = URL;
        }
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        if (!year.equals("")) {
            this.year = year;
        }
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        if (!month.equals("")) {
            this.month = month;
        }
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        if (!day.equals("")) {
            this.day = day;
        }
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getArticleText() {
        return articleText;
    }

    public void setArticleText(String articleText) {
        this.articleText = articleText;
    }

    public String[] getArticleTags() {
        return articleTags;
    }

    public void setArticleTags(String[] articleTags) {
        if (articleTags == null) {
            this.articleTags = new String[0];
        } else {
            this.articleTags = articleTags;
        }
    }

    public String getArticleOtherTags() {
        return articleOtherTags;
    }

    public void setArticleOtherTags(String articleOtherTags) {
        this.articleOtherTags = articleOtherTags;
    }

    public String getSearchTag() {
        return searchTag;
    }

    public void setSearchTag(String searchTag) {
        this.searchTag = searchTag;
    }

    public String getSearchString() {
        return searchString;
    }

    public void setSearchString(String searchString) {
        this.searchString = searchString;
    }
}