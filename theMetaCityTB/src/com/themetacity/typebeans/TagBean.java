package com.themetacity.typebeans;

/**
 * The bean that represents a metadata tag (eg articles about computers or python).
 */
public class TagBean {

    // Variables that are in this object
    private String tag;
    private int numTimesTagUsed;

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
