package com.themetacity.utilities;

import org.apache.log4j.Logger;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.*;

/**
 * Contains a collection of database access related functions.
 * Including update and execute.
 */
public class DatabaseBean {

    private static final Logger logger = Logger.getLogger(DatabaseBean.class);

    private Connection conn;
    private PreparedStatement prepStmt;

    /**
	 * Setup a connection to the database
	 * The connection specifics are managed in thetomcat server configuratio nand referenced through web.xml and context.xml
     */
    public DatabaseBean() {
        try {
            InitialContext initContext = new InitialContext();
            DataSource ds = (DataSource) initContext.lookup("java:/comp/env/jdbc/theMetaCity");
            conn = ds.getConnection();
        }
        catch (SQLException SQLEx) {
            logger.fatal("There was a problem with the database connection.");
            logger.fatal(SQLEx);
            logger.fatal(SQLEx.getCause());
        }
        catch (NamingException nameEx) {
            logger.fatal("There was a naming exception");
            logger.fatal(nameEx);
            logger.fatal(nameEx.getCause());
        }
    }

    /**
     * Execute a query. Do not use for statements (update, delete, insert etc).
     *
     * @return A ResultSet of the execute query. A set of size zero if no results were returned. It is never null.
     * @see #executeUpdate() for running update, insert delete etc.
     */

    public ResultSet executeQuery() {
        ResultSet result = null;
        try {
            result = prepStmt.executeQuery();
            logger.debug(prepStmt.toString());
        }
        catch (SQLException SQLEx) {
            logger.fatal("There was an error running a query");
            logger.fatal(SQLEx);
        }
        return result;
    }

    /**
     * Execute updates on the server (UPDATE, DELETE etc)
     * Do not use for querires (SELECT)
     *
     * @return an int for the number of rows affected.
     * @see #executeQuery() for queries (SELECT etc)
     */
    public int executeUpdate() {
        int returnValue = 0;

        try {
            returnValue = prepStmt.executeUpdate();
            logger.debug(prepStmt.toString());
        }
        catch (SQLException SQLEx) {
            logger.warn("There was a problem running an update");
            logger.warn(SQLEx);

        }
        return returnValue;
    }

    /**
     * Close the open database connection.
     * This method is called from the process beans as the connections cant be closed before the resultset is returned<br />
     * <p/>
     * This closes both the statment and the connection
     */
    public void close() {
        if (prepStmt != null) {
            try {
                prepStmt.close();
                prepStmt = null;
            } catch (SQLException SQLEx) {
                logger.warn("There was an error closing the prepared statment connection.");
            }
        }

        if (conn != null) {
            try {
                conn.close();
                conn = null;
            } catch (SQLException SQLEx) {
                logger.warn("There was an error closing the database connection.");
            }
        }
    }

    public Connection getConn() {
        return conn;
    }

    public PreparedStatement getPrepStmt() {
        return prepStmt;
    }

    public void setPrepStmt(PreparedStatement prepStmt) {
        this.prepStmt = prepStmt;
    }
}