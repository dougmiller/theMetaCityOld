package com.themetacity.beans;

import com.themetacity.typebeans.ArchiveEntryBean;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

public class ArchiveProcessBean {

    public LinkedList getArchive() {
        DatabaseBean dbBean = new DatabaseBean();
        LinkedList<ArchiveEntryBean> listOfBeans = new LinkedList<ArchiveEntryBean>();    // The list of populated beans.

        try {
            ResultSet result = dbBean.executeQuery("SELECT author, title, datetime FROM articles;");
            
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
}
