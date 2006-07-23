package com.themetacity.tags;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * User: doug
 * Date: Sep 1, 2005
 */
public class Error extends TagSupport {
    int errorNum = 0;
    int [] errorList = {403, 404};


    /* The writer gives access to the page context so its possible to write output */
    JspWriter out = pageContext.getOut();

    public int doEndTag() {
        try {
            System.out.println("<div class=\"errortitle\">" + errorNum + "</span><br />");

            switch (errorNum) {

                case 1:
                    //This should be handled to a redirect anyway so its a bit redundant
                    out.println("Page is forbidden");
                    break;
                case 2:
                    out.println("Page is not found");
                    out.println("The page you were looking for does not exist.");
                    out.println("This is YOUR FAULT. MY SITE IS PERFECT, YOU BROKE IT!");
                    out.println("Well it probably is my fault.");
                    break;
            }


        } catch (Exception e) {
            System.out.println("Something broke!");
            System.out.println(e);
        }
        return 0;
    }

    public void release() {
        errorNum = 0;
    }

    public int getErrorNum() {
        return errorNum;
    }

    public void setErrorNum(int errorNum) {
        this.errorNum = errorNum;
    }

    public int[] getErrorList() {
        return errorList;
    }

    public void setErrorList(int[] errorList) {
        this.errorList = errorList;
    }

}
