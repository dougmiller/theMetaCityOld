package com.themetacity.admintags;

import com.themetacity.typebeans.TagBean;

import java.util.LinkedList;
import java.io.IOException;

import org.apache.log4j.Logger;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;

public class ArticleTags extends SimpleTagSupport {
    // This tag takes a list of tag beans rather then an individual tag.
    // This makes formatting much easier.
    private LinkedList<TagBean> tagsList;
    private LinkedList<TagBean> usedTagsList = new LinkedList<TagBean>();  // determine what to check and what not

    private static final Logger logger = Logger.getLogger(ArticleTags.class);

    public void doTag() throws JspException, IOException {
        JspWriter out = getJspContext().getOut();
        try {
            String checked = "";
            out.println("<div>");
            for (TagBean tag : tagsList) {
                for (TagBean usedTag : usedTagsList) {
                    if (usedTag.getTag().equals(tag.getTag())) {
                        checked = "CHECKED";
                        break;
                    }
                }
                out.println("<input type=\"checkbox\" name=\"tag\" value=\"" + tag.getTag() + "\" " + checked + "/>" + tag.getTag());
                checked = "";
            }
            out.println("</div>");
        } catch (IOException IOEx) {
            logger.warn("There was an error in the Admin ArticleTags.");
            logger.warn(IOEx);
        }
    }

    public LinkedList<TagBean> getTagsList() {
        return tagsList;
    }

    public void setTagsList(LinkedList<TagBean> tagsList) {
        this.tagsList = tagsList;
    }

    public LinkedList<TagBean> getUsedTagsList() {
        return usedTagsList;
    }

    public void setUsedTagsList(LinkedList<TagBean> usedTagsList) {
        this.usedTagsList = usedTagsList;
    }
}
