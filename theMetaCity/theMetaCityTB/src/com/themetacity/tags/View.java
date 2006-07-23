package com.themetacity.tags;
/**
 * @version 1.0
 * @Author: Douglas Miller
 */

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

public class View extends TagSupport {

    /* Variables */
    int picId = 0;
    int maxId = 0;
    String imgSrc = null;
    String altTag = null;

    /* The writer gives access to the page context so its possible to write output */
    JspWriter out = pageContext.getOut();


    public int doEndTag() {
        try {
            out.print("<img src=" + imgSrc + ".jpg alt=" + altTag + " /><br />\n");

            if (picId > 1) {
                out.print("<a href=\"view.jsp?picId=" + (picId - 1) + "\">Previous</a>");
            }

            if (picId < maxId) {
                out.print("<a href=\"view.jsp?picId=" + (picId + 1) + "\">Next</a>");
            }

            if (picId != maxId) {
                out.print("<a href=\"view.jsp?picId=" + (maxId) + "\">Latest</a>");
            }

            if (picId != 1) {
                out.print("<a href=\"view.jsp?picId=" + (1) + "\">First</a>");
            }

        } catch (Exception e) {
            System.out.println("Something broke!");
            System.out.println(e);
        }
        return 0;
    }

    public void release() {
        picId = 0;
        maxId = 0;
        imgSrc = null;
        altTag = null;
    }

    public int getPicId() {
        return picId;
    }

    public void setPicId(int picId) {
        this.picId = picId;
    }

    public int getMaxId() {
        return maxId;
    }

    public void setMaxId(int maxId) {
        this.maxId = maxId;
    }

    public String getImgSrc() {
        return imgSrc;
    }

    public void setImgSrc(String imgSrc) {
        this.imgSrc = imgSrc;
    }

    public String getAltTag() {
        return altTag;
    }

    public void setAltTag(String altTag) {
        this.altTag = altTag;
    }
}