package com.themetacity.tags.blog;

import com.themetacity.typebeans.TagBean;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;
import java.util.LinkedList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Displays all the tags in a nice formatted way
 */
public class AllTags extends SimpleTagSupport {

    // This tag takes a list of tag beans rather then an individual tag.
    // This makes formatting much easier.
    private LinkedList<TagBean> tagsList;
    private Integer highestVal = 0;
    private Integer highVal = 0;
    private Integer avgVal = 0;
    private Integer lowVal = 0;
    private Integer lowestVal = 0;

    private static final Logger logger = LogManager.getLogger(AllTags.class);

    public void doTag() throws JspException, IOException {
        JspWriter out = getJspContext().getOut();

        for (TagBean tag : tagsList) {
            avgVal = avgVal + tag.getNumTimesTagUsed();
            if (tag.getNumTimesTagUsed() > highestVal) {
                highestVal = tag.getNumTimesTagUsed();
            }

            if (tag.getNumTimesTagUsed() == 0) {  // Set initial low value
                lowVal = tag.getNumTimesTagUsed();
            }

            if (tag.getNumTimesTagUsed() < lowestVal) {
                lowestVal = tag.getNumTimesTagUsed();
            }
        }

        avgVal = avgVal / tagsList.size();
        highVal = ((highestVal - avgVal) / 2) + avgVal;
        lowVal = ((avgVal - lowestVal) / 2) + lowestVal;

        try {

            for (TagBean tag : tagsList) {
                out.println(buildLink(tag) + " ");
            }

        } catch (IOException IOEx) {
            logger.warn("There was an error in the AllTag bean.");
            logger.warn(IOEx);
        }
    }

    private String buildLink(TagBean tag) {
        return "<a rel=\"tag\" class=\"" + cloudNodeClassSelector(tag) + "\" href=\"/blog/tags/" + tag.getTag() + "\">" + tag.getTag() + "</a>";
    }

    private String cloudNodeClassSelector(TagBean tag) {
        if (tag.getNumTimesTagUsed() == highestVal) {
            return "tagCloudHighest";
        } else if (tag.getNumTimesTagUsed() >= highVal) {
            return "tagCloudHigh";
        } else if (tag.getNumTimesTagUsed() >= avgVal) {
            return "tagCloudMid";
        } else if (tag.getNumTimesTagUsed() >= lowVal) {
            return "tagCloudLow";
        } else if (tag.getNumTimesTagUsed() >= lowestVal) {
            return "tagCloudLowest";
        }
        return "tagCloutMid";
    }

    public LinkedList<TagBean> getTagsList() {
        return tagsList;
    }

    public void setTagsList(LinkedList<TagBean> tagsList) {
        this.tagsList = tagsList;
    }
}
