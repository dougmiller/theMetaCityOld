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
    private String title = null;
    private String author = "";
    private String articleText = "";
    private String[] articleTags;
    private String articleOtherTags;
    private String year = null;
    private String month = null;
    private String day = null;
    private String searchTag = "";

    private static final Logger logger = Logger.getLogger(ArticleProcessBean.class);

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
                // Make a new ArticleBeanTest that represents one article
                ArticleBean articleBean = new ArticleBean();
                // Set the properties of the bean
                try {
                    articleBean.setArticleID(result.getString("id"));
                } catch (SQLException SQLEx) {
                    //munch
                }
                try {
                    articleBean.setAuthor(result.getString("author"));
                } catch (SQLException SQLEx) {
                    //munch
                }
                try {
                    articleBean.setTitle(result.getString("title"));
                } catch (SQLException SQLEx) {
                    //munch
                }
                try {
                    articleBean.setArticleText(result.getString("article_text"));
                } catch (SQLException SQLEx) {
                    //munch
                }
                try {
                    articleBean.setDateTime(result.getTimestamp("date_time"));
                } catch (SQLException SQLEx) {
                    //munch
                }

                // Process this articles tags
                try {
                    TagProcessBean tagProcessBean = new TagProcessBean();
                    tagProcessBean.setArticleID("" + result.getInt("id"));     // Set the user in the TagProcessBean *cast to String*
                    articleBean.setTags(tagProcessBean.getArticleTags());             // Assign the results to the bean
                } catch (SQLException SQLEx) {
                    logger.warn(SQLEx);
                }

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
     * @return a linked list fo the articles
     */
    public LinkedList<ArticleBean> getFrontpageArticles() {
        try {
            dbaBean.setPrepStmt(dbaBean.getConn().prepareStatement("SELECT id, title, author, date_time, article_text FROM articles ORDER BY id desc LIMIT 5;"));
        } catch (SQLException SQLEx) {
            logger.fatal(SQLEx);
        }
        return processQuery();
    }

    /**
     * Filter the articles according to date or title or both
     *
     * @return a linked list of filtered articles
     */
    public LinkedList<ArticleBean> getFilteredArticles() {
        try {
            ArticleBean articleBean = new ArticleBean();
            dbaBean.setPrepStmt(dbaBean.getConn().prepareStatement("SELECT * FROM articles WHERE " +
                    "(YEAR(date_time) = ? OR ? is null) " +
                    "AND (MONTH(date_time) = ? OR ? is null) AND " +
                    "(DAY(date_time) = ? OR ? is null) AND " +
                    "(title = ? OR ? is null) " +
                    "ORDER BY id desc;"));
            dbaBean.getPrepStmt().setString(1, year);
            dbaBean.getPrepStmt().setString(2, year);
            dbaBean.getPrepStmt().setString(3, month);
            dbaBean.getPrepStmt().setString(4, month);
            dbaBean.getPrepStmt().setString(5, day);
            dbaBean.getPrepStmt().setString(6, day);
            dbaBean.getPrepStmt().setString(7, articleBean.extractTitle(title));
            dbaBean.getPrepStmt().setString(8, articleBean.extractTitle(title));
        } catch (SQLException
                SQLEx) {
            logger.fatal(SQLEx);
        }
        return processQuery();
    }

    /**
     * Return all the articles posted under the given tag
     *
     * @return A linked list of articles
     */
    public LinkedList<ArticleBean> getArticlesWithTag() {
        try {
            dbaBean.setPrepStmt(dbaBean.getConn().prepareStatement("SELECT * FROM articles, articletags " +
                    "WHERE articles.id = articletags.id " +
                    "AND articletags.tag = ? " +
                    "ORDER BY articletags.id desc"));
            dbaBean.getPrepStmt().setString(1, searchTag);

        } catch (SQLException SQLEx) {
            logger.fatal(SQLEx);
        }
        return processQuery();
    }

    /**
     * Get an aricle vy the ID
     *
     * @return a linked list of the articles with the matching id
     */
    public LinkedList<ArticleBean> getArticlesByID() {
        try {
            dbaBean.setPrepStmt(dbaBean.getConn().prepareStatement("SELECT id, title, article_text FROM articles WHERE id = ?;"));
            dbaBean.getPrepStmt().setString(1, articleID);

        } catch (SQLException SQLEx) {
            logger.fatal(SQLEx);
        }
        return processQuery();
    }

    /**
     * Add an article
     *
     * @return True from succesfully adding a new article or fasle for not adding it
     */
    public int getAddArticle() {
        // add the article
        try {
            dbaBean.setPrepStmt(dbaBean.getConn().prepareStatement("INSERT INTO articles (author, title, article_text, date_time) VALUES (?, ?, ?, now());"));
            dbaBean.getPrepStmt().setString(1, author);
            dbaBean.getPrepStmt().setString(2, title);
            dbaBean.getPrepStmt().setString(3, articleText);
        } catch (SQLException SQLEx) {
            logger.fatal(SQLEx);
        }

        int articleResult = dbaBean.executeUpdate();

        // now add the tags
        dbaBean = new DatabaseBean();

        // get the id of the article just added
        try {
            dbaBean.setPrepStmt(dbaBean.getConn().prepareStatement("SELECT * FROM articles WHERE title = ?;"));
            dbaBean.getPrepStmt().setString(1, title);
        } catch (SQLException SQLEx) {
            logger.fatal(SQLEx);
        }

        LinkedList<ArticleBean> articleWithID = processQuery();

        for (ArticleBean article : articleWithID) {
            articleID = article.getArticleID();
        }

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

    private int updateArticleTags() {
        // transaction
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

        // Handle textbox input        
        String[] textBoxToArray = articleOtherTags.split(",");
        String[] tempTotalTags = new String[textBoxToArray.length + articleTags.length];
        System.arraycopy(textBoxToArray, 0, tempTotalTags, 0, textBoxToArray.length);
        System.arraycopy(articleTags, 0, tempTotalTags, textBoxToArray.length, articleTags.length);

        // Put in new check box ones
        tagDBBean = new DatabaseBean();
        for (String tag : tempTotalTags) {
            try {
                tagDBBean.setPrepStmt(tagDBBean.getConn().prepareStatement("INSERT INTO articletags VALUES (?, ?);"));
                tagDBBean.getPrepStmt().setString(1, articleID);
                tagDBBean.getPrepStmt().setString(2, tag.trim());
                tagDBBean.executeUpdate();
            } catch (SQLException SQLEx) {
                logger.fatal(SQLEx);
                return 0;
            }
        }
        tagDBBean.close();

        // end transation

        return 1;
    }

    /**
     * Delete an article by it's id number
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
        return processUpdate();
    }

    /**
     * Get all the articles so for the edit/delete list in the admin app
     *
     * @return a lined list containg all the articles in ArticleBean format
     */
    public LinkedList<ArticleBean> getAllAdminArticles() {
        try {
            dbaBean.setPrepStmt(dbaBean.getConn().prepareStatement("SELECT id, title, article_text, date_time FROM articles GROUP BY id ASC;"));
        } catch (SQLException SQLEx) {
            logger.fatal(SQLEx);
        }
        return processQuery();
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        if (!title.equals("")) {
            this.title = title.trim();
        }
    }

    public String getSearchTag() {
        return searchTag;
    }

    public void setSearchTag(String searchTag) {
        this.searchTag = searchTag;
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

    public String getArticleID() {
        return articleID;
    }

    public void setArticleID(String articleID) {
        this.articleID = articleID;
    }

    public String[] getArticleTags() {
        return articleTags;
    }

    public void setArticleTags(String[] articleTags) {
        this.articleTags = articleTags;
    }

    public String getArticleOtherTags() {
        return articleOtherTags;
    }

    public void setArticleOtherTags(String articleOtherTags) {
        this.articleOtherTags = articleOtherTags;
    }
}