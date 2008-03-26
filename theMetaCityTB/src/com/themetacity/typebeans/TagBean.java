package com.themetacity.typebeans;

/**
 * The bean that represents a metadata tag (eg articles about computers or python).
 *
 * @Author Douglas Miller
 * @Date 11 Nov 2007
 */
public class TagBean {

    // Variables that are in this object
    private String tag;
    private int numTimesTagUsed;

    // Zero argument constructor
    // Generic setters for the variables
    public TagBean() {
        tag = "Space";
        numTimesTagUsed = 2;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public int getNumTimesTagUsed() {
        return numTimesTagUsed;
    }

    public void setNumTimesTagUsed(int numTimesTagUsed) {
        this.numTimesTagUsed = numTimesTagUsed;
    }
}
