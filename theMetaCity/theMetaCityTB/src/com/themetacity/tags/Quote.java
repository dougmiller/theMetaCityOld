package com.themetacity.tags;

import javax.servlet.jsp.JspContext;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;

/**
 * This tag generates formatted quotes.
 * //@param quoteAuthor The person who made the quote
 */
public class Quote extends SimpleTagSupport {

    private String quoteAuthor = null;

    public void doTag() throws JspException, IOException {
        //todo figure out whats going on here with the context
        JspContext jspContext = getJspContext();

        // Get the page context so output can be written to it.
        JspWriter out = jspContext.getOut();

        // Begin outputting content
        out.println("<div class=\"quote\">");
        out.println("<span class=\"quotetext\">");
        //todo get the body and insert it here
        //jspContext.
        //out.println(quoteText);
        out.println("<hr />");
        out.println("<div class=\"authorposition\">");
        //todo is this the right way to do this?
        out.println("<span class=\"quoteauthor\">" + quoteAuthor + "</span>");
        out.println("</div>");
        out.println("</div>");
    }

    public void setQuoteAuthor(String quoteAuthor) {
        this.quoteAuthor = quoteAuthor;
    }

    public void release() {
        quoteAuthor = null;
    }
}
