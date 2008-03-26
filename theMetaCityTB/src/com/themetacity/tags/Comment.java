package com.themetacity.tags;

import com.themetacity.typebeans.CommentBean;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;

import org.apache.log4j.Logger;

/**
 * This is the custom tag that formats a CommentBean into readable format. It is called in JSP pages.
 */
public class Comment extends SimpleTagSupport {

    private CommentBean commentStub = new CommentBean();

    static Logger logger = Logger.getLogger(Comment.class);


    // Start processing
    public void doTag() {
        // Must initialse this here as the context will not be available before this time.
        JspWriter out = getJspContext().getOut();
        try {
            out.println(commentStub.getName());
            out.println(commentStub.getContact());
            out.println(commentStub.getDateTime());
            out.println(commentStub.getComment());
        } catch (IOException IOEx) {
            logger.warn("There was an Error");
            logger.warn(IOEx);
        }
    }

    // Free the Article used
    public void release() {
        commentStub = null;
    }

    public CommentBean getCommentStub() {
        return commentStub;
    }

    public void setCommentStub(CommentBean commentStub) {
        this.commentStub = commentStub;
    }
}
