package com.themetacity.beans;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseAccessBean {

    public DatabaseAccessBean() {
    }

    /**
     * Make a connection to the database and executes an SQL query.
     *
     * @param SQLStatementTotExecute The SQL statement to execute as a string.
     * @return List as a ResultsSet of the result of the executed SQL query.
     */
    public ResultSet getNewsResults(String SQLStatementTotExecute) throws NamingException {

        // The following three lines are used to acces a prepared connection defined in the deployment descriptor
        // Either use these or the fourth line, but not both
        Context initContext = new InitialContext();
        Context envContext = (Context) initContext.lookup("java:/comp/env");
        DataSource ds = (DataSource) envContext.lookup("jdbc/theMetaCity");

        // Defined connection here rather than in the deployment descriptor
        //DataSource ds =

        // Loca variables
        Connection conn = null; // The connection to the database
        Statement stmt = null;  // The statment to execute
        ResultSet rs = null;    // The results of the executed statment

        // Now try to execute the SQL statement
        try {
            conn = ds.getConnection();  // Get a connection to the database
            stmt = conn.createStatement();
            rs = stmt.executeQuery(SQLStatementTotExecute);

        } catch (SQLException e) {
            System.out.println("Ther was an error with your SQL.");
            System.out.println(e);
        }

        finally {
            try {
                //if (rs != null) rs.close();
            } catch (Exception e) {
                /* There was an exception */
                System.out.println("The ResultSet failed to close.");
                System.out.println(e);
            }
            try {
                //if (stmt != null) stmt.close();
            } catch (Exception e) {
                /* There was an exception */
                System.out.println("The statement failed to close.");
                System.out.println(e);
            }
            /* Check if the connection is closed and if it isnt then close it */
            try {
                //if (conn != null) conn.close();
            } catch (Exception e) {
                /* There was an exception */
                System.out.println("The connection failed to close.");
                System.out.println(e);
            }

        }
        return rs;
    }
}



