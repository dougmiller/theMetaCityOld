package com.themetacity.beans;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Contains a collection of database access related functions.
 */
public class DatabaseAccessBean implements Serializable {

    /**
     * Empty constructor
     */
    public DatabaseAccessBean() {
    }

    /**
     * Make a connection to the database and executes an SQL query.
     *
     * @param SQLStatementToExecute The SQL statement to execute, as a string.
     * @return A ResultsSet of the result of the executed SQL query.
     * @throws NamingException
     * @throws SQLException
     */
    public ResultSet executeQuery(String SQLStatementToExecute) throws NamingException, SQLException {

        // NOTE *The following three lines are used to acces a connection
        // defined in the deployment descriptor.
        // Either use these or the fourth line, but not both.*
        InitialContext initContext = new InitialContext();
        Context envContext = (Context) initContext.lookup("java:/comp/env");
        DataSource ds = (DataSource) envContext.lookup("jdbc/theMetaCity");

        // Defined connection here rather than in the deployment descriptor
        //DataSource ds = ""

        // Local variables
        Connection conn;                // The connection to the database
        PreparedStatement prepStmt;     // The statment to execute
        ResultSet rs = null;            // The results of the executed statment

        // Now try to execute the SQL statement
        conn = ds.getConnection();                                  // Get a connection to the database
        prepStmt = conn.prepareStatement(SQLStatementToExecute);    // Build a prepared stement to make things run faster, which can be put into cache
        rs = prepStmt.executeQuery();                               // Execute the statement and put the return into a RS
        conn.close();                                               // Close the connection so it can be returned to the pool

        // Return the result set
        return rs;
    }

    /**
     * Constructs a statement out of each part of the query into valid SQL.
     *
     * @return The a constructed statment.
     */
    //todo Make this work. Integrate this into the model, somehow.
    public String constructStatment() {
        return "";
    }
}



