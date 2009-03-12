package com.themetacity.typebeans;

/**
 */
public class ProjectBean {
    private String projectID;
    private String projectURL;
    private String descText;
    private String isValid;

    public String getProjectID() {
        return projectID;
    }

    public void setProjectID(String projectID) {
        this.projectID = projectID;
    }

    public String getProjectURL() {
        return projectURL;
    }

    public void setProjectURL(String projectURL) {
        this.projectURL = projectURL;
    }

    public String getDescText() {
        return descText;
    }

    public void setDescText(String descText) {
        this.descText = descText;
    }

    public String getValid() {
        return isValid;
    }

    public void setValid(String valid) {
        isValid = valid;
    }
}
