package com.themetacity.beans;

import com.themetacity.typebeans.ArticleBean;
import com.themetacity.utilities.DatabaseBean;
import org.apache.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;
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
        DatabaseBean dbaBean = new DatabaseBean();
        LinkedList<ArticleBean> listOfBeans = new LinkedList<ArticleBean>();

        try {
            dbaBean.setPrepStmt(dbaBean.getConn().prepareStatement(
                    "SELECT id, title, url, author, date_time, article_text " +
                            "FROM articles " +
                            "ORDER BY id desc " +
                            "LIMIT 5;"));
        } catch (SQLException SQLEx) {
            logger.fatal("Problem with the SQL in the ArticleProcessBean.getFrontpageArticles() function");
            logger.fatal(SQLEx);
        }

        ResultSet result = dbaBean.executeQuery();

        try {
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
            logger.warn("You had an error mapping objects in ArticleProcessBean.getFrontpageArticles()");
            logger.warn(SQLEx);
        }
        try {
            result.close();
            dbaBean.close();
        } catch (SQLException SQLEx) {
            logger.warn("There was an error closing the resultset and the database connection.");
            logger.warn(SQLEx);
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
        } catch (SQLException SQLEx) {
            logger.fatal("Problem with the SQL in the ArticleProcessBean.getFilteredArticles() function");
            logger.fatal(SQLEx);
        }

        ResultSet result = dbaBean.executeQuery();

        try {
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
        try {
            result.close();
            dbaBean.close();
        } catch (SQLException SQLEx) {
            logger.warn("There was an error closing the resultset and the database connection.");
            logger.warn(SQLEx);
        }
        return listOfBeans;
    }

     /**
     * Get an aricle vy the ID
     *
     * @return a linked list of the articles with the matching id
     */
    public LinkedList<ArticleBean> getArticlesByID() {
        DatabaseBean dbaBean = new DatabaseBean();
        LinkedList<ArticleBean> listOfBeans = new LinkedList<ArticleBean>();

        try {
            dbaBean.setPrepStmt(dbaBean.getConn().prepareStatement("SELECT id, title, url, article_text FROM articles WHERE id = ?;"));
            dbaBean.getPrepStmt().setString(1, articleID);
        } catch (SQLException SQLEx) {
            logger.fatal("Problem with the SQL in the ArticleProcessBean.getArticlesByID() function");
            logger.fatal(SQLEx);
        }

        ResultSet result = dbaBean.executeQuery();

        try {
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
        try {
            result.close();
            dbaBean.close();
        } catch (SQLException SQLEx) {
            logger.warn("There was an error closing the resultset and the database connection.");
            logger.warn(SQLEx);
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
        } catch (SQLException SQLEx) {
            logger.fatal("Problem with the SQL in the ArticleProcessBean.getFilteredArticles() function");
            logger.fatal(SQLEx);
        }

        ResultSet result = dbaBean.executeQuery();

        try {
            while (result.next()) {
                ArticleBean articleBean = new ArticleBean();

                articleBean.setTitle(result.getString("title"));
                articleBean.setURL(result.getString("url"));
                articleBean.setDateTime(result.getTimestamp("date_time"));

                listOfBeans.add(articleBean);
            }
        } catch (SQLException SQLEx) {
            logger.warn("You had an error mapping objects in ArticleProcessBean.getFilteredArticles()");
            logger.warn(SQLEx);
        }
        try {
            result.close();
            dbaBean.close();
        } catch (SQLException SQLEx) {
            logger.warn("There was an error closing the resultset and the database connection.");
            logger.warn(SQLEx);
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
        // transaction   NOT YET!
        // Clear out the old tags
        DatabaseBean tagDBBean = new DatabaseBean();
        try {
            tagDBBean.setPrepStmt(tagDBBean.getConn().prepareStatement("DELETE FROM articletags WHERE id = ?;"));
            tagDBBean.getPrepStmt().setString(1, articleID);
            tagDBBean.executeUpdate();
        } catch (SQLException SQLEx) {
            logger.fatal(SQLEx);
            return 0;
        }
        tagDBBean.close();

        if (articleTags.length == 0) {
            articleTags = new String[0];
        }
        // Handle textbox input
        String[] textBoxToArray = articleOtherTags.split(",");

        // Stop empty tags being inserted
        try {
            if (textBoxToArray[0].equals("")) {          // nothing entered into the box
                textBoxToArray = new String[0];
            }
        } catch (ArrayIndexOutOfBoundsException as) {    // catch if the size is 0 (only happens when string to split is ',' or more only)
            textBoxToArray = new String[0];
        }

        // Now combine the textbox newly created tags with the already existing ones.
        String[] tempTotalTags = new String[textBoxToArray.length + articleTags.length];
        System.arraycopy(textBoxToArray, 0, tempTotalTags, 0, textBoxToArray.length);
        System.arraycopy(articleTags, 0, tempTotalTags, textBoxToArray.length, articleTags.length);
        // Put in the tags
        tagDBBean = new DatabaseBean();
        for (String tag : tempTotalTags) {
            try {
                tagDBBean.setPrepStmt(tagDBBean.getConn().prepareStatement(
                        "INSERT INTO " +
                                "articletags " +
                                "VALUES (?, ?);"));
                tagDBBean.getPrepStmt().setString(1, articleID);
                tagDBBean.getPrepStmt().setString(2, tag.trim());
                tagDBBean.executeUpdate();
            } catch (SQLException SQLEx) {
                logger.fatal(SQLEx);
            }
        }

        // end transation

        return 1;
    }

    /**
     * Delete an article by it's id number. Including associated tags.
     *
     * @return and integer shoing the number of rows affected
     */
    public int getDeleteArticle
            () {
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
        } catch (SQLException SQLEx) {
            logger.fatal("Problem with the SQL in the ArticleProcessBean.getAllAdminArticles() function");
            logger.fatal(SQLEx);
        }

        ResultSet result = dbaBean.executeQuery();

        try {
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
        try {
            result.close();
            dbaBean.close();
        } catch (SQLException SQLEx) {
            logger.warn("There was an error closing the resultset and the database connection.");
            logger.warn(SQLEx);
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
        } catch (SQLException SQLEx) {
            logger.fatal("Problem with the SQL in the ArticleProcessBean.getMatchURLByTitle() function");
            logger.fatal(SQLEx);
        }

        ResultSet result = dbaBean.executeQuery();

        try {
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
        try {
            result.close();
            dbaBean.close();
        } catch (SQLException SQLEx) {
            logger.warn("There was an error closing the resultset and the database connection.");
            logger.warn(SQLEx);
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
        } catch (SQLException SQLEx) {
            logger.fatal("Problem with the SQL in the ArticleProcessBean.getIdOfArticleFromTitle() function");
            logger.fatal(SQLEx);
        }

        ResultSet result = dbaBean.executeQuery();

        try {
            while (result.next()) {
                idOfArticle = Integer.parseInt(result.getString("id"));
            }
        } catch (SQLException SQLEx) {
            logger.warn("You had an error mapping objects in ArticleProcessBean.getIdOfArticleFromTitle()");
            logger.warn(SQLEx);
        }
        try {
            result.close();
            dbaBean.close();
        } catch (SQLException SQLEx) {
            logger.warn("There was an error closing the resultset and the database connection.");
            logger.warn(SQLEx);
        }

        return idOfArticle;
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
}