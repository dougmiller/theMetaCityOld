package com.themetacity.tags;

import com.themetacity.typebeans.TagBean;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;
import java.util.LinkedList;

import org.apache.log4j.Logger;

/**
 * Displays all the tags in a nice formatted way
 */
public class AllTags extends SimpleTagSupport {

    // This tag takes a list of tag beans rather then an individual tag.
    // This makes formatting much easier.
    private LinkedList<TagBean> tagsList;

    private static final Logger logger = Logger.getLogger(AllTags.class);

    public void doTag() throws JspException, IOException {
        JspWriter out = getJspContext().getOut();
        try {

            for (TagBean tag : tagsList) {
                out.println(buildLink(tag) + " ");
            }

        } catch (IOException IOEx) {
            logger.warn("There was an error in the AllTag bean.");
            logger.warn(IOEx);
        }
    }

    private String buildLink(TagBean tag){
        return "<a href=\"/tags/" + tag.getTag() + "\">" + tag.getTag() + "(" + tag.getNumTimesTagUsed()+ ")</a>";
    }

    public LinkedList<TagBean> getTagsList() {
        return tagsList;
    }

    public void setTagsList(LinkedList<TagBean> tagsList) {
        this.tagsList = tagsList;
    }
}
