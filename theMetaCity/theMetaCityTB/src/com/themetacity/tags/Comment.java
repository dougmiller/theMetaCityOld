package com.themetacity.tags;
/**
 *
 */

import com.themetacity.typebeans.CommentBean;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;

/**
 * This is the custom tag that formats a CommentBean into readable format. It is called in JSP pages.
 */
public class Comment extends TagSupport {

    // Variables
    private CommentBean commentStub = new CommentBean();

    // The writer gives access to the page context so its possible to write output
    JspWriter out;

    // Start processing
    public void doTag() {
        // Must initialse this here as the context will not be available before this time.
        out = pageContext.getOut();
        try {
            out.println(commentStub.getName());
            out.println(commentStub.getContact());
            out.println(commentStub.getDate().toString());
            out.println(commentStub.getComment());
        } catch (IOException IOEx) {
            System.out.print("There was an Error");
            System.out.print(IOEx);
        }
    }

    // Free the Article used
    public void release() {
        commentStub = null;
    }

    public void setNewsArticle(CommentBean commentStub) {
        this.commentStub = commentStub;
    }

    public CommentBean getCommentBean() {
        return commentStub;
    }
}
