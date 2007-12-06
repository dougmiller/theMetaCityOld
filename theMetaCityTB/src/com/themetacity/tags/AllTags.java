package com.themetacity.tags;

import com.themetacity.typebeans.TagBean;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;
import java.util.LinkedList;

/**
 * Displays all the tags in a nice formatted way
 */
public class AllTags extends SimpleTagSupport {

    // This tag takes a list of tag beans rather then an individual tag.
    // This makes formatting much easier.
    LinkedList<TagBean> tagsList = new LinkedList<TagBean>();

    public void doTag() throws JspException, IOException {
        JspWriter out = getJspContext().getOut();
        try {

            for (TagBean tag : tagsList) {
                out.println(buildLink(tag) + " ");
            }

        } catch (IOException IOex) {
            System.out.println("There was an error in the AllTag bean.");
        }
    }

    public void release() {
        tagsList = null;
    }

    private String buildLink(TagBean tag){
        return "<a href=\"/tags.jsp?tag=" + tag.getTag() + "\">" + tag.getTag() + "(" + tag.getNumTimesTagUsed()+ ")</a>";
    }

    public LinkedList<TagBean> getTagsList() {
        return tagsList;
    }

    public void setTagsList(LinkedList<TagBean> tagsList) {
        this.tagsList = tagsList;
    }
}
