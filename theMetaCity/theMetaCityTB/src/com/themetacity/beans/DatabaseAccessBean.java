package com.themetacity.beans;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.io.Serializable;

/**
 * Contains a collection of database access related functions.
 */
public class DatabaseAccessBean  implements Serializable {

    public DatabaseAccessBean() {
    }

    /**
     * Make a connection to the database and executes an SQL query.
     *
     * @param SQLStatementToExecute The SQL statement to execute as a string.
     * @return A ResultsSet of the result of the executed SQL query.
     */
    public ResultSet executeQuery(String SQLStatementToExecute) throws NamingException {

        // NOTE *The following three lines are used to acces a connection defined in the deployment descriptor
        // Either use these or the fourth line, but not both.*
        InitialContext initContext = new InitialContext();
        Context envContext = (Context) initContext.lookup("java:/comp/env");
        DataSource ds = (DataSource) envContext.lookup("jdbc/theMetaCity");

        // Defined connection here rather than in the deployment descriptor
        //DataSource ds =

        // Local variables
        Connection conn; // The connection to the database
        Statement stmt;  // The statment to execute
        ResultSet rs = null;    // The results of the executed statment

        // Now try to execute the SQL statement
        try {
            conn = ds.getConnection();  // Get a connection to the database
            stmt = conn.createStatement();
            rs = stmt.executeQuery(SQLStatementToExecute);  // Execute the statement and put the return into a RS

        } catch (SQLException SQLEx) {
            System.out.println("There was an error with your SQL.");
            System.out.println(SQLEx);
        }
        return rs;
    }

    /**
     * Constructs a statement out of each part of the query into valid SQL.
     * @return The a constructed statment.
     */
    public String constructStatment(){
        return "";
    }
}



